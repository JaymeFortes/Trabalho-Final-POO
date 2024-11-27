package dados;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DronePessoal extends Drone {

    @JsonProperty
    private int qtdPessoas;

    public DronePessoal(int codigo, double custoFixo, double autonomia, int qtdPessoas) {
        super(codigo, custoFixo, autonomia);
        this.qtdPessoas = qtdPessoas;
    }

    @JsonProperty
    public int getQtdPessoas() {
        return qtdPessoas;
    }

    @JsonProperty
    public void setQtdPessoas(int QTDPessoas) {
        this.qtdPessoas = QTDPessoas;
    }

    @JsonProperty
    @Override
    public String getTipoDrone() {
        return "Drone pessoal";
    }

    @JsonProperty
    public double custoVariado() {
        return qtdPessoas * 2;
    }

    @JsonProperty
    @Override
    public double calculaCustoKm() {
        return custoVariado() + getCustoFixo();
    }

    @JsonProperty
    @Override
    public String toString() {
        return super.toString() + "Quantidade de pessoas: " + qtdPessoas + "\n";
    }
}
