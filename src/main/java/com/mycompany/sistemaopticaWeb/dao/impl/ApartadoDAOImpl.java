/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.dao.impl;

import com.mycompany.sistemaoptica.dao.IApartadoDAO;
import com.mycompany.sistemaoptica.model.Apartado;
import com.mycompany.sistemaoptica.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApartadoDAOImpl implements IApartadoDAO {

    @Override
    public boolean insertarApartado(Apartado apartado) {
        String sql = "INSERT INTO Apartados (id_cliente, id_producto, estado) "
                   + "SELECT ?, ?, 'PENDIENTE' "
                   + "WHERE EXISTS (SELECT 1 FROM Clientes WHERE id_cliente = ?) "
                   + "AND EXISTS (SELECT 1 FROM Productos WHERE id_producto = ? "
                   + "AND tipo = 'ARO' AND estado = 'ACTIVO' AND stock > 0)";

        try (Connection conexion = ConexionDB.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, apartado.getIdCliente());
            ps.setInt(2, apartado.getIdProducto());
            ps.setInt(3, apartado.getIdCliente());
            ps.setInt(4, apartado.getIdProducto());

            return ps.execute();

        } catch (SQLException e) {
            System.out.println("Error al guardar el apartado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Apartado> listarApartadosPorCliente(int idCliente) {
        ArrayList<Apartado> lista = new ArrayList<>();

        String sql = "SELECT id_apartado, id_cliente, id_producto, fecha, estado "
                   + "FROM Apartados WHERE id_cliente = ? ORDER BY fecha DESC";

        try (Connection conexion = ConexionDB.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Apartado apartado = new Apartado();
                    apartado.setIdApartado(rs.getInt("id_apartado"));
                    apartado.setIdCliente(rs.getInt("id_cliente"));
                    apartado.setIdProducto(rs.getInt("id_producto"));
                    apartado.setFecha(rs.getTimestamp("fecha"));
                    apartado.setEstado(rs.getString("estado"));

                    lista.add(apartado);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar apartados: " + e.getMessage());
        }

        return lista;
    }
}
