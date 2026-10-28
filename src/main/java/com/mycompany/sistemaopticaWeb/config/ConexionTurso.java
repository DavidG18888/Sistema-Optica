/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionTurso {



    private static final String URL =
            "jdbc:dbeaver:libsql:https://optica-db-davidgomez188.aws-us-east-1.turso.io";

    private static final String TOKEN =
            System.getenv("TURSO_AUTH_TOKEN");

    private ConexionTurso() {
    }

    public static Connection obtenerConexion() throws SQLException {

        if (TOKEN == null || TOKEN.isBlank()) {
            throw new SQLException(
                    "No se encuentra la variable de entorno TURSO_AUTH_TOKEN."
            );
        }

        return DriverManager.getConnection(URL, null, TOKEN);
    }

    public static void main(String[] args) {

        try (Connection conexion = obtenerConexion()) {

            System.out.println("CONEXION EXITOSA CON TURSO");

            try (var consulta = conexion.createStatement();
                 var resultado = consulta.executeQuery("SELECT 1")) {

                if (resultado.next()) {
                    System.out.println(
                            "Prueba SQL: " + resultado.getInt(1)
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CONECTAR CON TURSO");
            System.out.println("Mensaje: " + e.getMessage());
        }
    }
}
