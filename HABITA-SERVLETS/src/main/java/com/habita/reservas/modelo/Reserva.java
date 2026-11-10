package com.habita.reservas.modelo;

import java.time.LocalDate;

/**
 * Modelo que representa una reserva de habitación en HABITA.
 */
public class Reserva {

    private int idReserva;
    private int idUsuario;
    private int numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estado;
    private String nombreUsuario;

    public Reserva(int idUsuario, int numeroHabitacion,
                   LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.idUsuario = idUsuario;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = "confirmada";
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
