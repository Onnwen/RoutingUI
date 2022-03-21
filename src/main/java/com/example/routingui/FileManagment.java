package com.example.routingui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class FileManagment {
    public static File folder = new File("Networks");

    public static Network loadNetwork(String networkName) {
        try {
            File routesFile = new File("Networks/" + networkName + ".csv");
            Device[] devicesList = getDevicesArray(routesFile);
            Scanner fileScanner = new Scanner(routesFile);
            Route[] routesList = new Route[getFileLines(routesFile)];

            for(int routeIndex=0; routeIndex<routesList.length; routeIndex++) {
                String[] routeData = fileScanner.nextLine().split(";");
                try {
                    Device one = findDevice(UUID.fromString(routeData[2]), devicesList);
                    Device two = findDevice(UUID.fromString(routeData[3]), devicesList);
                    if (one != null && two != null) {
                        routesList[routeIndex] = new Route(UUID.fromString(routeData[0]), Integer.parseInt(routeData[1]), one, two);
                    }
                }
                catch (Exception e) {
                    System.out.println("Errore con rotta " + routeData[2] + " - " + routeData[3]);
                }
            }
            for (Device device:devicesList) {
                for(Route route:routesList) {
                    if (route != null && route.linkedTo(device)) {
                        device.addRoute(route);
                    }
                }
            }

            return new Network(devicesList);
        } catch (FileNotFoundException e) {
            System.out.println("Errore riscontrato durante il caricamento della rete.");
            return null;
        }
    }

    public static String[] getFilesNames() {
        String[] filesNames = new String[100];
        int totalFilesNames = 0;

        for (final File fileEntry : folder.listFiles()) {
            try {
                String[] fileNameExtension = fileEntry.getName().split("\\.");
                if (fileNameExtension[1].equals("csv")) {
                    filesNames[totalFilesNames] = fileNameExtension[0];
                    totalFilesNames++;
                }
            } catch (Exception e) {}
        }
        return Arrays.copyOf(filesNames, totalFilesNames);
    }

    public static String getFileName() {
        System.out.println("Selezionare la rete:");
        String[] filesNames = getFilesNames();

        for(int i=0; i<filesNames.length; i++) {
            System.out.println("[" + (i+1) + "] " + filesNames[i]);
        }

        return getFilesNames()[Read.getInt(1,getFilesNames().length)-1];
    }

    private static Device findDevice(UUID id, Device[] devices) {
        for (Device device:devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }
        return null;
    }

    private static int getFileLines(File file) {
        try {
            Scanner fileScanner = new Scanner(file);
            int lines = 0;
            while(fileScanner.hasNext()) {
                fileScanner.nextLine();
                lines++;
            }
            return lines;
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    public static int getDevicesCount(File routesFile) {
        return getDevicesArray(routesFile).length;
    }

    private static Device[] getDevicesArray(File routesFile) {
        try {
            Scanner fileScanner = new Scanner(routesFile);
            Device[] devicesList = new Device[getFileLines(routesFile)];
            int totalDevices = 0;
            for(int routeIndex=0; routeIndex<devicesList.length; routeIndex++) {
                String[] deviceData = fileScanner.nextLine().split(";");
                for(int i=2; i<=3; i++) {
                    try {
                        Device newDevice = new Device(UUID.fromString(deviceData[i]));
                        if (!arrayContains(newDevice, devicesList, totalDevices)) {
                            devicesList[totalDevices] = newDevice;
                            totalDevices++;
                        }
                    } catch (Exception e) {
                        System.out.println(deviceData[i]);
                    }
                }
            }
            return Arrays.copyOf(devicesList, totalDevices);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato.");
            return new Device[0];
        }
    }

    private static boolean arrayContains(Device device, Device[] devices, int length) {
        for(int i=0; i<length; i++) {
            if (devices[i].sameAs(device)) {
                return true;
            }
        }
        return false;
    }
}
