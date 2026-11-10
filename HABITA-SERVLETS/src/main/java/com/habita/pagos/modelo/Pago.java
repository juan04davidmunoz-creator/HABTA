package com.habita.pagos.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modelo de pagos del sistema HABITA.
 */
public class Pago {

    private int idPago;
    private int idReserva;
    private String metodoPago;
    private BigDecimal valor;
    private LocalDate fechaPago;
    private String estado;

    public Pago() {
    }

    public Pago(int idReserva, String metodoPago, BigDecimal valor,
                LocalDate fechaPago, String estado) {
        this.idReserva = idReserva;
        this.metodoPago = metodoPago;
        this.valor = valor;
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
