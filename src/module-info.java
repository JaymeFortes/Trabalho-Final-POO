module TF.POO {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    exports dados to com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.base;

    opens dados to com.fasterxml.jackson.databind;
    opens aplicacao;
}