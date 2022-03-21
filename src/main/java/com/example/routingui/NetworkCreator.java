package com.example.routingui;

public class NetworkCreator {
    Device[] devices = new Device[100];
    int totalDevices = 0;

    public NetworkCreator() {
        do {
            System.out.println("Inserire nome dispositivo oppure premere spazio invio:");
        } while (addDevice(Read.getString()));
    }

    private boolean addDevice(String name) {
        if (name.equals(" ")) {
            return false;
        }
        devices[totalDevices] = new Device(name);
        totalDevices++;
        return true;
    }

    private void addRoute() {

    }
}
