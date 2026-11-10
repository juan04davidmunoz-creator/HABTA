package com.habita.pagos.dao;

import com.habita.conexion.ConexionBD;
import com.habita.pagos.modelo.Pago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para gestionar los pagos de HABITA.
 */
public class PagoDAO {

    /**
     * Registra un pago asociado a una reserva.
     */
    public boolean registrar(Pago pago) throws SQLException {
        String sql = "INSERT INTO pagos "
                + "(id_reserva, metodo_pago, valor, fecha_pago, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setInt(1, pago.getIdReserva());
            sentencia.setString(2, pago.getMetodoPago());
            sentencia.setBigDecimal(3, pago.getValor());
            sentencia.setDate(4, Date.valueOf(pago.getFechaPago()));
            sentencia.setString(5, pago.getEstado());

            return sentencia.executeUpdate() > 0;
        }
    }

    /**
     * Consulta todos los pagos registrados.
     */
    public List<Pago> listar() throws SQLException {
        List<Pago> pagos = new ArrayList<>();

        String sql = "SELECT id_pago, id_reserva, metodo_pago, "
                + "valor, fecha_pago, estado FROM pagos "
                + "ORDER BY id_pago";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Pago pago = new Pago();

                pago.setIdPago(resultado.getInt("id_pago"));
                pago.setIdReserva(resultado.getInt("id_reserva"));
                pago.setMetodoPago(resultado.getString("metodo_pago"));
                pago.setValor(resultado.getBigDecimal("valor"));

                Date fecha = resultado.getDate("fecha_pago");
                if (fecha != null) {
                    pago.setFechaPago(fecha.toLocalDate());
                }

                pago.setEstado(resultado.getString("estado"));
                pagos.add(pago);
            }
        }

        return pagos;
    }
}
