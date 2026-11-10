<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.habita.modelo.Habitacion" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>HABITA - Gestión de habitaciones</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<header>
    <h1>HABITA</h1>
    <p>Sistema de gestión y reservación de habitaciones</p>
</header>

<main class="contenedor">

    <h2>Registrar habitación</h2>

    <% if ("exitoso".equals(request.getParameter("registro"))) { %>
        <p class="exito">Habitación registrada correctamente.</p>
    <% } %>

    <form action="<%= request.getContextPath() %>/habitaciones"
          method="POST">

        <label>Número de habitación:</label>
        <input type="number" name="numero" min="1" required>

        <label>Tipo de habitación (ID):</label>
        <input type="number" name="idTipo" min="1" required>

        <label>Precio por noche:</label>
        <input type="number" name="precio" min="1" step="0.01" required>

        <label>Estado:</label>
        <select name="estado" required>
            <option value="Disponible">Disponible</option>
            <option value="Ocupada">Ocupada</option>
            <option value="Mantenimiento">Mantenimiento</option>
        </select>

        <button type="submit">Registrar habitación</button>
    </form>

    <h2>Habitaciones registradas</h2>

    <table>
        <thead>
            <tr>
                <th>Número</th>
                <th>Tipo (ID)</th>
                <th>Precio</th>
                <th>Estado</th>
            </tr>
        </thead>
        <tbody>

        <%
            List<Habitacion> habitaciones =
                (List<Habitacion>) request.getAttribute("habitaciones");

            if (habitaciones != null && !habitaciones.isEmpty()) {
                for (Habitacion h : habitaciones) {
        %>
            <tr>
                <td><%= h.getNumero() %></td>
                <td><%= h.getIdTipo() %></td>
                <td>$<%= h.getPrecio() %></td>
                <td><%= h.getEstado() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No hay habitaciones registradas.</td>
            </tr>
        <% } %>

        </tbody>
    </table>

</main>

</body>
</html>
