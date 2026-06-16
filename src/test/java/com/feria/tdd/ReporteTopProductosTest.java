package com.feria.tdd;

import com.feria.modelos.Emprendedor;
import com.feria.servicios.GestorFeria;
import com.feria.servicios.Reportes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReporteTopProductosTest {

    private GestorFeria gestor;
    private Reportes reportes;

    @BeforeEach
    void setUp() {
        gestor = new GestorFeria();
        reportes = new Reportes();

        gestor.registrarEmprendedorConProductos(
            "Ana", "E001", "34234567890", "ana@gmail.com", "comida",
            Arrays.asList("Empanadas", "Tortas", "Alfajores"),
            Arrays.asList(500.0, 1500.0, 300.0),
            Arrays.asList(50, 10, 100)
        );

        gestor.registrarVenta("V001", "E001", "Empanadas", 5, 500.0, "2026-05-12");
        gestor.registrarVenta("V002", "E001", "Empanadas", 3, 500.0, "2026-05-13");
        gestor.registrarVenta("V003", "E001", "Tortas", 2, 1500.0, "2026-05-13");
    }

    @Test
    void topProductosMasVendidos_retornaOrdenadosPorCantidad() {
        List<String> top = reportes.topProductosMasVendidos(gestor, 2);

        assertEquals(2, top.size());
        assertEquals("Empanadas", top.get(0)); // 8 unidades vendidas
        assertEquals("Tortas", top.get(1));    // 2 unidades vendidas
    }

    @Test
    void topProductosMasVendidos_limiteMayorQueCantidadDeProductos_retornaLoQueHay() {
        List<String> top = reportes.topProductosMasVendidos(gestor, 10);
        assertEquals(2, top.size()); // solo Empanadas y Tortas tienen ventas
    }

    @Test
    void topProductosMasVendidos_sinVentas_retornaListaVacia() {
        GestorFeria gestorVacio = new GestorFeria();
        List<String> top = reportes.topProductosMasVendidos(gestorVacio, 3);
        assertTrue(top.isEmpty());
    }
}