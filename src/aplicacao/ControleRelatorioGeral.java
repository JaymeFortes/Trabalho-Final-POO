package aplicacao;

import dados.Drone;
import dados.Transporte;
import javafx.collections.ObservableList;
import servicos.DroneService;

import servicos.TransporteService;
import servicos.DroneService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ControleRelatorioGeral {

    @FXML
    private TextArea textAreaRelatorio, txtMensagem;

    @FXML
    private Button buttonSair, buttonVoltar;

    @FXML
    private Button CSVButtom;

    private TransporteService transporteService;
    private DroneService droneService;


    @FXML
    public void initialize() {
        if (textAreaRelatorio != null) {
            textAreaRelatorio.setEditable(false);
        }
        if (txtMensagem != null) {
            txtMensagem.setEditable(false);
        }

        buttonSair.setOnAction(event -> System.exit(0));
        buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());
        //CSVButtom.setOnAction(event -> salvarTransportesEmCsv());
    }

    public void setServicos(TransporteService transporteService, DroneService droneService) {
        this.transporteService = transporteService;
        this.droneService = droneService;
    }



    // Método para definir o DroneService
    public void setDroneService(DroneService droneService) {
        this.droneService = droneService;
    }


    // Método para exibir o relatório no TextArea

    public void exibirRelatorio() {
        StringBuilder relatorio = new StringBuilder("Relatório de Transportes e Drones:\n\n");

        if (droneService.getDrones().isEmpty()) {
            relatorio.append("Nenhum drone cadastrado.\n");
        } else {
            relatorio.append("Drones:\n");
            for (Drone drone : droneService.getDrones()) {
                relatorio.append(drone.toString()).append("\n");
            }
        }

        if (transporteService.getTransportes().isEmpty()) {
            relatorio.append("Nenhum transporte cadastrado.\n");
        } else {
            relatorio.append("Transportes:\n");
            for (Transporte transporte : transporteService.getTransportes()) {
                relatorio.append(transporte.toString()).append("\n");
                try {
                    relatorio.append("Custo do Transporte: R$ ").append(transporte.calculaCusto()).append("\n\n");
                } catch (IllegalStateException e) {
                    relatorio.append("Erro: ").append(e.getMessage()).append("\n\n");
                }
            }
        }
        textAreaRelatorio.setText(relatorio.toString());
    }

    public void exibirTodosTransportes() {
        StringBuilder relatorioTransportes = new StringBuilder();

        if (transporteService.getTransportes().isEmpty()) {
            relatorioTransportes.append("Nenhum transporte cadastrado.\n");
        } else {
            relatorioTransportes.append("Transportes:\n");
            for (Transporte transporte : transporteService.getTransportes()) {
                relatorioTransportes.append(transporte.toString()).append("\n");
                if (transporte.getDroneAlocado() != null) {
                    relatorioTransportes.append("Drone alocado:\n")
                            .append(transporte.getDroneAlocado().toString()).append("\n");
                } else {
                    relatorioTransportes.append("Nenhum drone alocado.\n");
                }

                try {
                    relatorioTransportes.append("Custo do Transporte: R$ ")
                            .append(transporte.calculaCusto()).append("\n\n");
                } catch (IllegalStateException e) {
                    relatorioTransportes.append("Erro no cálculo do custo: ")
                            .append(e.getMessage()).append("\n\n");
                }
            }
        }
        txtMensagem.setText(relatorioTransportes.toString());
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }

    private void salvarTransportesEmCsv() {
        File arquivoCsv = new File("transportes.csv");
        try (FileWriter writer = new FileWriter(arquivoCsv)) {
            // Cabeçalho
            writer.write("Número,Nome Cliente,Descrição,Peso,Latitude Origem,Longitude Origem,Latitude Destino,Longitude Destino,Situação,Drone,Tipo,Acrescimos,Custo\n");

            ObservableList<Transporte> listaTransportes = transporteService.getTransportes();
            System.out.println("Total de transportes para salvar: " + listaTransportes.size());

            for (Transporte transporte : listaTransportes) {
                // Debug de cada transporte
                System.out.println("Salvando transporte: " + transporte);


                // Salvando transporte no arquivo CSV
                writer.write(escapeCsv(String.valueOf(transporte.getNumero())) + ",");
                writer.write(escapeCsv(transporte.getNomeCliente()) + ",");
                writer.write(escapeCsv(transporte.getDescricao()) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getPeso())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getLatitudeOrigem())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getLongitudeOrigem())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getLatitudeDestino())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getLongitudeDestino())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getSituacao())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.getDroneAlocado())) + ",");
                writer.write(escapeCsv(transporte.getTipo()) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.calculaAcrescimos())) + ",");
                writer.write(escapeCsv(String.valueOf(transporte.calculaCusto())) + "\n");
            }

            textAreaRelatorio.setText("Transportes salvos em arquivo CSV com sucesso!");
        } catch (IOException e) {
            textAreaRelatorio.setText("ERRO ao salvar os transportes em CSV: " + e.getMessage());
        }
    }


    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
