package dados;

public class TransporteCargaInanimada extends Transporte {

    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero,
                                    String nomeCliente,
                                    String descricao,
                                    double peso,
                                    double latitudeOrigem,
                                    double longitudeOrigem,
                                    double latitudeDestino,
                                    double longitudeDestino,
                                    boolean cargaPerigosa) {
        super(numero,
                nomeCliente,
                descricao,
                peso,
                latitudeOrigem,
                longitudeOrigem,
                latitudeDestino,
                longitudeDestino);
        this.cargaPerigosa = cargaPerigosa;
    }

    public boolean isCargaPerigosa() {
        return cargaPerigosa;
    }

    public void setCargaPerigosa(boolean cargaPerigosa) {
        this.cargaPerigosa = cargaPerigosa;
    }

    @Override
    public double getAcrecimos() {
        return cargaPerigosa ? 500.00 : 0.00;
    }
}
