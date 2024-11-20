package dados;

public class TransportePessoal extends Transporte implements CalculaAcrescimos {
    int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
                             double longitudeOrigem, double latitudeDestino, double longitudeDestino,
                             Estado situacao, GeoCalculator geoCalculator) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino, situacao, geoCalculator);
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public double calculaAcrescimo() {
        return qtdPessoas * 10;
    }

    @Override
    public double calculaCusto() {
        double distancia = calculaDistancia();
        return (getDrone().calculaCustoKm() * distancia) + calculaAcrescimo();
    }

    @Override
    public String toString() {
        return super.toString() + "\n Quantidade de pessoas: " + qtdPessoas;
    }
}



