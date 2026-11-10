<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.habita.reservas.modelo.Reserva" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HABITA - Reservaciones</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<header>
    <h1>HABITA</h1>
    <p>Sistema de gestión y reservación de habitaciones</p>
</header>

<main class="contenedor">

    <h2>Consultar disponibilidad y reservar</h2>

    <% if ("exitoso".equals(request.getParameter("registro"))) { %>
        <p class="exito">La reserva fue registrada correctamente.</p>
    <% } %>

    <% if ("no-disponible".equals(request.getParameter("registro"))) { %>
        <p class="error">La habitación no está disponible para esas fechas.</p>
    <% } %>

    <section>
        <h3>Consultar habitaciones disponibles</h3>

        <form action="<%= request.getContextPath() %>/reservas"
              method="GET">

            <label>Fecha de entrada:</label>
            <input type="date" name="entrada" required>

            <label>Fecha de salida:</label>
            <input type="date" name="salida" required>

            <button type="submit">Consultar disponibilidad</button>
        </form>

        <%
            List<Integer> disponibles =
                (List<Integer>) request.getAttribute("habitacionesDisponibles");

            if (disponibles != null) {
        %>
            <h4>Habitaciones disponibles</h4>
            <% if (disponibles.isEmpty()) { %>
                <p>No hay habitaciones disponibles para ese rango de fechas.</p>
            <% } else { %>
                <ul>
                    <% for (Integer numero : disponibles) { %>
                        <li>Habitación número <%= numero %></li>
                    <% } %>
                </ul>
            <% } %>
        <% } %>
    </section>

    <section>
        <h3>Registrar una reserva</h3>

        <form action="<%= request.getContextPath() %>/reservas"
              method="POST">

            <label>ID del usuario:</label>
            <input type="number" name="idUsuario" min="1" required>

            <label>Número de habitación:</label>
            <input type="number" name="numeroHabitacion" min="1" required>

            <label>Fecha de entrada:</label>
            <input type="date" name="fechaEntrada" required>

            <label>Fecha de salida:</label>
            <input type="date" name="fechaSalida" required>

            <button type="submit">Confirmar reserva</button>
        </form>
    </section>

    <section>
        <h3>Reservas registradas</h3>

        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Usuario</th>
                    <th>Habitación</th>
                    <th>Entrada</th>
                    <th>Salida</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Reserva> reservas =
                        (List<Reserva>) request.getAttribute("reservas");

                    if (reservas != null && !reservas.isEmpty()) {
                        for (Reserva reserva : reservas) {
                %>
                    <tr>
                        <td><%= reserva.getIdReserva() %></td>
                        <td><%= reserva.getNombreUsuario() %></td>
                        <td><%= reserva.getNumeroHabitacion() %></td>
                        <td><%= reserva.getFechaEntrada() %></td>
                        <td><%= reserva.getFechaSalida() %></td>
                        <td><%= reserva.getEstado() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="6">No hay reservas registradas.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </section>

</main>
</body>
</html>
