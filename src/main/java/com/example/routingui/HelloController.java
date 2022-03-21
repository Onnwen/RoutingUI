package com.example.routingui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class HelloController {
    @FXML
    ListView networksListView = new ListView();
    private ObservableList<String> networksItems = FXCollections.observableArrayList();

    public void initialize() {
        networksListView.setItems(networksItems);
        networksItems.add("Rete 2");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
        networksItems.add("Rete 1");
    }

    @FXML
    protected void saluta() {
        System.out.println("Ciao!");
    }
}