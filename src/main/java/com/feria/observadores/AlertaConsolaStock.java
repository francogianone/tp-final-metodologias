package com.feria.observadores;

public class AlertaConsolaStock implements ObservadorStock {

    @Override
    public void notificarStockBajo(String nombreEmprendedor, String nombreProducto, int stockActual) {
        System.out.println("ALERTA: stock bajo - " + nombreEmprendedor + " / " + nombreProducto + " (stock: " + stockActual + ")");
    }
}
