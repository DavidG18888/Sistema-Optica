/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaoptica.controller;

import com.mycompany.sistemaoptica.dao.impl.ProductoDAO;
import com.mycompany.sistemaoptica.model.Producto;
import java.util.ArrayList;

public class ProductoController {

    private final ProductoDAO productoDAO;

    public ProductoController() {
        productoDAO = new ProductoDAO();
    }

    public ArrayList<Producto> listarProductos() {
        return productoDAO.listarProductos();
    }

    public boolean insertarProducto(Producto producto) {
        return productoDAO.insertarProducto(producto);
    }

    public boolean modificarProducto(Producto producto) {
        return productoDAO.modificarProducto(producto);
    }

    public boolean eliminarProducto(int idProducto) {
        return productoDAO.eliminarProducto(idProducto);
    }
}
