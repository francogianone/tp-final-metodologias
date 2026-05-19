package com.feria.modelos;

import java.util.ArrayList;
import java.util.List;

public class Emprendedor {

    public String nombre;
    public String id;
    public String telefono;
    public String email;
    public String categoria;

    public List<Producto> productos;

    public Emprendedor(String nombre, String id, String telefono, String email, String categoria) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.productos = new ArrayList<>();
    }

    public String mostrarInfo() {
        String info = "Emprendedor: " + nombre + "\n";
        info += "ID: " + id + "\n";
        info += "Contacto: " + telefono + " | " + email + "\n";
        info += "Categoría: " + categoria + "\n";
        info += "Productos:\n";
        for (Producto p : productos) {
            info += "  - " + p.nombre + " ($" + p.precio + ")\n";
        }
        return info;
    }

    public boolean validarCompleto() {
        if (nombre == null || nombre.length() < 2) return false;
        if (email == null || !email.contains("@")) return false;
        if (categoria == null || (!categoria.equals("comida") && !categoria.equals("artesania") &&
                !categoria.equals("tecnologia") && !categoria.equals("ropa"))) return false;
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public int calcularValorTotalStock() {
        int total = 0;
        for (Producto p : productos) {
            total += p.precio * p.stock;
        }
        return total;
    }
}