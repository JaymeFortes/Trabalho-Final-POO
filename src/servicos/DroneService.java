package servicos;

import dados.Drone;
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
}
