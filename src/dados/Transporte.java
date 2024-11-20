package dados;

public abstract class Transporte {

	private int numero;
	private String nomeCliente;
	private String descricao;
	private double peso;
	private double latitudeOrigem;
	private double longitudeOrigem;
	private double latitudeDestino;
	private double longitudeDestino;
	private Estado situacao;
	private Drone drone;
	private GeoCalculator geoCalculator;

	public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem,
					  double latitudeDestino, double longitudeDestino, Estado situacao, GeoCalculator geoCalculator) {
		this.numero = numero;
		this.nomeCliente = nomeCliente;
		this.descricao = descricao;
		this.peso = peso;
		this.latitudeOrigem = latitudeOrigem;
		this.longitudeOrigem = longitudeOrigem;
		this.latitudeDestino = latitudeDestino;
		this.longitudeDestino = longitudeDestino;
		this.situacao = Estado.PENDENTE;
		this.geoCalculator = geoCalculator;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getLatitudeOrigem() {
		return latitudeOrigem;
	}

	public void setLatitudeOrigem(double latitudeOrigem) {
		this.latitudeOrigem = latitudeOrigem;
	}

	public double getLongitudeOrigem() {
		return longitudeOrigem;
	}

	public void setLongitudeOrigem(double longitudeOrigem) {
		this.longitudeOrigem = longitudeOrigem;
	}

	public double getLatitudeDestino() {
		return latitudeDestino;
	}

	public void setLatitudeDestino(double latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}

	public double getLongitudeDestino() {
		return longitudeDestino;
	}

	public void setLongitudeDestino(double longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}

	public Estado getSituacao() {
		return situacao;
	}

	public void setSituacao(Estado situacao) {
		this.situacao = situacao;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public abstract double calculaCusto();


	public double calculaDistancia() {
		return geoCalculator.calculaDistancia(latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino);
	}

	@Override
	public String toString() {
		return "Transporte Número: " + numero +
				"\nCliente: " + nomeCliente +
				"\nDescrição: " + descricao +
				"\nPeso: " + peso +
				"\nOrigem: (" + latitudeOrigem + ", " + longitudeOrigem + ")" +
				"\nDestino: (" + latitudeDestino + ", " + longitudeDestino + ")" +
				"\nSituação: " + situacao +
				"\nStatus: " + (drone == null ? "Nenhum" : drone);
	}
}
