/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.dao.impl;

import com.mycompany.sistemaoptica.config.ConexionDB;
import com.mycompany.sistemaoptica.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario buscarPorCorreo(String correo) {

        String sql = "SELECT id_usuario, usuario, correo, " + "contrasena, rol, estado " + "FROM Usuarios WHERE correo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));

                    return usuario;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: "
                    + e.getMessage());
        }

        return null;
    }
    

    public int buscarIdClientePorUsuario(int idUsuario) {

        String sql = "SELECT id_cliente FROM Clientes WHERE id_usuario = ?";

        try (java.sql.Connection con = com.mycompany.sistemaoptica.config.ConexionDB.getConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cliente");
                }
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Error al buscar ID del cliente: " + e.getMessage());
        }

        return -1;
    }

}
