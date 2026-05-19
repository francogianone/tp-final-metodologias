package com.feria.servicios;

import com.feria.modelos.*;

public class Reportes {

    public String generarReportePorCategoria(IGestorFeria gestor, String categoria) {
        String reporte = "=== REPORTE DE EMPRENDEDORES - CATEGORIA: " + categoria + " ===\n";
        for (Emprendedor e : gestor.getEmprendedores()) {
            if (e.categoria.equals(categoria)) {
                reporte += e.mostrarInfo();
                reporte += "---\n";
            }
        }
        return reporte;
    }

    public double calcularVentasTotales(IGestorFeria gestor) {
        double total = 0;
        for (Venta v : gestor.getVentas()) {
            total += v.calcularTotalConDescuento();
        }
        return total;
    }

    public void imprimirResumenEjecutivo(IGestorFeria gestor) {
        System.out.println("========== RESUMEN EJECUTIVO ==========");
        System.out.println("Total emprendedores: " + gestor.getEmprendedores().size());
        System.out.println("Total productos: " + gestor.getProductos().size());
        System.out.println("Total ventas: " + gestor.getVentas().size());

        double totalVentas = 0;
        for (Venta v : gestor.getVentas()) {
            totalVentas += v.calcularTotalConDescuento();
        }
        System.out.println("Total facturado: $" + totalVentas);

        int emprendedoresStockBajo = 0;
        for (Emprendedor e : gestor.getEmprendedores()) {
            for (Producto p : e.productos) {
                if (p.isStockBajo()) {
                    emprendedoresStockBajo++;
                    break;
                }
            }
        }
        System.out.println("Emprendedores con stock bajo: " + emprendedoresStockBajo);
        System.out.println("=======================================");
    }
}