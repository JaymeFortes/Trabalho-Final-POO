package dados;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Drone {

	private int codigo;

	private double custoFixo;

	private double autonomia;

	private ArrayList<Transporte> transportes;

	public abstract double calculaCustoKm();

}
