/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.controller;

import com.mycompany.sistemaoptica.dao.impl.ApartadoDAOImpl;
import com.mycompany.sistemaoptica.model.Apartado;
import java.util.ArrayList;

public class ApartadoController {

    private final ApartadoDAOImpl apartadoDAO;

    public ApartadoController() {
        apartadoDAO = new ApartadoDAOImpl();
    }

    public boolean insertarApartado(Apartado apartado) {
        if (apartado == null
                || apartado.getIdCliente() <= 0
                || apartado.getIdProducto() <= 0) {
            return false;
        }

        return apartadoDAO.insertarApartado(apartado);
    }

    public ArrayList<Apartado> listarApartadosPorCliente(int idCliente) {
        if (idCliente <= 0) {
            return new ArrayList<>();
        }

        return apartadoDAO.listarApartadosPorCliente(idCliente);
    }
}
