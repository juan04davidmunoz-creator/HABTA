package com.habita.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/habita_db";

    private static final String USUARIO =
            System.getenv("HABITA_DB_USER");

    private static final String CLAVE =
            System.getenv("HABITA_DB_PASSWORD");

    public static Connection conectar() {

        if (USUARIO == null || CLAVE == null) {
            System.out.println("Faltan las variables de conexi?n HABITA_DB_USER y HABITA_DB_PASSWORD.");
            return null;
        }

        try {
            Connection conexion = DriverManager.getConnection(
                    URL, USUARIO, CLAVE
            );

            System.out.println("Conexion con HABITA exitosa!");
            return conexion;

        } catch (SQLException e) {
            System.out.println("Error al conectar con PostgreSQL: "
                    + e.getMessage());
            return null;
        }
    }
}
