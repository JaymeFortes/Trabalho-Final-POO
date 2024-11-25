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
	public String getTipoDrone() {
		return "Carga Inanimada";
	}

	@Override
	public double custoVariado() {
			double custo = 0;
			if (protecao) {
				custo = getCustoFixo() + 10.00;
			} else {
				custo = getCustoFixo() + 5.00;
			}
			return custo;
		}


	@Override
	public double calculaCustoKm() {
		return custoVariado() + getCustoFixo();
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
