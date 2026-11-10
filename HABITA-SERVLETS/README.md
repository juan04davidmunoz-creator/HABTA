# HABITA - Gestión y reservación de habitaciones

## Evidencia SENA
GA7-220501096-AA2-EV02 - Módulos de software codificados y probados.

## Autor
Juan David Muñoz López

## Tecnologías
- Java 21
- Jakarta Servlets 6.0
- JSP
- Maven
- Apache Tomcat 10
- PostgreSQL
- HTML y CSS

## Funcionalidad
- Consultar habitaciones mediante GET.
- Registrar habitaciones mediante POST.
- Guardar y consultar información en PostgreSQL.
- Mostrar las habitaciones registradas en una vista JSP.

## Ejecución
1. Configurar la base de datos PostgreSQL habita_db.
2. Configurar las variables HABITA_DB_USER y HABITA_DB_PASSWORD.
3. Ejecutar `mvn clean package`.
4. Copiar `target/HABITA.war` a la carpeta webapps de Tomcat.
5. Iniciar Tomcat.
6. Abrir http://localhost:8080/HABITA/habitaciones

## Repositorio
Consultar el archivo enlace_repositorio.txt.
