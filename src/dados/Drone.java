package dados;

import java.util.Collection;

public abstract class Drone {

	private int codigo;

	private double custoFixo;

	private double autonomia;

	private Collection<Transporte> transporte;

	public abstract double calculaCustoKm();

}
