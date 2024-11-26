package aplicacao;

import dados.Transporte;
import dados.Drone;
import servicos.TransporteService;
import servicos.DroneService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ControleRelatorioGeral {

    @FXML
    private TextArea textAreaRelatorio, txtMensagem;
    @FXML
    private Button buttonSair, buttonVoltar;

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
    }

    public void setServicos(TransporteService transporteService, DroneService droneService) {
        this.transporteService = transporteService;
        this.droneService = droneService;
    }

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
}
