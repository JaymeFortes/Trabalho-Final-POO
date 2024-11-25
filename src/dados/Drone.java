package dados;

import java.util.ArrayList;

public abstract class Drone {

    private int codigo;
    private double custoFixo;
    private double autonomia;
    private ArrayList<Transporte> transportes;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
        this.transportes = new ArrayList<>(); // Inicialize o atributo da classe
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCustoFixo(double custoFixo) {
        this.custoFixo = custoFixo;
    }

    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public double getCustoFixo() {
        return this.custoFixo;
    }

    public double getAutonomia() {
        return this.autonomia;
    }


    public abstract String getTipoDrone();

    public double calculaCustoKm() {
        return 0;
    }


    public String toString() {
        return "\nCódigo: " + codigo + "\nCusto Fixo: R$ " + String.format("%.2f", custoFixo) +
                "\nAutonomia: " + String.format("%.2f", autonomia) + " KM\n";
    }

    public void alocarParaTransporte(Transporte transporte) {

    }
}
