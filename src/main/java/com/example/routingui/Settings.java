package com.example.routingui;

public class Settings {
    private boolean privilegesMinorCost;
    private int optionalHopsCost;
    private boolean privilegesPerformances;

    public Settings(boolean privilegesMinorCost, int optionalHopsCost, boolean privilegesPerformances) {
        this.privilegesMinorCost = privilegesMinorCost;
        this.optionalHopsCost = optionalHopsCost;
        this.privilegesPerformances = privilegesPerformances;
    }

    public void print() {
        System.out.print("Privilegia rapidità di calcolo: ");
        if (privilegesPerformances) {
            System.out.println("\uDBC0\uDD85");
        }
        else {
            System.out.println("\uDBC0\uDD84");
        }

        System.out.print("Privilegia costi minori: ");
        if (privilegesPerformances) {
            System.out.println("-");
        }
        else if (privilegesMinorCost) {
            System.out.println("\uDBC0\uDD85");
        }
        else {
            System.out.println("\uDBC0\uDD84");
        }

        System.out.print("Costo considerato per salti facoltativi: ");
        if (privilegesMinorCost && !privilegesPerformances) {
            System.out.println(optionalHopsCost);
        }
        else {
            System.out.println("-");
        }
    }

    public boolean isPrivilegesMinorCost() {
        return privilegesMinorCost;
    }

    public void setPrivilegesMinorCost(boolean privilegesMinorCost) {
        this.privilegesMinorCost = privilegesMinorCost;
    }

    public int getOptionalHopsCost() {
        return optionalHopsCost;
    }

    public void setOptionalHopsCost(int optionalHopsCost) {
        this.optionalHopsCost = optionalHopsCost;
    }

    public boolean isPrivilegesPerformances() {
        return privilegesPerformances;
    }

    public void setPrivilegesPerformances(boolean privilegesPerformances) {
        this.privilegesPerformances = privilegesPerformances;
    }
}
