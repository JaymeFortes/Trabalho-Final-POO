package aplicacao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ACMEAirDrones {
    public void executar() {
        abrirTelaCadastroDrone();
        abrirTelaCadastroTransporte();
        sairSistema();
    }

    @FXML
    private void abrirTelaCadastroDrone() {
        carregarNovaTela("modeloDrone.fxml", "Cadastro de Drones");
    }

    @FXML
    private void abrirTelaCadastroTransporte() {
        carregarNovaTela("modeloTransporte.fxml", "Cadastro de Transporte");
    }

    @FXML
    private void sairSistema() {
        System.exit(0);
    }

    private void carregarNovaTela(String fxmlPath, String titulo) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}