package com.habita.modelo;

public class Habitacion {

    private int numero;
    private Integer idTipo;
    private double precio;
    private String estado;

    public Habitacion(int numero, Integer idTipo, double precio, String estado) {
        this.numero = numero;
        this.idTipo = idTipo;
        this.precio = precio;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public double getPrecio() {
        return precio;
    }

    public String getEstado() {
        return estado;
    }
}
