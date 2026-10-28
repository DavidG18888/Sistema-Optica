/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.controller;

import com.mycompany.sistemaoptica.dao.impl.MedicionDAO;
import com.mycompany.sistemaoptica.model.Medicion;
import java.util.ArrayList;

public class MedicionController {

    private final MedicionDAO medicionDAO;

    public MedicionController() {
        medicionDAO = new MedicionDAO();
    }

    public boolean insertarMedicion(Medicion medicion) {

        if (medicion == null || medicion.getIdCliente() <= 0) {
            return false;
        }

        if (medicion.getEje() != null
                && (medicion.getEje() < 0 || medicion.getEje() > 180)) {
            return false;
        }

        return medicionDAO.insertarMedicion(medicion);
    }

    public ArrayList<Medicion> listarPorCliente(int idCliente) {

        if (idCliente <= 0) {
            return new ArrayList<>();
        }

        return medicionDAO.listarPorCliente(idCliente);
    }
}
