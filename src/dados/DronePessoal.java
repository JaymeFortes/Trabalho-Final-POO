package dados;

public class DronePessoal extends Drone  {
    private int qtdPessoas;

    public DronePessoal(int codigo, double custoFixo, double autonomia,  int qtdPessoas) {
        super(codigo, custoFixo, autonomia);
        this.qtdPessoas = qtdPessoas;
    }

    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(int QTDPessoas) {
        this.qtdPessoas = QTDPessoas;
    }


    @Override
    public String getTipoDrone() {
        return "Drone pessoal";
    }

    public double custoVariado(){
        return qtdPessoas * 2;
    }

    @Override
    public double calculaCustoKm() {
        return custoVariado() + getCustoFixo();
    }

    @Override
    public String toString() {
        return super.toString() + "Quantidade de pessoas: " + qtdPessoas;
    }
}
