
Trabajo Práctico Integrador – Metodologías de Sistemas II

Entrega final: repositorio Git + informe + defensa

Objetivo general

Aplicar de manera integrada:

Código limpio y principios SOLID
Refactorización
Al menos un patrón de diseño
Pruebas unitarias con mocks y TDD
Pipeline básico de integración continua
sobre un sistema base existente proporcionado por la cátedra

.

Estructura por semanas

Semana 1 – Refactorización inicial (código limpio + SOLID)

Actividades:

Identificar 3 códigos de olores en el código base.
Refactorizar aplicando:
a. Nomenclatura clara
b. Métodos cortos y con una sola responsabilidad
c. Al menos 2 principios SOLID (ej. SRP, OCP, o DIP)
Mantenga exactamente la misma funcionalidad externa.
Entregable parcial 1 :

Código refactorizado subido a un repositorio Git (local o remoto)
Listado breve de:
o Olores detectados
o Refactorizaciones aplicadas
o Principios SOLID usados
Semana 2 – Patrón de diseño

Actividades:

Identificar un problema recurrente en el sistema que pueda resolverse con un
patrón de diseño (creacional, estructural o de comportamiento).
Implementar el patrón elegido.
Justificar por qué ese patrón es apropiado (alternativa simple descartada, mejora
obtenida).
Entregable parcial 2:

Código modificado con el patrón incorporado
Diagrama UML simple (antes/después o solo después)
Justificación técnica: máx 1 página
Semana 3 – Testing automatizado (unitario + simulacros + TDD)

Actividades:

Escribir pruebas unitarias para al menos 3 métodos del sistema (incluidos casos
límite).
Usar simulacros para aislar dependencias externas (base de datos, API, etc.).
Agregar una nueva funcionalidad pequeña usando TDD :
a. Rojo (escribir prueba que falla)
b. Verde (implementar lo mínimo para que pase)
c. Refactor (limpiar el código nuevo)
Entregable parcial 3:

Suite de tests automatizados
Explicación breve del TDD aplicado (qué funcionalidad nueva se agregó)
Informe de cobertura (captura de pantalla o texto)
Semana 4 – Integración continua + proyecto final

Actividades:

Configure un pipeline básico de CI (GitHub Actions, GitLab CI, o similar) que:
a. Ejecutar automáticamente todos los tests
b. Indique si pasan o fallan
Preparar el repositorio final con:
a. Todo el código (refactorizado + patrón + pruebas)
b. Archivo README.md con:
i. Cómo ejecutar el programa
ii. Cómo correr las pruebas
iii. Breve descripción de decisiones técnicas (máx 10 líneas)
Subir el proyecto a un repositorio remoto (GitHub/GitLab público o privado con
acceso al docente).
Entregable final:

Enlace al repositorio remoto
Pipeline CI funcionando (captura o enlace directo)
Informe final máximo 2 páginas que resumen:
o Refactorización
o Patrón aplicado
o Estrategia de testing
o Resultados de CI