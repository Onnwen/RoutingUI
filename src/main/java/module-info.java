module com.onnwencassitto.routingui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires junit;
    requires java.logging;

    opens com.onnwencassitto.routingui to javafx.fxml;
    exports com.onnwencassitto.routingui;
}