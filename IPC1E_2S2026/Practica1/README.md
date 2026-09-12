# IPC1E_2S2026
# 🚗 Sistema de Gestión de Estacionamiento en Java

Un sistema interactivo de consola desarrollado en **Java** para administrar las operaciones completas de un parqueo automatizado. La aplicación utiliza una matriz de **10x10** para mapear visualmente el tablero del estacionamiento, controlando accesos, cobros en tiempo real y el cálculo de rutas óptimas de evacuación.

---

## 📋 Tabla de Contenidos
- [Descripción General](#-descripción-general)
- [Mapa del Parqueo (Matriz 10x10)](#-mapa-del-parqueo-matriz-10x10)
- [Funcionalidades Principales](#-funcionalidades-principales)
- [Requisitos del Sistema](#-requisitos-del-sistema)
- [Instrucciones de Compilación y Ejecución](#-instrucciones-de-compilación-y-ejecución)
- [Historial de Commits](#-historial-de-commits)
- [Información Académica](#-información-académica)

---

## 🚘 Descripción General
El proyecto simula el comportamiento de un parqueo inteligente. Los vehículos ingresan asignando coordenadas dentro de la cuadrícula, el sistema toma la hora exacta de ingreso en milisegundos y, al momento del retiro, calcula la tarifa según el tiempo transcurrido en consola. Además, cuenta con un algoritmo de búsqueda en anchura (**BFS**) para determinar el camino más corto entre la entrada y la salida del parqueo esquivando vehículos estacionados.

---

## 🗺️ Mapa del Parqueo (Matriz 10x10)

El parqueo cuenta con 64 espacios interiores y acceso en los bordes:

| Carácter | Elemento | Descripción |
| :---: | :--- | :--- |
| `=` | Vía Exterior | Bordes del parqueo (Fila 0, Fila 9, Columna 0, Columna 9). |
| `E` | Entrada | Acceso generado de forma aleatoria en los bordes. |
| `S` | Salida | Egreso generado de forma aleatoria en los bordes. |
| `L` | Lugar Libre | Espacio interno de parqueo disponible (64 casillas). |
| `A` | Automóvil | Espacio ocupado por un vehículo ingresado. |
| `*` | Ruta BFS | Camino óptimo calculado entre la Entrada (`E`) y la Salida (`S`). |

---

## ✨ Funcionalidades Principales

1. **Ingreso de Vehículos y Validación:**
   - Exige placas con el formato oficial `P####LLL` (ejemplo: `P123ABC`).
   - Bloquea el ingreso si la placa ya se encuentra dentro del parqueo.
   - Guarda el timestamp de entrada (`System.currentTimeMillis()`).

2. **Retiro de Vehículos y Cobro:**
   - Calcula el tiempo exacto transcurrido en segundos.
   - Aplica una tarifa configurable de **Q0.50 por segundo**.
   - Genera un recibo de pago y libera la casilla para futuros vehículos.

3. **Búsqueda por Placa:**
   - Ubica la casilla exacta `(Fila, Columna)` donde está estacionado un vehículo en tiempo real.

4. **Algoritmo de Ruta Más Corta (BFS):**
   - Implementa Búsqueda en Anchura (*Breadth-First Search*) para encontrar el trayecto más corto desde `E` hasta `S`.
   - Considera a los vehículos (`A`) como obstáculos a esquivar.

5. **Reporte Financiero:**
   - Contabiliza el total acumulado en Quetzales por concepto de cobros a vehículos retirados.

---

## 🛠️ Requisitos del Sistema
- **Java Development Kit (JDK):** Versión 11 o superior.
- **IDE / Editor:** Visual Studio Code, IntelliJ IDEA o ejecución directa en consola.

---

## 🚀 Instrucciones de Compilación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/EduardoAjsivinac/IPC1E_2S2026.git](https://github.com/EduardoAjsivinac/IPC1E_2S2026.git)