package com.example.pruebamonigote;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptadorContraseñas {

    // Método para encriptar una cadena utilizando el algoritmo SHA-256
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar excepción
            e.printStackTrace();
        }
        return null;
    }

}
