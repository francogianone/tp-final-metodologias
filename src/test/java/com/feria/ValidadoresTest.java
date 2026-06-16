package com.feria;

import com.feria.modelos.Emprendedor;
import com.feria.utils.Validadores;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadoresTest {


    @Test
    void emailValido_emailCorrecto_retornaTrue() {
        assertTrue(Validadores.emailValido("test@gmail.com"));
    }

    @Test
    void emailValido_sinArroba_retornaFalse() {
        assertFalse(Validadores.emailValido("testgmail.com"));
    }

    @Test
    void emailValido_null_retornaFalse() {
        assertFalse(Validadores.emailValido(null));
    }

    @Test
    void emailValido_muyCorto_retornaFalse() {
        assertFalse(Validadores.emailValido("a@b"));
    }


    @Test
    void telefonoValido_telefonoCorrecto_retornaTrue() {
        assertTrue(Validadores.telefonoValido("12345678"));
    }

    @Test
    void telefonoValido_muyCorto_retornaFalse() {
        assertFalse(Validadores.telefonoValido("123"));
    }

    @Test
    void telefonoValido_null_retornaFalse() {
        assertFalse(Validadores.telefonoValido(null));
    }


    @Test
    void validarPrecioStock_valoresValidos_retornaTrue() {
        assertTrue(Validadores.validarPrecioStock(100.0, 5));
    }

    @Test
    void validarPrecioStock_precioNegativo_retornaFalse() {
        assertFalse(Validadores.validarPrecioStock(-1.0, 5));
    }

    @Test
    void validarPrecioStock_stockNegativo_retornaFalse() {
        assertFalse(Validadores.validarPrecioStock(100.0, -1));
    }


    @Test
    void validarEmprendedorCompleto_todosLosCamposValidos_retornaTrue() {
        Emprendedor e = new Emprendedor("Ana", "E1", "12345678", "ana@mail.com", "comida");
        assertTrue(Validadores.validarEmprendedorCompleto(e));
    }

    @Test
    void validarEmprendedorCompleto_null_retornaFalse() {
        assertFalse(Validadores.validarEmprendedorCompleto(null));
    }

    @Test
    void validarEmprendedorCompleto_emailInvalido_retornaFalse() {
        Emprendedor e = new Emprendedor("Ana", "E1", "12345678", "invalido", "comida");
        assertFalse(Validadores.validarEmprendedorCompleto(e));
    }
}