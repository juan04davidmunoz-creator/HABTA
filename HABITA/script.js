
/* =====================================================
   HABITA - SISTEMA DE GESTIÓN Y RESERVACIÓN
   Archivo: script.js
   Autor: Juan David Muñoz López
   ===================================================== */

document.addEventListener("DOMContentLoaded", () => {

    // FORMULARIO DE LA PÁGINA PRINCIPAL
    const inicioReservaForm = document.getElementById("inicioReservaForm");

    if (inicioReservaForm) {
        inicioReservaForm.addEventListener("submit", (event) => {
            event.preventDefault();

            const nombre = document.getElementById("inicioNombre").value.trim();
            const correo = document.getElementById("inicioCorreo").value.trim();
            const entrada = document.getElementById("entrada").value;
            const salida = document.getElementById("salida").value;

            if (!nombre || !correo || !entrada || !salida) {
                alert("Por favor, complete todos los campos.");
                return;
            }

            const fechaEntrada = new Date(entrada + "T00:00:00");
            const fechaSalida = new Date(salida + "T00:00:00");

            if (fechaEntrada < new Date(new Date().toDateString())) {
                alert("La fecha de entrada no puede ser anterior a hoy.");
                return;
            }

            if (fechaSalida <= fechaEntrada) {
                alert("La fecha de salida debe ser posterior a la entrada.");
                return;
            }

            // Guardar temporalmente los datos durante la navegación.
            const datosConsulta = {
                nombre: nombre,
                correo: correo,
                entrada: entrada,
                salida: salida
            };

            sessionStorage.setItem(
                "habitaConsulta",
                JSON.stringify(datosConsulta)
            );

            window.location.href = "reservas.html";
        });
    }

    // INICIO DE SESIÓN
    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", (event) => {
            event.preventDefault();

            const usuario = document.getElementById("usuario").value.trim();
            const contrasena = document.getElementById("password").value.trim();

            // Credenciales de prueba del prototipo.
            if (usuario === "admin" && contrasena === "1234") {
                alert("Inicio de sesión exitoso.");
                window.location.href = "menu.html";
            } else {
                alert("Usuario o contraseña incorrectos.");
            }
        });
    }

    // REGISTRO DE USUARIOS
    const registroForm = document.getElementById("registroForm");

    if (registroForm) {
        registroForm.addEventListener("submit", (event) => {
            event.preventDefault();

            const nombre = document.getElementById("nombre").value.trim();
            const apellido = document.getElementById("apellido").value.trim();
            const cedula = document.getElementById("cedula").value.trim();
            const fecha = document.getElementById("fecha").value;

            if (!nombre || !apellido || !cedula || !fecha) {
                alert("Por favor, complete todos los campos.");
                return;
            }

            if (!/^\d+$/.test(cedula)) {
                alert("La cédula debe contener solamente números.");
                return;
            }

            alert("Registro validado correctamente en el prototipo.");
            registroForm.reset();
            window.location.href = "login.html";
        });
    }

    // RESERVAS
    const reservaForm = document.getElementById("reservaForm");

    if (reservaForm) {
        const consultaGuardada = sessionStorage.getItem("habitaConsulta");

        if (consultaGuardada) {
            const datos = JSON.parse(consultaGuardada);
            const campoFecha = reservaForm.querySelector('input[type="date"]');

            if (campoFecha && datos.entrada) {
                campoFecha.value = datos.entrada;
            }
        }

        reservaForm.addEventListener("submit", (event) => {
            event.preventDefault();

            const fecha = reservaForm.querySelector('input[type="date"]').value;
            const hora = reservaForm.querySelector('input[type="time"]').value;
            const servicio = reservaForm.querySelector("select").value;

            if (!servicio || !fecha || !hora) {
                alert("Por favor, complete todos los datos de la reserva.");
                return;
            }

            const fechaReserva = new Date(`${fecha}T${hora}`);
            const ahora = new Date();

            if (fechaReserva <= ahora) {
                alert("Seleccione una fecha y hora futuras.");
                return;
            }

            const datosReserva = {
                servicio: servicio,
                fecha: fecha,
                hora: hora
            };

            sessionStorage.setItem(
                "habitaReserva",
                JSON.stringify(datosReserva)
            );

            alert("Solicitud validada correctamente en el prototipo.");
            reservaForm.reset();
        });
    }

    // PERFIL DE USUARIO
    const perfilForm = document.getElementById("perfilForm");

    if (perfilForm) {
        perfilForm.addEventListener("submit", (event) => {
            event.preventDefault();

            alert("Los cambios del perfil fueron validados en el prototipo.");
        });
    }

    // HISTORIAL DE RESERVAS
    const historial = document.querySelector("table tbody");

    if (historial) {
        historial.addEventListener("click", (event) => {
            const boton = event.target.closest("button");

            if (!boton) return;

            if (boton.classList.contains("delete")) {
                const confirmar = confirm(
                    "¿Está seguro de que desea cancelar esta reserva?"
                );

                if (confirmar) {
                    const fila = boton.closest("tr");
                    fila.remove();

                    alert("Registro eliminado de la vista de demostración.");
                }
            }

            if (boton.classList.contains("edit")) {
                alert(
                    "La edición de reservas estará disponible en una próxima versión."
                );
            }
        });
    }

});
