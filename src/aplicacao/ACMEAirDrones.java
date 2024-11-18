package aplicacao;

import dados.DroneCarga;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.Random;

public class ACMEAirDrones {
	@FXML
	private TextField codigoField, autonomiaField, custoFixoField, pesoMaximoField;
	@FXML
	private TextArea txtAreaMensagem;
	@FXML
	private Button cadastraButton, limparButton, sairButton, mostrarButton;
	@FXML
	private RadioButton droneVivaRadio, droneInanimadaRadio;

	private ObservableList<DroneCarga> drones = FXCollections.observableArrayList();

	public void initialize() {
		txtAreaMensagem.setEditable(false);

		cadastraButton.setOnAction(event -> cadastrarDrone());
		limparButton.setOnAction(event -> limparCampos());
		mostrarButton.setOnAction(event -> mostrarDrones());
		sairButton.setOnAction(event -> System.exit(0));

		ToggleGroup group = new ToggleGroup();
		droneVivaRadio.setToggleGroup(group);
		droneInanimadaRadio.setToggleGroup(group);
		droneVivaRadio.setOnAction(event -> txtAreaMensagem.setText("Drone de Carga Viva selecionado!"));
		droneInanimadaRadio.setOnAction(event -> txtAreaMensagem.setText("Drone de Carga Inanimada selecionado!"));
	}

	public void cadastrarDrone() {
		try {

			if (codigoField.getText().isEmpty() ||
					autonomiaField.getText().isEmpty() ||
					custoFixoField.getText().isEmpty() ||
					pesoMaximoField.getText().isEmpty()) {
				txtAreaMensagem.setText("Todos os campos devem ser preenchidos!");
				return;
			}

			int codigo = Integer.parseInt(codigoField.getText());
			double autonomia = Double.parseDouble(autonomiaField.getText());
			double custoFixo = Double.parseDouble(custoFixoField.getText());
			double pesoMax = Double.parseDouble(pesoMaximoField.getText());

			for (DroneCarga drone : drones) {
				if (drone.getCodigo() == codigo) {
					txtAreaMensagem.setText("ERRO: Já existe um drone cadastrado com esse código.");
					return;
				}
			}

			Random random = new Random();
			if (droneVivaRadio.isSelected()) {
				boolean climatizado = random.nextBoolean();
				drones.add(new DroneCargaViva(codigo, autonomia, custoFixo, pesoMax, climatizado));
				txtAreaMensagem.setText("Drone de Carga Viva cadastrado com sucesso!\n" +
						drones.get(drones.size() - 1));
			} else if (droneInanimadaRadio.isSelected()) {
				boolean possuiProtecao = random.nextBoolean();
				drones.add(new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMax, possuiProtecao));
				txtAreaMensagem.setText("Drone de Carga Inanimada cadastrado com sucesso!\n" +
						drones.get(drones.size() - 1));
			} else {
				txtAreaMensagem.setText("ERRO: Nenhum tipo de drone foi selecionado.");
				return;
			}

			drones.sort(Comparator.comparing(DroneCarga::getCodigo));

		} catch (NumberFormatException e) {
			txtAreaMensagem.setText("ERRO: Autonomia, custo fixo e peso máximo devem ser números válidos.");
		} catch (IllegalArgumentException e) {
			txtAreaMensagem.setText("ERRO: " + e.getMessage());
		}
	}

	public void limparCampos() {
		codigoField.setText("");
		txtAreaMensagem.setText("");
		autonomiaField.setText("");
		custoFixoField.setText("");
		pesoMaximoField.setText("");
		droneVivaRadio.setSelected(false);
		droneInanimadaRadio.setSelected(false);
	}

	public void mostrarDrones() {
		if (drones.isEmpty()) {
			txtAreaMensagem.setText("Nenhum drone cadastrado");
		} else {
			StringBuilder todosDrones = new StringBuilder("Drones de carga cadastrados:\n\n");
			for (DroneCarga drone : drones) {
				todosDrones.append(drone.getTipoDrone()).append(": ").append(drone).append("\n\n");
			}

			txtAreaMensagem.setText(todosDrones.toString());
		}
	}
}