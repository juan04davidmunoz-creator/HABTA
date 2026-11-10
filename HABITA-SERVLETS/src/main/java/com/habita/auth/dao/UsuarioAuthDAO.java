
package com.habita.auth.dao;

import com.habita.auth.modelo.UsuarioAuth;
import com.habita.conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Acceso a datos de usuarios para el servicio de autenticación.
 */
public class UsuarioAuthDAO {

    /**
     * Busca un usuario por su correo electrónico.
     */
    public UsuarioAuth buscarPorCorreo(String correo) throws SQLException {

        String sql = """
            SELECT id_usuario, nombre, correo, contrasena
            FROM usuarios
            WHERE LOWER(correo) = LOWER(?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, correo);

            try (ResultSet resultado = consulta.executeQuery()) {

                if (resultado.next()) {
                    UsuarioAuth usuario = new UsuarioAuth();

                    usuario.setIdUsuario(resultado.getInt("id_usuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setContrasena(resultado.getString("contrasena"));

                    return usuario;
                }
            }
        }

        return null;
    }

    /**
     * Registra un usuario nuevo en la base de datos.
     */
    public boolean registrar(UsuarioAuth usuario) throws SQLException {

        String sql = """
            INSERT INTO usuarios (nombre, correo, contrasena, id_rol)
            VALUES (?, ?, ?, 1)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, usuario.getNombre());
            consulta.setString(2, usuario.getCorreo());
            consulta.setString(3, usuario.getContrasena());

            return consulta.executeUpdate() > 0;
        }
    }
}
