document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const registroForm = document.getElementById("registroForm");

    if (loginForm) {
        loginForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const user = document.getElementById("usuario").value.trim();
            const pass = document.getElementById("password").value.trim();

            // Validación simple: ejemplo de credenciales demo
            if (user === "admin" && pass === "1234") {
                alert("Inicio de sesión exitoso");
                // Aquí puedes redirigir a la vista principal del prototipo
                // window.location.href = "dashboard.html";
            } else {
                // Pasamos un query param con el mensaje (opcional)
                window.location.href = "error.html";
            }
        });
    }

    if (registroForm) {
        registroForm.addEventListener("submit", function (e) {
            e.preventDefault();

            // Validar campos mínimos
            const nombre = document.getElementById("nombre").value.trim();
            const apellido = document.getElementById("apellido").value.trim();
            const cedula = document.getElementById("cedula").value.trim();
            const fecha = document.getElementById("fecha").value;

            if (!nombre || !apellido || !cedula || !fecha) {
                alert("Por favor complete todos los campos.");
                return;
            }

            alert("Usuario registrado correctamente");
            window.location.href = "login.html";
        });
    }
});
