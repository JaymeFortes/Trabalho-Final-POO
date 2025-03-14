package dados;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransporteCargaViva extends Transporte {
    @JsonProperty
    private double temperaturaMinima;
    @JsonProperty
    private double temperaturaMaxima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem,
                               double latitudeDestino, double longitudeDestino, Estado estado, double temperaturaMinima,
                               double temperaturaMaxima) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino, estado);
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public boolean isTempaturaMaiorque10() {
        double intervaloTemperatura = Math.abs(temperaturaMaxima - temperaturaMinima);
        if (intervaloTemperatura > 10) {
            return true;
        }
        return false;
    }

    public String estadoTransporte() {
        if (isTempaturaMaiorque10()) {
            return " Sim";
        } else {
            return " Não";
        }
    }

    @Override
    public double calculaAcrescimos() {
        double acrescimo = 0;
        if (isTempaturaMaiorque10()) {
            acrescimo = 1000;
        } else {
            acrescimo = 0;
        }
        return acrescimo;
    }

    @Override
    public double calculaCusto() {
        double distancia = calculaDistancia();
        return (getDroneAlocado().calculaCustoKm() * distancia) + calculaAcrescimos();
    }

    @Override
    public String getTipo() {
        return "Carga Viva";
    }

    @Override
    public void setDrone(Drone drone) {
        if (drone instanceof DroneCargaViva) {
            super.setDrone(drone);
        } else {
            throw new IllegalArgumentException("Este transporte só aceita drones do tipo Pessoal.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n Temperatura maior que 10: " + estadoTransporte() + "\n";
    }
}
