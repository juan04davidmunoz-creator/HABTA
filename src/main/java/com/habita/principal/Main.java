package com.habita.principal;

import com.habita.conexion.ConexionBD;
import com.habita.dao.HabitacionDAO;
import com.habita.modelo.Habitacion;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) {
                System.out.println("No fue posible conectar con HABITA.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error de conexi?n: " + e.getMessage());
            return;
        }

        HabitacionDAO dao = new HabitacionDAO();

        // 1. CREAR
        Habitacion nueva = new Habitacion(302, 1, 80000.0, "disponible");

        if (dao.insertar(nueva)) {
            System.out.println("1. Habitaci?n 302 creada correctamente.");
        } else {
            System.out.println("No se pudo crear la habitaci?n.");
            return;
        }

        // 2. CONSULTAR
        System.out.println("\n2. LISTADO DE HABITACIONES:");
        for (Habitacion h : dao.listar()) {
            System.out.println(h);
        }

        // 3. BUSCAR
        System.out.println("\n3. B?SQUEDA DE HABITACI?N 302:");
        System.out.println(dao.buscarPorNumero(302));

        // 4. ACTUALIZAR
        Habitacion actualizada = new Habitacion(302, 2, 120000.0, "ocupada");

        if (dao.actualizar(actualizada)) {
            System.out.println("\n4. Habitaci?n 302 actualizada correctamente.");
        }

        // 5. ELIMINAR
        if (dao.eliminar(302)) {
            System.out.println("5. Habitaci?n de prueba eliminada correctamente.");
        }
    }
}
