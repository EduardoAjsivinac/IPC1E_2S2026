package Tareas.Informacion;

import java.util.Random;
import java.util.Scanner;

public class Estacionamiento {

    // Constantes de diseño
    private static final double TARIFA = 10.00;
    
    // Tablero de 10x10 (Perímetro: Vía exterior, Centro 8x8: Parqueo)
    private static String[][] tablero = new String[10][10];
    
    // Coordenadas de Entrada (E) y Salida (S) en el perímetro 10x10
    private static int entradaFila, entradaCol;
    private static int salidaFila, salidaCol;

    // Métricas del sistema
    private static int vehiculosCobrados = 0;
    private static double totalRecaudado = 0.0;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarTablero();
        generarEntradaSalida();

        int opcion = 0;
        do {
            mostrarMenu();
            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    ingresarVehiculo();
                    break;
                case 2:
                    retirarVehiculo();
                    break;
                case 3:
                    mostrarEstacionamiento();
                    break;
                case 4:
                    buscarVehiculoPorPlaca();
                    break;
                case 5:
                    mostrarRutaMasCorta();
                    break;
                case 6:
                    mostrarIngresos();
                    break;
                case 7:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Ingrese un número de 1 a 7.");
                    break;
            }
        } while (opcion != 7);
    }

    // Inicializa la vía exterior '=' y los 64 espacios internos 'L'
    private static void inicializarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    tablero[i][j] = "=";
                } else {
                    tablero[i][j] = "L";
                }
            }
        }
    }

    // Genera 'E' y 'S' en el perímetro evitando esquinas y duplicados
    private static void generarEntradaSalida() {
        Random random = new Random();

        do {
            int[] pos = obtenerPosicionPerimetroAleatoria(random);
            entradaFila = pos[0];
            entradaCol = pos[1];
        } while (esEsquina(entradaFila, entradaCol));

        do {
            int[] pos = obtenerPosicionPerimetroAleatoria(random);
            salidaFila = pos[0];
            salidaCol = pos[1];
        } while ((salidaFila == entradaFila && salidaCol == entradaCol) || esEsquina(salidaFila, salidaCol));

        tablero[entradaFila][entradaCol] = "E";
        tablero[salidaFila][salidaCol] = "S";
    }

    private static int[] obtenerPosicionPerimetroAleatoria(Random r) {
        int lado = r.nextInt(4);
        int f = 0, c = 0;
        switch (lado) {
            case 0: f = 0; c = r.nextInt(10); break; // Fila superior
            case 1: f = 9; c = r.nextInt(10); break; // Fila inferior
            case 2: f = r.nextInt(10); c = 0; break; // Columna izquierda
            case 3: f = r.nextInt(10); c = 9; break; // Columna derecha
        }
        return new int[]{f, c};
    }

    private static boolean esEsquina(int f, int c) {
        return (f == 0 && c == 0) || (f == 0 && c == 9) || (f == 9 && c == 0) || (f == 9 && c == 9);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE ESTACIONAMIENTO =====");
        System.out.println("1. Ingresar vehículo");
        System.out.println("2. Retirar vehículo");
        System.out.println("3. Mostrar estacionamiento");
        System.out.println("4. Buscar vehículo por placa");
        System.out.println("5. Mostrar ruta más corta entre entrada y salida");
        System.out.println("6. Mostrar ingresos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // OPCIÓN 1: Ingresar vehículo con cobro y validación estricta
    private static void ingresarVehiculo() {
        if (estacionamientoLleno()) {
            System.out.println("Error: El estacionamiento está completamente lleno.");
            return;
        }

        System.out.print("Ingrese la placa (Formato P###LLL): ");
        String placa = scanner.nextLine().trim();

        if (!validarPlaca(placa)) {
            System.out.println("Error: Formato de placa inválido. Debe ser P###LLL (Ej: P401JZQ, solo mayúsculas).");
            return;
        }

        if (buscarPlaca(placa) != null) {
            System.out.println("Error: La placa ingresada ya existe dentro del estacionamiento.");
            return;
        }

        int fila = pedirEnteroRango("Ingrese la fila (1-8): ", 1, 8);
        int col = pedirEnteroRango("Ingrese la columna (1-8): ", 1, 8);

        // Mapeo a la matriz 10x10 (Sumar 1 a los índices ingresados por el usuario)
        if (!tablero[fila][col].equals("L")) {
            System.out.println("Error: El espacio seleccionado ya está ocupado por otro vehículo.");
            return;
        }

        System.out.printf("Tarifa: Q%.2f\n", TARIFA);
        double monto = 0;
        while (true) {
            System.out.print("Ingrese el monto entregado: Q");
            try {
                monto = Double.parseDouble(scanner.nextLine());
                if (monto < 0) {
                    System.out.println("Error: No se aceptan montos negativos.");
                } else if (monto < TARIFA) {
                    System.out.println("El pago es insuficiente. Ingrese una cantidad mayor o igual a Q10.00.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Monto inválido. Ingrese un valor numérico.");
            }
        }

        double cambio = monto - TARIFA;
        System.out.printf("Cambio: Q%.2f\n", cambio);

        // Almacenar placa completa
        tablero[fila][col] = placa;
        vehiculosCobrados++;
        totalRecaudado += TARIFA;
        System.out.println("Vehículo ingresado correctamente.");
    }

    // OPCIÓN 2: Retirar vehículo
    private static void retirarVehiculo() {
        System.out.print("Ingrese la placa a retirar: ");
        String placa = scanner.nextLine().trim();

        if (!validarPlaca(placa)) {
            System.out.println("Error: Formato de placa inválido.");
            return;
        }

        int[] pos = buscarPlaca(placa);
        if (pos == null) {
            System.out.println("La placa ingresada no existe en el estacionamiento.");
        } else {
            int f = pos[0];
            int c = pos[1];
            tablero[f][c] = "L";
            System.out.printf("Vehículo retirado exitosamente del espacio (Fila: %d, Columna: %d).\n", f, c);
        }
    }

    // OPCIÓN 3: Mostrar tablero de 10x10 con leyenda y estadísticas
    private static void mostrarEstacionamiento() {
        System.out.println("\n===== TABLERO DEL ESTACIONAMIENTO =====");
        System.out.print("    ");
        for (int c = 1; c <= 8; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        int libres = 0;
        int ocupados = 0;

        for (int f = 0; f < 10; f++) {
            if (f >= 1 && f <= 8) {
                System.out.print(f + " ");
            } else {
                System.out.print("  ");
            }

            for (int c = 0; c < 10; c++) {
                String val = tablero[f][c];
                if (f >= 1 && f <= 8 && c >= 1 && c <= 8) {
                    if (val.equals("L")) {
                        System.out.print("L ");
                        libres++;
                    } else {
                        System.out.print("A ");
                        ocupados++;
                    }
                } else {
                    System.out.print(val + " ");
                }
            }
            System.out.println();
        }

        System.out.println("\nLeyenda: E = Entrada | S = Salida | = = Vía exterior | L = Libre | A = Automóvil");
        System.out.println("Espacios Libres: " + libres + " | Espacios Ocupados: " + ocupados);
    }

    // OPCIÓN 4: Buscar vehículo por placa
    private static void buscarVehiculoPorPlaca() {
        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine().trim();

        if (!validarPlaca(placa)) {
            System.out.println("Error: Formato de placa inválido.");
            return;
        }

        int[] pos = buscarPlaca(placa);
        if (pos != null) {
            System.out.println("Vehículo encontrado.");
            System.out.println("Fila: " + pos[0]);
            System.out.println("Columna: " + pos[1]);
        } else {
            System.out.println("El vehículo no se encuentra en el estacionamiento.");
        }
    }

    // OPCIÓN 5: Mostrar la ruta más corta por la vía exterior (Horario vs Antihorario)
    private static void mostrarRutaMasCorta() {
        // Mapeo ordenado de las 32 casillas del perímetro en sentido horario
        int[][] perimetro = new int[32][2];
        int idx = 0;

        for (int c = 0; c < 10; c++) perimetro[idx++] = new int[]{0, c};       // Borde superior (0,0 -> 0,9)
        for (int f = 1; f < 10; f++) perimetro[idx++] = new int[]{f, 9};       // Borde derecho (1,9 -> 9,9)
        for (int c = 8; c >= 0; c--) perimetro[idx++] = new int[]{9, c};       // Borde inferior (9,8 -> 9,0)
        for (int f = 8; f >= 1; f--) perimetro[idx++] = new int[]{f, 0};       // Borde izquierdo (8,0 -> 1,0)

        int idxEntrada = -1;
        int idxSalida = -1;

        for (int i = 0; i < 32; i++) {
            if (perimetro[i][0] == entradaFila && perimetro[i][1] == entradaCol) {
                idxEntrada = i;
            }
            if (perimetro[i][0] == salidaFila && perimetro[i][1] == salidaCol) {
                idxSalida = i;
            }
        }

        // Cálculo de distancias
        int distHorario = (idxSalida - idxEntrada + 32) % 32;
        int distAntihorario = (idxEntrada - idxSalida + 32) % 32;

        System.out.printf("Entrada: fila %d, columna %d\n", entradaFila, entradaCol);
        System.out.printf("Salida: fila %d, columna %d\n", salidaFila, salidaCol);
        System.out.println("Distancia Sentido Horario: " + distHorario + " posiciones");
        System.out.println("Distancia Sentido Antihorario: " + distAntihorario + " posiciones");

        if (distHorario < distAntihorario) {
            System.out.println("Ruta recomendada: sentido horario (" + distHorario + " posiciones)");
        } else if (distAntihorario < distHorario) {
            System.out.println("Ruta recomendada: sentido antihorario (" + distAntihorario + " posiciones)");
        } else {
            System.out.println("Ruta recomendada: Ambas rutas tienen la misma distancia (" + distHorario + " posiciones). Puede utilizar cualquiera.");
        }
    }

    // OPCIÓN 6: Mostrar reporte de ingresos recaudados
    private static void mostrarIngresos() {
        System.out.println("\n===== INGRESOS =====");
        System.out.println("Vehículos cobrados: " + vehiculosCobrados);
        System.out.printf("Tarifa por vehículo: Q%.2f\n", TARIFA);
        System.out.printf("Total recaudado: Q%.2f\n", totalRecaudado);
    }

    // VALIDACIÓN: Placa P###LLL (P mayúscula, 3 dígitos, 3 letras mayúsculas)
    private static boolean validarPlaca(String placa) {
        if (placa == null || placa.length() != 7) return false;
        if (placa.charAt(0) != 'P') return false;

        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(placa.charAt(i))) return false;
        }

        for (int i = 4; i <= 6; i++) {
            char c = placa.charAt(i);
            if (c < 'A' || c > 'Z') return false;
        }
        return true;
    }

    // Búsqueda de la placa en el parqueo interno (1..8, 1..8)
    private static int[] buscarPlaca(String placa) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (tablero[i][j].equals(placa)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private static boolean estacionamientoLleno() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (tablero[i][j].equals("L")) return false;
            }
        }
        return true;
    }

    private static int pedirEnteroRango(String mensaje, int min, int max) {
        int val;
        while (true) {
            System.out.print(mensaje);
            try {
                val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) {}
            System.out.printf("Error: Debe ingresar un número entero entre %d y %d.\n", min, max);
        }
    }
}1