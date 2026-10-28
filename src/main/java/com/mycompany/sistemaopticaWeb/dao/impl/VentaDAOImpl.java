/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.sistemaoptica.dao.impl;

import com.mycompany.sistemaoptica.config.ConexionDB;
import com.mycompany.sistemaoptica.dao.IVentaDAO;
import com.mycompany.sistemaoptica.model.Venta;
import com.mycompany.sistemaoptica.model.DetalleVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VentaDAOImpl implements IVentaDAO {

    @Override
    public boolean registrarVenta(
            Venta venta, ArrayList<DetalleVenta> detalles) {

        if (venta == null || detalles == null || detalles.isEmpty()) {
            return false;
        }

        try (Connection con = ConexionDB.getConexion()) {

            if (con == null) {
                return false;
            }

            // Validar cliente, vendedor y medición
            String validar = "SELECT COUNT(*) FROM Mediciones M "
                    + "INNER JOIN Clientes C ON M.id_cliente = C.id_cliente "
                    + "INNER JOIN Usuarios U ON U.id_usuario = ? "
                    + "WHERE M.id_medicion = ? AND C.id_cliente = ? "
                    + "AND U.rol = 'VENDEDOR' AND U.estado = 'ACTIVO'";

            try (PreparedStatement ps = con.prepareStatement(validar)) {
                ps.setInt(1, venta.getIdVendedor());
                ps.setInt(2, venta.getIdMedicion());
                ps.setInt(3, venta.getIdCliente());

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next() || rs.getInt(1) == 0) {
                        System.out.println(
                                "Cliente, vendedor o medición no válidos.");
                        return false;
                    }
                }
            }

            Map<Integer, Integer> cantidades = new HashMap<>();

            for (DetalleVenta d : detalles) {
                if (d == null || d.getIdProducto() <= 0
                        || d.getCantidad() <= 0
                        || d.getPrecioUnitario() == null
                        || d.getSubtotal() == null
                        || d.getPrecioUnitario().signum() < 0
                        || d.getSubtotal().signum() < 0) {
                    return false;
                }

                cantidades.merge(
                        d.getIdProducto(), d.getCantidad(), Integer::sum);
            }

            // Obtener ID de venta manualmente
            int idVenta;

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT COALESCE(MAX(id_venta), 0) + 1 FROM Ventas")) {
                rs.next();
                idVenta = rs.getInt(1);
            }

            String sqlVenta = "INSERT INTO Ventas "
                    + "(id_venta, id_cliente, id_vendedor, id_medicion, "
                    + "subtotal, impuesto, total) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sqlVenta)) {
                ps.setInt(1, idVenta);
                ps.setInt(2, venta.getIdCliente());
                ps.setInt(3, venta.getIdVendedor());
                ps.setInt(4, venta.getIdMedicion());
                ps.setBigDecimal(5, venta.getSubtotal());
                ps.setBigDecimal(6, venta.getImpuesto());
                ps.setBigDecimal(7, venta.getTotal());
                ps.execute();
            }

            // Registrar detalles y descontar inventario
            String sqlDetalle = "INSERT INTO DetalleVenta "
                    + "(id_detalle, id_venta, id_producto, cantidad, "
                    + "precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

            String sqlStock = "UPDATE Productos "
                    + "SET stock = stock - ? "
                    + "WHERE id_producto = ? AND estado = 'ACTIVO' "
                    + "AND stock >= ?";

            for (DetalleVenta d : detalles) {

                int idDetalle;

                try (Statement st = con.createStatement();
                     ResultSet rs = st.executeQuery(
                             "SELECT COALESCE(MAX(id_detalle), 0) + 1 "
                             + "FROM DetalleVenta")) {
                    rs.next();
                    idDetalle = rs.getInt(1);
                }

                try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                    ps.setInt(1, idDetalle);
                    ps.setInt(2, idVenta);
                    ps.setInt(3, d.getIdProducto());
                    ps.setInt(4, d.getCantidad());
                    ps.setBigDecimal(5, d.getPrecioUnitario());
                    ps.setBigDecimal(6, d.getSubtotal());
                    ps.execute();
                }
            }

            for (Map.Entry<Integer, Integer> item : cantidades.entrySet()) {
                int cantidad = item.getValue();

                String validarStock = "SELECT stock FROM Productos "
                        + "WHERE id_producto = ? AND estado = 'ACTIVO'";

                try (PreparedStatement ps =
                             con.prepareStatement(validarStock)) {
                    ps.setInt(1, item.getKey());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next() || rs.getInt("stock") < cantidad) {
                            System.out.println(
                                    "Producto inexistente o stock insuficiente.");
                            return false;
                        }
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(sqlStock)) {
                    ps.setInt(1, cantidad);
                    ps.setInt(2, item.getKey());
                    ps.setInt(3, cantidad);
                    ps.execute();
                }
            }

            System.out.println("Venta registrada correctamente. ID: "
                    + idVenta);
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar la venta:");
            e.printStackTrace();
            return false;
        }
    }
}
