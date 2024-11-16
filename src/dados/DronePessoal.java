package dados;
import java.util.Locale;

public class DronePessoal extends Drone {

	private int QTDPessoas;
	private double custoFinal;
	private String descricao;
	private double peso;

	public DronePessoal(int codigo, double peso, double autonomia, int QTDPessoas, String descricao, double custoFixo, double custoFinal) {
		super(codigo, custoFixo, autonomia);
		this.peso = peso;
		this.QTDPessoas = QTDPessoas;
		this.descricao = descricao;
		this.custoFinal = custoFinal;
	}

	public int getQTDPessoas() {
		return QTDPessoas;
	}
	public void setQTDPessoas(int QTDPessoas) {
		this.QTDPessoas = QTDPessoas;
	}
	public double getCustoFinal() {
		return custoFinal;
	}
	public void setCustoFinal(double custoFinal) {
		this.custoFinal = custoFinal;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String toString() {
		return "Código: " + getCodigo() + ", Peso suportado: " + String.format(Locale.US, "%.2f",getPeso()) + " Kg, Distancia de autonomia: " + String.format(Locale.US, "%.2f",getAutonomia()) + " Km, " + getQTDPessoas() + " Pessoas, Descrição: " + getDescricao() ;
	}

	public double calculaCustoKm() {
		return 0;
	}
}
