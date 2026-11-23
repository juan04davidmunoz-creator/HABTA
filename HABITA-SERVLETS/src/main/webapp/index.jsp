<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HABITA - Inicio</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<header>
    <h1>HABITA</h1>
    <p>Sistema de gestión y reservación de habitaciones</p>
</header>

<main class="contenedor inicio">
    <h2>Bienvenido a HABITA</h2>

    <p>
        Administra las habitaciones y consulta las reservas
        de manera sencilla desde una sola plataforma.
    </p>

    <section class="menu-inicio">
        <article class="opcion-inicio">
            <h3>Gestión de habitaciones</h3>
            <p>Registra habitaciones y consulta su información, precio y estado.</p>
            <a href="<%= request.getContextPath() %>/habitaciones">
                Ir a habitaciones
            </a>
        </article>

        <article class="opcion-inicio">
            <h3>Gestión de reservas</h3>
            <p>Consulta la disponibilidad y registra nuevas reservas.</p>
            <a href="<%= request.getContextPath() %>/reservas">
                Ir a reservas
            </a>
        </article>
    </section>
</main>

<footer class="pie-pagina">
    <p>HABITA - Sistema de gestión hotelera</p>
</footer>

</body>
</html>
