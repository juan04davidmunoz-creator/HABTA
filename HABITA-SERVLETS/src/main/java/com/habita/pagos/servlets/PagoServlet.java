package com.habita.pagos.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.habita.pagos.dao.PagoDAO;
import com.habita.pagos.modelo.Pago;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/pagos")
public class PagoServlet extends HttpServlet {

    private final PagoDAO pagoDAO = new PagoDAO();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), pagoDAO.listar());

        } catch (SQLException e) {
            responder(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    false, "No fue posible consultar los pagos.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            Map<String, Object> datos =
                    mapper.readValue(request.getInputStream(), Map.class);

            if (datos == null || datos.get("idReserva") == null
                    || datos.get("metodoPago") == null
                    || datos.get("valor") == null
                    || datos.get("fechaPago") == null
                    || datos.get("estado") == null) {

                responder(response, HttpServletResponse.SC_BAD_REQUEST,
                        false, "Todos los campos son obligatorios.");
                return;
            }

            int idReserva = Integer.parseInt(
                    datos.get("idReserva").toString());

            String metodoPago = datos.get("metodoPago").toString().trim();
            BigDecimal valor = new BigDecimal(
                    datos.get("valor").toString());

            LocalDate fechaPago = LocalDate.parse(
                    datos.get("fechaPago").toString());

            String estado = datos.get("estado").toString().trim();

            if (idReserva <= 0 || metodoPago.isEmpty()
                    || valor.compareTo(BigDecimal.ZERO) <= 0
                    || estado.isEmpty()) {

                responder(response, HttpServletResponse.SC_BAD_REQUEST,
                        false, "Los datos del pago no son válidos.");
                return;
            }

            Pago pago = new Pago(
                    idReserva, metodoPago, valor, fechaPago, estado);

            pagoDAO.registrar(pago);

            responder(response, HttpServletResponse.SC_CREATED,
                    true, "Pago registrado correctamente.");

        } catch (NumberFormatException | DateTimeParseException e) {
            responder(response, HttpServletResponse.SC_BAD_REQUEST,
                    false, "Verifique el formato de los datos enviados.");

        } catch (SQLException e) {
            responder(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    false, "No fue posible registrar el pago.");

        } catch (IOException e) {
            responder(response, HttpServletResponse.SC_BAD_REQUEST,
                    false, "El formato JSON enviado no es válido.");
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
