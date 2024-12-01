package servicos;

import dados.Transporte;
import java.util.LinkedList;
import java.util.Queue;

public class TransporteService {
    private Queue<Transporte> transportes = new LinkedList<>();

    public Queue<Transporte> getTransportes() {
        return transportes;
    }

    public void adicionarTransporte(Transporte transporte) {
        transportes.add(transporte);
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
        for (Transporte t : transportes) {
            if (t.getNumero() == transporte.getNumero()) {
                transportes.remove(t);
                transportes.add(transporte);
                return;
            }
        }
        throw new IllegalArgumentException("Transporte com o número " + transporte.getNumero() + " não encontrado.");
    }
}