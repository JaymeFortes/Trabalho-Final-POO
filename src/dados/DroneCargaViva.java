package dados;

public class DroneCargaViva extends DroneCarga implements CustoVariado {

    private boolean climatizado;

    public DroneCargaViva(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean climatizado) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.climatizado = climatizado;
    }

    public boolean isClimatizado() {
        return climatizado;
    }

    public void setClimatizado(boolean climatizado) {
        this.climatizado = climatizado;
    }

    @Override
    public String getTipoDrone() {
        return "Drone Carga Viva";
    }

    @Override
    public double calcularCustoVariado() {
        double custo = 0;
        if (climatizado) {
            custo = getCustoFixo() + 20.00;
        } else {
            custo = getCustoFixo() + 10.00;
        }
        return custo;
    }

    @Override
    public double calculaCustoKm() {
        return super.calculaCustoKm() + calcularCustoVariado();
    }

    public String estadoClimatizado() {
        if (isClimatizado()) {
            return "Sim";
        } else {
            return "Nao";
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nClimatizado: " + estadoClimatizado();
    }
}
