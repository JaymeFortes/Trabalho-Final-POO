package aplicacao;

import dados.*;
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

            String relatorio = gerarRelatorioGeral();
            controller.exibirRelatorio(relatorio);

            Stage stage = new Stage();
            stage.setTitle("Relatório Geral");
            stage.setScene(new Scene(root));
            stage.show();
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
            controller.setTransporteService(transporteService);  // Pass necessary services

            Stage stage = new Stage();
            stage.setTitle("Processar Transportes Pendentes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String gerarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();

        // Relatório de Drones
        relatorio.append("Relatório de Drones:\n");
        for (Drone drone : droneService.getDrones()) {
            relatorio.append(drone.toString())
                    .append("Custo por KM: R$ ")
                    .append(String.format("%.2f", drone.calculaCustoKm()))
                    .append("\n");
        }

        relatorio.append("\nRelatório de Transportes:\n");
        for (Transporte transporte : transporteService.getTransportes()) {
            relatorio.append(transporte.toString())
                    .append("\nCusto do Transporte: R$ ")
                    .append(String.format("%.2f", transporte.calculaCusto()))
                    .append("\nDistância: ")
                    .append(String.format("%.2f", transporte.calculaDistancia()))
                    .append(" KM\n");
        }

        return relatorio.toString();
    }


    @FXML
    private void sairSistema() {
        System.exit(0);
    }
}
