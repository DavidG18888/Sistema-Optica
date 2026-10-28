/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.mycompany.sistemaoptica.dao;

import com.mycompany.sistemaoptica.model.Venta;
import com.mycompany.sistemaoptica.model.DetalleVenta;
import java.util.ArrayList;

public interface IVentaDAO {

    boolean registrarVenta(
            Venta venta,
            ArrayList<DetalleVenta> detalles
    );
}
