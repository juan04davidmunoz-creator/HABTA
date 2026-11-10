
# HABITA - Servicio web de autenticación

## Evidencia SENA
GA7-220501096-AA5-EV01 - Diseño y desarrollo de servicios web.

## Autor
Juan David Muñoz López

## Descripción
Desarrollo de un servicio web REST para el registro de usuarios y la autenticación mediante correo electrónico y contraseña, conectado a una base de datos PostgreSQL.

## Tecnologías utilizadas
- Java 21
- Jakarta Servlets 6.0
- Maven
- Apache Tomcat 10.1
- PostgreSQL
- BCrypt para protección de contraseñas
- Jackson para procesamiento JSON
- Postman para pruebas

## Funcionalidades
- Registro de nuevos usuarios.
- Validación de los datos recibidos.
- Verificación de correos registrados.
- Almacenamiento de contraseñas mediante hash BCrypt.
- Inicio de sesión con correo y contraseña.
- Respuesta de autenticación satisfactoria o error de autenticación.
- Respuestas HTTP y JSON.

## Servicios disponibles

### Registrar usuario
POST `/HABITA/api/auth/registro`

Ejemplo:
```json
{
  "nombre": "Usuario Prueba",
  "correo": "prueba.aa5@habita.com",
  "contrasena": "Habita2026*"
}
```

### Iniciar sesión
POST `/HABITA/api/auth/login`

Ejemplo:
```json
{
  "correo": "prueba.aa5@habita.com",
  "contrasena": "Habita2026*"
}
```

## Pruebas realizadas
- Registro exitoso de usuario.
- Inicio de sesión con credenciales correctas.
- Rechazo de inicio de sesión con contraseña incorrecta.

## Ejecución
1. Configurar PostgreSQL y la base de datos `habita_db`.
2. Configurar las variables `HABITA_DB_USER` y `HABITA_DB_PASSWORD`.
3. Ejecutar `mvn clean package`.
4. Copiar `target/HABITA.war` a la carpeta `webapps` de Tomcat.
5. Iniciar Tomcat.
6. Probar los servicios mediante Postman.

## Repositorio
Consultar el archivo `enlace_repositorio.txt`.
