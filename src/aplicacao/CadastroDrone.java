package aplicacao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import dados.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import servicos.DroneService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CadastroDrone {

    private DroneService droneService;

    @FXML
    private TextField codigoField, autonomiaField, custoFixoField, pessoasField, pesoMaxField;
    @FXML
    private TextArea txtAreaMensagem;
    @FXML
    private Button cadastraButton, limparButton, sairButton, mostrarButton, buttonVoltar;
    @FXML
    private ComboBox<String> cbControl;
    @FXML
    private Label pessoasLabel, pesoMaxLabel;
    @FXML
    private CheckBox climaBox, proteBox;

    public void setDroneService(DroneService droneService) {
        this.droneService = droneService;
    }

    public void initialize() {
        txtAreaMensagem.setEditable(false);

        cadastraButton.setOnAction(event -> cadastrarDrone());
        limparButton.setOnAction(event -> limparCampos());
        mostrarButton.setOnAction(event -> mostrarDrones());
        sairButton.setOnAction(event -> System.exit(0));
        buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());

        cbControl.setItems(FXCollections.observableArrayList("Drone Pessoal", "Drone de Carga Viva", "Drone de Carga Inanimada"));

        cbControl.setOnAction(event -> {
            String tipoSelecionado = cbControl.getSelectionModel().getSelectedItem();
            boolean isDronePessoal = "Drone Pessoal".equals(tipoSelecionado);
            boolean isDroneCargaViva = "Drone de Carga Viva".equals(tipoSelecionado);
            boolean isDroneCargaInanimada = "Drone de Carga Inanimada".equals(tipoSelecionado);

            pessoasLabel.setVisible(isDronePessoal);
            pessoasField.setVisible(isDronePessoal);

            climaBox.setVisible(isDroneCargaViva);
            pesoMaxLabel.setVisible(isDroneCargaViva || isDroneCargaInanimada);
            pesoMaxField.setVisible(isDroneCargaViva || isDroneCargaInanimada);

            proteBox.setVisible(isDroneCargaInanimada);
        });

        pessoasLabel.setVisible(false);
        pessoasField.setVisible(false);
        climaBox.setVisible(false);
        pesoMaxField.setVisible(false);
        pesoMaxLabel.setVisible(false);
        proteBox.setVisible(false);
    }

    Drone drone = null;

    public void cadastrarDrone() {

        try {
            if (codigoField.getText().isEmpty() || autonomiaField.getText().isEmpty() || custoFixoField.getText().isEmpty()) {
                txtAreaMensagem.setText("ERRO: Todos os campos obrigatórios devem ser preenchidos.");
                return;
            }

            int codigo = Integer.parseInt(codigoField.getText());
            double autonomia = Double.parseDouble(autonomiaField.getText());
            double custoFixo = Double.parseDouble(custoFixoField.getText());

            for (Drone drone : droneService.getDrones()) {
                if (drone.getCodigo() == codigo) {
                    txtAreaMensagem.setText("ERRO: Já existe um drone com este código.");
                    return;
                }
            }

            String tipo = cbControl.getValue();
            if (tipo == null) {
                txtAreaMensagem.setText("ERRO: Selecione o tipo de drone.");
                return;
            }

            if ("Drone Pessoal".equals(tipo)) {
                if (pessoasField.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira a quantidade de pessoas.");
                    return;
                }

                int quantidadePessoas = Integer.parseInt(pessoasField.getText());
                if (quantidadePessoas <= 0) {
                    txtAreaMensagem.setText("ERRO: A quantidade de pessoas deve ser maior que 0.");
                    return;
                }

                drone = new DronePessoal(codigo, autonomia, custoFixo, quantidadePessoas);
                droneService.adicionarDrone(drone);
                txtAreaMensagem.setText("Drone Pessoal cadastrado com sucesso.");

            } else if ("Drone de Carga Viva".equals(tipo)) {
                if (pesoMaxField.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira o peso máximo.");
                    return;
                }

                boolean climatizado = climaBox.isSelected();
                double pesoMaximo = Double.parseDouble(pesoMaxField.getText());
                if (pesoMaximo <= 0) {
                    txtAreaMensagem.setText("ERRO: O peso máximo deve ser maior que 0.");
                    return;
                }

                drone = new DroneCargaViva(codigo, autonomia, custoFixo, pesoMaximo, climatizado);
                droneService.adicionarDrone(drone);
                txtAreaMensagem.setText("Drone de Carga Viva cadastrado com sucesso.");

            } else if ("Drone de Carga Inanimada".equals(tipo)) {
                if (pesoMaxField.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira o peso máximo.");
                    return;
                }

                boolean protecao = proteBox.isSelected();
                double pesoMaximo = Double.parseDouble(pesoMaxField.getText());
                if (pesoMaximo <= 0) {
                    txtAreaMensagem.setText("ERRO: O peso máximo deve ser maior que 0.");
                    return;
                }

                drone = new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMaximo, protecao);
                droneService.adicionarDrone(drone);
                txtAreaMensagem.setText("Drone de Carga Inanimada cadastrado com sucesso.");
            }
        } catch (NumberFormatException e) {
            txtAreaMensagem.setText("ERRO: Valores inválidos");
        } catch (IllegalArgumentException e) {
            txtAreaMensagem.setText("ERRO: " + e.getMessage());
        }
    }

    public void limparCampos() {
        codigoField.clear();
        autonomiaField.clear();
        custoFixoField.clear();
        pessoasField.clear();
        pesoMaxField.clear();
        txtAreaMensagem.clear();

        cbControl.setValue(null);

        pessoasLabel.setVisible(false);
        pessoasField.setVisible(false);
        climaBox.setVisible(false);
        pesoMaxField.setVisible(false);
        pesoMaxLabel.setVisible(false);
        proteBox.setVisible(false);
    }

    public void mostrarDrones() {
        StringBuilder mensagem = new StringBuilder("Drones cadastrados:\n\n");
        for (Drone drone : droneService.getDrones()) {
            mensagem.append(drone.getTipoDrone()).append(drone).append("\n\n");
        }
        txtAreaMensagem.setText(mensagem.toString());
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }


}
