package aplicacao;

import dados.DroneCarga;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class ACMEAirDrones {
	private ObservableList<DroneCarga> drones = FXCollections.observableArrayList();

	public String cadastrarDrone(int codigo, double autonomia, double custoFixo, double pesoMax, boolean isViva, boolean isInanimada, boolean climatizado, boolean possuiProtecao) {
		// Verifica duplicação de código
		for (DroneCarga drone : drones) {
			if (drone.getCodigo() == codigo) {
				return "ERRO: Já existe um drone cadastrado com esse código.";
			}
		}

		// Cadastro de drone
		if (isViva) {
			// Criação de Drone de Carga Viva
			drones.add(new DroneCargaViva(codigo, autonomia, custoFixo, pesoMax, climatizado));
			drones.sort(Comparator.comparing(DroneCarga::getCodigo));
			return "Drone de Carga Viva cadastrado com sucesso!\n" + drones.get(drones.size() - 1);
		} else if (isInanimada) {
			// Criação de Drone de Carga Inanimada
			drones.add(new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMax, possuiProtecao));
			drones.sort(Comparator.comparing(DroneCarga::getCodigo));
			return "Drone de Carga Inanimada cadastrado com sucesso!\n" + drones.get(drones.size() - 1);
		}

		return "ERRO: Nenhum tipo de drone foi selecionado.";
	}

	public String mostrarDrones() {
		if (drones.isEmpty()) {
			return "Nenhum drone cadastrado";
		}

		StringBuilder todosDrones = new StringBuilder("Drones de carga cadastrados:\n\n");
		for (DroneCarga drone : drones) {
			todosDrones.append(drone.getTipoDrone()).append(": ").append(drone).append("\n\n");
		}
		return todosDrones.toString();
	}

	public ObservableList<DroneCarga> getDrones() {
		return drones;
	}
}
