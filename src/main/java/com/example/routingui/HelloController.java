package com.example.routingui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.UUID;

public class HelloController {
    final int MAX_COST = 1000;
    final int MAX_HOPS = 1000;
    final int CONSIDERED_COST_PER_HOPS = 0;
    final int MAX_ROUTES = 10;

    @FXML ListView networksListView = new ListView();
    private ObservableList<String> networksItems = FXCollections.observableArrayList();

    @FXML ListView networkDevicesListView = new ListView();
    private ObservableList<String> networkDevicesItems = FXCollections.observableArrayList();

    @FXML ComboBox startingDevice = new ComboBox();
    @FXML ComboBox endingDevice = new ComboBox();

    @FXML Button estimateRouteButton = new Button();

    @FXML Label networkName = new Label();
    private Network selectedNetwork = FileManagment.loadNetwork(FileManagment.getFilesNames()[0]);

    @FXML ListView foundRoutesListView = new ListView();
    private ObservableList<String> foundRoutesItems = FXCollections.observableArrayList();

    @FXML ListView routeView = new ListView();
    private ObservableList<String> routeItems = FXCollections.observableArrayList();

    @FXML Label totalCost = new Label();
    @FXML Label totalHops = new Label();
    @FXML Label totalAttemps = new Label();
    @FXML Label calculationTime = new Label();

    @FXML Label statusText = new Label();
    @FXML Text messageText = new Text();
    @FXML Circle statusColor = new Circle();

    @FXML TextField maxCost = new TextField();
    @FXML TextField maxHops = new TextField();
    @FXML TextField consideredCostHops = new TextField();
    @FXML TextField maxRoutes = new TextField();

    RoutesFound routesFound;

    public void initialize() {
        networksListView.setItems(networksItems);
        networkDevicesListView.setItems(networkDevicesItems);
        foundRoutesListView.setItems(foundRoutesItems);
        routeView.setItems(routeItems);
        for (String networkFile: FileManagment.getFilesNames()) {
            networksItems.add(networkFile);
        }
        networksListView.getSelectionModel().select(0);
        updateNetworkDevicesList();
    }

    public void updateStatus() {
        if (startingDevice.getSelectionModel().getSelectedIndex() == -1 || endingDevice.getSelectionModel().getSelectedIndex() == -1) {
            messageText.setText("Selezionare i dispositivi di partenza e arrivo.");
            statusColor.setFill(javafx.scene.paint.Color.RED);
            statusText.setText("Non pronto");
            statusText.setTextFill(javafx.scene.paint.Color.RED);
            estimateRouteButton.setDisable(true);
        }
        else if (startingDevice.getSelectionModel().getSelectedIndex() == endingDevice.getSelectionModel().getSelectedIndex()) {
            messageText.setText("I dispositivi di partenza e arrivo non possono combaciare.");
            statusColor.setFill(javafx.scene.paint.Color.RED);
            statusText.setText("Non pronto");
            statusText.setTextFill(javafx.scene.paint.Color.RED);
            estimateRouteButton.setDisable(true);
        }
        else if (!validInput()) {
            messageText.setText("Immettere valori coerenti.");
            statusColor.setFill(javafx.scene.paint.Color.RED);
            statusText.setText("Non pronto");
            statusText.setTextFill(javafx.scene.paint.Color.RED);
            estimateRouteButton.setDisable(true);
        }
        else {
            statusColor.setFill(javafx.scene.paint.Color.GREEN);
            statusText.setText("Pronto");
            statusText.setTextFill(javafx.scene.paint.Color.GREEN);
            messageText.setText("");
            estimateRouteButton.setDisable(false);
        }
    }

    public boolean validInput() {
        return getMaxCost() != -1 && getMaxHops() != -1 && getConsideredCost() != -1 && getMaxRoutes() != 1;
    }

    public int getMaxCost() {
        if (maxCost.getText() == null || maxCost.getText().trim().isEmpty()) {
            return MAX_COST;
        }
        else {
            try {
                return Integer.parseInt(maxCost.getText());
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public int getMaxHops() {
        if (maxHops.getText() == null || maxHops.getText().trim().isEmpty()) {
            return MAX_HOPS;
        }
        else {
            try {
                return Integer.parseInt(maxHops.getText());
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public int getConsideredCost() {
        if (consideredCostHops.getText() == null || consideredCostHops.getText().trim().isEmpty()) {
            return CONSIDERED_COST_PER_HOPS;
        }
        else {
            try {
                return Integer.parseInt(consideredCostHops.getText());
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public int getMaxRoutes() {
        if (maxRoutes.getText() == null || maxRoutes.getText().trim().isEmpty()) {
            return MAX_ROUTES;
        }
        else {
            try {
                return Integer.parseInt(maxRoutes.getText());
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public void updateNetworkDevicesList() {
        updateSelectedNetwork(networksListView.getSelectionModel().getSelectedIndex());
        for (Device device: selectedNetwork.getDevices()) {
            networkDevicesItems.add(device.getName());
            startingDevice.getItems().add(device.getName());
            endingDevice.getItems().add(device.getName());
        }
    }

    private void updateSelectedNetwork(int index) {
        networkDevicesItems.clear();
        startingDevice.getItems().clear();
        endingDevice.getItems().clear();
        startingDevice.setPromptText("Partenza");
        endingDevice.setPromptText("Arrivo");
        networkName.setText("Dispositivi della rete \"" + FileManagment.getFilesNames()[index] + "\":");
        selectedNetwork = FileManagment.loadNetwork(FileManagment.getFilesNames()[index]);
    }

    public void updateFoundRoutesList() {
        updateSelectedRoute(foundRoutesListView.getSelectionModel().getSelectedIndex());
    }

    private void updateSelectedRoute(int index) {
        routeItems.clear();
        EstimatedRoute route = routesFound.getSortedRoutes()[index];
        if (route.validRoute()) {
            Device currentDevice = route.getStartingPoint();

            totalCost.setText("Costo complessivo: " + route.getCost());
            totalHops.setText("Salti totali: " + route.getHops());
            totalAttemps.setText("Tentativi effettuati: " + route.getAttempt());
            calculationTime.setText("Tempo di calcolo: " + routesFound.getCalculationTime() + " secondi");

            routeItems.add("DISPOSITIVO " + currentDevice.getId().toString());

            try {
                while (currentDevice.getLinkedRoutes()[0].linked()) {
                    routeItems.add("↓  ROTTA " + currentDevice.getLinkedRoutes()[0].getId().toString() + " - Costo: " + currentDevice.getLinkedRoutes()[0].getStringCost());
                    currentDevice = currentDevice.getLinkedRoutes()[0].getTwo();
                    if (currentDevice.getLinkedRoutes()[0].linked()) {
                        routeItems.add("↓  DISPOSITIVO " + currentDevice.getId());
                    }
                }
            } catch (Exception e) {
                routeItems.add("DISPOSITIVO " + currentDevice.getId());
            }
        }
    }

    @FXML
    protected void estimateRoute() {
        if (startingDevice.getSelectionModel().getSelectedIndex() == endingDevice.getSelectionModel().getSelectedIndex()) {
            estimateRouteButton.setText("Calcolo percorso - I dispositivi di partenza e arrivo non possono combaciare.");
            estimateRouteButton.setStyle("-fx-background-color: red;");
        }
        if (startingDevice.getSelectionModel().getSelectedIndex() == -1 && endingDevice.getSelectionModel().getSelectedIndex() == -1) {
            estimateRouteButton.setText("Calcola percorso - Selezionare i dispositivi di partenza e arrivo.");
        }
        else {
            estimateRouteButton.setText("Calcola percorso");
            routesFound = selectedNetwork.estimateRoute(selectedNetwork.getDevice(UUID.fromString(startingDevice.getValue().toString())), selectedNetwork.getDevice(UUID.fromString(endingDevice.getValue().toString())), getMaxHops());
            foundRoutesItems.clear();
            EstimatedRoute[] routes = routesFound.getSortedRoutes();
            if (routes[0].validRoute()) {
                int j = 1;
                for (int i=0; i<routes.length; i++) {
                    if (i == 0) {
                        if (routesFound.firstIsBest()) {
                            foundRoutesItems.add("Percorso migliore");
                        }
                        else {
                            foundRoutesItems.add("Percorso più veloce");
                            i++;
                            foundRoutesItems.add("Percorso meno costoso");
                        }
                    }
                    else {
                        if (routes[i].validRoute()) {
                            foundRoutesItems.add("Percorso alternativo " + j);
                            j++;
                        }
                    }
                }
            }
            else {
                foundRoutesItems.add("Nessun percorso trovato");
            }
        }
        updateSelectedRoute(0);
        foundRoutesListView.getSelectionModel().select(0);
    }
}