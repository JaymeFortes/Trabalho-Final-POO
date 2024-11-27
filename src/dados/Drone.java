package dados;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public abstract class Drone {
    @JsonProperty
    private int codigo;
    @JsonProperty
    private double custoFixo;
    @JsonProperty
    private double autonomia;
    @JsonProperty
    private ArrayList<Transporte> transportes;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
        this.transportes = new ArrayList<>();
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
        return "Código: " + codigo + "\nCusto Fixo: R$ " + String.format("%.2f", custoFixo) +
                "\nAutonomia: " + String.format("%.2f", autonomia) + " KM\n" + "Custo por KM " + String.format("%.2f", calculaCustoKm()) + " KM\n";
    }
}
