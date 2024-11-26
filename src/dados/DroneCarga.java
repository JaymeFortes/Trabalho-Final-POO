package dados;


public abstract class DroneCarga extends Drone {
    private double pesoMaximo;

    public DroneCarga(int codigo, double autonomia, double custoFixo, double pesoMaximo) {
        super(codigo, autonomia, custoFixo);
        this.pesoMaximo = pesoMaximo;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public abstract double custoVariado();

    @Override
    public String toString() {
        return super.toString() + "Peso Máximo: " + String.format("%.2f", pesoMaximo) + " KG\n";
    }
}