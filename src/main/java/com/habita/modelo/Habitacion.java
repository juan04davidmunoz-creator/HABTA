
package com.habita.modelo;

public class Habitacion {

    private int numero;
    private Integer idTipo;
    private Double precio;
    private String estado;

    public Habitacion() {
    }

    public Habitacion(int numero, Integer idTipo, Double precio, String estado) {
        this.numero = numero;
        this.idTipo = idTipo;
        this.precio = precio;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", idTipo=" + idTipo +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                '}';
    }
}
