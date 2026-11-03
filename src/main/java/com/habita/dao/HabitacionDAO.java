
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

    // CREAR HABITACIÓN
    public boolean insertar(Habitacion habitacion) {

        String sql = "INSERT INTO habitaciones "
                + "(numero, id_tipo, precio, estado) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, habitacion.getNumero());
            ps.setObject(2, habitacion.getIdTipo());
            ps.setObject(3, habitacion.getPrecio());
            ps.setString(4, habitacion.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // CONSULTAR TODAS LAS HABITACIONES
    public List<Habitacion> listar() {

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

        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }

        return habitaciones;
    }

    // BUSCAR HABITACIÓN POR NÚMERO
    public Habitacion buscarPorNumero(int numero) {

        String sql = "SELECT numero, id_tipo, precio, estado "
                + "FROM habitaciones WHERE numero = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, numero);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Habitacion(
                            rs.getInt("numero"),
                            (Integer) rs.getObject("id_tipo"),
                            rs.getDouble("precio"),
                            rs.getString("estado")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }

        return null;
    }

    // ACTUALIZAR HABITACIÓN
    public boolean actualizar(Habitacion habitacion) {

        String sql = "UPDATE habitaciones SET id_tipo = ?, "
                + "precio = ?, estado = ? WHERE numero = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setObject(1, habitacion.getIdTipo());
            ps.setObject(2, habitacion.getPrecio());
            ps.setString(3, habitacion.getEstado());
            ps.setInt(4, habitacion.getNumero());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR HABITACIÓN
    public boolean eliminar(int numero) {

        String sql = "DELETE FROM habitaciones WHERE numero = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, numero);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}
