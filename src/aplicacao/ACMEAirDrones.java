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
	private Label pessoasLabel,pesoMaxLabel; // Label para "Quantidade de Pessoas"
	@FXML
	private CheckBox climaBox; // Checkbox para "Climatizado"

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


			pessoasLabel.setVisible(isDronePessoal);
			pessoasField.setVisible(isDronePessoal); // Torna o campo de texto visível apenas se for Drone Pessoal

			// Torna o CheckBox "Climatizado" visível apenas se for "Drone de Carga Viva"
			climaBox.setVisible(isDroneCargaViva);// Torna o CheckBox visível apenas se for Drone de Carga Viva
			pesoMaxLabel.setVisible(isDroneCargaViva);
			pesoMaxField.setVisible(isDroneCargaViva);
		});

		// Inicialmente, oculta o campo de pessoas e o CheckBox de clima
		pessoasLabel.setVisible(false);
		pessoasField.setVisible(false);
		climaBox.setVisible(false);
		pesoMaxField.setVisible(false);
		pesoMaxLabel.setVisible(false);
	}

	public void cadastrarDrone() {
		try {
			// Verificação dos campos obrigatórios
			if (codigoField.getText().isEmpty() || autonomiaField.getText().isEmpty() || custoFixoField.getText().isEmpty()) {
				txtAreaMensagem.setText("ERRO: Todos os campos devem ser preenchidos.");
				return;
			}

			int codigo = Integer.parseInt(codigoField.getText());
			double autonomia = Double.parseDouble(autonomiaField.getText());
			double custoFixo = Double.parseDouble(custoFixoField.getText());

			// Verifica se o drone já está cadastrado
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

			// Cadastro de Drone Pessoal
			if ("Drone Pessoal".equals(tipo)) {
				if (pessoasField.getText().isEmpty()) {
					txtAreaMensagem.setText("ERRO: Insira a quantidade de pessoas.");
					return;
				}

				int quantidadePessoas = Integer.parseInt(pessoasField.getText());
				DronePessoal dronePessoal = new DronePessoal(codigo, autonomia, custoFixo, quantidadePessoas);
				drones.add(dronePessoal);
				drones.sort((d1, d2) -> Integer.compare(d1.getCodigo(), d2.getCodigo())); // Ordena pela quantidade de código
				txtAreaMensagem.setText("Drone Pessoal cadastrado com sucesso.");

				// Cadastro de Drone de Carga Viva
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
				DroneCargaInanimada droneCargaInanimada = new DroneCargaInanimada(codigo, autonomia, custoFixo, 0, true);
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
	}

	public void mostrarDrones() {

		StringBuilder mensagem = new StringBuilder("Drones cadastrados:\n\n");
		for (Drone drone : drones) {
			mensagem.append(drone.getTipoDrone()).append(": ").append(drone).append("\n\n");
		}
		txtAreaMensagem.setText(mensagem.toString());
	}
}
