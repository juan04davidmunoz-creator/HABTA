# HABITA-JDBC

Modulo de habitaciones con JDBC y PostgreSQL (CRUD: insertar, consultar, actualizar y eliminar).

## Ejecucion
1. Crear la base de datos `habita_db` y ejecutar `script_habita_db.sql`.
2. Definir las variables de entorno (PowerShell):

       $env:HABITA_DB_USER="postgres"
       $env:HABITA_DB_PASSWORD="su_clave"

3. Ejecutar la clase `com.habita.principal.Main`.

## Estructura
- `com.habita.conexion`: conexion JDBC
- `com.habita.modelo`: clase Habitacion
- `com.habita.dao`: operaciones CRUD
- `com.habita.principal`: Main de demostracion
