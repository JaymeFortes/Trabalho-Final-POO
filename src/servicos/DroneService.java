package servicos;

import dados.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DroneService {
    private ObservableList<Drone> drones = FXCollections.observableArrayList();

    public ObservableList<Drone> getDrones() {
        return drones;
    }

    public void adicionarDrone(Drone drone) {
        drones.add(drone);
    }

    public void limparDrones() {
        drones.clear();
    }

    public Drone buscarDroneParaTransporte(Transporte transporte) {
        for (Drone drone : drones) {
            if (transporte instanceof TransportePessoal && drone instanceof DronePessoal) {
                return drone;
            } else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
                return drone;
            } else if (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) {
                return drone;
            }
        }
        return null;  // Retorna null caso não encontre um drone compatível
    }
}
