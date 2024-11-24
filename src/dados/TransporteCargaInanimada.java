package dados;

public class TransporteCargaInanimada extends Transporte  {

    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem,
                                    double latitudeDestino, double longitudeDestino, Estado estado,
                                    boolean cargaPerigosa,GeoCalculator geoCalculator) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,estado,geoCalculator);
        this.cargaPerigosa = cargaPerigosa;
    }

    public boolean isCargaPerigosa() {
        return cargaPerigosa;
    }

    public void setCargaPerigosa(boolean cargaPerigosa) {
        this.cargaPerigosa = cargaPerigosa;
    }

    public String estadoTransporte(){
        if (isCargaPerigosa()){
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
        return (getDrone().calculaCustoKm() * distancia) + calculaAcrescimos();
    }

    @Override
    public String getTipoTransporte() {
        return "Transporte de Carga Inanimada:";
    }

    @Override
    public String toString() {
        return super.toString() + "\n Carga Perigosa: " + estadoTransporte();
    }
}
