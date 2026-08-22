import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Estacionamiento {

    // Matriz principal de 10x10 para la representación visual
    static char[][] tablero = new char[10][10];
    
    // Matriz paralela para almacenar las placas registradas en sus coordenadas
    static String[][] placas = new String[10][10];
    
    // Matriz para guardar el tiempo de ingreso en milisegundos (System.currentTimeMillis())
    static long[][] tiemposIngreso = new long[10][10];

    // Variables globales para guardar la posición fija de Entrada (E) y Salida (S)
    static int filaEntrada, colEntrada;
    static int filaSalida, colSalida;

    // Control del total recabado en el sistema
    static double ingresosTotales = 0.0;
    static final double TARIFA_POR_SEGUNDO = 0.50; // Q0.50 por segundo simulado

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inicializarTablero();

        int opcion = 0;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el búfer

                switch (opcion) {
                    case 1:
                        ingresarVehiculo(scanner);
                        break;
                    case 2:
                        retirarVehiculo(scanner);
                        break;
                    case 3:
                        mostrarTablero();
                        break;
                    case 4:
                        buscarVehiculo(scanner);
                        break;
                    case 5:
                        mostrarRutaMasCorta();
                        break;
                    case 6:
                        mostrarIngresos();
                        break;
                    case 7:
                        System.out.println("\n¡Gracias por usar el sistema de estacionamiento!");
                        break;
                    default:
                        System.out.println("\n[Error] Opción no válida. Intente de nuevo.");
                }
            } else {
                System.out.println("\n[Error] Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        } while (opcion != 7);

        scanner.close();
    }

    // Inicializa la matriz con vías '=', espacios libres 'L' y genera 'E' y 'S'
    public static void inicializarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    tablero[i][j] = '='; // Vía exterior
                } else {
                    tablero[i][j] = 'L'; // Lugar libre interior
                }
            }
        }
        generarEntradaYSalida();
    }

    // Genera la Entrada (E) y Salida (S) en posiciones aleatorias de los bordes evitando esquinas
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
        int lado = rand.nextInt(4); // 0: Arriba, 1: Abajo, 2: Izquierda, 3: Derecha
        int pos = 1 + rand.nextInt(8); // Posiciones del 1 al 8 (evitando esquinas)

        int fila = 0, col = 0;
        switch (lado) {
            case 0: fila = 0; col = pos; break;
            case 1: fila = 9; col = pos; break;
            case 2: fila = pos; col = 0; break;
            case 3: fila = pos; col = 9; break;
        }
        return new int[]{fila, col};
    }

    // Despliega el menú principal de opciones
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

    // Imprime la matriz actual en consola con coordenadas numeradas del 1 al 8
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

    // Valida que la placa tenga el formato de 7 caracteres P####LLL (Ejemplo: P123ABC)
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

    // Verifica si la placa ya fue registrada dentro del estacionamiento
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

    // Opción 1: Módulo para estacinar un nuevo automóvil
    public static void ingresarVehiculo(Scanner scanner) {
        System.out.print("\nIngrese la placa del vehículo (Formato P####LLL): ");
        String placa = scanner.nextLine().trim().toUpperCase();

        if (!esPlacaValida(placa)) {
            System.out.println("[Error] La placa no cumple con el formato válido (P####LLL).");
            return;
        }

        if (existePlaca(placa)) {
            System.out.println("[Error] El vehículo con la placa " + placa + " ya está dentro del estacionamiento.");
            return;
        }

        System.out.print("Ingrese la fila para estacionar (1-8): ");
        int fila = scanner.nextInt();
        System.out.print("Ingrese la columna para estacionar (1-8): ");
        int col = scanner.nextInt();
        scanner.nextLine();

        if (fila < 1 || fila > 8 || col < 1 || col > 8) {
            System.out.println("[Error] Coordenadas fuera de rango. Deben ser del 1 al 8.");
            return;
        }

        if (tablero[fila][col] != 'L') {
            System.out.println("[Error] La ubicación especificada no está libre.");
            return;
        }

        tablero[fila][col] = 'A';
        placas[fila][col] = placa;
        tiemposIngreso[fila][col] = System.currentTimeMillis();

        System.out.println("\n[Éxito] Vehículo con placa " + placa + " registrado exitosamente.");
    }

    // Opción 2: Módulo para retirar un vehículo, calcular tiempo y generar recibo de cobro
    public static void retirarVehiculo(Scanner scanner) {
        System.out.print("\nIngrese la placa del vehículo a retirar: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        int f = -1, c = -1;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (placas[i][j] != null && placas[i][j].equals(placa)) {
                    f = i;
                    c = j;
                    break;
                }
            }
        }

        if (f == -1) {
            System.out.println("[Error] No se encontró ningún vehículo con la placa especificada.");
            return;
        }

        long tiempoFin = System.currentTimeMillis();
        long segundosTranscurridos = (tiempoFin - tiemposIngreso[f][c]) / 1000;
        if (segundosTranscurridos < 1) segundosTranscurridos = 1; // Tarifa mínima de 1 segundo

        double totalPagar = segundosTranscurridos * TARIFA_POR_SEGUNDO;
        ingresosTotales += totalPagar;

        // Liberar espacio
        tablero[f][c] = 'L';
        placas[f][c] = null;
        tiemposIngreso[f][c] = 0;

        System.out.println("\n===== RECIBO DE PAGO =====");
        System.out.println("Placa: " + placa);
        System.out.println("Tiempo estacionado: " + segundosTranscurridos + " segundos");
        System.out.printf("Monto a pagar: Q%.2f\n", totalPagar);
        System.out.println("==========================");
    }

    // Opción 4: Módulo de búsqueda por número de placa
    public static void buscarVehiculo(Scanner scanner) {
        System.out.print("\nIngrese la placa a buscar: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (placas[i][j] != null && placas[i][j].equals(placa)) {
                    System.out.println("\n[Encontrado] El vehículo con placa " + placa + " está en la posición (" + i + ", " + j + ").");
                    return;
                }
            }
        }

        System.out.println("\n[No Encontrado] El vehículo con la placa " + placa + " no está en el estacionamiento.");
    }

    // Opción 5: Algoritmo Breadth-First Search (BFS) para calcular e imprimir la ruta más corta (E -> S)
    public static void mostrarRutaMasCorta() {
        boolean[][] visitado = new boolean[10][10];
        int[][] padreFila = new int[10][10];
        int[][] padreCol = new int[10][10];

        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{filaEntrada, colEntrada});
        visitado[filaEntrada][colEntrada] = true;

        int[] dFila = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        boolean encontrado = false;

        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            int f = actual[0];
            int c = actual[1];

            if (f == filaSalida && c == colSalida) {
                encontrado = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nf = f + dFila[i];
                int nc = c + dCol[i];

                if (nf >= 0 && nf < 10 && nc >= 0 && nc < 10) {
                    if (!visitado[nf][nc] && tablero[nf][nc] != 'A') { // Los automóviles 'A' actúan como obstáculos
                        visitado[nf][nc] = true;
                        padreFila[nf][nc] = f;
                        padreCol[nf][nc] = c;
                        cola.add(new int[]{nf, nc});
                    }
                }
            }
        }

        if (!encontrado) {
            System.out.println("\n[Error] No hay una ruta disponible hacia la salida (bloqueada totalmente por automóviles).");
            return;
        }

        // Crear copia del tablero para dibujar el camino sin alterar el tablero principal
        char[][] copiaTablero = new char[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(tablero[i], 0, copiaTablero[i], 0, 10);
        }

        int curF = filaSalida;
        int curC = colSalida;

        while (!(curF == filaEntrada && curC == colEntrada)) {
            if (copiaTablero[curF][curC] != 'E' && copiaTablero[curF][curC] != 'S') {
                copiaTablero[curF][curC] = '*'; // Marcador de la ruta
            }
            int pf = padreFila[curF][curC];
            int pc = padreCol[curF][curC];
            curF = pf;
            curC = pc;
        }

        System.out.println("\n===== RUTA MÁS CORTA (E -> S) =====");
        System.out.println("* = Camino más corto\n");
        System.out.println("    1 2 3 4 5 6 7 8");
        for (int i = 0; i < 10; i++) {
            if (i >= 1 && i <= 8) {
                System.out.print(i + " ");
            } else {
                System.out.print("  ");
            }
            for (int j = 0; j < 10; j++) {
                System.out.print(copiaTablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Opción 6: Módulo para visualizar la recaudación total acumulada
    public static void mostrarIngresos() {
        System.out.println("\n===== REPORTE DE INGRESOS =====");
        System.out.printf("Total recaudado acumulado: Q%.2f\n", ingresosTotales);
        System.out.println("===============================");
    }
}