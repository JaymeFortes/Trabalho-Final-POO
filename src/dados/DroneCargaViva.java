package dados;

public class DroneCargaViva extends DroneCarga {

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
        return "Carga Viva";
    }

    @Override
    public double custoVariado() {
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
        return custoVariado() + getCustoFixo();
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
