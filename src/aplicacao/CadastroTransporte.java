package aplicacao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import dados.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import servicos.TransporteService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CadastroTransporte {

    private TransporteService transporteService;
    private ObservableList<Transporte> transportes;

    @FXML
    private ComboBox<String> comboBoxTransporteTipo;

    @FXML
    private Label descricaoLabel, latDestinoLabel, latOrigemLabel, longDestinoLabel, longOrigemLabel, nomeClienteLabel,
            numeroLabel, pesoLabel, minTempLabel, maxTempLabel, numPessoasLabel;

    @FXML
    private TextField descricaoTextField, latDestinoTextField, latOrigemTextField, longDestinoTextField,
            longOrigemTextField, nomeClienteTextField, numTextField, pesoTextField,
            txtFieldMinTemp, txtFieldMaxTemp, txtFieldNumPessoas;

    @FXML
    private TextArea txtAreaMensagem;

    @FXML
    private Button buttonCadastrar, buttonLimpar, buttonSair, buttonMostrar, buttonVoltar;

    @FXML
    private CheckBox cargaPerigosaCheckBox;

    @FXML
    private Button JSONButtom;

    @FXML
    private Button CSVButtom;

    public CadastroTransporte() {
        this.transporteService = new TransporteService();
        this.transportes = FXCollections.observableArrayList();
    }

    public void setTransporteService(TransporteService transporteService) {
        this.transporteService = transporteService;
    }


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

            cargaPerigosaCheckBox.setVisible(isTransporteInanimada);

            txtFieldMaxTemp.setVisible(isTransporteCargaViva);
            txtFieldMinTemp.setVisible(isTransporteCargaViva);
            minTempLabel.setVisible(isTransporteCargaViva);
            maxTempLabel.setVisible(isTransporteCargaViva);

            txtFieldNumPessoas.setVisible(isTransportePessoal);
            numPessoasLabel.setVisible(isTransportePessoal);

        });

        cargaPerigosaCheckBox.setVisible(false);
        txtFieldMaxTemp.setVisible(false);
        txtFieldNumPessoas.setVisible(false);
        txtFieldMinTemp.setVisible(false);
        minTempLabel.setVisible(false);
        maxTempLabel.setVisible(false);
        numPessoasLabel.setVisible(false);
    }

    Transporte transporte = null;


    public void cadastrarTransporte() {
        try {

            JSONButtom.setOnAction(event -> salvarTransportesEmJson());
            //CSVButtom.setOnAction(event -> salvarTransportesEmCsv());

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

            for (Transporte transporte : transporteService.getTransportes()) {
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
            if (tipo.equals("Transporte Pessoal")) {
                if (txtFieldNumPessoas.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira a quantidade de pessoas.");
                    return;
                }
                int qtdPessoas = Integer.parseInt(txtFieldNumPessoas.getText());
                if (qtdPessoas <= 0) {
                    txtAreaMensagem.setText("ERRO: A quantidade de pessoas deve ser maior que zero");
                    return;
                }

                transporte = new TransportePessoal(numero, nomeCliente, descricao, peso,
                        latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, qtdPessoas);
                transporteService.adicionarTransporte(transporte);
                txtAreaMensagem.setText("Transporte Pessoal cadastrado com sucesso!");

            } else if (tipo.equals("Transporte de Carga Viva")) {
                if (txtFieldMaxTemp.getText().isEmpty() || txtFieldMinTemp.getText().isEmpty()) {
                    txtAreaMensagem.setText("ERRO: Insira as temperaturas.");
                    return;
                }
                double maxTemp = Double.parseDouble(txtFieldMaxTemp.getText());
                double minTemp = Double.parseDouble(txtFieldMinTemp.getText());
                transporte = new TransporteCargaViva(numero, nomeCliente, descricao, peso,
                        latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, maxTemp, minTemp);
                transporteService.adicionarTransporte(transporte);

                txtAreaMensagem.setText("Transporte Carga Viva cadastrado com sucesso!");

            } else if (tipo.equals("Transporte de Carga Inanimada")) {
                boolean perigoso = cargaPerigosaCheckBox.isSelected();
                transporte = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso,
                        latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, perigoso);
                transporteService.adicionarTransporte(transporte);

                txtAreaMensagem.setText("Transporte de Carga Inanimada cadastrado com sucesso!");
            }
        } catch (NumberFormatException e) {
            txtAreaMensagem.setText("ERRO: Valores inválidos");
        } catch (Exception e) {
            txtAreaMensagem.setText("ERRO: " + e.getMessage());
        }
    }

    public void limparCampos() {
        numTextField.clear();
        descricaoTextField.clear();
        latDestinoTextField.clear();
        latOrigemTextField.clear();
        longDestinoTextField.clear();
        longOrigemTextField.clear();
        nomeClienteTextField.clear();
        txtAreaMensagem.clear();
        pesoTextField.clear();
        comboBoxTransporteTipo.setValue(null);
    }

    public void mostrarTransporte() {
        if (transporteService.getTransportes().isEmpty()) {
            txtAreaMensagem.setText("Nenhum transporte cadastrado.");
            return;
        }

        StringBuilder mensagem = new StringBuilder("Transportes cadastrados:\n\n");
        for (Transporte transporte : transporteService.getTransportes()) {
            mensagem.append(transporte.toString()).append("\n");
        }
        txtAreaMensagem.setText(mensagem.toString());
    }

    public void alterarSituacaoTransporte(int numero, String novaSituacao) {
        Transporte transporte = transporteService.getTransportes().stream()
                .filter(t -> t.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transporte não encontrado."));

        transporte.alterarSituacao(novaSituacao);
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }

    private void salvarTransportesEmJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY); // Configuração de visibilidade

        File arquivo = new File("transportes.json");

        try {
            // Converte a ObservableList para ArrayList
            ArrayList<Transporte> transportesList = new ArrayList<>(transportes);

            // Serializa a lista
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, transportesList);

            txtAreaMensagem.setText("Transportes salvos em arquivo JSON com sucesso!");
        } catch (IOException e) {
            txtAreaMensagem.setText("ERRO ao salvar os transportes em JSON: " + e.getMessage());
        }
    }

}

