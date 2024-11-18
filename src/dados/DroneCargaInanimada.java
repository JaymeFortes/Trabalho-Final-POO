package dados;

public class DroneCargaInanimada extends DroneCarga {

	private boolean protecao;

	public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo,boolean protecao) {
		super(codigo, custoFixo, autonomia, pesoMaximo);
		this.protecao = protecao;
	}

	public boolean isProtecao() {
		return protecao;
	}

	public void setProtecao(boolean protecao) {
		this.protecao = protecao;
	}

	@Override
	public double calculaCustoKm() {
		return super.calculaCustoKm();
	}

	public String EstadoProtecao() {
		if (isProtecao()) {
			return "Sim";
		} else {
			return "Nao";
		}
	}

	@Override
	public String toString() {
		return super.toString() + "\nProteção: " + EstadoProtecao();
	}
}
