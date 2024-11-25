package aplicacao;

import dados.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import servicos.DroneService;
import servicos.TransporteService;

public class TransportesPendentes {

    @FXML
    private Button buttonVoltar, buttonSair, buttonProcessarPendentes;
    @FXML
    private TextArea txtMensagem;

    // Serviços
    DroneService droneService = new DroneService();
    TransporteService transporteService = new TransporteService();

    @FXML
    public void initialize() {
        txtMensagem.setEditable(false);

        buttonSair.setOnAction(e -> System.exit(0));

    }

    // Método para alocar drones aos transportes de acordo com o tipo
    public void alocarDroneAoTransporte(Transporte transporte, Drone drone) {
        if (transporte instanceof TransportePessoal && drone instanceof DronePessoal) {
            ((TransportePessoal) transporte).setDrone(drone);
            txtMensagem.appendText("Drone pessoal alocado ao transporte.\n");
        } else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
            ((TransporteCargaViva) transporte).setDrone(drone);
            txtMensagem.appendText("Drone carga viva alocado ao transporte.\n");
        } else if (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) {
            ((TransporteCargaInanimada) transporte).setDrone(drone);
            txtMensagem.appendText("Drone carga alocado ao transporte.\n");
        } else {
            throw new IllegalArgumentException("Tipo de transporte e drone não compatíveis.");
        }
    }

    // Método para processar todos os transportes pendentes e alocar drones a eles
    public void processarTransportesPendentes() {
        // Recuperar todos os transportes pendentes
        for (Transporte transporte : transporteService.getTransportes()) {
            if (transporte.getSituacao() == Estado.PENDENTE) {
                // Para cada transporte pendente, encontrar um drone compatível
                Drone drone = droneService.buscarDroneParaTransporte(transporte);

                // Se houver drone compatível, alocar o drone ao transporte
                if (drone != null) {
                    alocarDroneAoTransporte(transporte, drone);
                    txtMensagem.appendText("Drone alocado ao transporte.\n");
                } else {
                    txtMensagem.appendText("Nenhum drone encontrado para o transporte: ");
                }
            }
        }
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }


    public void setTransporteService(TransporteService transporteService) {
    }
}
