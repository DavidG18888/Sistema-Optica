/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERACIONES = 210000;
    private static final int LONGITUD_HASH = 256;

    public static String hashPassword(String password) {

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        byte[] hash = generarHash(password.toCharArray(), salt);

        return ITERACIONES + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verificarPassword(
            String password, String hashGuardado) {

        if (password == null || hashGuardado == null) {
            return false;
        }

        try {
            String[] partes = hashGuardado.split(":");

            if (partes.length != 3) {
                return false;
            }

            int iteraciones = Integer.parseInt(partes[0]);
            byte[] salt = Base64.getDecoder().decode(partes[1]);
            byte[] hashOriginal = Base64.getDecoder().decode(partes[2]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    iteraciones,
                    hashOriginal.length * 8
            );

            byte[] hashNuevo = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec)
                    .getEncoded();

            spec.clearPassword();

            return java.security.MessageDigest.isEqual(
                    hashOriginal, hashNuevo);

        } catch (IllegalArgumentException
                | NoSuchAlgorithmException
                | InvalidKeySpecException e) {
            return false;
        }
    }

    private static byte[] generarHash(char[] password, byte[] salt) {

        PBEKeySpec spec = new PBEKeySpec(
                password, salt, ITERACIONES, LONGITUD_HASH);

        try {
            return SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec)
                    .getEncoded();

        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException e) {
            throw new IllegalStateException(
                    "No se pudo generar el hash de la contraseña.", e);
        } finally {
            spec.clearPassword();
        }
    }
}
