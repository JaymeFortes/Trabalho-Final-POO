package aplicacao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import servicos.DroneService;
import servicos.TransporteService;

import java.io.IOException;

public class ACMEAirDrones {
    private DroneService droneService = new DroneService();
    private TransporteService transporteService = new TransporteService();

    @FXML
    private void abrirTelaCadastroDrone() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modeloDrone.fxml"));
            Parent root = loader.load();
            CadastroDrone controller = loader.getController();
            controller.setDroneService(droneService);

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Drones");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaCadastroTransporte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modeloTransporte.fxml"));
            Parent root = loader.load();
            CadastroTransporte controller = loader.getController();
            controller.setTransporteService(transporteService);

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Transporte");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaRelatorioGeral() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("relatorioGeral.fxml"));
            Parent root = loader.load();
            ControleRelatorioGeral controller = loader.getController();
            controller.setTransporteService(transporteService);
            controller.exibirRelatorio();

            Stage stage = new Stage();
            stage.setTitle("Relatório Geral");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaProcessarTransportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProcessarPendentes.fxml"));
            Parent root = loader.load();
            TransportesPendentes controller = loader.getController();
            controller.setServicos(droneService, transporteService);

            Stage stage = new Stage();
            stage.setTitle("Processar Transportes Pendentes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void processarTransportesPendentesAction() {
        abrirTelaProcessarTransportes();
    }

    @FXML
    private void sairSistema() {
        System.exit(0);
    }
}
