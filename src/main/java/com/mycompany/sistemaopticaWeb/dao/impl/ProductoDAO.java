
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaoptica.dao.impl;

import com.mycompany.sistemaoptica.config.ConexionDB;
import com.mycompany.sistemaoptica.dao.IProductoDAO;
import com.mycompany.sistemaoptica.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO implements IProductoDAO {

    @Override
    public ArrayList<Producto> listarProductos() {
        ArrayList<Producto> lista = new ArrayList<>();

        String sql = "SELECT id_producto, nombre, tipo, marca, "
                + "material, forma, precio, stock, estado "
                + "FROM Productos ORDER BY id_producto";

        try (Connection con = ConexionDB.getConexion()) {

            if (con == null) {
                throw new SQLException("No se pudo conectar con Turso.");
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Producto p = new Producto();

                    p.setIdProducto(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setTipo(rs.getString("tipo"));
                    p.setMarca(rs.getString("marca"));
                    p.setMaterial(rs.getString("material"));
                    p.setForma(rs.getString("forma"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setStock(rs.getInt("stock"));
                    p.setEstado(rs.getString("estado"));

                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar productos: "
                    + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean insertarProducto(Producto p) {

        String sql = "INSERT INTO Productos "
                + "(nombre, tipo, marca, material, forma, precio, stock, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion()) {

            if (con == null) {
                throw new SQLException("No se pudo conectar con Turso.");
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, p.getNombre());
                ps.setString(2, p.getTipo());
                ps.setString(3, p.getMarca());
                ps.setString(4, p.getMaterial());
                ps.setString(5, p.getForma());
                ps.setDouble(6, p.getPrecio());
                ps.setInt(7, p.getStock());
                ps.setString(8, p.getEstado());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: "
                    + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarProducto(Producto p) {

        String sql = "UPDATE Productos SET nombre=?, tipo=?, marca=?, "
                + "material=?, forma=?, precio=?, stock=?, estado=? "
                + "WHERE id_producto=?";

        try (Connection con = ConexionDB.getConexion()) {

            if (con == null) {
                throw new SQLException("No se pudo conectar con Turso.");
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, p.getNombre());
                ps.setString(2, p.getTipo());
                ps.setString(3, p.getMarca());
                ps.setString(4, p.getMaterial());
                ps.setString(5, p.getForma());
                ps.setDouble(6, p.getPrecio());
                ps.setInt(7, p.getStock());
                ps.setString(8, p.getEstado());
                ps.setInt(9, p.getIdProducto());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar producto: "
                    + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(int idProducto) {

        String sql = "DELETE FROM Productos WHERE id_producto=?";

        try (Connection con = ConexionDB.getConexion()) {

            if (con == null) {
                throw new SQLException("No se pudo conectar con Turso.");
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idProducto);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: "
                    + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}