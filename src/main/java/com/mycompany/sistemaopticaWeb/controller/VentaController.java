/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.controller;

import com.mycompany.sistemaoptica.dao.impl.VentaDAOImpl;
import com.mycompany.sistemaoptica.model.Venta;
import com.mycompany.sistemaoptica.model.DetalleVenta;
import java.util.ArrayList;

public class VentaController {

    private final VentaDAOImpl ventaDAO;

    public VentaController() {
        ventaDAO = new VentaDAOImpl();
    }

    public boolean registrarVenta(
            Venta venta,
            ArrayList<DetalleVenta> detalles) {

        if (venta == null || detalles == null || detalles.isEmpty()) {
            return false;
        }

        if (venta.getIdCliente() <= 0
                || venta.getIdVendedor() <= 0 || venta.getIdMedicion() <= 0 || venta.getSubtotal() == null
                || venta.getImpuesto() == null || venta.getTotal() == null) {
            return false;
        }

        if (venta.getSubtotal().signum() < 0
                || venta.getImpuesto().signum() < 0 || venta.getTotal().signum() < 0 || venta.getSubtotal()
                .add(venta.getImpuesto())
                .compareTo(venta.getTotal()) != 0) {
            return false;
        }

        return ventaDAO.registrarVenta(venta, detalles);
    }
}
