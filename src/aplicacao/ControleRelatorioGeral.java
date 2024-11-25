package aplicacao;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ControleRelatorioGeral {

    @FXML
    private TextArea textAreaRelatorio;
    @FXML
    private Button buttonSair, buttonVoltar;

    // Inicialização da tela
    @FXML
    public void initialize() {
        // Torna o TextArea apenas leitura
        textAreaRelatorio.setEditable(false);

        // Ação para o botão "Sair"
        buttonSair.setOnAction(event -> System.exit(0));
    }

    // Método para exibir o relatório no TextArea
    public void exibirRelatorio(String relatorio) {
        textAreaRelatorio.setText(relatorio);
    }

    // Método para voltar para o menu principal
    @FXML
    private void voltarParaMenuPrincipal() {
        // Obtém a janela atual (associada ao botão "Voltar") e a fecha
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }
}
