package servicos;

import dados.Transporte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransporteService {
    private ObservableList<Transporte> transportes = FXCollections.observableArrayList();

    public ObservableList<Transporte> getTransportes() {
        return transportes;
    }

    public void adicionarTransporte(Transporte transporte) {
        transportes.add(transporte);
    }

    public void limparTransportes() {
        transportes.clear();
    }
}
