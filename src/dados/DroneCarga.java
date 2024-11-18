package dados;

public abstract class DroneCarga extends Drone {
    private double pesoMaximo;

    public DroneCarga(int codigo, double custoFixo, double autonomia, double pesoMaximo) {
        super(codigo, custoFixo, autonomia);
        this.pesoMaximo = pesoMaximo;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public abstract String getTipoDrone();

    public abstract double custoVariado();

    @Override
    public double calculaCustoKm() {
        return getCustoFixo() + custoVariado();
    }

    @Override
    public String toString() {
        return super.toString() + "Peso Máximo: " + String.format("%.2f", pesoMaximo) + " KG";
    }
}
