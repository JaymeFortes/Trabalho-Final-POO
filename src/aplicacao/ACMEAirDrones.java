package aplicacao;

import java.io.FileWriter;
import java.io.IOException;

import dados.Drone;
import dados.DronePessoal;
import dados.DroneCargaViva;
import dados.DroneCargaInanimada;
import javafx.collections.ObservableList;
import dados.Transporte;

import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ACMEAirDrones {
    @FXML
    private void abrirTelaCadastroDrone() {
        carregarNovaTela("TelaCadastroDrone.fxml", "Cadastro de Drones");
    }

    @FXML
    private void abrirTelaCadastroTransporte() {
        carregarNovaTela("TelaCadastroTransporte.fxml", "Cadastro de Transporte");
    }

    @FXML
    private void sairSistema() {
        System.exit(0);
    }

    private void carregarNovaTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
