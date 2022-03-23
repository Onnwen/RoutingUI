package com.example.routingui;

import org.junit.Test;

class ProjectTest {
    @Test
    void testSettingsUI() {
        new Settings(true, 0, false).print();
    }

    @Test
    void testEstimateRoute() {
        Network net = FileManagment.loadNetwork("routes1-small.csv");
        net.estimateRoute(net.getDevices()[3], net.getDevices()[7], 100).print();
    }

    @Test
    void testRandomGeneration() {
        Network.generate(10, 1).printFileString();
    }

    @Test
    void testNetworkCreator() {
        NetworkCreator networkCreator = new NetworkCreator();
    }
}