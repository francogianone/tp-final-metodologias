# Trabajo Practico Integrador - Metodologias de Sistemas II
## Feria de Emprendedores

---

## Semana 1 - Refactorizacion inicial (Codigo limpio + SOLID)

### Code Smells detectados

**1. Nombres cripticos en `Emprendedor`**

Los atributos de la clase usaban nombres de una sola letra (`n`, `t`, `m`, `cat`, `prods`) que no expresan su proposito. Esto dificulta la lectura y el mantenimiento del codigo.

```java
// Antes
public String n;   // nombre
public String t;   // telefono
public String m;   // email
public String cat; // categoria
public List<Producto> prods;

// Despues
public String nombre;
public String telefono;
public String email;
public String categoria;
public List<Producto> productos;
```

---

**2. Metodo con multiples responsabilidades: `mostrarInfoYValidar()`**

El metodo construia el string de presentacion del emprendedor Y ejecutaba validaciones de negocio en el mismo lugar, mezclando dos responsabilidades distintas en un solo metodo.

```java
// Antes: un metodo que hace dos cosas
public String mostrarInfoYValidar() {
    String info = "Emprendedor: " + n + "\n";
    // ... arma el string ...
    if (n == null || n.length() < 2) {
        info += "NOMBRE DEMASIADO CORTO\n"; // validacion mezclada con presentacion
    }
    // ...
}

// Despues: solo presenta datos (la validacion ya existe en validarCompleto())
public String mostrarInfo() {
    String info = "Emprendedor: " + nombre + "\n";
    // ... solo arma el string, sin validaciones ...
    return info;
}
```

---

**3. Codigo duplicado**

Tres instancias de logica duplicada:

- `hayStockBajo()` e `isStockBajo()` en `Producto` eran identicos. Se elimino `hayStockBajo()`.
- `generarReportePorCategoriaAlternativo()` en `Reportes` duplicaba `generarReportePorCategoria()`. Se elimino el duplicado.
- La logica `p.stock < 5` estaba hardcodeada en `Reportes.imprimirResumenEjecutivo()` ignorando el metodo `isStockBajo()` ya existente.
- Acceso directo a campos internos de `GestorFeria` desde `Main` (`gestor.emprendedores.add(emp2)`) saltando la logica de registro.

---

### Refactorizaciones aplicadas

| # | Refactorizacion | Archivo |
|---|---|---|
| 1 | Renombrar campos cripticos (`n`→`nombre`, `t`→`telefono`, `m`→`email`, `cat`→`categoria`, `prods`→`productos`) | `Emprendedor.java` |
| 2 | Reemplazar `mostrarInfoYValidar()` por `mostrarInfo()` con unica responsabilidad | `Emprendedor.java` |
| 3 | Eliminar metodo duplicado `hayStockBajo()` | `Producto.java` |
| 4 | Eliminar metodo duplicado `generarReportePorCategoriaAlternativo()` | `Reportes.java` |
| 5 | Usar `p.isStockBajo()` en lugar de `p.stock < 5` hardcodeado | `Reportes.java` |
| 6 | Agregar `registrarEmprendedor(Emprendedor e)` en `GestorFeria`; corregir acceso directo desde `Main` | `GestorFeria.java`, `Main.java` |
| 7 | Usar `Validadores.emailValido()` en `GestorFeria` en lugar de logica inline repetida | `GestorFeria.java` |
| 8 | Crear interfaz `IGestorFeria`; hacer que `Reportes` dependa de ella en lugar de `GestorFeria` concreto | `IGestorFeria.java`, `Reportes.java` |

---

### Principios SOLID aplicados

**SRP - Single Responsibility Principle**

`mostrarInfoYValidar()` tenia dos responsabilidades: presentar datos y validarlos. Se separo en `mostrarInfo()` (solo presentacion). La validacion ya existia en `validarCompleto()` y en la clase `Validadores`. Cada metodo ahora tiene una sola razon para cambiar.

**DIP - Dependency Inversion Principle**

`Reportes` dependia directamente de `GestorFeria` (clase concreta). Se extrajo la interfaz `IGestorFeria` con los metodos necesarios (`getEmprendedores()`, `getProductos()`, `getVentas()`). Ahora `Reportes` depende de la abstraccion, no de la implementacion. `GestorFeria` implementa `IGestorFeria`.

```java
// Antes: dependencia directa sobre clase concreta
public String generarReportePorCategoria(GestorFeria gestor, ...) { ... }

// Despues: dependencia sobre abstraccion
public String generarReportePorCategoria(IGestorFeria gestor, ...) { ... }
```

---

### Estructura del proyecto

```
src/main/java/com/feria/
    Main.java
    modelos/
        Emprendedor.java
        Producto.java
        Venta.java
    servicios/
        IGestorFeria.java      <- nuevo
        GestorFeria.java
        Reportes.java
    utils/
        Validadores.java
```

---

### Como ejecutar

```bash
mvn compile
mvn package
java -cp target/feria-emprendedores-1.0-SNAPSHOT.jar com.feria.Main
```
