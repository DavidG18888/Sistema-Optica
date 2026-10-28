
package com.mycompany.sistemaoptica.dao.impl;

import com.mycompany.sistemaoptica.config.PasswordUtil;
import com.mycompany.sistemaoptica.config.ConexionDB;
import com.mycompany.sistemaoptica.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteDAO {


    public boolean insertarCliente(Cliente cliente, String contrasena) {

        String sqlUsuario = "INSERT INTO Usuarios "
                + "(id_usuario, usuario, correo, contrasena, rol, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlCliente = "INSERT INTO Clientes "
                + "(id_cliente, id_usuario, nombre, dni, telefono) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion()) {

            if (con == null) {
                System.out.println("No hay conexión con Turso.");
                return false;
            }

            int idUsuario;
            int idCliente;

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT COALESCE(MAX(id_usuario), 0) + 1 FROM Usuarios")) {

                rs.next();
                idUsuario = rs.getInt(1);
            }

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT COALESCE(MAX(id_cliente), 0) + 1 FROM Clientes")) {

                rs.next();
                idCliente = rs.getInt(1);
            }

            try (PreparedStatement ps = con.prepareStatement(sqlUsuario)) {
                ps.setInt(1, idUsuario);
                ps.setString(2, cliente.getCorreo());
                ps.setString(3, cliente.getCorreo());
                ps.setString(4, PasswordUtil.hashPassword(contrasena));
                ps.setString(5, "CLIENTE");
                ps.setString(6, "ACTIVO");
                ps.execute();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setInt(1, idCliente);
                ps.setInt(2, idUsuario);
                ps.setString(3, cliente.getNombre());
                ps.setString(4, cliente.getDni());
                ps.setString(5, cliente.getTelefono());
                ps.execute();
            }

            System.out.println("Cliente registrado correctamente.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar cliente:");
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Cliente> listarClientes() {

        ArrayList<Cliente> lista = new ArrayList<>();

        String sql = "SELECT c.id_cliente, c.id_usuario, c.nombre, "
                + "c.dni, c.telefono, u.correo "
                + "FROM Clientes c "
                + "INNER JOIN Usuarios u "
                + "ON c.id_usuario = u.id_usuario";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setIdUsuario(rs.getInt("id_usuario"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDni(rs.getString("dni"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));

                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes:");
            e.printStackTrace();
        }

        return lista;
    }

    public int buscarIdClientePorUsuario(int idUsuario) {

        String sql = "SELECT id_cliente FROM Clientes WHERE id_usuario = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cliente");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar ID del cliente:");
            e.printStackTrace();
        }

        return -1;
    }
}
