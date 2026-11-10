package com.habita.servlets;

import com.habita.dao.HabitacionDAO;
import com.habita.modelo.Habitacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/habitaciones")
public class HabitacionServlet extends HttpServlet {

    private final HabitacionDAO habitacionDAO = new HabitacionDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Habitacion> habitaciones = habitacionDAO.listar();
            request.setAttribute("habitaciones", habitaciones);

            request.getRequestDispatcher("/WEB-INF/views/habitaciones.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar las habitaciones", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int numero = Integer.parseInt(request.getParameter("numero"));
            int idTipo = Integer.parseInt(request.getParameter("idTipo"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            String estado = request.getParameter("estado");

            Habitacion habitacion =
                    new Habitacion(numero, idTipo, precio, estado);

            habitacionDAO.insertar(habitacion);

            response.sendRedirect(
                    request.getContextPath() + "/habitaciones?registro=exitoso"
            );

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Verifica los datos numéricos de la habitación.");
        } catch (SQLException e) {
            throw new ServletException("Error al registrar la habitación", e);
        }
    }
}
