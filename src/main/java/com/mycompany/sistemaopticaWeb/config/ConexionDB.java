

package com.mycompany.sistemaoptica.config;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionDB {

    private ConexionDB() {
    }

    public static Connection getConexion() {
        try {
            return ConexionTurso.obtenerConexion();

        } catch (SQLException e) {
            System.err.println(
                    "Error al conectar con Turso: " + e.getMessage()
            );
            return null;
        }
    }

    public static void cerrarConexion() {
        // Las conexiones se cierran automáticamente en los DAO.
    }
}
