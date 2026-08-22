import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Sistema de estacionamiento de vehículos en consola (Java).
 * Permite registrar automóviles, controlar espacios ocupados, retirar
 * vehículos, buscar placas, mostrar el estado del estacionamiento,
 * calcular la ruta más corta entre la entrada y la salida, y consultar
 * los ingresos recaudados.
 */
public class Estacionamiento {

    // ===================== CONSTANTES =====================
    static final int FILAS = 8;          // filas internas del estacionamiento
    static final int COLUMNAS = 8;       // columnas internas del estacionamiento
    static final double TARIFA = 10.00;  // tarifa fija por vehículo

    // ===================== ESTRUCTURAS DE DATOS =====================
    static String[][] placas = new String[FILAS][COLUMNAS]; // null = espacio libre
    static int espaciosOcupados = 0;

    static int filaEntrada, colEntrada; // coordenadas sobre el tablero 10x10 (vía exterior)
    static int filaSalida, colSalida;

    static int vehiculosCobrados = 0;
    static double totalRecaudado = 0.0;

    static Scanner sc = new Scanner(System.in);

    // ===================== MAIN =====================
    public static void main(String[] args) {
        generarEntradaSalida();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: ingresarVehiculo(); break;
                case 2: retirarVehiculo(); break;
                case 3: mostrarEstacionamiento(); break;
                case 4: buscarVehiculoPorPlaca(); break;
                case 5: mostrarRutaMasCorta(); break;
                case 6: mostrarIngresos(); break;
                case 7: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 7);

        sc.close();
    }

    static void mostrarMenu() {
        System.out.println("===== SISTEMA DE ESTACIONAMIENTO =====");
        System.out.println("1. Ingresar vehículo");
        System.out.println("2. Retirar vehículo");
        System.out.println("3. Mostrar estacionamiento");
        System.out.println("4. Buscar vehículo por placa");
        System.out.println("5. Mostrar ruta más corta entre entrada y salida");
        System.out.println("6. Mostrar ingresos");
        System.out.println("7. Salir");
    }

    // ===================== UTILIDADES DE LECTURA =====================
    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un valor numérico.");
            }
        }
    }

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    // ===================== ENTRADA / SALIDA ALEATORIAS =====================
    static void generarEntradaSalida() {
        Random rand = new Random();
        int[] pos1 = generarPosicionPerimetro(rand);
        int[] pos2;
        do {
            pos2 = generarPosicionPerimetro(rand);
        } while (pos2[0] == pos1[0] && pos2[1] == pos1[1]);

        filaEntrada = pos1[0];
        colEntrada = pos1[1];
        filaSalida = pos2[0];
        colSalida = pos2[1];
    }

    // Genera una posición aleatoria sobre la vía exterior del tablero 10x10,
    // sin ubicarse en ninguna esquina.
    static int[] generarPosicionPerimetro(Random rand) {
        int fila, col;
        do {
            int lado = rand.nextInt(4); // 0=arriba,1=abajo,2=izquierda,3=derecha
            switch (lado) {
                case 0: fila = 1;  col = 1 + rand.nextInt(10); break;
                case 1: fila = 10; col = 1 + rand.nextInt(10); break;
                case 2: fila = 1 + rand.nextInt(10); col = 1;  break;
                default: fila = 1 + rand.nextInt(10); col = 10; break;
            }
        } while (esEsquina(fila, col));
        return new int[]{fila, col};
    }

    static boolean esEsquina(int fila, int col) {
        return (fila == 1 || fila == 10) && (col == 1 || col == 10);
    }

    // ===================== OPCIÓN 1: INGRESAR VEHÍCULO =====================
    static void ingresarVehiculo() {
        System.out.println();
        System.out.println("--- Ingresar vehículo ---");

        if (espaciosOcupados >= FILAS * COLUMNAS) {
            System.out.println("El estacionamiento está lleno. No se pueden ingresar más vehículos.");
            return;
        }

        // --- Validación de placa ---
        String placa;
        while (true) {
            placa = leerTexto("Ingrese la placa (formato P###LLL): ").toUpperCase();
            if (!validarFormatoPlaca(placa)) {
                System.out.println("Formato de placa inválido. Ejemplo válido: P401JZQ");
                continue;
            }
            if (buscarPlaca(placa) != null) {
                System.out.println("Esa placa ya se encuentra registrada en el estacionamiento.");
                continue;
            }
            break;
        }

        // --- Validación de fila y columna ---
        int fila, col;
        while (true) {
            fila = leerEntero("Fila (1-8): ");
            col = leerEntero("Columna (1-8): ");
            if (fila < 1 || fila > FILAS || col < 1 || col > COLUMNAS) {
                System.out.println("Posición fuera de rango. Debe estar entre 1 y 8.");
                continue;
            }
            if (placas[fila - 1][col - 1] != null) {
                System.out.println("Ese espacio ya está ocupado. Elija otro.");
                continue;
            }
            break;
        }

        // --- Cobro y cálculo de cambio ---
        System.out.printf("Tarifa: Q%.2f%n", TARIFA);
        double monto;
        while (true) {
            monto = leerDouble("Ingrese el monto entregado: Q");
            if (monto < 0) {
                System.out.println("El monto no puede ser negativo.");
                continue;
            }
            if (monto < TARIFA) {
                System.out.println("Pago insuficiente. Debe ingresar al menos Q" + String.format("%.2f", TARIFA));
                continue;
            }
            break;
        }

        double cambio = monto - TARIFA;

        // --- Registro del vehículo (solo después de completar el pago) ---
        placas[fila - 1][col - 1] = placa;
        espaciosOcupados++;
        vehiculosCobrados++;
        totalRecaudado += TARIFA;

        System.out.printf("Cambio: Q%.2f%n", cambio);
        System.out.println("Vehículo ingresado correctamente.");
    }

    static boolean validarFormatoPlaca(String placa) {
        if (placa == null || placa.length() != 7) return false;
        return placa.matches("P\\d{3}[A-Z]{3}");
    }

    // Devuelve un arreglo {fila, columna} (1-8) si encuentra la placa, o null si no existe.
    static int[] buscarPlaca(String placa) {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (placa.equals(placas[f][c])) {
                    return new int[]{f + 1, c + 1};
                }
            }
        }
        return null;
    }

    // ===================== OPCIÓN 2: RETIRAR VEHÍCULO =====================
    static void retirarVehiculo() {
        System.out.println();
        System.out.println("--- Retirar vehículo ---");

        String placa;
        while (true) {
            placa = leerTexto("Ingrese la placa del vehículo a retirar: ").toUpperCase();
            if (!validarFormatoPlaca(placa)) {
                System.out.println("Formato de placa inválido. Ejemplo válido: P401JZQ");
                continue;
            }
            break;
        }

        int[] posicion = buscarPlaca(placa);
        if (posicion == null) {
            System.out.println("No se encontró un vehículo con esa placa.");
            return;
        }

        System.out.println("Vehículo encontrado en fila " + posicion[0] + ", columna " + posicion[1] + ".");
        placas[posicion[0] - 1][posicion[1] - 1] = null;
        espaciosOcupados--;
        System.out.println("Vehículo retirado correctamente.");
        // Nota: los ingresos acumulados NO se modifican, ya que el pago se realizó al ingresar.
    }

    // ===================== OPCIÓN 3: MOSTRAR ESTACIONAMIENTO =====================
    static void mostrarEstacionamiento() {
        System.out.println();
        System.out.println("E = Entrada   S = Salida   = = Vía exterior");
        System.out.println("L = Lugar libre   A = Automóvil");
        System.out.println();

        System.out.print("    ");
        for (int c = 1; c <= 10; c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();

        for (int fila = 1; fila <= 10; fila++) {
            System.out.printf("%2d  ", fila);
            for (int col = 1; col <= 10; col++) {
                System.out.printf("%2s ", obtenerSimboloCelda(fila, col));
            }
            System.out.println();
        }

        int libres = (FILAS * COLUMNAS) - espaciosOcupados;
        System.out.println();
        System.out.println("Espacios libres: " + libres);
        System.out.println("Espacios ocupados: " + espaciosOcupados);
    }

    // fila y col en escala 1-10 (todo el tablero, incluyendo vía exterior).
    // La celda interior (fila 2-9, col 2-9) corresponde al índice interno 1-8.
    static String obtenerSimboloCelda(int fila, int col) {
        if (fila == filaEntrada && col == colEntrada) return "E";
        if (fila == filaSalida && col == colSalida) return "S";
        if (fila == 1 || fila == 10 || col == 1 || col == 10) return "=";
        if (placas[fila - 2][col - 2] != null) return "A";
        return "L";
    }

    // ===================== OPCIÓN 4: BUSCAR VEHÍCULO POR PLACA =====================
    static void buscarVehiculoPorPlaca() {
        System.out.println();
        System.out.println("--- Buscar vehículo por placa ---");

        String placa;
        while (true) {
            placa = leerTexto("Ingrese la placa: ").toUpperCase();
            if (!validarFormatoPlaca(placa)) {
                System.out.println("Formato de placa inválido. Ejemplo válido: P401JZQ");
                continue;
            }
            break;
        }

        int[] posicion = buscarPlaca(placa);
        if (posicion == null) {
            System.out.println("Vehículo no encontrado.");
        } else {
            System.out.println("Vehículo encontrado.");
            System.out.println("Fila: " + posicion[0]);
            System.out.println("Columna: " + posicion[1]);
        }
    }

    // ===================== OPCIÓN 5: RUTA MÁS CORTA =====================
    static void mostrarRutaMasCorta() {
        System.out.println();
        System.out.println("--- Ruta más corta entre entrada y salida ---");

        List<int[]> perimetro = construirPerimetro();

        int idxEntrada = indiceEnPerimetro(perimetro, filaEntrada, colEntrada);
        int idxSalida = indiceEnPerimetro(perimetro, filaSalida, colSalida);

        int total = perimetro.size();
        int horario = (idxSalida - idxEntrada + total) % total;
        int antihorario = total - horario;

        System.out.println("Entrada: fila " + filaEntrada + ", columna " + colEntrada);
        System.out.println("Salida: fila " + filaSalida + ", columna " + colSalida);
        System.out.println("Ruta sentido horario: " + horario + " posiciones");
        System.out.println("Ruta sentido antihorario: " + antihorario + " posiciones");

        if (horario < antihorario) {
            System.out.println("Ruta recomendada: sentido horario (" + horario + " posiciones)");
        } else if (antihorario < horario) {
            System.out.println("Ruta recomendada: sentido antihorario (" + antihorario + " posiciones)");
        } else {
            System.out.println("Ambas rutas tienen la misma distancia (" + horario + " posiciones). Puede utilizar cualquiera.");
        }
    }

    // Construye la lista de coordenadas de la vía exterior (perímetro del tablero 10x10)
    // en sentido horario, comenzando en la esquina (1,1).
    static List<int[]> construirPerimetro() {
        List<int[]> perimetro = new ArrayList<>();

        // fila 1, columnas 1 a 10 (borde superior)
        for (int col = 1; col <= 10; col++) {
            perimetro.add(new int[]{1, col});
        }
        // columna 10, filas 2 a 10 (borde derecho)
        for (int fila = 2; fila <= 10; fila++) {
            perimetro.add(new int[]{fila, 10});
        }
        // fila 10, columnas 9 a 1 (borde inferior)
        for (int col = 9; col >= 1; col--) {
            perimetro.add(new int[]{10, col});
        }
        // columna 1, filas 9 a 2 (borde izquierdo)
        for (int fila = 9; fila >= 2; fila--) {
            perimetro.add(new int[]{fila, 1});
        }

        return perimetro;
    }

    static int indiceEnPerimetro(List<int[]> perimetro, int fila, int col) {
        for (int i = 0; i < perimetro.size(); i++) {
            int[] p = perimetro.get(i);
            if (p[0] == fila && p[1] == col) return i;
        }
        return -1; // no debería ocurrir
    }

    // ===================== OPCIÓN 6: MOSTRAR INGRESOS =====================
    static void mostrarIngresos() {
        System.out.println();
        System.out.println("===== INGRESOS =====");
        System.out.println("Vehículos cobrados: " + vehiculosCobrados);
        System.out.printf("Tarifa por vehículo: Q%.2f%n", TARIFA);
        System.out.printf("Total recaudado: Q%.2f%n", totalRecaudado);
    }
}