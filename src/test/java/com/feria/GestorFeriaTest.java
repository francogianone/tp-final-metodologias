package com.feria;

import com.feria.modelos.Emprendedor;
import com.feria.modelos.Producto;
import com.feria.observadores.ObservadorStock;
import com.feria.servicios.GestorFeria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorFeriaTest {

    private GestorFeria gestor;

    @Mock
    private ObservadorStock observadorMock;

    @BeforeEach
    void setUp() {
        gestor = new GestorFeria();
        gestor.suscribir(observadorMock);
    }


    @Test
    void registrarEmprendedor_datosValidos_loAgrega() {
        Emprendedor e = new Emprendedor("Ana", "E001", "34234567890", "ana@gmail.com", "comida");
        gestor.registrarEmprendedor(e);
        assertEquals(1, gestor.getEmprendedores().size());
    }

    @Test
    void registrarEmprendedor_emailInvalido_noLoAgrega() {
        Emprendedor e = new Emprendedor("Ana", "E001", "34234567890", "emailinvalido", "comida");
        gestor.registrarEmprendedor(e);
        assertEquals(0, gestor.getEmprendedores().size());
    }

    @Test
    void registrarEmprendedor_nombreMuyCorto_noLoAgrega() {
        Emprendedor e = new Emprendedor("A", "E001", "34234567890", "ana@gmail.com", "comida");
        gestor.registrarEmprendedor(e);
        assertEquals(0, gestor.getEmprendedores().size());
    }


    @Test
    void registrarVenta_stockSuficiente_descuentaStock() {
        gestor.registrarEmprendedorConProductos(
            "Ana", "E001", "34234567890", "ana@gmail.com", "comida",
            Arrays.asList("Empanadas"),
            Arrays.asList(500.0),
            Arrays.asList(20)
        );

        gestor.registrarVenta("V001", "E001", "Empanadas", 5, 500.0, "2026-05-12");

        Producto p = gestor.getProductos().get(0);
        assertEquals(15, p.stock);
    }

    @Test
    void registrarVenta_stockInsuficiente_noRegistraVenta() {
        gestor.registrarEmprendedorConProductos(
            "Ana", "E001", "34234567890", "ana@gmail.com", "comida",
            Arrays.asList("Empanadas"),
            Arrays.asList(500.0),
            Arrays.asList(3)
        );

        gestor.registrarVenta("V001", "E001", "Empanadas", 10, 500.0, "2026-05-12");

        assertEquals(0, gestor.getVentas().size());
    }

    @Test
    void registrarVenta_productoInexistente_noRegistraVenta() {
        gestor.registrarVenta("V001", "E001", "ProductoFalso", 1, 100.0, "2026-05-12");
        assertEquals(0, gestor.getVentas().size());
    }


    @Test
    void registrarVenta_stockBajoResultante_notificaObservador() {
        gestor.registrarEmprendedorConProductos(
            "Ana", "E001", "34234567890", "ana@gmail.com", "comida",
            Arrays.asList("Empanadas"),
            Arrays.asList(500.0),
            Arrays.asList(5)  // stock inicial 5, umbral bajo es < 5
        );

        // Vendemos 1, queda stock = 4 → isStockBajo() = true
        gestor.registrarVenta("V001", "E001", "Empanadas", 1, 500.0, "2026-05-12");

        verify(observadorMock, times(1))
            .notificarStockBajo("Ana", "Empanadas", 4);
    }

    @Test
    void registrarVenta_stockSuficientePostVenta_noNotificaObservador() {
        gestor.registrarEmprendedorConProductos(
            "Ana", "E001", "34234567890", "ana@gmail.com", "comida",
            Arrays.asList("Empanadas"),
            Arrays.asList(500.0),
            Arrays.asList(50)
        );

        gestor.registrarVenta("V001", "E001", "Empanadas", 1, 500.0, "2026-05-12");

        verify(observadorMock, never())
            .notificarStockBajo(anyString(), anyString(), anyInt());
    }
}