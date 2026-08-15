package Tareas;

import java.util.Random;
import java.util.Scanner;

public class Estacionamiento {
    // Tablero de 10x10
    static String[][] tablero = new String[10][10];
    // Matriz interna de 8x8 para placas
    static String[][] estacionamiento = new String[8][8];
    
    // Coordenadas del perímetro (36 celdas)
    static int[][] perimetro = new int[36][2];
    static int idxEntrada = -1;
    static int idxSalida = -1;

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        inicializarPerimetro();
        generarEntradaSalida();
        inicializarTablero();

        int opcion = 0;
        do {
            mostrarMenu();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 3 -> mostrarEstacionamiento();
                    case 7 -> System.out.println("¡Gracias por utilizar el sistema!");
                    case 1, 2, 4, 5, 6 -> System.out.println("Funcionalidad en desarrollo...");
                    default -> System.out.println("Opción inválida. Intente de nuevo.");
                }
            } else {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine();
            }
        } while (opcion != 7);
    }

    static void inicializarPerimetro() {
        int idx = 0;
        for (int c = 0; c < 10; c++) { perimetro[idx][0] = 0; perimetro[idx++][1] = c; } // Arriba
        for (int r = 1; r < 10; r++) { perimetro[idx][0] = r; perimetro[idx++][1] = 9; } // Derecha
        for (int c = 8; c >= 0; c--) { perimetro[idx][0] = 9; perimetro[idx++][1] = c; } // Abajo
        for (int r = 8; r >= 1; r--) { perimetro[idx][0] = r; perimetro[idx++][1] = 0; } // Izquierda
    }

    static void generarEntradaSalida() {
        int[] esquinas = {0, 9, 18, 27};
        do {
            idxEntrada = random.nextInt(36);
        } while (esEsquina(idxEntrada, esquinas));

        do {
            idxSalida = random.nextInt(36);
        } while (esEsquina(idxSalida, esquinas) || idxSalida == idxEntrada);
    }

    static boolean esEsquina(int idx, int[] esquinas) {
        for (int e : esquinas) {
            if (idx == e) return true;
        }
        return false;
    }

    static void inicializarTablero() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (r == 0 || r == 9 || c == 0 || c == 9) {
                    tablero[r][c] = "=";
                } else {
                    tablero[r][c] = "L";
                    estacionamiento[r - 1][c - 1] = null;
                }
            }
        }
        tablero[perimetro[idxEntrada][0]][perimetro[idxEntrada][1]] = "E";
        tablero[perimetro[idxSalida][0]][perimetro[idxSalida][1]] = "S";
    }

    static void mostrarMenu() {
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

    static void mostrarEstacionamiento() {
        System.out.println("\n   1 2 3 4 5 6 7 8");
        int libres = 0, ocupados = 0;

        for (int r = 0; r < 10; r++) {
            if (r >= 1 && r <= 8) System.out.print(r + " ");
            else System.out.print("  ");

            for (int c = 0; c < 10; c++) {
                System.out.print(tablero[r][c] + " ");
                if (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
                    if (tablero[r][c].equals("L")) libres++;
                    else if (tablero[r][c].equals("A")) ocupados++;
                }
            }
            System.out.println();
        }
        System.out.println("Espacios libres: " + libres + " | Espacios ocupados: " + ocupados);
    }
}