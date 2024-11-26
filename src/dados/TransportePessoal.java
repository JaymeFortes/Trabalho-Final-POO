package dados;

public class TransportePessoal extends Transporte {
    private int qtdPessoas;


    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
                             double longitudeOrigem, double latitudeDestino, double longitudeDestino,
                             Estado situacao, int qtdPessoas) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino, situacao);
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public double calculaAcrescimos() {
        return qtdPessoas * 10;
    }

    @Override
    public double calculaCusto() {
        double distancia = calculaDistancia();
        return (getDroneAlocado().calculaCustoKm() * distancia) + calculaAcrescimos();
    }

    @Override
    public String getTipo() {
        return "Drone pessoal";
    }

    @Override
    public String toString() {
        return super.toString() + "\nQuantidade de pessoas: " + qtdPessoas + "\n";
    }

    @Override
    public void setDrone(Drone drone) {
        if (drone instanceof DronePessoal) {
            super.setDrone(drone);
        } else {
            throw new IllegalArgumentException("Este transporte só aceita drones do tipo Pessoal.");
        }
    }
}



