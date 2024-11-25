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
    private TextArea textAreaRelatorio;
    @FXML
    private Button buttonSair, buttonVoltar;

    private TransporteService transporteService;
    private DroneService droneService;

    @FXML
    public void initialize() {
        textAreaRelatorio.setEditable(false);
        buttonSair.setOnAction(event -> System.exit(0));
        buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());
    }

    public void setServicos(TransporteService transporteService, DroneService droneService) {
        this.transporteService = transporteService;
        this.droneService = droneService;
    }

    public void exibirRelatorio() {
        StringBuilder relatorio = new StringBuilder("Relatório de Transportes e Drones:\n\n");

        // Exibir dados dos drones
        if (droneService.getDrones().isEmpty()) {
            relatorio.append("Nenhum drone cadastrado.\n");
        } else {
            relatorio.append("Drones:\n");
            for (Drone drone : droneService.getDrones()) {
                relatorio.append(drone.toString()).append("\n");
            }
        }

        // Exibir dados dos transportes
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

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }
}
