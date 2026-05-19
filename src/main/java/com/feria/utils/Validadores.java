package com.feria.utils;

import com.feria.modelos.Emprendedor;

public class Validadores {

    public static boolean emailValido(String email) {
        if (email == null) return false;
        if (!email.contains("@")) return false;
        if (email.length() < 5) return false;
        return true;
    }

    public static boolean telefonoValido(String t) {
        if (t == null) return false;
        if (t.length() < 8) return false;
        return true;
    }

    public static boolean validarPrecioStock(double precio, int stock) {
        if (precio <= 0) return false;
        if (stock < 0) return false;
        return true;
    }

    public static boolean validarEmprendedorCompleto(Emprendedor e) {
        if (e == null) return false;
        if (!emailValido(e.email)) return false;
        if (!telefonoValido(e.telefono)) return false;
        if (e.nombre == null || e.nombre.length() < 2) return false;
        if (e.categoria == null) return false;
        return true;
    }

    public static boolean categoriaPermitida(String categoria) {
        String[] permitidas = {"comida", "artesania", "tecnologia", "ropa"};
        for (String c : permitidas) {
            if (c.equals(categoria)) return true;
        }
        return false;
    }
}