# HABITA - Sistema de Gestión y Reservación de Habitaciones

## Evidencia GA7-220501096-AA5-EV03
**Aprendiz:** Juan David Muñoz López

## Descripción
HABITA es un sistema de gestión y reservación de habitaciones que permite administrar usuarios, habitaciones, reservas y pagos mediante servicios web REST.

## Tecnologías utilizadas
- Java 21
- Jakarta Servlet 6.0
- Apache Maven
- Apache Tomcat 10.1
- PostgreSQL
- Jackson
- BCrypt
- Postman

## Servicios web REST

| Módulo | Método | Endpoint | Función |
|---|---|---|---|
| Autenticación | POST | /api/auth/registro | Registrar usuarios |
| Autenticación | POST | /api/auth/login | Iniciar sesión |
| Habitaciones | GET | /api/habitaciones | Consultar habitaciones |
| Habitaciones | POST | /api/habitaciones | Registrar habitaciones |
| Reservas | GET | /api/reservas | Consultar reservas |
| Reservas | POST | /api/reservas | Registrar reservas |
| Pagos | GET | /api/pagos | Consultar pagos |
| Pagos | POST | /api/pagos | Registrar pagos |

## Configuración local
1. Instalar Java 21, Maven, PostgreSQL y Apache Tomcat 10.1.
2. Crear la base de datos `habita_db`.
3. Configurar las variables de entorno `HABITA_DB_USER` y `HABITA_DB_PASSWORD`.
4. Ejecutar `mvn clean package`.
5. Desplegar `target/HABITA.war` en Tomcat.

## Pruebas realizadas
Se verificaron las consultas GET de habitaciones, reservas y pagos. También se realizaron pruebas de registro e inicio de sesión del módulo de autenticación.

## Documentación
Consultar el documento Word correspondiente a la evidencia GA7-220501096-AA5-EV03.

## Repositorio
https://github.com/juan04davidmunoz-creator/HABTA/tree/ga7-220501096-aa5-ev03-servicios-web