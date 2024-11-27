package aplicacao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicos.DroneService;
import servicos.TransporteService;

import java.awt.event.ActionEvent;
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
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
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
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
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
            controller.setServicos(transporteService, droneService);
            controller.exibirRelatorio();

            Stage stage = new Stage();
            stage.setTitle("Relatório Geral");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTodosTransportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mostrarTodosTransportes.fxml"));
            Parent root = loader.load();

            ControleRelatorioGeral controller = loader.getController();
            controller.setServicos(transporteService, droneService);
            controller.exibirTodosTransportes();

            Stage stage = new Stage();
            stage.setTitle("Todos Transportes");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void processarTransportesPendentesAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProcessarPendentes.fxml"));
            Parent root = loader.load();
            TransportesPendentes controller = loader.getController();
            controller.setServicos(droneService, transporteService);

            Stage stage = new Stage();
            stage.setTitle("Processar Transportes Pendentes");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirAlterarSituacao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modeloAlterarSituacao.fxml"));
            Parent root = loader.load();

            AlterarSituacao controller = loader.getController();
            controller.setTransporteService(transporteService);

            Stage stage = new Stage();
            stage.setTitle("Alterar Situação de Transporte");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirDadosSimulacao(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modeloSimularDados.fxml"));
            Parent root = loader.load();

            ControleRelatorioGeral controller = loader.getController();
            controller.setServicos(transporteService, droneService);
            controller.realizarLeituraDadosSimulacao();


            Stage stage = new Stage();
            stage.setTitle("Leitura de Dados de Simulação");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void lerArquivoSemExtensao() {

        ControleRelatorioGeral controller = new ControleRelatorioGeral();
        controller.setServicos(transporteService, droneService);
        controller.CarregarDados();
    }

    @FXML
    private void abrirSalvarCsv(){
        ControleRelatorioGeral controller = new ControleRelatorioGeral();
        controller.setServicos(transporteService, droneService);
        controller.salvarTransportesEDronesEmCsv();
    }

    @FXML
    private void sairSistema() {
        System.exit(0);
    }
}
