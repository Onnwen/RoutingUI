package com.example.routingui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class HelloController {
    @FXML
    ListView networksListView = new ListView();
    private ObservableList<String> networksItems = FXCollections.observableArrayList();

    @FXML
    ListView networkDevicesListView = new ListView();
    private ObservableList<String> networkDevicesItems = FXCollections.observableArrayList();

    @FXML
    ComboBox startingDevice = new ComboBox();
    @FXML
    ComboBox endingDevice = new ComboBox();

    @FXML
    Label textMessage = new Label();
    @FXML
    Label networkName = new Label();

    private Network selectedNetwork = FileManagment.loadNetwork(FileManagment.getFilesNames()[0]);

    @FXML
    ListView routeView = new ListView();
    private ObservableList<String> routeItems = FXCollections.observableArrayList();

    public void initialize() {
        networksListView.setItems(networksItems);
        networkDevicesListView.setItems(networkDevicesItems);
        for (String networkFile: FileManagment.getFilesNames()) {
            networksItems.add(networkFile);
        }
        networksListView.getSelectionModel().select(0);
        updateNetworkDevicesList();
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

    @FXML
    protected void estimateRoute() {
        if (startingDevice.getSelectionModel().getSelectedIndex() == endingDevice.getSelectionModel().getSelectedIndex()) {
            textMessage.setText("I dispositivi di partenza e arrivo non possono combaciare.");
        }
        else if (startingDevice.getSelectionModel().getSelectedIndex() != -1 && endingDevice.getSelectionModel().getSelectedIndex() != -1) {
            textMessage.setText("");
        }
        else {
            textMessage.setText("Selezionare i dispositivi di partenza e arrivo.");
        }
    }
}