package aplicacao;

import dados.DroneCarga;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControleCadastros {
    @FXML
    private TextField codigoField, autonomiaField, custoFixoField, pesoMaximoField;
    @FXML
    private TextArea txtAreaMensagem;
    @FXML
    private Button cadastraButton, limparButton, sairButton, mostrarButton;
    @FXML
    private RadioButton droneVivaRadio, droneInanimadaRadio;

    private ACMEAirDrones acmeAirDrones = new ACMEAirDrones();

    public void initialize() {
        txtAreaMensagem.setEditable(false);

        // Configurações dos botões
        cadastraButton.setOnAction(event -> cadastrarDrone());
        limparButton.setOnAction(event -> limparCampos());
        mostrarButton.setOnAction(event -> mostrarDrones());
        sairButton.setOnAction(event -> System.exit(0));

        // Configuração dos RadioButtons
        ToggleGroup group = new ToggleGroup();
        droneVivaRadio.setToggleGroup(group);
        droneInanimadaRadio.setToggleGroup(group);
        droneVivaRadio.setOnAction(event -> txtAreaMensagem.setText("Drone de Carga Viva selecionado!"));
        droneInanimadaRadio.setOnAction(event -> txtAreaMensagem.setText("Drone de Carga Inanimada selecionado!"));
    }

    public void cadastrarDrone() {
        try {
            if (codigoField.getText().isEmpty() || autonomiaField.getText().isEmpty() ||
                    custoFixoField.getText().isEmpty() || pesoMaximoField.getText().isEmpty()) {
                txtAreaMensagem.setText("Todos os campos devem ser preenchidos!");
                return;
            }

            int codigo = Integer.parseInt(codigoField.getText());
            double autonomia = Double.parseDouble(autonomiaField.getText());
            double custoFixo = Double.parseDouble(custoFixoField.getText());
            double pesoMax = Double.parseDouble(pesoMaximoField.getText());

            boolean isViva = droneVivaRadio.isSelected();
            boolean isInanimada = droneInanimadaRadio.isSelected();

            String mensagem = acmeAirDrones.cadastrarDrone(codigo, autonomia, custoFixo, pesoMax, isViva, isInanimada);
            txtAreaMensagem.setText(mensagem);

        } catch (NumberFormatException e) {
            txtAreaMensagem.setText("ERRO: Autonomia, custo fixo e peso máximo devem ser números válidos.");
        }
    }

    public void limparCampos() {
        codigoField.setText("");
        autonomiaField.setText("");
        custoFixoField.setText("");
        pesoMaximoField.setText("");
        txtAreaMensagem.setText("");
        droneVivaRadio.setSelected(false);
        droneInanimadaRadio.setSelected(false);
    }

    public void mostrarDrones() {
        String mensagem = acmeAirDrones.mostrarDrones();
        txtAreaMensagem.setText(mensagem);
    }

    public ObservableList<DroneCarga> getDrones() {
        return acmeAirDrones.getDrones();
    }
}
