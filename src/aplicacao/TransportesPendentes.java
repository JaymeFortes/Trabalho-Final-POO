package aplicacao;

import dados.*;
import servicos.DroneService;
import servicos.TransporteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TransportesPendentes {

    @FXML
    private Button buttonVoltar, buttonSair;
    @FXML
    private TextArea txtMensagem;
    private DroneService droneService;
    private TransporteService transporteService;
    private boolean mensagemEnviada = false;

    @FXML
    public void initialize() {
        txtMensagem.setEditable(false);
        buttonSair.setOnAction(e -> System.exit(0));
    }

    public void setServicos(DroneService droneService, TransporteService transporteService) {
        this.droneService = droneService;
        this.transporteService = transporteService;
    }

    public void alocarDroneAoTransporte(Transporte transporte, Drone drone) {
        if (transporte instanceof TransportePessoal && drone instanceof DronePessoal) {
            ((TransportePessoal) transporte).setDrone(drone);
            txtMensagem.appendText("Drone pessoal alocado ao transporte.\n");
        } else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
            ((TransporteCargaViva) transporte).setDrone(drone);
            txtMensagem.appendText("Drone carga viva alocado ao transporte.\n");
        } else if (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) {
            ((TransporteCargaInanimada) transporte).setDrone(drone);
            txtMensagem.appendText("Drone carga inanimada alocado ao transporte.\n");
        } else {
            txtMensagem.appendText("Erro: Tipo de transporte e drone não compatíveis.\n");
        }
    }

    public void processarTransportesPendentes() {
        if (!mensagemEnviada && transporteService.getTransportes().isEmpty()) {
            txtMensagem.appendText("Não existem transportes criados.\n");
            mensagemEnviada = true;
            return;
        }

        for (Transporte transporte : transporteService.getTransportes()) {
            if (transporte.getSituacao() == Estado.PENDENTE) {
                Drone drone = droneService.buscarDroneParaTransporte(transporte);
                if (drone != null) {
                    alocarDroneAoTransporte(transporte, drone);
                    transporte.setSituacao(Estado.ALOCADO);
                    txtMensagem.appendText("Drone alocado ao transporte " + transporte.getNumero() + ".\n");
                } else {
                    txtMensagem.appendText("Nenhum drone encontrado para o transporte " + transporte.getNumero() + ".\n");
                }
            }
        }
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }
}