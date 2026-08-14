import java.util.Random;

public class Estacionamiento {

    // Matriz principal de 10x10 para la representación visual
    static char[][] tablero = new char[10][10];

    // Variables globales para guardar las posiciones de E y S (fila, columna)
    static int filaEntrada, colEntrada;
    static int filaSalida, colSalida;

    public static void main(String[] args) {
        // Inicializamos y mostramos el tablero base
        inicializarTablero();
        mostrarTablero();
    }

    // Método para llenar el tablero con '=' y 'L', y colocar E y S
    public static void inicializarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    tablero[i][j] = '='; // Vía exterior
                } else {
                    tablero[i][j] = 'L'; // Lugar libre
                }
            }
        }
        generarEntradaYSalida();
    }

    // Genera posiciones aleatorias en los bordes evitando esquinas
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

        int fila = 0;
        int col = 0;

        switch (lado) {
            case 0: fila = 0; col = pos; break;
            case 1: fila = 9; col = pos; break;
            case 2: fila = pos; col = 0; break;
            case 3: fila = pos; col = 9; break;
        }

        return new int[]{fila, col};
    }

    // Muestra el tablero con coordenadas del 1 al 8
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
}