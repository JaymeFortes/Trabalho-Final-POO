package aplicacao;

import dados.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroTransporte {

    @FXML
    private ComboBox<String> comboBoxTransporteTipo;

    @FXML
    private Label descricaoLabel, qtdPessoasLabel, temperaturaLabel;

    @FXML
    private TextField descricaoTextField, qtdPessoasTextField, temperaturaTextField;

    @FXML
    private Label latDestinoLabel;

    @FXML
    private TextField latDestinoTextField;

    @FXML
    private Label latOrigemLabel;

    @FXML
    private TextField latOrigemTextField;

    @FXML
    private Label longDestinoLabel;

    @FXML
    private TextField longDestinoTextField;

    @FXML
    private Label longOrigemLabel;

    @FXML
    private TextField longOrigemTextField;

    @FXML
    private Label nomeClienteLabel;

    @FXML
    private TextField nomeClienteTextField;

    @FXML
    private TextField numTextField;

    @FXML
    private Label numeroLabel;

    @FXML
    private Label pesoLabel;

    @FXML
    private TextField pesoTextField;

    @FXML
    private TextArea txtAreaMensagem;

    @FXML
    private Button buttonCadastrar, buttonSair, buttonMostrar, buttonLimpar;

    @FXML
    private CheckBox checkBoxCargaPerigosa;


    private ObservableList<Transporte> transportes = FXCollections.observableArrayList();
    private CadastroDrone cadastroDrone = new CadastroDrone();
    private ObservableList<Drone> drones = cadastroDrone.getDrones();


    public void initialize() {
        txtAreaMensagem.setEditable(false);

        buttonCadastrar.setOnAction(event -> cadastrarTransporte());
        buttonLimpar.setOnAction(event -> limparCampos());
        buttonMostrar.setOnAction(event -> mostrarTransporte());
        buttonSair.setOnAction(event -> System.exit(0));

        comboBoxTransporteTipo.setItems(FXCollections.observableArrayList("Transporte Pessoal", "Transporte de Carga Viva", "Transporte de Carga Inanimada"));

        comboBoxTransporteTipo.setOnAction(event -> {
            String tipoSelecionado = comboBoxTransporteTipo.getSelectionModel().getSelectedItem();
            boolean isTransportePessoal = "Transporte Pessoal".equals(tipoSelecionado);
            boolean isTransporteCargaViva = "Transporte de Carga Viva".equals(tipoSelecionado);
            boolean isTransporteCargaInanimada = "Transporte de Carga Inanimada".equals(tipoSelecionado);

            qtdPessoasTextField.setVisible(isTransportePessoal);
            qtdPessoasLabel.setVisible(isTransportePessoal);

            checkBoxCargaPerigosa.setVisible(isTransporteCargaInanimada);

            temperaturaTextField.setVisible(isTransporteCargaViva);
            temperaturaLabel.setVisible(isTransporteCargaViva);

        });

        qtdPessoasTextField.setVisible(false);
        qtdPessoasLabel.setVisible(false);
        checkBoxCargaPerigosa.setVisible(false);
        temperaturaTextField.setVisible(false);
        temperaturaLabel.setVisible(false);
    }

    public void cadastrarTransporte() {
        try {

            if (numTextField.getText().isEmpty() || descricaoTextField.getText().isEmpty()
                    || latDestinoTextField.getText().isEmpty() || latOrigemTextField.getText().isEmpty()
                    || latOrigemTextField.getText().isEmpty() || longDestinoTextField.getText().isEmpty()
                    || nomeClienteTextField.getText().isEmpty() || pesoTextField.getText().isEmpty()) {
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
            double longDestino = Double.parseDouble(longDestinoTextField.getText());


            for (Transporte transporte : transportes) {
                if (transporte.getNumero() == numero) {
                    txtAreaMensagem.setText("ERRO: Já existe um transporte com este número");
                    return;
                }
            }

            String tipo = comboBoxTransporteTipo.getValue();
            if (tipo == null) {
                txtAreaMensagem.setText("ERRO: Selecione o tipo de transporte.");
                return;
            }

            Transporte transporte = null;
            if (tipo.equals("Transporte Pessoal")) {
                if (qtdPessoasTextField.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira a quantidade de pessoas");
                    return;
                }
                int qtdPessoas = Integer.parseInt(qtdPessoasTextField.getText());
                if (qtdPessoas < 0) {
                    txtAreaMensagem.setText("ERRO: A quantidade de pessoas deve ser maior que zero.");
                    return;
                }

                Drone dronePessoal = null;
                for (Drone drone : drones) {
                    if (drone instanceof DronePessoal) {
                        dronePessoal = drone;
                        break;
                    }
                }
                if (dronePessoal == null) {
                    txtAreaMensagem.setText("ERRO: Não há drones disponíveis para transporte pessoal.");
                    return;
                }
                transporte = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, qtdPessoas);
                ((TransportePessoal) transporte).setDronePessoal((DronePessoal) dronePessoal);
                transportes.add(transporte);
                transportes.sort((t1,t2) -> Integer.compare(t1.getNumero(), t2.getNumero()));
                txtAreaMensagem.setText("Transporte pessoal cadastrado com sucesso!");



            } else if (tipo.equals("Transporte Carga Viva")) {
                if (temperaturaTextField.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira a temperatura do transporte");
                    return;
                }
                int temperatura = Integer.parseInt(temperaturaTextField.getText());

                Drone droneCargaViva = null;
                for (Drone drone : drones) {
                    if (drone instanceof DroneCargaViva) {
                        droneCargaViva = drone;
                        break;
                    }
                }
                if (droneCargaViva == null) {
                    txtAreaMensagem.setText("ERRO: Não há drones disponíveis para transporte de carga viva.");
                    return;
                }
                transporte = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, temperatura);
                ((TransportePessoal) transporte).setDronePessoal((DronePessoal) dronePessoal);
                transportes.add(transporte);
                transportes.sort((t1,t2) -> Integer.compare(t1.getNumero(), t2.getNumero()));
                txtAreaMensagem.setText("Transporte pessoal cadastrado com sucesso!");
            }

            }
        }


    public void limparCampos() {
        numTextField.clear();
        descricaoTextField.clear();
        latDestinoTextField.clear();
        latOrigemTextField.clear();
        latDestinoTextField.clear();
        longDestinoTextField.clear();
        nomeClienteTextField.clear();
        pesoTextField.clear();
        comboBoxTransporteTipo.setValue(null);
    }

    public void mostrarTransporte() {
        StringBuilder mensagem = new StringBuilder("Transportes cadastrados:\n\n");
        for (Transporte transporte : transportes) {
            mensagem.append(transporte.getTi)
        }
    }
}
