package com.habita.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habita.dao.HabitacionDAO;
import com.habita.modelo.Habitacion;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/habitaciones")
public class HabitacionApiServlet extends HttpServlet {

    private final HabitacionDAO dao = new HabitacionDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), dao.listar());

        } catch (SQLException e) {
            responder(response, 500, false,
                    "No fue posible consultar las habitaciones.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> datos;

        try {
            datos = mapper.readValue(request.getInputStream(), Map.class);
        } catch (IOException e) {
            responder(response, 400, false, "El JSON enviado no es valido.");
            return;
        }

        try {
            if (datos == null
                    || datos.get("numero") == null
                    || datos.get("precio") == null
                    || datos.get("estado") == null) {

                responder(response, 400, false,
                        "Numero, precio y estado son obligatorios.");
                return;
            }

            int numero = Integer.parseInt(datos.get("numero").toString());

            Integer idTipo = datos.get("idTipo") == null
                    ? null
                    : Integer.valueOf(datos.get("idTipo").toString());

            double precio = Double.parseDouble(
                    datos.get("precio").toString());

            String estado = datos.get("estado").toString().trim();

            if (numero <= 0 || precio <= 0
                    || !Double.isFinite(precio)
                    || (idTipo != null && idTipo <= 0)
                    || estado.isEmpty()) {

                responder(response, 400, false,
                        "Los datos de la habitacion no son validos.");
                return;
            }

            Habitacion habitacion =
                    new Habitacion(numero, idTipo, precio, estado);

            if (dao.insertar(habitacion)) {
                responder(response, 201, true,
                        "Habitacion registrada correctamente.");
            } else {
                responder(response, 400, false,
                        "No fue posible registrar la habitacion.");
            }

        } catch (NumberFormatException e) {
            responder(response, 400, false,
                    "Verifique el formato de los datos.");

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                responder(response, 409, false,
                        "El numero de habitacion ya existe.");
            } else {
                responder(response, 500, false,
                        "Error al registrar la habitacion.");
            }
        }
    }

    private void responder(HttpServletResponse response,
                           int estado, boolean exito,
                           String mensaje) throws IOException {

        response.setStatus(estado);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("exito", exito);
        resultado.put("mensaje", mensaje);

        mapper.writeValue(response.getWriter(), resultado);
    }
}