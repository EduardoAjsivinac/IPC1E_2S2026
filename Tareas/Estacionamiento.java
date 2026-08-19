package Tareas;
import java.util.Scanner;
import java.util.Random;

public class Estacionamiento {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    static String[][] tablero = new String[10][10];
    static int[][] perimetro = new int[36][2];

    static int entrada;
    static int salida;

    public static void main(String[] args) {

        crearPerimetro();
        crearEntradaSalida();
        iniciarTablero();

        int opcion;

        do {
            menu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {

                case 1:
                    System.out.println("Ingreso de vehiculos: pendiente.");
                    break;

                case 2:
                    System.out.println("Retiro de vehiculos: pendiente.");
                    break;

                case 3:
                    mostrarTablero();
                    break;

                case 4:
                    System.out.println("Busqueda: pendiente.");
                    break;

                case 5:
                    System.out.println("Ruta: pendiente.");
                    break;

                case 6:
                    System.out.println("Ingresos: pendiente.");
                    break;

                case 7:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 7);
    }

    static void menu() {

        System.out.println("\n===== SISTEMA DE ESTACIONAMIENTO =====");
        System.out.println("1. Ingresar vehiculo");
        System.out.println("2. Retirar vehiculo");
        System.out.println("3. Mostrar estacionamiento");
        System.out.println("4. Buscar vehiculo");
        System.out.println("5. Mostrar ruta mas corta");
        System.out.println("6. Mostrar ingresos");
        System.out.println("7. Salir");
    }

    static void crearPerimetro() {

        int x = 0;

        // Parte superior
        for (int c = 0; c < 10; c++) {
            perimetro[x][0] = 0;
            perimetro[x][1] = c;
            x++;
        }

        // Parte derecha
        for (int f = 1; f < 10; f++) {
            perimetro[x][0] = f;
            perimetro[x][1] = 9;
            x++;
        }

        // Parte inferior
        for (int c = 8; c >= 0; c--) {
            perimetro[x][0] = 9;
            perimetro[x][1] = c;
            x++;
        }

        // Parte izquierda
        for (int f = 8; f >= 1; f--) {
            perimetro[x][0] = f;
            perimetro[x][1] = 0;
            x++;
        }
    }

    static void crearEntradaSalida() {

        do {
            entrada = random.nextInt(36);
        } while (esEsquina(entrada));

        do {
            salida = random.nextInt(36);
        } while (esEsquina(salida) || salida == entrada);
    }

    static boolean esEsquina(int posicion) {

        return posicion == 0 ||
               posicion == 9 ||
               posicion == 18 ||
               posicion == 27;
    }

    static void iniciarTablero() {

        for (int f = 0; f < 10; f++) {

            for (int c = 0; c < 10; c++) {

                if (f == 0 || f == 9 || c == 0 || c == 9) {
                    tablero[f][c] = "=";
                } else {
                    tablero[f][c] = "L";
                }
            }
        }

        tablero[perimetro[entrada][0]][perimetro[entrada][1]] = "E";
        tablero[perimetro[salida][0]][perimetro[salida][1]] = "S";
    }

    static void mostrarTablero() {

        System.out.println("\n  1 2 3 4 5 6 7 8");

        for (int f = 0; f < 10; f++) {

            if (f >= 1 && f <= 8) {
                System.out.print(f + " ");
            } else {
                System.out.print("  ");
            }

            for (int c = 0; c < 10; c++) {
                System.out.print(tablero[f][c] + " ");
            }

            System.out.println();
        }
    }

    static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            if (sc.hasNextInt()) {

                int numero = sc.nextInt();
                sc.nextLine();

                return numero;

            } else {

                System.out.println("Ingrese un numero entero.");
                sc.nextLine();
            }
        }
    }
}
