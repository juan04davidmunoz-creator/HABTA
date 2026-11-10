package com.habita.reservas.dao;

import com.habita.conexion.ConexionBD;
import com.habita.reservas.modelo.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la gestión de reservas HABITA.
 */
public class ReservaDAO {

    /**
     * Consulta las habitaciones disponibles para un rango de fechas.
     */
    public List<Integer> listarHabitacionesDisponibles(
            Date entrada, Date salida) throws SQLException {

        List<Integer> habitaciones = new ArrayList<>();

        String sql = """
            SELECT h.numero
            FROM habitaciones h
            WHERE LOWER(h.estado) = 'disponible'
            AND NOT EXISTS (
                SELECT 1
                FROM reservas r
                WHERE r.numero_habitacion = h.numero
                AND LOWER(r.estado) = 'confirmada'
                AND r.fecha_entrada < ?
                AND r.fecha_salida > ?
            )
            ORDER BY h.numero
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, salida);
            ps.setDate(2, entrada);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    habitaciones.add(rs.getInt("numero"));
                }
            }
        }

        return habitaciones;
    }

    /**
     * Registra una reserva si no existe cruce de fechas.
     */
    public boolean insertar(Reserva reserva) throws SQLException {

        String sql = """
            INSERT INTO reservas
                (id_usuario, numero_habitacion,
                 fecha_entrada, fecha_salida, estado)
            SELECT ?, ?, ?, ?, 'confirmada'
            WHERE EXISTS (
                SELECT 1
                FROM habitaciones h
                WHERE h.numero = ?
                AND LOWER(h.estado) = 'disponible'
            )
            AND NOT EXISTS (
                SELECT 1
                FROM reservas r
                WHERE r.numero_habitacion = ?
                AND LOWER(r.estado) = 'confirmada'
                AND r.fecha_entrada < ?
                AND r.fecha_salida > ?
            )
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, reserva.getIdUsuario());
            ps.setInt(2, reserva.getNumeroHabitacion());
            ps.setDate(3, Date.valueOf(reserva.getFechaEntrada()));
            ps.setDate(4, Date.valueOf(reserva.getFechaSalida()));
            ps.setInt(5, reserva.getNumeroHabitacion());
            ps.setInt(6, reserva.getNumeroHabitacion());
            ps.setDate(7, Date.valueOf(reserva.getFechaSalida()));
            ps.setDate(8, Date.valueOf(reserva.getFechaEntrada()));

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Consulta las reservas registradas con el nombre del usuario.
     */
    public List<Reserva> listar() throws SQLException {

        List<Reserva> reservas = new ArrayList<>();

        String sql = """
            SELECT r.id_reserva, r.id_usuario, r.numero_habitacion,
                   r.fecha_entrada, r.fecha_salida, r.estado, u.nombre
            FROM reservas r
            INNER JOIN usuarios u ON u.id_usuario = r.id_usuario
            ORDER BY r.id_reserva DESC
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("id_usuario"),
                    rs.getInt("numero_habitacion"),
                    rs.getDate("fecha_entrada").toLocalDate(),
                    rs.getDate("fecha_salida").toLocalDate()
                );

                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setNombreUsuario(rs.getString("nombre"));

                reservas.add(reserva);
            }
        }

        return reservas;
    }
}
