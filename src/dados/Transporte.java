package dados;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Transporte {
    @JsonProperty
    private int numero;
    @JsonProperty
    private String nomeCliente;
    @JsonProperty
    private String descricao;
    @JsonProperty
    private double peso;
    @JsonProperty
    private double latitudeOrigem;
    @JsonProperty
    private double longitudeOrigem;
    @JsonProperty
    private double latitudeDestino;
    @JsonProperty
    private double longitudeDestino;
    @JsonProperty
    private Estado situacao;
    @JsonIgnore
    private GeoCalculator geoCalculator = new GeoCalculator();
    @JsonProperty
    private Drone droneAlocado;

    public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem,
                      double latitudeDestino, double longitudeDestino, Estado situacao) {

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

    public Drone getDroneAlocado() {
        return droneAlocado;
    }

    public void setDrone(Drone drone) {
        if (drone != null) {
            this.droneAlocado = drone;
            System.out.println("Drone alocado com sucesso!");
        } else {
            throw new IllegalArgumentException("Drone não pode ser nulo.");
        }
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

    public abstract String getTipo();

    public abstract double calculaCusto();

    public abstract double calculaAcrescimos();

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
                "\nSituação: " + situacao;
    }
}