package com.habita.reservas.servlets;

import com.habita.reservas.dao.ReservaDAO;
import com.habita.reservas.modelo.Reserva;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet encargado de gestionar las reservaciones de HABITA.
 */
@WebServlet("/reservas")
public class ReservaServlet extends HttpServlet {

    private final ReservaDAO reservaDAO = new ReservaDAO();

    /**
     * Muestra el formulario y las reservas registradas.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("reservas", reservaDAO.listar());

            String entrada = request.getParameter("entrada");
            String salida = request.getParameter("salida");

            if (entrada != null && salida != null
                    && !entrada.isBlank() && !salida.isBlank()) {

                LocalDate fechaEntrada = LocalDate.parse(entrada);
                LocalDate fechaSalida = LocalDate.parse(salida);

                if (fechaSalida.isAfter(fechaEntrada)) {
                    List<Integer> disponibles =
                            reservaDAO.listarHabitacionesDisponibles(
                                    Date.valueOf(fechaEntrada),
                                    Date.valueOf(fechaSalida));

                    request.setAttribute("habitacionesDisponibles", disponibles);
                }
            }

            request.getRequestDispatcher(
                    "/WEB-INF/views/reservas/reservas.jsp")
                    .forward(request, response);

        } catch (SQLException | java.time.format.DateTimeParseException e) {
            throw new ServletException("Error al consultar las reservas.", e);
        }
    }

    /**
     * Recibe y registra una nueva reserva.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int idUsuario = Integer.parseInt(
                    request.getParameter("idUsuario"));
            int numeroHabitacion = Integer.parseInt(
                    request.getParameter("numeroHabitacion"));

            LocalDate entrada = LocalDate.parse(
                    request.getParameter("fechaEntrada"));
            LocalDate salida = LocalDate.parse(
                    request.getParameter("fechaSalida"));

            if (entrada.isBefore(LocalDate.now())
                    || !salida.isAfter(entrada)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Verifica las fechas de la reserva.");
                return;
            }

            Reserva reserva = new Reserva(
                    idUsuario, numeroHabitacion, entrada, salida);

            if (reservaDAO.insertar(reserva)) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/reservas?registro=exitoso");
            } else {
                response.sendRedirect(
                        request.getContextPath()
                        + "/reservas?registro=no-disponible");
            }

        } catch (NumberFormatException
                 | java.time.format.DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Los datos enviados no son válidos.");
        } catch (SQLException e) {
            throw new ServletException("Error al registrar la reserva.", e);
        }
    }
}
