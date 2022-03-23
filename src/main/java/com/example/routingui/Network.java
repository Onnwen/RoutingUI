package com.example.routingui;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class Network {
    private Device mainDevice;
    private final Device[] devices;
    private final int authorizedMaximumLinkPerDevice = 10;

    public Network(Device[] devices) {
        this.devices = devices;
        if (devices.length > 0) {
            Device mainDevice = devices[0];
            for(Device device: devices) {
                if (device.getLinkedRoutes().length > mainDevice.getLinkedRoutes().length) {
                    mainDevice = device;
                }
            }
            this.mainDevice = mainDevice;
        }
    }

    public void print() {
        System.out.println("Selezionare il metodo d'input:");
        System.out.println("[1] Esplora rete");
        System.out.println("[2] Inserimento UUID manuale");
        if (Read.getInt(1, 2) == 1) {
            exploreNetwork(mainDevice, new Device[2]);
        }
        else {
            userInputByUUID();
        }
    }

    public void setMainDevice(Device mainDevice) {
        this.mainDevice = mainDevice;
    }

    public Device[] getDevices() {
        return devices;
    }

    public Route[] getRoutes() {
        Route[] routes = new Route[devices.length*authorizedMaximumLinkPerDevice];
        int totalRoutes = 0;

        for(Device device:devices) {
            for(Route deviceRoute: device.getLinkedRoutes()) {
                boolean add = true;
                for(Route route:routes) {
                    if (route != null && route.sameAs(deviceRoute)) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    routes[totalRoutes] = deviceRoute;
                    totalRoutes++;
                }
            }
        }

        return Arrays.copyOf(routes, totalRoutes);
    }

    public Device getDevice(UUID uuid) {
        for(Device device:getDevices()) {
            if (device.getId().toString().equals(uuid.toString())) {
                return device;
            }
        }
        return null;
    }

    private void userInputByUUID() {
        Device one = null;
        System.out.println("Digitare l'UUID del primo dispositivo:");
        do {
            one = getDevice(Read.getUUID());
            if (one == null) {
                System.out.println("\uDBC0\uDD84 Il dispositivo non è stato trovato.");
            }
        } while (one == null);

        Device two = null;
        System.out.println("Digitare l'UUID del secondo dispositivo:");
        do {
            two = getDevice(Read.getUUID());
            if (two == null) {
                System.out.println("\uDBC0\uDD84 Il dispositivo non è stato trovato.");
            }
        } while (two == null);

        estimateRoute(one, two, 100).print();
    }

    private void exploreNetwork(Device device, Device[] selectedDevices) {
        Route[] routes = device.getLinkedRoutes();

        System.out.println("-------------------------------------------");
        System.out.println("\uDBC1\uDE57 " + device.getId().toString());
        System.out.println("Connesso a:");

        for(int i=0; i<device.getLinkedRoutes().length; i++) {
            System.out.println("[" + (i+1) + "] \uDBC1\uDE57 " + routes[i].getNextDevice(device).getId().toString() + " \uDBC3\uDC11 \uDBC0\uDE64 " + routes[i].getId().toString());
        }

        System.out.println("Digitare l'indice di un dispositivo per mostrare i dispositivi ai quali è connesso oppure digitare l'indice preceduto da un meno per selezionare il dispositivo come estremità del percorso:");
        int input = Read.getInt(-routes.length, routes.length);
        if (input != 0) {
            if (input < 0) {
                if (selectedDevices[0] == null) {
                    selectedDevices[0] = routes[input*-1 - 1].getNextDevice(device);
                    exploreNetwork(routes[input*-1 - 1].getNextDevice(device), selectedDevices);
                }
                else {
                    selectedDevices[1] = routes[input*-1 - 1].getNextDevice(device);
                    estimateRoute(selectedDevices[0], selectedDevices[1], 100).print();
                }
            }
            else {
                exploreNetwork(routes[input - 1].getNextDevice(device), selectedDevices);
            }
        }
        else {
            System.out.println("-------------------------------------------");
        }
    }

    public RoutesFound estimateRoute(Device sendingDevice, Device receivingDevice, int maxHops) {
        final int totalEstimatedRoute = 100;
        RoutesFound routesFound = new RoutesFound(totalEstimatedRoute);

        Instant start = Instant.now();

        if (sendingDevice.sameAs(receivingDevice)) {
            System.out.println("\uDBC0\uDD84 I dispositivi di partenza e di arrivo combaciano.");
        }
        else {
            do {
                routesFound.addFoundRoute(new EstimatedRoute(sendingDevice, receivingDevice, this, routesFound.getRoutesToAvoid(), maxHops));
            } while(routesFound.getLast().validRoute());
        }

        Instant end = Instant.now();

        routesFound.setCalculationTime(Duration.between(start, end).toMillis());
        return routesFound;
    }

    public static Network generate(int totalDevices, int maxLinkPerDevice) {
        Device[] generatedDevices = new Device[totalDevices];
        for(int i=0; i<totalDevices; i++) {
            generatedDevices[i] = new Device(UUID.randomUUID());
        }

        for(Device generatedDevice: generatedDevices) {
            for(int i=0; i<Read.getRandom(1, maxLinkPerDevice); i++) {
                Device foundDevice = generatedDevices[Read.getRandom(0, totalDevices - 1)];
                do {
                    foundDevice = generatedDevices[Read.getRandom(0, totalDevices - 1)];
                } while(foundDevice.sameAs(generatedDevice) || foundDevice.alreadyLinked(foundDevice));
                generatedDevice.addRoute(new Route(UUID.randomUUID(), Read.getRandom(0, 100), generatedDevice, foundDevice));
            }
        }
        return new Network(generatedDevices);
    }

    public void printFileString() {
        for(Route route: getRoutes()) {
            System.out.println(route.getId() + ";" + route.getCost() + ";" + route.getOne().getId() + ";" + route.getTwo().getId());
        }
    }
}
