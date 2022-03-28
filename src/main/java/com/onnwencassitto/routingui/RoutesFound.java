package com.onnwencassitto.routingui;

import java.util.Arrays;

public class RoutesFound {
    private final EstimatedRoute[] routes;
    private int total;
    private final Route[] lastRoutesToAvoid;
    private int totalRoutesToAvoid;
    private double calculationTime;

    public RoutesFound(int totalRoutes) {
        this.total = 0;
        this.lastRoutesToAvoid = new Route[totalRoutes];
        this.routes = new EstimatedRoute[totalRoutes];
        this.totalRoutesToAvoid = 0;
        this.calculationTime = 0;
    }

    public double getCalculationTime() {
        return calculationTime/1000;
    }

    public void setCalculationTime(double calculationTime) {
        this.calculationTime = calculationTime;
    }

    public int getTotalWantedRoutes() {
        return routes.length;
    }

    public void addRouteToAvoid(Route route) {
        lastRoutesToAvoid[totalRoutesToAvoid] = route;
        totalRoutesToAvoid++;
    }

    public Route[] getRoutesToAvoid() {
        return Arrays.copyOf(lastRoutesToAvoid, totalRoutesToAvoid);
    }

    public EstimatedRoute[] getRoutes() {
        return Arrays.copyOf(routes, total);
    }

    public void addFoundRoute(EstimatedRoute route) {
        routes[total] = route;
        total++;
        addRouteToAvoid(route.getLastRoute());
    }

    public EstimatedRoute getLast() {
        return routes[total-1];
    }

    public EstimatedRoute getMinorCostRoute() {
        EstimatedRoute bestRoute = routes[0];
        for(EstimatedRoute foundRoute:getRoutes()) {
            if (foundRoute.validRoute() && foundRoute.getCost() < bestRoute.getCost()) {
                bestRoute = foundRoute;
            }
        }
        return bestRoute;
    }

    public boolean firstIsBest() {
        return (getMinorCostRoute() == routes[0] || getMinorCostRoute().getHops() == routes[0].getHops());
    }

    public EstimatedRoute[] getSortedRoutes() {
        EstimatedRoute[] sortedRoutes = new EstimatedRoute[total];
        int totalSortedRoutes = 0;

        if (getMinorCostRoute() == routes[0] || getMinorCostRoute().getHops() == routes[0].getHops()) {
            sortedRoutes[0]  = routes[0];
            totalSortedRoutes++;
        }
        else {
            sortedRoutes[0] = routes[0];
            sortedRoutes[1] = getMinorCostRoute();
        }

        for(int i=1; i<total; i++) {
            if (routes[i] != getMinorCostRoute()) {
                sortedRoutes[totalSortedRoutes] = routes[i];
                totalSortedRoutes++;
            }
        }

        for(int i=1; i<totalSortedRoutes; i++) {
            try {
                if (sortedRoutes[i].getHops() > sortedRoutes[i + 1].getHops()) {
                    EstimatedRoute temp = sortedRoutes[i];
                    sortedRoutes[i] = sortedRoutes[i + 1];
                    sortedRoutes[i + 1] = temp;
                }
            } catch (Exception e) {}
        }

        for(int i=1; i<totalSortedRoutes; i++) {
            try {
                if (sortedRoutes[i].getCost() > sortedRoutes[i + 1].getCost()) {
                    EstimatedRoute temp = sortedRoutes[i];
                    sortedRoutes[i] = sortedRoutes[i + 1];
                    sortedRoutes[i + 1] = temp;
                }
            } catch (Exception e) {}
        }

        return Arrays.copyOf(sortedRoutes, totalSortedRoutes);
    }

    public void print() {
        if (getRoutes().length > 0) {
            System.out.println("\n-------------------------------------------------------------");
            if (getRoutes().length-1 == 1) {
                System.out.println("\uDBC0\uDD85\t 1 percorso valido trovato.");
            }
            else {
                System.out.println("\uDBC0\uDD85 " + (getRoutes().length - 1) + " percorsi validi trovati | \uDBC1\uDD87 Tempo di calcolo: " + calculationTime/1000 + " sec");
            }
            System.out.println("-------------------------------------------------------------");
            if (getMinorCostRoute() == routes[0] || getMinorCostRoute().getHops() == routes[0].getHops()) {
                System.out.println("Percorso migliore:");
                if (getMinorCostRoute().getHops() == routes[0].getHops()) {
                    getMinorCostRoute().print();
                }
                else {
                    routes[0].print();
                }
            }
            else {
                System.out.println("Percorso più veloce:");
                routes[0].print();
                System.out.println("\n-------------------------------------------------------------");
                System.out.println("Percorso meno costoso:");
                getMinorCostRoute().print();
            }
            for(EstimatedRoute foundRoute:getRoutes()) {
                if (foundRoute.validRoute() && foundRoute != routes[0] && foundRoute != getMinorCostRoute()) {
                    System.out.println("\n-------------------------------------------------------------");
                    System.out.println("Percorso alternativo:");
                    foundRoute.print();
                }
            }
            System.out.println("\n-------------------------------------------------------------");
        }
        else {
            try {
                routes[0].print();
            } catch (Exception e) {}
        }
    }
}
