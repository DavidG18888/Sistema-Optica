/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaoptica.dao;

import com.mycompany.sistemaoptica.model.Producto;
import java.util.ArrayList;

public interface IProductoDAO {

    ArrayList<Producto> listarProductos();

    boolean insertarProducto(Producto p);

    boolean modificarProducto(Producto p);

    boolean eliminarProducto(int idProducto);
}