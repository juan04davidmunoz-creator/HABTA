package com.habita.reservas.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.habita.reservas.dao.ReservaDAO;
import com.habita.reservas.modelo.Reserva;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/reservas")
public class ReservaApiServlet extends HttpServlet {

    private final ReservaDAO dao = new ReservaDAO();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            mapper.writeValue(response.getWriter(), dao.listar());
        } catch (SQLException e) {
            responder(response, 500, false,
                    "No fue posible consultar las reservas.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            Map<String, Object> datos =
                    mapper.readValue(request.getInputStream(), Map.class);

            if (datos == null || datos.get("idUsuario") == null
                    || datos.get("numeroHabitacion") == null
                    || datos.get("fechaEntrada") == null
                    || datos.get("fechaSalida") == null) {
                responder(response, 400, false,
                        "Todos los datos son obligatorios.");
                return;
            }

            int idUsuario = Integer.parseInt(datos.get("idUsuario").toString());
            int numero = Integer.parseInt(datos.get("numeroHabitacion").toString());
            LocalDate entrada = LocalDate.parse(datos.get("fechaEntrada").toString());
            LocalDate salida = LocalDate.parse(datos.get("fechaSalida").toString());

            if (idUsuario <= 0 || numero <= 0 || !salida.isAfter(entrada)) {
                responder(response, 400, false, "Los datos de la reserva no son válidos.");
                return;
            }

            Reserva reserva = new Reserva(idUsuario, numero, entrada, salida);

            if (dao.insertar(reserva)) {
                responder(response, 201, true, "Reserva registrada correctamente.");
            } else {
                responder(response, 409, false,
                        "La habitación no está disponible para esas fechas.");
            }

        } catch (NumberFormatException | DateTimeParseException e) {
            responder(response, 400, false, "El formato de los datos no es válido.");
        } catch (SQLException e) {
            responder(response, 500, false, "Error al registrar la reserva.");
        } catch (IOException e) {
            responder(response, 400, false, "El JSON enviado no es válido.");
        }
    }

    private void responder(HttpServletResponse response, int estado,
                           boolean exito, String mensaje) throws IOException {
        response.setStatus(estado);
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("exito", exito);
        resultado.put("mensaje", mensaje);
        mapper.writeValue(response.getWriter(), resultado);
    }
}