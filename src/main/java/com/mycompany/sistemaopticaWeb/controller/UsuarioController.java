/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.sistemaoptica.controller;

import com.mycompany.sistemaoptica.config.PasswordUtil;
import com.mycompany.sistemaoptica.dao.impl.UsuarioDAO;
import com.mycompany.sistemaoptica.model.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario iniciarSesion(String correo, String contrasena) {

        if (correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorCorreo(correo.trim());

        if (usuario == null) {
            return null;
        }

        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            return null;
        }

        String hashGuardado = usuario.getContrasena();

        if (hashGuardado == null || !hashGuardado.startsWith("210000:")) {
            return null;
        }

        if (!PasswordUtil.verificarPassword(contrasena, hashGuardado)) {
            return null;
        }

        return usuario;
    }
}
