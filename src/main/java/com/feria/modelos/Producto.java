package com.feria.modelos;

public class Producto {

    public String nombre;
    public double precio;
    public int stock;
    public String categoriaProducto;
    public String emprendedorId;

    public Producto(String nombre, double precio, int stock, String categoriaProd, String empId) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoriaProducto = categoriaProd;
        this.emprendedorId = empId;
    }

    public double valorTotal() {
        return precio * stock;
    }

    public String mostrar() {
        return nombre + " - $" + precio + " (stock: " + stock + ")";
    }

    public boolean isStockBajo() {
        return stock < 5;
    }
}