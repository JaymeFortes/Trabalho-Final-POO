package dados;

public class TransporteCargaViva extends Transporte {

	private double temperaturaMinima;
	private double temperaturaMaxima;

	public TransporteCargaViva(int numero,
							   String nomeCliente,
							   String descricao,
							   double peso,
							   double latitudeOrigem,
							   double longitudeOrigem,
							   double latitudeDestino,
							   double longitudeDestino,
							   double temperaturaMinima,
							   double temperaturaMaxima) {
		super(numero,
				nomeCliente,
				descricao,
				peso,
				latitudeOrigem,
				longitudeOrigem,
				latitudeDestino,
				longitudeDestino);
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

	@Override
	public double getAcrecimos() {
		return (temperaturaMaxima - temperaturaMinima > 10) ? 1000.00 : 0.00;
	}
}
