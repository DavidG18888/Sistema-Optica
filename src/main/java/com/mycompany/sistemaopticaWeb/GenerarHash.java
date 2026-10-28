/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica;

import com.mycompany.sistemaoptica.config.PasswordUtil;

public class GenerarHash {

    public static void main(String[] args) {
        String contrasena = "Vendedor123";
        String hash = PasswordUtil.hashPassword(contrasena);

        System.out.println("Hash generado:");
        System.out.println(hash);
    }
}
