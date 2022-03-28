package com.onnwencassitto.routingui;

import java.util.Arrays;
import java.util.UUID;

public class Device extends Id {
    private String name;
    private Route[] linkedRoutes;
    private int totalLinkedRoutes;

    public Device(UUID id) {
        super(id);
        this.linkedRoutes = new Route[100];
        this.totalLinkedRoutes = 0;
    }

    public Device(String name, UUID id) {
        super(id);
        this.name = name;
        this.linkedRoutes = new Route[100];
        this.totalLinkedRoutes = 0;
    }

    public Device(String name) {
        super();
        this.name = name;
        this.linkedRoutes = new Route[100];
        this.totalLinkedRoutes = 0;
    }

    public Device copy() {
        return new Device(id);
    }

    public boolean sameAs(Device device) {
        return id.toString().equals(device.getId().toString());
    }

    public Route[] getLinkedRoutes() {
        return Arrays.copyOf(linkedRoutes, totalLinkedRoutes);
    }

    public String getName() {
        if (hasName()) {
            return name;
        }
        else {
            return id.toString();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public boolean hasName() {
        return name != null && name != "";
    }

    public void addRoute(Route route) {
        if (!routeAlreadyLinked(route)) {
            linkedRoutes[totalLinkedRoutes] = route;
            totalLinkedRoutes++;
        }
    }

    public void resetRoutes() {
        this.linkedRoutes = new Route[10];
        this.totalLinkedRoutes = 0;
    }

    public boolean routeAlreadyLinked(Route route) {
        for(Route linkedRoute:getLinkedRoutes()) {
            if (linkedRoute == route) {
                return true;
            }
        }
        return false;
    }

    public boolean alreadyLinked(Device device) {
        for(Route linkedRoute:getLinkedRoutes()) {
            if (linkedRoute.linkedTo(device)) {
                return true;
            }
        }
        return false;
    }
}
