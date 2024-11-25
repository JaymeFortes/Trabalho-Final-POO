package aplicacao;

import dados.Transporte;
import servicos.TransporteService;
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

    // Inicialização da tela
    @FXML
    public void initialize() {
        // Torna o TextArea apenas leitura
        textAreaRelatorio.setEditable(false);

        // Ação para o botão "Sair"
        buttonSair.setOnAction(event -> System.exit(0));
        buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());
    }

    // Método para definir o TransporteService
    public void setTransporteService(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    // Método para exibir o relatório no TextArea
    public void exibirRelatorio() {
        StringBuilder relatorio = new StringBuilder("Relatório de Transportes:\n");
        for (Transporte transporte : transporteService.getTransportes()) {
            relatorio.append(transporte.toString()).append("\n");
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
}
