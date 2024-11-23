package dados;


public abstract class DroneCarga extends Drone {
    private double pesoMaximo;

    public DroneCarga(int codigo, double autonomia, double pesoMaximo, double custoFixo) {
        super(codigo, autonomia, custoFixo);
        this.pesoMaximo = pesoMaximo;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public abstract String getTipoDrone();

    public double calculaCustoVariado() {
        return 0;
    }

    public abstract double custoVariado();

    @Override
    public double calculaCustoKm() {
        return super.calculaCustoKm();
    }

    @Override
    public String toString() {
        return super.toString() + "Peso Máximo: " + String.format("%.2f", pesoMaximo) + " KG";
    }
}