import java.util.Random;
import java.util.Scanner;

public class Estacionamiento {

    // Matriz principal de 10x10 para la representación visual
    static char[][] tablero = new char[10][10];

    // Matriz paralela para almacenar las placas en sus coordenadas
    static String[][] placas = new String[10][10];

    // Variables globales para guardar las posiciones de E y S
    static int filaEntrada, colEntrada;
    static int filaSalida, colSalida;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inicializarTablero();

        int opcion = 0;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar salto de línea
                
                switch (opcion) {
                    case 1:
                        System.out.println("\n[Módulo de ingreso en desarrollo...]");
                        break;
                    case 2:
                        System.out.println("\n[Módulo de retiro en desarrollo...]");
                        break;
                    case 3:
                        mostrarTablero();
                        break;
                    case 4:
                        System.out.println("\n[Módulo de búsqueda en desarrollo...]");
                        break;
                    case 5:
                        System.out.println("\n[Módulo de ruta más corta en desarrollo...]");
                        break;
                    case 6:
                        System.out.println("\n[Módulo de ingresos en desarrollo...]");
                        break;
                    case 7:
                        System.out.println("\n¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("\n[Error] Opción no válida.");
                }
            } else {
                System.out.println("\n[Error] Entrada inválida.");
                scanner.nextLine();
            }
        } while (opcion != 7);

        scanner.close();
    }

    public static void inicializarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    tablero[i][j] = '=';
                } else {
                    tablero[i][j] = 'L';
                }
            }
        }
        generarEntradaYSalida();
    }

    private static void generarEntradaYSalida() {
        Random rand = new Random();

        int[] posE = obtenerPosicionBordeAleatoria(rand);
        filaEntrada = posE[0];
        colEntrada = posE[1];
        tablero[filaEntrada][colEntrada] = 'E';

        do {
            int[] posS = obtenerPosicionBordeAleatoria(rand);
            filaSalida = posS[0];
            colSalida = posS[1];
        } while (filaSalida == filaEntrada && colSalida == colEntrada);

        tablero[filaSalida][colSalida] = 'S';
    }

    private static int[] obtenerPosicionBordeAleatoria(Random rand) {
        int lado = rand.nextInt(4);
        int pos = 1 + rand.nextInt(8);

        int fila = 0, col = 0;
        switch (lado) {
            case 0: fila = 0; col = pos; break;
            case 1: fila = 9; col = pos; break;
            case 2: fila = pos; col = 0; break;
            case 3: fila = pos; col = 9; break;
        }
        return new int[]{fila, col};
    }

    public static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE ESTACIONAMIENTO =====");
        System.out.println("1. Ingresar vehículo");
        System.out.println("2. Retirar vehículo");
        System.out.println("3. Mostrar estacionamiento");
        System.out.println("4. Buscar vehículo por placa");
        System.out.println("5. Mostrar ruta más corta entre entrada y salida");
        System.out.println("6. Mostrar ingresos");
        System.out.println("7. Salir");
    }

    public static void mostrarTablero() {
        System.out.println("\nE = Entrada       S = Salida       = = Vía exterior");
        System.out.println("L = Lugar libre   A = Automóvil\n");

        System.out.println("    1 2 3 4 5 6 7 8");

        for (int i = 0; i < 10; i++) {
            if (i >= 1 && i <= 8) {
                System.out.print(i + " ");
            } else {
                System.out.print("  ");
            }

            for (int j = 0; j < 10; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Método para validar que la placa cumpla el formato P####LLL
    public static boolean esPlacaValida(String placa) {
        if (placa == null || placa.length() != 7) return false;
        if (placa.charAt(0) != 'P') return false;

        for (int i = 1; i <= 3; i++) {
            char c = placa.charAt(i);
            if (c < '0' || c > '9') return false;
        }

        for (int i = 4; i <= 6; i++) {
            char c = placa.charAt(i);
            if (c < 'A' || c > 'Z') return false;
        }

        return true;
    }

    // Verifica si la placa ya se encuentra dentro del estacionamiento
    public static boolean existePlaca(String placa) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (placas[i][j] != null && placas[i][j].equals(placa)) {
                    return true;
                }
            }
        }
        return false;
    }
}