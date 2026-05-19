package com.feria.servicios;

import com.feria.modelos.*;
import com.feria.utils.Validadores;
import java.util.ArrayList;
import java.util.List;

public class GestorFeria implements IGestorFeria {

    private List<Emprendedor> emprendedores;
    private List<Producto> productos;
    private List<Venta> ventas;

    public GestorFeria() {
        emprendedores = new ArrayList<>();
        productos = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    @Override
    public List<Emprendedor> getEmprendedores() {
        return emprendedores;
    }

    @Override
    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public List<Venta> getVentas() {
        return ventas;
    }

    public void registrarEmprendedor(Emprendedor e) {
        if (!Validadores.validarEmprendedorCompleto(e)) {
            System.out.println("Error: datos del emprendedor inválidos");
            return;
        }
        for (Producto p : e.productos) {
            productos.add(p);
        }
        emprendedores.add(e);
        System.out.println("Emprendedor registrado: " + e.getNombre());
    }

    public void registrarEmprendedorConProductos(String nombre, String id, String telefono,
                                                  String email, String categoria,
                                                  List<String> nombresProductos,
                                                  List<Double> precios,
                                                  List<Integer> stocks) {
        if (nombre == null || nombre.length() < 2) {
            System.out.println("Error: nombre inválido");
            return;
        }
        if (!Validadores.emailValido(email)) {
            System.out.println("Error: email inválido");
            return;
        }

        Emprendedor e = new Emprendedor(nombre, id, telefono, email, categoria);
        for (int i = 0; i < nombresProductos.size(); i++) {
            Producto p = new Producto(nombresProductos.get(i), precios.get(i), stocks.get(i), categoria, id);
            e.agregarProducto(p);
            productos.add(p);
        }
        emprendedores.add(e);
        System.out.println("Emprendedor registrado con " + nombresProductos.size() + " productos");
    }

    public void registrarVenta(String idVenta, String empId, String prodNombre, int cantidad, double precio, String fecha) {
        Producto productoEncontrado = null;
        for (Producto p : productos) {
            if (p.nombre.equals(prodNombre) && p.emprendedorId.equals(empId)) {
                productoEncontrado = p;
                break;
            }
        }

        if (productoEncontrado == null) {
            System.out.println("Producto no encontrado");
            return;
        }
        if (productoEncontrado.stock < cantidad) {
            System.out.println("Stock insuficiente");
            return;
        }

        Venta v = new Venta(idVenta, empId, prodNombre, cantidad, precio, fecha);
        ventas.add(v);
        productoEncontrado.stock -= cantidad;
        System.out.println("Venta registrada. Nuevo stock: " + productoEncontrado.stock);
    }

    public List<Emprendedor> getEmprendedoresConStockBajo() {
        List<Emprendedor> resultado = new ArrayList<>();
        for (Emprendedor e : emprendedores) {
            for (Producto p : e.productos) {
                if (p.isStockBajo()) {
                    resultado.add(e);
                    break;
                }
            }
        }
        return resultado;
    }

    public void procesarVentasPendientesYCobrar() {
        double totalRecaudado = 0;
        for (Venta v : ventas) {
            if (!v.pagoRealizado) {
                double monto = v.calcularTotalConDescuento();
                totalRecaudado += monto;
                v.pagoRealizado = true;
                System.out.println("Cobrada venta " + v.idVenta + " por $" + monto);
            }
        }
        System.out.println("Total recaudado: $" + totalRecaudado);
    }
}