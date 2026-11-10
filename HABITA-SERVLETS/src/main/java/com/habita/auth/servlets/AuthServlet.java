
package com.habita.auth.servlets;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.habita.auth.dao.UsuarioAuthDAO;
import com.habita.auth.modelo.UsuarioAuth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio web REST para registrar usuarios e iniciar sesión en HABITA.
 */
@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {

    private final UsuarioAuthDAO usuarioDAO = new UsuarioAuthDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Atiende las solicitudes POST de registro e inicio de sesión.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String ruta = request.getPathInfo();

        try {
            UsuarioAuth usuario = mapper.readValue(
                request.getInputStream(), UsuarioAuth.class
            );

            if ("/registro".equals(ruta)) {
                registrar(usuario, response);

            } else if ("/login".equals(ruta)) {
                iniciarSesion(usuario, response);

            } else {
                responder(response, 404, false,
                    "Servicio no encontrado.");
            }

        } catch (IOException e) {
            responder(response, 400, false,
                "Los datos enviados no tienen un formato JSON válido.");

        } catch (SQLException e) {
            responder(response, 500, false,
                "No fue posible procesar la solicitud.");
        }
    }

    /**
     * Registra usuarios y almacena su contraseña protegida con BCrypt.
     */
    private void registrar(UsuarioAuth usuario,
                           HttpServletResponse response)
            throws SQLException, IOException {

        if (usuario.getNombre() == null
                || usuario.getCorreo() == null
                || usuario.getContrasena() == null
                || usuario.getNombre().isBlank()
                || usuario.getCorreo().isBlank()
                || usuario.getContrasena().isBlank()) {

            responder(response, 400, false,
                "Todos los campos son obligatorios.");
            return;
        }

        if (!usuario.getCorreo().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            responder(response, 400, false,
                "El correo electrónico no es válido.");
            return;
        }

        if (usuario.getContrasena().length() < 8) {
            responder(response, 400, false,
                "La contraseña debe tener mínimo 8 caracteres.");
            return;
        }

        // Evitar registrar dos usuarios con el mismo correo.
        if (usuarioDAO.buscarPorCorreo(usuario.getCorreo()) != null) {
            responder(response, 409, false,
                "El correo electrónico ya está registrado.");
            return;
        }

        // Generar un hash de la contraseña antes de guardarla.
        String hash = BCrypt.withDefaults().hashToString(
            12, usuario.getContrasena().toCharArray()
        );

        usuario.setContrasena(hash);

        if (usuarioDAO.registrar(usuario)) {
            responder(response, 201, true,
                "Usuario registrado correctamente.");
        } else {
            responder(response, 500, false,
                "No fue posible registrar el usuario.");
        }
    }

    /**
     * Valida el correo y la contraseña del usuario.
     */
    private void iniciarSesion(UsuarioAuth datos,
                              HttpServletResponse response)
            throws SQLException, IOException {

        if (datos.getCorreo() == null
                || datos.getContrasena() == null
                || datos.getCorreo().isBlank()
                || datos.getContrasena().isBlank()) {

            responder(response, 400, false,
                "Debe ingresar correo y contraseña.");
            return;
        }

        UsuarioAuth usuario =
            usuarioDAO.buscarPorCorreo(datos.getCorreo());

        if (usuario == null) {
            responder(response, 401, false,
                "Error en la autenticación.");
            return;
        }

        // Comparar la contraseña ingresada con el hash almacenado.
        BCrypt.Result resultado = BCrypt.verifyer().verify(
            datos.getContrasena().toCharArray(),
            usuario.getContrasena()
        );

        if (resultado.verified) {
            responder(response, 200, true,
                "Autenticación satisfactoria.");
        } else {
            responder(response, 401, false,
                "Error en la autenticación.");
        }
    }

    /**
     * Devuelve una respuesta JSON con el código HTTP correspondiente.
     */
    private void responder(HttpServletResponse response,
                           int codigo,
                           boolean exito,
                           String mensaje) throws IOException {

        response.setStatus(codigo);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("exito", exito);
        resultado.put("mensaje", mensaje);

        mapper.writeValue(response.getOutputStream(), resultado);
    }
}
