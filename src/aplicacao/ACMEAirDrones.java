package aplicacao;

import dados.Drone;
import dados.DronePessoal;
import dados.DroneCargaViva;
import dados.DroneCargaInanimada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ACMEAirDrones {
	@FXML
	private TextField codigoField, autonomiaField, custoFixoField, pessoasField,pesoMaxField;
	@FXML
	private TextArea txtAreaMensagem;
	@FXML
	private Button cadastraButton, limparButton, sairButton, mostrarButton;
	@FXML
	private ComboBox<String> cbControl;
	@FXML
	private Label pessoasLabel,pesoMaxLabel;
	@FXML
	private CheckBox climaBox, proteBox;

	private ObservableList<Drone> drones = FXCollections.observableArrayList();

	public void initialize() {
		txtAreaMensagem.setEditable(false);

		cadastraButton.setOnAction(event -> cadastrarDrone());
		limparButton.setOnAction(event -> limparCampos());
		mostrarButton.setOnAction(event -> mostrarDrones());
		sairButton.setOnAction(event -> System.exit(0));

		cbControl.setItems(FXCollections.observableArrayList("Drone Pessoal", "Drone de Carga Viva", "Drone de Carga Inanimada"));

		cbControl.setOnAction(event -> {
			String tipoSelecionado = cbControl.getSelectionModel().getSelectedItem();
			boolean isDronePessoal = "Drone Pessoal".equals(tipoSelecionado);
			boolean isDroneCargaViva = "Drone de Carga Viva".equals(tipoSelecionado);
			boolean isDroneCargaInanimada = "Drone de Carga Inanimada".equals(tipoSelecionado);

			pessoasLabel.setVisible(isDronePessoal);
			pessoasField.setVisible(isDronePessoal);

			climaBox.setVisible(isDroneCargaViva);
			pesoMaxLabel.setVisible(isDroneCargaViva);
			pesoMaxField.setVisible(isDroneCargaViva);

			proteBox.setVisible(isDroneCargaInanimada);
			pesoMaxField.setVisible(isDroneCargaInanimada);
			pesoMaxLabel.setVisible(isDroneCargaInanimada);
		});

		pessoasLabel.setVisible(false);
		pessoasField.setVisible(false);
		climaBox.setVisible(false);
		pesoMaxField.setVisible(false);
		pesoMaxLabel.setVisible(false);
		proteBox.setVisible(false);

	}

	public void cadastrarDrone() {
		try {

			if (codigoField.getText().isEmpty() || autonomiaField.getText().isEmpty() || custoFixoField.getText().isEmpty()) {
				txtAreaMensagem.setText("ERRO: Todos os campos devem ser preenchidos.");
				return;
			}

			int codigo = Integer.parseInt(codigoField.getText());
			double autonomia = Double.parseDouble(autonomiaField.getText());
			double custoFixo = Double.parseDouble(custoFixoField.getText());

			for (Drone drone : drones) {
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
				DronePessoal dronePessoal = new DronePessoal(codigo, autonomia, custoFixo, quantidadePessoas);
				drones.add(dronePessoal);
				drones.sort((d1, d2) -> Integer.compare(d1.getCodigo(), d2.getCodigo()));
				txtAreaMensagem.setText("Drone Pessoal cadastrado com sucesso.");

			} else if ("Drone de Carga Viva".equals(tipo)) {
				if (pesoMaxField.getText().isEmpty()) {
					txtAreaMensagem.setText("ERRO: Insira o peso máximo.");
					return;
				}

				boolean climatizado = climaBox.isSelected();
				double pesoMaximo = Double.parseDouble(pesoMaxField.getText());

				DroneCargaViva droneCargaViva = new DroneCargaViva(codigo, autonomia, custoFixo, pesoMaximo, climatizado);
				drones.add(droneCargaViva);
				drones.sort((d1, d2) -> Integer.compare(d1.getCodigo(), d2.getCodigo()));
				txtAreaMensagem.setText("Drone de Carga Viva cadastrado com sucesso.");


			} else if ("Drone de Carga Inanimada".equals(tipo)) {
				if (pesoMaxField.getText().isEmpty()) {
					txtAreaMensagem.setText("Erro: Insira o peso máximo.");
					return;
				}

				boolean protecao = proteBox.isSelected();
				double pesoMaximo = Double.parseDouble(pesoMaxField.getText());
				DroneCargaInanimada droneCargaInanimada = new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMaximo, protecao);
				drones.add(droneCargaInanimada);
				drones.sort((d1, d2) -> Integer.compare(d1.getCodigo(), d2.getCodigo()));
				txtAreaMensagem.setText("Drone de Carga Inanimada cadastrado com sucesso.");
			}

		} catch (NumberFormatException e) {
			txtAreaMensagem.setText("ERRO: Valores inválidos nos campos de autonomia, custo fixo ou quantidade de pessoas.");
		} catch (IllegalArgumentException e) {
			txtAreaMensagem.setText("ERRO: " + e.getMessage());
		}
	}

	public void limparCampos() {

		codigoField.clear();
		autonomiaField.clear();
		custoFixoField.clear();
		pessoasField.clear();
		txtAreaMensagem.clear();
		pessoasLabel.setVisible(false);
		pessoasField.setVisible(false);
		climaBox.setVisible(false);
		pesoMaxField.setVisible(false);
		pesoMaxLabel.setVisible(false);
		proteBox.setVisible(false);
	}

	public void mostrarDrones() {
		StringBuilder mensagem = new StringBuilder("Drones cadastrados:\n\n");
		for (Drone drone : drones) {
			mensagem.append(drone.getTipoDrone()).append(": ").append(drone).append("\n\n");
		}
		txtAreaMensagem.setText(mensagem.toString());
	}
}
