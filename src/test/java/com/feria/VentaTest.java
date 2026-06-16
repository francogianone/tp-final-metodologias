package com.feria;

import com.feria.modelos.Producto;
import com.feria.modelos.Venta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VentaTest {


    @Test
    void calcularTotal_sinDescuentos_retornaPrecioNormal() {
        Venta v = new Venta("V1", "E1", "Prod", 2, 100.0, "2026-01-01");
        assertEquals(200.0, v.calcularTotalConDescuento(), 0.01);
    }

    @Test
    void calcularTotal_cantidadMayorA10_aplicaDescuento10Porciento() {
        Venta v = new Venta("V1", "E1", "Prod", 11, 100.0, "2026-01-01");
        // 11 * 100 = 1100 → 1100 * 0.9 = 990
        assertEquals(990.0, v.calcularTotalConDescuento(), 0.01);
    }

    @Test
    void calcularTotal_totalMayorA5000_aplicaDescuentoAdicional5Porciento() {
        Venta v = new Venta("V1", "E1", "Prod", 2, 3000.0, "2026-01-01");
        // 2 * 3000 = 6000 → 6000 * 0.95 = 5700
        assertEquals(5700.0, v.calcularTotalConDescuento(), 0.01);
    }

    @Test
    void calcularTotal_ambosDescuentos_aplicaAmbos() {
        Venta v = new Venta("V1", "E1", "Prod", 11, 600.0, "2026-01-01");
        // 11 * 600 = 6600 → * 0.9 = 5940 → * 0.95 = 5643
        assertEquals(5643.0, v.calcularTotalConDescuento(), 0.01);
    }


    @Test
    void registrarPago_productoValido_marcaPagoYDescuentaStock() {
        Venta v = new Venta("V1", "E1", "Prod", 3, 100.0, "2026-01-01");
        Producto p = new Producto("Prod", 100.0, 10, "cat", "E1");

        v.registrarPagoYActualizarStock(p);

        assertTrue(v.pagoRealizado);
        assertEquals(7, p.stock);
    }

    @Test
    void registrarPago_productoNull_soloMarcaPago() {
        Venta v = new Venta("V1", "E1", "Prod", 3, 100.0, "2026-01-01");

        v.registrarPagoYActualizarStock(null);

        assertTrue(v.pagoRealizado);
    }



    @Test
    void generarRecibo_contieneIdVentaYProducto() {
        Venta v = new Venta("V99", "E1", "Alfajor", 2, 150.0, "2026-01-01");
        String recibo = v.generarRecibo();

        assertTrue(recibo.contains("V99"));
        assertTrue(recibo.contains("Alfajor"));
    }
}