/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.dao.impl;

import com.mycompany.sistemaoptica.config.ConexionDB;
import com.mycompany.sistemaoptica.model.Medicion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicionDAO {

    public boolean insertarMedicion(Medicion medicion) {

        String sql = "INSERT INTO Mediciones "
                + "(id_cliente, esfera, cilindro, eje, adicion, observaciones) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, medicion.getIdCliente());

            if (medicion.getEsfera() != null) {
                ps.setDouble(2, medicion.getEsfera());
            } else {
                ps.setNull(2, java.sql.Types.DECIMAL);
            }

            if (medicion.getCilindro() != null) {
                ps.setDouble(3, medicion.getCilindro());
            } else {
                ps.setNull(3, java.sql.Types.DECIMAL);
            }

            if (medicion.getEje() != null) {
                ps.setInt(4, medicion.getEje());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            if (medicion.getAdicion() != null) {
                ps.setDouble(5, medicion.getAdicion());
            } else {
                ps.setNull(5, java.sql.Types.DECIMAL);
            }

            ps.setString(6, medicion.getObservaciones());

            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar medición: "
                    + e.getMessage());
            return false;
        }
    }

    public ArrayList<Medicion> listarPorCliente(int idCliente) {

        ArrayList<Medicion> lista = new ArrayList<>();

        String sql = "SELECT id_medicion, id_cliente, fecha, esfera, " + "cilindro, eje, adicion, observaciones " + "FROM Mediciones "
                + "WHERE id_cliente = ? " + "ORDER BY fecha DESC, id_medicion DESC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicion m = new Medicion();

                    m.setIdMedicion(rs.getInt("id_medicion"));
                    m.setIdCliente(rs.getInt("id_cliente"));
                    m.setFecha(rs.getDate("fecha"));

                    double esfera = rs.getDouble("esfera");
                    m.setEsfera(rs.wasNull() ? null : esfera);

                    double cilindro = rs.getDouble("cilindro");
                    m.setCilindro(rs.wasNull() ? null : cilindro);

                    int eje = rs.getInt("eje");
                    m.setEje(rs.wasNull() ? null : eje);

                    double adicion = rs.getDouble("adicion");
                    m.setAdicion(rs.wasNull() ? null : adicion);

                    m.setObservaciones(rs.getString("observaciones"));

                    lista.add(m);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar mediciones: "
                    + e.getMessage());
        }

        return lista;
    }
}
