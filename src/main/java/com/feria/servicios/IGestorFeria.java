package com.feria.servicios;

import com.feria.modelos.Emprendedor;
import com.feria.modelos.Producto;
import com.feria.modelos.Venta;
import java.util.List;

public interface IGestorFeria {
    List<Emprendedor> getEmprendedores();
    List<Producto> getProductos();
    List<Venta> getVentas();
}
