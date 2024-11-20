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

	public Transporte(int numero, String nomeCliente, String descricao, double peso,
					  double latitudeOrigem, double longitudeOrigem,
					  double latitudeDestino, double longitudeDestino) {
		this.numero = numero;
		this.nomeCliente = nomeCliente;
		this.descricao = descricao;
		this.peso = peso;
		this.latitudeOrigem = latitudeOrigem;
		this.longitudeOrigem = longitudeOrigem;
		this.latitudeDestino = latitudeDestino;
		this.longitudeDestino = longitudeDestino;
		this.situacao = Estado.PENDENTE;
	}


	public double calculaCustoBase() {
		double distancia = calculaDistancia(latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino);
		return drone.calculaCustoKm() * distancia;
	}


	public abstract double getAcrecimos();


	public double calculaCusto() {
		return calculaCustoBase() + getAcrecimos();
	}


	private double calculaDistancia(double latitude1,
									double longitude1,
									double latitude2,
									double longitude2) {
		final int R = 6371; // Raio da Terra em km
		double distanciaLatitude = Math.toRadians(latitude2 - latitude1);
		double distanciaLongitude = Math.toRadians(longitude2 - longitude1);
		double a = Math.sin(distanciaLatitude / 2) * Math.sin(distanciaLatitude / 2) +
				Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
						Math.sin(distanciaLongitude / 2) * Math.sin(distanciaLongitude / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
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

	@Override
	public String toString() {
		return "Transporte Número: " + numero +
				"\nCliente: " + nomeCliente +
				"\nDescrição: " + descricao +
				"\nPeso: " + peso +
				"\nOrigem: (" + latitudeOrigem + ", " + longitudeOrigem + ")" +
				"\nDestino: (" + latitudeDestino + ", " + longitudeDestino + ")" +
				"\nSituação: " + situacao +
				"\nDrone Alocado: " + (drone == null ? "Nenhum" : drone);
	}
}
