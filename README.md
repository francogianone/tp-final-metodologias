# Trabajo Practico Integrador - Metodologias de Sistemas II
## Feria de Emprendedores
## Grupo: Gianone Franco, Godoy Nahuel, Acosta Florencia, Toscanini Juan Manuel
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

En `Emprendedor.java` se renombraron los campos cripticos: `n` paso a `nombre`, `t` a `telefono`, `m` a `email`, `cat` a `categoria` y `prods` a `productos`. Tambien se reemplazo el metodo `mostrarInfoYValidar()` por `mostrarInfo()`, que solo presenta datos sin mezclar validaciones.

En `Producto.java` se elimino el metodo `hayStockBajo()`, que era identico al ya existente `isStockBajo()`.

En `Reportes.java` se elimino el metodo `generarReportePorCategoriaAlternativo()`, que duplicaba la logica de `generarReportePorCategoria()`. Ademas, se reemplazo la condicion hardcodeada `p.stock < 5` por una llamada al metodo `p.isStockBajo()`, y el calculo inline del total facturado en `imprimirResumenEjecutivo()` fue reemplazado por una llamada al metodo `calcularVentasTotales()`.

En `GestorFeria.java` se agrego el metodo `registrarEmprendedor(Emprendedor e)` para centralizar el registro, eliminando el acceso directo a la lista interna que se hacia desde `Main.java`. Tambien se reemplazo la logica de validacion de email inline por una llamada a `Validadores.emailValido()`.

Se extrajo la interfaz `IGestorFeria` con los metodos `getEmprendedores()`, `getProductos()` y `getVentas()`. `GestorFeria` ahora la implementa y `Reportes` depende de ella en lugar de la clase concreta.

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

## Semana 2 - Patron de diseño

### Problema 

En`GestorFeria`, el metodo getEmprendedoresConStockBajo() pedia ser llamado manualmente despues de cada venta para saber si algun producto habia quedado con stock bajo. No existia ninguna notificacion automatica

### Patron elegido: Observer

Implementamos el patron Observer para que GestorFeria notifique automaticamente cuando el stock de un producto esta por debajo de los limites al registrar una venta

- `ObservadorStock` : define el contrato `notificarStockBajo(nombreEmprendedor, nombreProducto, stockActual)`
- `AlertaConsolaStock` : imprime la alerta en consola
- `GestorFeria` : mantiene una lista de observadores y llama `notificarStockBajo()` dentro de `registrarVenta()` cuando corresponde

### Justificacion

Una solucion alternativa era llamar `getEmprendedoresConStockBajo()` manualmente desde main despues de cada venta. Funcionaba, pero acopla a quien registra ventas con la responsabilidad de revisar el stock, y si hay otros modulos que necesiten reaccionar, cada uno tendria que hacer lo mismo por su cuenta

**Implementado:** GestorFeria notifica solo a quienes se hayan suscrito, sin saber quienes son ni cuantos. Se pueden agregar nuevos observadores  sin tocar GestorFeria. La logica de ventas queda separada de la logica de reaccion ante el evento


### Diagrama UML codigo actual
![Diagrama UML del patrón Observer implementado](umlsemana2.png)
---

## Semana 3 - Testing automatizado (JUnit 5 + Mockito + TDD)

### Suite de tests

Se crearon 4 clases de test con un total de **31 pruebas**, todas pasando

| Clase | Tests | Que cubre |
|---|---|---|
| GestorFeriaTest | 8 | Registro de emprendedores, ventas, validaciones, notificacion Observer con mock |
| VentaTest | 7 | Calculo de descuentos (casos limite), registro de pago, generacion de recibo |
| ValidadoresTest | 13 | Email, telefono, precio/stock, validacion completa de emprendedor |
| ReporteTopProductosTest | 3 | Nueva funcionalidad desarrollada con TDD |

### Mocks con Mockito

En "GestorFeriaTest" se uso Mockito para testear el patron Observer sin depender de la salida por consola. En lugar de usar "AlertaConsolaStock" real, se inyecta un observador falso y se verifica si fue llamado o no:

```java
// Verifica que se notifico exactamente una vez cuando el stock quedo bajo
verify(observadorMock, times(1)).notificarStockBajo("Ana", "Empanadas", 4);

// Verifica que NO se notifico cuando el stock seguia siendo suficiente
verify(observadorMock, never()).notificarStockBajo(anyString(), anyString(), anyInt());
```

### TDD aplicado – nueva funcionalidad: `topProductosMasVendidos`

Se agrego el metodo "topProductosMasVendidos(IGestorFeria gestor, int limite)" en "Reportes".
Devuelve los nombres de productos ordenados de mayor a menor por cantidad total vendida, limitado a N resultados

**Ciclo aplicado:**

- **Rojo:** Se escribio "ReporteTopProductosTest" antes de que el metodo existiera. Al correr `mvn test` fallaba con error de metodo no encontrado
- **Verde:** Se implemento el metodo acumulando cantidades en un "Map" y ordenando con "Stream"
- **Refactor:** Se simplific el ordenamiento usando "Map.Entry.comparingByValue().reversed()" de la API de Streams

**Casos cubiertos:**
- Lista ordenada correctamente con multiples productos y distintas cantidades vendidas
- Limite mayor a la cantidad de productos con ventas: devuelve solo los que tienen ventas, sin error
- Sin ventas registradas: devuelve lista vacia

### Resultado de los tests

```
Tests run: 8,  Failures: 0, Errors: 0, Skipped: 0  -- GestorFeriaTest
Tests run: 3,  Failures: 0, Errors: 0, Skipped: 0  -- ReporteTopProductosTest
Tests run: 13, Failures: 0, Errors: 0, Skipped: 0  -- ValidadoresTest
Tests run: 7,  Failures: 0, Errors: 0, Skipped: 0  -- VentaTest

Tests run: 31, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Semana 4 - Integracion continua + proyecto final

### Pipeline CI con GitHub Actions

Se configuro el archivo ".github/workflows/ci.yml". Cada vez que se hace un push a cualquier rama, GitHub Actions automaticamente:

1. Levanta una maquina Ubuntu con Java 17
2. Ejecuta `mvn test`
3. Muestra verde o rojo segun si los tests pasan
4. Guarda el reporte de Surefire como artefacto descargable

### Como ejecutar el programa

```bash
mvn package
java -cp target/feria-emprendedores-1.0-SNAPSHOT.jar com.feria.Main
```

### Como correr los tests

```bash
mvn test
```

Los reportes quedan en "target/surefire-reports/".

### Decisiones tecnicas

1. Se aplico **SRP** separando validaciones en "Validadores" y reportes en "Reportes"
2. Se aplico **DIP** usando la interfaz "IGestorFeria" para que "Reportes" no dependa de la clase concreta
3. Se implemento el patron **Observer** para notificacion de stock bajo, permitiendo agregar nuevos tipos de alerta sin tocar "GestorFeria"
4. Los tests usan **Mockito** para aislar el observador y verificar interacciones sin depender de salida por consola
5. La nueva funcionalidad "topProductosMasVendidos" fue desarrollada con **TDD** (rojo → verde → refactor)
6. El pipeline de **CI con GitHub Actions** ejecuta `mvn test` automaticamente en cada push

---

## Informe final

### Refactorizacion

Se detectaron tres code smells principales: campos con nombres cripticos en "Emprendedor", un metodo con dos responsabilidades mezcladas (mostrarInfoYValidar) y codigo duplicado en "Producto" y "Reportes". Se aplicaron **SRP** y **DIP**, se separo la presentacion de la validacion, se elimino codigo duplicado y se extrajo la interfaz "IGestorFeria" para desacoplar "Reportes" de la implementacion concreta

### Patron aplicado

Se implemento **Observer** para resolver la notificacion automatica de stock bajo al registrar ventas. La alternativa descartada era verificar el stock manualmente desde "Main" despues de cada venta, lo que acoplaba la responsabilidad de revision a quien registra. Con Observer, "GestorFeria" notifica a todos los suscriptores sin saber quienes son, y se pueden agregar nuevas alertas sin modificar ninguna clase existente

### Estrategia de testing

Se combinaron tests unitarios clasicos con mocks de Mockito para aislar dependencias. Se cubrio "GestorFeria" (flujo principal y Observer), "Venta" (logica de descuentos y casos limite), "Validadores" (todos los casos posibles incluyendo nulos) y "Reportes" (nueva funcionalidad via TDD). El uso de mocks permitio verificar interacciones del patron Observer sin acoplar los tests a la salida por consola

### Resultados de CI

El pipeline de GitHub Actions ejecuta los 31 tests automaticamente en cada push. El estado del pipeline queda visible en la pestana Actions del repositorio, y los reportes de Surefire se guardan como artefactos descargables por 90 dias
