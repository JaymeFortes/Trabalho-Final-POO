package dados;

public class TransporteCargaViva extends Transporte {

	private double temperaturaMinima;

	private double temperaturaMaxima;

	@Override
	public double calculaCusto() {
		return 0;
	}
}
