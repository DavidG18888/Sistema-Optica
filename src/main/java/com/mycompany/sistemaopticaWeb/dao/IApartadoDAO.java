/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.mycompany.sistemaoptica.dao;

import com.mycompany.sistemaoptica.model.Apartado;
import java.util.ArrayList;

public interface IApartadoDAO {

    boolean insertarApartado(Apartado apartado);

    ArrayList<Apartado> listarApartadosPorCliente(int idCliente);
}
