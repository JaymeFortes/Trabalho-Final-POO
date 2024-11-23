package aplicacao;

import dados.Drone;
import dados.DronePessoal;
import dados.DroneCargaViva;
import dados.DroneCargaInanimada;
import javafx.collections.ObservableList;

public class ACMEAirDrones {
	private ObservableList<Drone> drones;

	public ACMEAirDrones(ObservableList<Drone> drones) {
		this.drones = drones;
	}

	public void cadastrarDrone(int codigo, double autonomia, double custoFixo, String tipo, int quantidadePessoas, boolean climatizado, boolean protecao, double pesoMaximo) {
		// Verifica se já existe um drone com o código
		for (Drone drone : drones) {
			if (drone.getCodigo() == codigo) {
				throw new IllegalArgumentException("ERRO: Já existe um drone com este código.");
			}
		}

		// Criação dos drones conforme o tipo
		if ("Drone Pessoal".equals(tipo)) {
			DronePessoal dronePessoal = new DronePessoal(codigo, autonomia, custoFixo, quantidadePessoas);
			drones.add(dronePessoal);
		} else if ("Drone de Carga Viva".equals(tipo)) {
			DroneCargaViva droneCargaViva = new DroneCargaViva(codigo, autonomia, custoFixo, pesoMaximo, climatizado);
			drones.add(droneCargaViva);
		} else if ("Drone de Carga Inanimada".equals(tipo)) {
			DroneCargaInanimada droneCargaInanimada = new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMaximo, protecao);
			drones.add(droneCargaInanimada);
		}

		// Ordena a lista de drones pelo código
		drones.sort((d1, d2) -> Integer.compare(d1.getCodigo(), d2.getCodigo()));
	}

	public String mostrarDrones() {
		StringBuilder mensagem = new StringBuilder("Drones cadastrados:\n\n");
		for (Drone drone : drones) {
			mensagem.append(drone.getTipoDrone()).append(": ").append(drone).append("\n\n");
		}
		return mensagem.toString();
	}

	public void limparDrones() {
		drones.clear();
	}
}
