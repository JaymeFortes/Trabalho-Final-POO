package dados;

public class DroneCargaViva extends DroneCarga {

	private boolean climatizado;

	public DroneCargaViva(int codigo, double custoFixo, double autonomia, double pesoMaximo,boolean climatizado) {
		super(codigo, custoFixo, autonomia, pesoMaximo);
		this.climatizado = climatizado;
	}

	public boolean isClimatizado() {
		return climatizado;
	}

	public void setClimatizado(boolean climatizado) {
		this.climatizado = climatizado;
	}

	@Override
	public double calculaCustoKm() {
		return super.calculaCustoKm();
	}

	public String estadoClimatizado() {
		if (isClimatizado()) {
			return "Sim";
		} else {
			return "Nao";
		}
	}

	@Override
	public String toString() {
		return super.toString() + "\nClimatizado: " + estadoClimatizado();
	}
}
