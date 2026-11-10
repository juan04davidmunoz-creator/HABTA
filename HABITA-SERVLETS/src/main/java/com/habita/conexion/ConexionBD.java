```java
package com.habita.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/habita_db";

    public static Connection conectar() throws SQLException {

        // Cargar el controlador JDBC de PostgreSQL
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "No se encontró el controlador JDBC de PostgreSQL.", e
            );
        }

        // Obtener las credenciales del sistema
        String usuario = System.getenv("HABITA_DB_USER");
        String clave = System.getenv("HABITA_DB_PASSWORD");

        // Verificar que existan las credenciales
        if (usuario == null || clave == null) {
            throw new SQLException(
                "Faltan las variables HABITA_DB_USER y HABITA_DB_PASSWORD."
            );
        }

        // Establecer conexión con PostgreSQL
        return DriverManager.getConnection(URL, usuario, clave);
    }
}
```