package dados;
import java.util.Locale;

public class DronePessoal extends DroneCarga {
	private int qtdPessoas;

	public DronePessoal(int codigo, double custoFixo, double autonomia, double pesoMaximo,int qtdPessoas) {
		super(codigo, custoFixo, autonomia, pesoMaximo);
		this.qtdPessoas = qtdPessoas;
	}

	public int getQtdPessoas() {
		return qtdPessoas;
	}
	public void setQtdPessoas(int QTDPessoas) {
		this.qtdPessoas = QTDPessoas;
	}

	public double calculaCustoKm() {
		return 0;
	}

	@Override
	public String toString() {
		return super.toString() + "Quantidade de pessoas: " + qtdPessoas;
	}
}
