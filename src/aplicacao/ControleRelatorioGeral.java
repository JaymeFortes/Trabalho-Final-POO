package aplicacao;

import dados.Drone;
import dados.Transporte;
import javafx.collections.ObservableList;
import servicos.DroneService;
import servicos.TransporteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ControleRelatorioGeral {

    @FXML
    private TextArea textAreaRelatorio;

    @FXML
    private Button buttonSair, buttonVoltar;

    @FXML
    private Button CSVButtom;

    private TransporteService transporteService;
    private DroneService droneService; // Certifique-se de que este serviço seja inicializado corretamente

    // Inicialização da tela
    @FXML
    public void initialize() {
        // Torna o TextArea apenas leitura
        textAreaRelatorio.setEditable(false);

        // Ação para o botão "Sair"
        buttonSair.setOnAction(event -> System.exit(0));
        buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());
        CSVButtom.setOnAction(event -> salvarTransportesEmCsv());
    }

    // Método para definir o TransporteService
    public void setTransporteService(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    // Método para definir o DroneService
    public void setDroneService(DroneService droneService) {
        this.droneService = droneService;
    }


    // Método para exibir o relatório no TextArea
    public void exibirRelatorio() {
        if (transporteService.getTransportes().isEmpty()) {
            textAreaRelatorio.setText("Nenhum transporte ou drone cadastrado no momento.\n");
            return;
        }

        StringBuilder relatorio = new StringBuilder("Relatório Geral de Transportes e Drones:\n");

        for (Transporte transporte : transporteService.getTransportes()) {
            relatorio.append("Transporte:\n");
            relatorio.append(transporte.toString()).append("\n");
            try {
                // Calcula o custo e exibe
                double custo = transporte.calculaCusto();
                relatorio.append("Custo do transporte: R$ ").append(String.format("%.2f", custo)).append("\n");
            } catch (IllegalArgumentException e) {
                relatorio.append("Erro ao calcular o custo: ").append(e.getMessage()).append("\n");
            }
            relatorio.append("--------------------\n");
        }

        textAreaRelatorio.setText(relatorio.toString());
    }

    // Método para voltar para o menu principal
    @FXML
    private void voltarParaMenuPrincipal() {
        // Obtém a janela atual (associada ao botão "Voltar") e a fecha
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
