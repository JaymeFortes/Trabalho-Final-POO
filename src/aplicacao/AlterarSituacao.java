package aplicacao;

import dados.Estado;
import dados.Transporte;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import servicos.TransporteService;

public class AlterarSituacao {
    @FXML
    private TextField numeroField;

    @FXML
    private TextArea txtMensagem;

    @FXML
    private Button buttonVoltar,buttonSair,buttonAlterarSituacao;

    @FXML
    private ComboBox<Estado> comboBoxAlterarSituacao;

    private TransporteService transporteService;

    public void setTransporteService(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    @FXML
    private void initialize() {
        txtMensagem.setEditable(false);
        buttonSair.setOnAction(event -> System.exit(0));
        buttonAlterarSituacao.setOnAction(event -> alterarSituacao());
        comboBoxAlterarSituacao.setItems(FXCollections.observableArrayList(Estado.values()));
    }

    @FXML
    private void alterarSituacao() {
        String numeroTransporte = numeroField.getText().trim();

        if (numeroTransporte.isEmpty()) {
            txtMensagem.setText("Erro: O número do transporte não foi informado.");
            return;
        }

        try {
            int numero = Integer.parseInt(numeroTransporte);
            Transporte transporte = transporteService.buscarTransportePelo(numero);

            if (transporte == null) {
                txtMensagem.setText("Erro: Transporte com número " + numero + " não encontrado.");
                return;
            }

            if (transporte.getSituacao() == Estado.CANCELADO || transporte.getSituacao() == Estado.TERMINADO) {
                txtMensagem.setText("Erro: Não é possível alterar a situação de um transporte TERMINADO ou CANCELADO.");
                return;
            }

            Estado novaSituacao = comboBoxAlterarSituacao.getValue();
            if (novaSituacao == null) {
                txtMensagem.setText("Erro: Por favor, selecione a nova situação no ComboBox.");
                return;
            }

            transporte.setSituacao(novaSituacao);
            transporteService.atualizarTransporte(transporte);

            txtMensagem.setText("Situação do transporte " + numero + " alterada para: " + novaSituacao);
        } catch (NumberFormatException e) {
            txtMensagem.setText("Erro: Dados errados.");
        }
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }

}
