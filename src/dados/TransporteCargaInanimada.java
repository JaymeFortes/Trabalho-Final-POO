package dados;

public class TransporteCargaInanimada extends Transporte {

    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem,
                                    double latitudeDestino, double longitudeDestino, Estado estado,
                                    boolean cargaPerigosa) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino, estado);
        this.cargaPerigosa = cargaPerigosa;
    }

    public boolean isCargaPerigosa() {
        return cargaPerigosa;
    }

    public void setCargaPerigosa(boolean cargaPerigosa) {
        this.cargaPerigosa = cargaPerigosa;
    }

    public String estadoTransporte() {
        if (isCargaPerigosa()) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    @Override
    public double calculaAcrescimos() {
        double acrescimo = 0;
        if (isCargaPerigosa()) {
            acrescimo = 500;
        } else {
            acrescimo = 0;
        }
        return acrescimo;
    }

    @Override
    public double calculaCusto() {
        double distancia = calculaDistancia();
        Drone drone = getDroneAlocado();
        if (drone == null) {
            throw new IllegalStateException("Nenhum drone associado a este transporte.");
        }
        return (drone.calculaCustoKm() * distancia) + calculaAcrescimos();
    }

    @Override
    public String getTipo() {
        return "Carga Inanimada";
    }

    @Override
    public void setDrone(Drone drone) {
        if (drone instanceof DroneCargaInanimada) {
            super.setDrone(drone);
        } else {
            throw new IllegalArgumentException("Este transporte só aceita drones do tipo Carga.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n Carga Perigosa: " + estadoTransporte();
    }
}
