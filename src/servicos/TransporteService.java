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

    public Transporte buscarTransportePelo(int numero) {
        for (Transporte transporte : transportes) {
            if (transporte.getNumero() == numero) {
                return transporte;
            }
        }
        return null;
    }

    public void atualizarTransporte(Transporte transporte) {
        for (int i = 0; i < transportes.size(); i++) {
            if (transportes.get(i).getNumero() == transporte.getNumero()) {
                transportes.set(i, transporte); // Atualiza o transporte na lista
                return;
            }
        }
        throw new IllegalArgumentException("Transporte com o número " + transporte.getNumero() + " não encontrado.");
    }
}



