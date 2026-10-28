/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.sistemaoptica.controller;

import com.mycompany.sistemaoptica.dao.impl.ClienteDAO;
import com.mycompany.sistemaoptica.model.Cliente;
import java.util.ArrayList;

public class ClienteController {

    private final ClienteDAO clienteDAO;

    public ClienteController() {
        clienteDAO = new ClienteDAO();
    }

    public boolean insertarCliente(Cliente cliente, String contrasena) {

        if (cliente == null
                || cliente.getNombre() == null || cliente.getNombre().trim().isEmpty() || cliente.getDni() == null
                || cliente.getDni().trim().isEmpty() || cliente.getCorreo() == null || cliente.getCorreo().trim().isEmpty() || contrasena == null
                || contrasena.length() < 8) {
            return false;
        }

        return clienteDAO.insertarCliente(cliente, contrasena);
    }

    public ArrayList<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }
}
