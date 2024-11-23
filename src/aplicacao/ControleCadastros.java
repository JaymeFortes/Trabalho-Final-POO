package aplicacao;

import dados.DroneCarga;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ControleCadastros {
    @FXML
    private TextField codigoField, autonomiaField, custoFixoField, pesoMaximoField;
    @FXML
    private TextArea txtAreaMensagem;
    @FXML
    private Button cadastraButton, limparButton, sairButton, mostrarButton;
    @FXML
    private RadioButton droneVivaRadio, droneInanimadaRadio;
    @FXML
    private RadioButton climatizadoRadio, protecaoRadio;
    @FXML
    private VBox optionsVBox;  // Contém os RadioButtons para climatizado e proteção
    @FXML
    private ComboBox<String> cbControl;

    private ACMEAirDrones acmeAirDrones = new ACMEAirDrones();

    public void initialize() {
        txtAreaMensagem.setEditable(false);

        // Configurações dos botões
        cadastraButton.setOnAction(event -> cadastrarDrone());
        limparButton.setOnAction(event -> limparCampos());
        mostrarButton.setOnAction(event -> mostrarDrones());
        sairButton.setOnAction(event -> System.exit(0));
        //cbControl.getItems().addAll("Opção 1", "Opção 2", "Opção 3");

        // Exemplo de manipulação de seleção
        cbControl.setOnAction(event -> {
            String selecionado = cbControl.getSelectionModel().getSelectedItem();
            System.out.println("Item selecionado: " + selecionado);
        });

        // Configuração dos RadioButtons
        ToggleGroup group = new ToggleGroup();
        droneVivaRadio.setToggleGroup(group);
        droneInanimadaRadio.setToggleGroup(group);

        // Inicialmente, os RadioButtons de climatizado e proteção estão invisíveis
        optionsVBox.setVisible(false); // Oculta a VBox que contém as opções de climatizado/proteção

        droneVivaRadio.setOnAction(event -> {
            txtAreaMensagem.setText("Drone de Carga Viva selecionado!\n Se o drone precisa de climatização, clique em 'Climatizado'");
            // Exibe a opção de "Climatizado"
            optionsVBox.setVisible(true);
            climatizadoRadio.setVisible(true);
            protecaoRadio.setVisible(false);
        });

        droneInanimadaRadio.setOnAction(event -> {
            txtAreaMensagem.setText("Drone de Carga Inanimada selecionado! \nSe o drone precisa de proteção, clique em 'Proteção'");
            // Exibe a opção de "Proteção"
            optionsVBox.setVisible(true);
            climatizadoRadio.setVisible(false);
            protecaoRadio.setVisible(true);
        });
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

            // Lógica para obter as opções de climatizado e proteção
            boolean climatizado = climatizadoRadio.isSelected();
            boolean possuiProtecao = protecaoRadio.isSelected();

            String mensagem = acmeAirDrones.cadastrarDrone(codigo, autonomia, custoFixo, pesoMax, isViva, isInanimada, climatizado, possuiProtecao);
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
        optionsVBox.setVisible(false); // Esconde as opções de climatizado/proteção ao limpar
    }

    public void mostrarDrones() {
        String mensagem = acmeAirDrones.mostrarDrones();
        txtAreaMensagem.setText(mensagem);
    }

    public ObservableList<DroneCarga> getDrones() {
        return acmeAirDrones.getDrones();
    }
}
