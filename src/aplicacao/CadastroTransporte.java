package aplicacao;

import dados.Transporte;
import dados.TransportePessoal;
import dados.TransporteCargaViva;
import dados.TransporteCargaInanimada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroTransporte {

        @FXML
        private ComboBox<String> comboBoxTransporteTipo;

        @FXML
        private Label descricaoLabel,latDestinoLabel,latOrigemLabel,longDestinoLabel,longOrigemLabel,nomeClienteLabel,
                numeroLabel,pesoLabel;

        @FXML
        private TextField descricaoTextField,latDestinoTextField,latOrigemTextField,longDestinoTextField,
                longOrigemTextField,nomeClienteTextField,numTextField,pesoTextField;

        @FXML
        private TextArea txtAreaMensagem;

        @FXML
        private Button buttonCadastrar,buttonLimpar,buttonSair,buttonMostrar,buttonVoltar;


        private ObservableList<Transporte> transportes = FXCollections.observableArrayList();

        public void initialize() {

                txtAreaMensagem.setEditable(false);

                buttonCadastrar.setOnAction(e -> cadastrarTransporte());
                buttonLimpar.setOnAction(event -> limparCampos());
                buttonSair.setOnAction(event -> System.exit(0));
                buttonMostrar.setOnAction(event -> mostrarTransporte());
                buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());

                comboBoxTransporteTipo.setItems(FXCollections.observableArrayList("Transporte Pessoal", "Transporte de Carga Viva", "Transporte de Carga Inanimada"));

                comboBoxTransporteTipo.setOnAction(event -> {
                        String tipoSelecionado = comboBoxTransporteTipo.getSelectionModel().getSelectedItem();
                        boolean isTransportePessoal = "Transporte Pessoal".equals(tipoSelecionado);
                        boolean isTransporteCargaViva = "Transporte de Carga Viva".equals(tipoSelecionado);
                        boolean isTransporteInanimada = "Transporte de Carga Inanimada".equals(tipoSelecionado);

                });


        }

        public void cadastrarTransporte(){
                try {

                     if (numTextField.getText().isEmpty() || descricaoTextField.getText().isEmpty()
                     || latDestinoTextField.getText().isEmpty() || latOrigemTextField.getText().isEmpty()
                             || latOrigemTextField.getText().isEmpty() || longDestinoTextField.getText().isEmpty()
                             || nomeClienteTextField.getText().isEmpty() || pesoTextField.getText().isEmpty()){
                             txtAreaMensagem.setText("ERRO: Todos os campos obrigatórios devem ser preenchidos.");
                             return;
                     }
                     int numero = Integer.parseInt(numTextField.getText());
                     String descricao = descricaoTextField.getText();
                     String nomeCliente = nomeClienteTextField.getText();
                     double peso = Double.parseDouble(pesoTextField.getText());
                     double latOrigem = Double.parseDouble(latOrigemTextField.getText());
                     double latDestino = Double.parseDouble(latDestinoTextField.getText());
                     double longOrigem = Double.parseDouble(longOrigemTextField.getText());

                     for (Transporte transporte : transportes){
                             if (transporte.getNumero() == numero){
                                     txtAreaMensagem.setText("ERRO: Já existe um transporte com este número");
                                     return;
                             }
                     }

                     String tipo = comboBoxTransporteTipo.getValue();
                        if (tipo == null) {
                                txtAreaMensagem.setText("ERRO: Selecione o tipo de transporte.");
                                return;
                        }
                        if (tipo.equals("Transporte Pessoal")){

                        }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }

        public void limparCampos(){
                numTextField.clear();
                descricaoTextField.clear();
                latDestinoTextField.clear();
                latOrigemTextField.clear();
                longDestinoTextField.clear();
                longDestinoTextField.clear();
                nomeClienteTextField.clear();
                txtAreaMensagem.clear();
                pesoTextField.clear();
                comboBoxTransporteTipo.setValue(null);
        }

        public void mostrarTransporte(){
                StringBuilder mensagem = new StringBuilder("Transportes cadastrados:\n\n");
                for (Transporte transporte : transportes){
                        mensagem.append(transporte.getTi);
                }
        }
        @FXML
        private void voltarParaMenuPrincipal() {
                Stage stage = (Stage) buttonVoltar.getScene().getWindow();
                stage.close();
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaPrincipal.fxml"));
                        Parent root = loader.load();
                        Stage newStage = new Stage();
                        newStage.setTitle("ACMEAirDrones");
                        newStage.setScene(new Scene(root));
                        newStage.show();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
