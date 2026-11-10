package com.habita.dao;

import com.habita.conexion.ConexionBD;
import com.habita.modelo.Habitacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    public List<Habitacion> listar() throws SQLException {

        List<Habitacion> habitaciones = new ArrayList<>();

        String sql = "SELECT numero, id_tipo, precio, estado "
                   + "FROM habitaciones ORDER BY numero";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Habitacion habitacion = new Habitacion(
                    rs.getInt("numero"),
                    (Integer) rs.getObject("id_tipo"),
                    rs.getDouble("precio"),
                    rs.getString("estado")
                );

                habitaciones.add(habitacion);
            }
        }

        return habitaciones;
    }

    public boolean insertar(Habitacion habitacion) throws SQLException {

        String sql = "INSERT INTO habitaciones "
                   + "(numero, id_tipo, precio, estado) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, habitacion.getNumero());
            ps.setObject(2, habitacion.getIdTipo());
            ps.setDouble(3, habitacion.getPrecio());
            ps.setString(4, habitacion.getEstado());

            return ps.executeUpdate() > 0;
        }
    }
}
