package com.feria.observadores;

public interface ObservadorStock {
    void notificarStockBajo(String nombreEmprendedor, String nombreProducto, int stockActual);
}
