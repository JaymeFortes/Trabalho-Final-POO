package aplicacao;

import dados.Transporte;
import dados.TransportePessoal;
import dados.TransporteCargaViva;
import dados.TransporteCargaInanimada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroTransporte {



    public class PleaseProvideControllerClassName {

        @FXML
        private ComboBox<?> comboBoxTransporteTipo;

        @FXML
        private Label descricaoLabel;

        @FXML
        private TextField descricaoTextField;

        @FXML
        private Label latDestinoLabel;

        @FXML
        private TextField latDestinoTextField;

        @FXML
        private Label latOrigemLabel;

        @FXML
        private TextField latOrigemTextField;

        @FXML
        private Label longDestinoLabel;

        @FXML
        private TextField longDestinoTextField;

        @FXML
        private Label longOrigemLabel;

        @FXML
        private TextField longOrigemTextField;

        @FXML
        private Label nomeClienteLabel;

        @FXML
        private TextField nomeClienteTextField;

        @FXML
        private TextField numTextField;

        @FXML
        private Label numeroLabel;

        @FXML
        private Label pesoLabel;

        @FXML
        private TextField pesoTextField;

    }

    private ObservableList<Transporte> transportes = FXCollections.observableArrayList();

    public void initialize() {

    }
}
