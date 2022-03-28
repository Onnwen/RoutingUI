
package com.onnwencassitto.routingui;

import java.util.Arrays;

public class EstimatedRoute {
    private Device startingPoint;
    private final Device arrivingDevice;
    private double cost;
    private int hops;

    private int maximumHops;
    private final int maximumCost;
    private final int authorizedMaximumHops;
    private int attempt;

    public EstimatedRoute(Device sendingDevice, Device arrivingDevice, Network net, Route[] routesToAvoid, int authorizedMaximumHops) {
        this.startingPoint = sendingDevice.copy();
        this.arrivingDevice = arrivingDevice;
        this.maximumHops = 0;
        this.maximumCost = 0;
        this.authorizedMaximumHops = authorizedMaximumHops;
        this.attempt = 1;
        this.cost = 0;
        this.hops = 0;

        PathNetwork network = new PathNetwork(net);
        network.setMainDevice(sendingDevice);

        for(Route routeToAvoid: routesToAvoid) {
            network.addGlobalRouteToAvoid(routeToAvoid);
        }

        do {
            reset();
            resetStartingPoint();
            network.resetRoutesToAvoid();
            maximumHops++;
            checkLink(this, arrivingDevice.copy(), network);
        } while (canProceed() && !startingPoint.equals(arrivingDevice));
    }

    public Route[] getRoutes() {
        Route[] routes = new Route[100];
        int totalRoutes = 0;

        if (startingPoint.getLinkedRoutes().length != 0) {
            Route currentCheckingRoute = startingPoint.getLinkedRoutes()[0];
            while (currentCheckingRoute != null && currentCheckingRoute.linked()) {
                if (currentCheckingRoute.getNextDevice(currentCheckingRoute.getOne()).getLinkedRoutes().length == 0) {
                    break;
                }
                routes[totalRoutes] = currentCheckingRoute;
                totalRoutes++;
                currentCheckingRoute = currentCheckingRoute.getNextDevice(currentCheckingRoute.getOne()).getLinkedRoutes()[0];
            }
            routes[totalRoutes] = currentCheckingRoute;
            totalRoutes++;
            return Arrays.copyOf(routes, totalRoutes);
        }
        return null;
    }

    public Device[] getDevices() {
        Device[] devices = new Device[100];
        int totalDevices = 0;

        if (startingPoint.getLinkedRoutes().length != 0) {
            Device currentCheckingDevice = startingPoint;
            while (currentCheckingDevice.getLinkedRoutes().length > 0 && currentCheckingDevice.getLinkedRoutes()[0] != null && currentCheckingDevice.getLinkedRoutes()[0].linked()) {
                devices[totalDevices] = currentCheckingDevice;
                totalDevices++;
                currentCheckingDevice = currentCheckingDevice.getLinkedRoutes()[0].getNextDevice(currentCheckingDevice);
            }
            devices[totalDevices] = currentCheckingDevice;
            devices[totalDevices] = currentCheckingDevice;
            totalDevices++;
            return Arrays.copyOf(devices, totalDevices);
        }
        return null;
    }

    private EstimatedRoute checkLink(EstimatedRoute currentRoute, Device arrivingDevice, PathNetwork network) {
        Device networkLastDevice = network.getNetworkDevice(currentRoute.getLastDevice());

        if (currentRoute.getLastDevice().sameAs(arrivingDevice)) {
            return currentRoute;
        }

        for(Route deviceRoute:networkLastDevice.getLinkedRoutes()) {
            if (!network.needToAvoid(deviceRoute)) {
                Device nextDevice = deviceRoute.getNextDevice(networkLastDevice);
                if (nextDevice.sameAs(arrivingDevice)) {
                    addHop(deviceRoute, nextDevice);
                    return currentRoute;
                }
            }
        }

        for(Route deviceRoute:networkLastDevice.getLinkedRoutes()) {
            if (!network.needToAvoid(deviceRoute) && deviceRoute.getNextDevice(networkLastDevice) != null) {
                if (addHop(deviceRoute, deviceRoute.getNextDevice(networkLastDevice))) {
                    network.addRouteToAvoid(deviceRoute);

                    if (currentRoute.getLastDevice().sameAs(arrivingDevice)) {
                        return currentRoute;
                    }

                    checkLink(currentRoute, arrivingDevice, network);
                }
                else {
                    break;
                }
            }
        }

        if (getLastDevice().sameAs(startingPoint)) {
            return null;
        }
        else {
            removeLastHop();
            return checkLink(currentRoute, arrivingDevice, network);
        }
    }

    public boolean addHop(Route route, Device device) {
        attempt++;
        if (canProceed()) {
            getLastDevice().addRoute(route.copy());
            getLastRoute().setOne(getLastDevice());
            getLastRoute().setTwo(device.copy());
            incrementHops();
            increaseCost(route.getCost());
            return true;
        }
        return false;
    }

    public void reset() {
        resetStartingPoint();
        this.cost = 0;
        this.hops = 0;
    }

    public void resetStartingPoint() {
        while (getLastDevice() != startingPoint) {
            removeLastRoute();
        }
    }

    public void removeLastHop() {
        attempt++;
        decreaseCost(getLastRoute().getCost());
        decreaseHops();
        removeLastRoute();
    }

    public void removeLastDevice() {
        if (getLastRoute() != null) {
            getLastRoute().removeTwo();
        }
    }

    public void removeLastRoute() {
        if (getLastRoute() != null) {
            getLastRoute().getOne().resetRoutes();
        }
    }

    public Device getLastDevice() {
        Device currentDevice = startingPoint;
        while (currentDevice.getLinkedRoutes().length > 0 && currentDevice.getLinkedRoutes()[0] != null && currentDevice.getLinkedRoutes()[0].linked()) {
            currentDevice = currentDevice.getLinkedRoutes()[0].getNextDevice(currentDevice);
        }
        return currentDevice;
    }

    public boolean validRoute() {
        return getLastDevice().sameAs(arrivingDevice);
    }

    public int getAttempt() {
        return attempt;
    }

    public void print() {
        if (!validRoute()) {
            System.out.println("\uDBC0\uDD84\tNessun percorso trovato.");
            System.out.println("\uDBC1\uDE72\tPARTENZA: " + startingPoint.getId());
            System.out.println("\uDBC3\uDC58\tARRIVO: " + arrivingDevice.getId());
            System.out.print("\uDBC0\uDE64\tSalti massimi tentati: " + maximumHops);
        } else {
            Device currentDevice = startingPoint;
            System.out.println("\uDBC0\uDE64\tSalti: " + this.hops + " | \uDBC0\uDF70 Costo: " + this.cost  + " | \uDBC1\uDD80 Tentativi totali: " + this.attempt);
            System.out.print("\n\uDBC1\uDE72\t\uDBC1\uDE57\tDISPOSITIVO " + currentDevice.getId().toString());
            try {
                while (currentDevice.getLinkedRoutes()[0].linked()) {
                    System.out.println("\n↓\t\uDBC0\uDE64\tROTTA " + currentDevice.getLinkedRoutes()[0].getId().toString() + " - " + currentDevice.getLinkedRoutes()[0].getStringCost());
                    currentDevice = currentDevice.getLinkedRoutes()[0].getTwo();
                    if (currentDevice.getLinkedRoutes()[0].linked()) {
                        System.out.print("↓\t\uDBC1\uDE57\tDISPOSITIVO " + currentDevice.getId());
                    }
                }
            } catch (Exception e) {
                System.out.print("\uDBC3\uDC58\t\uDBC1\uDE57\tDISPOSITIVO " + currentDevice.getId());
            }
        }
    }

    public Route getLastRoute() {
        if (startingPoint.getLinkedRoutes().length != 0) {
            Route currentCheckingRoute = startingPoint.getLinkedRoutes()[0];
            while (currentCheckingRoute != null && currentCheckingRoute.linked()) {
                if (currentCheckingRoute.getNextDevice(currentCheckingRoute.getOne()).getLinkedRoutes().length == 0) {
                    break;
                }
                currentCheckingRoute = currentCheckingRoute.getNextDevice(currentCheckingRoute.getOne()).getLinkedRoutes()[0];
            }
            return currentCheckingRoute;
        }
        return null;
    }

    public Device getStartingPoint() {
        return startingPoint;
    }

    public double getCost() {
        return cost;
    }

    public void increaseCost(double cost) {
        this.cost += cost;
    }

    public void decreaseCost(double cost) {
        this.cost -= cost;
    }

    public int getHops() {
        return hops;
    }

    public void decreaseHops() { this.hops--; }

    public void incrementHops() {
        this.hops++;
    }

    public void paste(EstimatedRoute newRoute) {
        this.startingPoint = newRoute.startingPoint;
        this.cost = newRoute.cost;
        this.hops = newRoute.hops;
    }

    private boolean canProceed() {
        return ((hops < maximumHops || maximumHops == 0) && (cost < maximumCost || maximumCost == 0) && maximumHops < authorizedMaximumHops);
    }
}
