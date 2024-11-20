package dados;

public class TransporteCargaViva extends Transporte implements CalculaAcrescimos {

	private double temperaturaMinima;
	private double temperaturaMaxima;

	public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem,
							   double latitudeDestino, double longitudeDestino, double temperaturaMinima,
							   double temperaturaMaxima,Estado estado,GeoCalculator geoCalculator) {

		super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,estado, geoCalculator);
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

	public boolean isTempaturaMaiorque10(){
		if(temperaturaMinima > 10){
			return true;
		} else {
			return false;
		}
	}

	public String estadoTransporte(){
		if (isTempaturaMaiorque10()){
			return "Sim";
		} else {
			return "Nao";
		}
	}

	@Override
	public double calculaAcrescimo() {
		double acrescimo = 0;
		if (isTempaturaMaiorque10()){
			acrescimo = 500;
		} else {
			acrescimo = 0;
		}
		return acrescimo;
	}

	@Override
	public double calculaCusto() {
		double distancia = calculaDistancia();
		return (getDrone().calculaCustoKm() * distancia) + calculaAcrescimo();
	}

	@Override
	public String toString() {
		return super.toString() + "\n Temperatura maior que 10: " + estadoTransporte();
	}
}
