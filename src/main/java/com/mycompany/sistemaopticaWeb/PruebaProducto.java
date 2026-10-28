/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaoptica;

import com.mycompany.sistemaoptica.config.ConexionDB;
import com.mycompany.sistemaoptica.dao.impl.ProductoDAO;
import com.mycompany.sistemaoptica.model.Producto;
import java.sql.Connection;
import java.util.ArrayList;

public class PruebaProducto {

    public static void main(String[] args) {

    Connection con = ConexionDB.getConexion();

    if (con != null) {
        System.out.println("Conexion correcta a ConexionOptica");

        ProductoDAO dao = new ProductoDAO();

        ArrayList<Producto> lista = dao.listarProductos();

        for (Producto p : lista) {
          System.out.println(
               p.getIdProducto() + " - "
                + p.getNombre() + " - "
                + p.getTipo() + " - L. "
                + p.getPrecio() + " - Stock: "
                + p.getStock()
            );
        }

            ConexionDB.cerrarConexion();

        } else {
            System.out.println("No se pudo conectar con la base de datos");
        }
    }
}