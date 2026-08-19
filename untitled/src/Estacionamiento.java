import java.util.Scanner;
import java.util.Random;

public class Estacionamiento {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    static String[][] tablero = new String[10][10];
    static String[][] placas = new String[8][8];

    static int[][] perimetro = new int[36][2];

    static int entrada;
    static int salida;

    static int vehiculos = 0;
    static double ingresos = 0;

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
                    ingresar();
                    break;

                case 2:
                    System.out.println("Retiro: se agregara en el siguiente commit.");
                    break;

                case 3:
                    mostrarTablero();
                    break;

                case 4:
                    System.out.println("Busqueda: se agregara en el siguiente commit.");
                    break;

                case 5:
                    System.out.println("Ruta: se agregara en el siguiente commit.");
                    break;

                case 6:
                    mostrarIngresos();
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

        for (int c = 0; c < 10; c++) {
            perimetro[x][0] = 0;
            perimetro[x][1] = c;
            x++;
        }

        for (int f = 1; f < 10; f++) {
            perimetro[x][0] = f;
            perimetro[x][1] = 9;
            x++;
        }

        for (int c = 8; c >= 0; c--) {
            perimetro[x][0] = 9;
            perimetro[x][1] = c;
            x++;
        }

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

    static void ingresar() {

        if (lleno()) {
            System.out.println("El estacionamiento esta lleno.");
            return;
        }

        System.out.print("Ingrese la placa: ");
        String placa = sc.nextLine().trim();

        if (!validarPlaca(placa)) {
            System.out.println("Placa invalida.");
            return;
        }

        if (buscarPlaca(placa) != null) {
            System.out.println("La placa ya se encuentra dentro.");
            return;
        }

        int fila = leerEntero("Ingrese fila (1-8): ");
        int columna = leerEntero("Ingrese columna (1-8): ");

        if (fila < 1 || fila > 8 ||
            columna < 1 || columna > 8) {

            System.out.println("Fila o columna invalida.");
            return;
        }

        if (!tablero[fila][columna].equals("L")) {
            System.out.println("El espacio esta ocupado.");
            return;
        }

        double pago;

        do {

            pago = leerDouble("Ingrese pago: Q");

            if (pago < 10) {
                System.out.println("Pago insuficiente. La tarifa es Q10.00.");
            }

        } while (pago < 10);

        System.out.printf("Cambio: Q%.2f\n", pago - 10);

        placas[fila - 1][columna - 1] = placa;
        tablero[fila][columna] = "A";

        vehiculos++;
        ingresos += 10;

        System.out.println("Vehiculo ingresado correctamente.");
    }

    static boolean validarPlaca(String placa) {

        if (placa.length() != 7) {
            return false;
        }

        if (placa.charAt(0) != 'P') {
            return false;
        }

        for (int i = 1; i <= 3; i++) {

            if (!Character.isDigit(placa.charAt(i))) {
                return false;
            }
        }

        for (int i = 4; i <= 6; i++) {

            if (placa.charAt(i) < 'A' ||
                placa.charAt(i) > 'Z') {

                return false;
            }
        }

        return true;
    }

    static boolean lleno() {

        for (int f = 0; f < 8; f++) {

            for (int c = 0; c < 8; c++) {

                if (placas[f][c] == null) {
                    return false;
                }
            }
        }

        return true;
    }

    static int[] buscarPlaca(String placa) {

        for (int f = 0; f < 8; f++) {

            for (int c = 0; c < 8; c++) {

                if (placa.equals(placas[f][c])) {
                    return new int[]{f, c};
                }
            }
        }

        return null;
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

    static void mostrarIngresos() {

        System.out.println("\n===== INGRESOS =====");
        System.out.println("Vehiculos cobrados: " + vehiculos);
        System.out.println("Tarifa: Q10.00");
        System.out.printf("Total recaudado: Q%.2f\n", ingresos);
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

    static double leerDouble(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            if (sc.hasNextDouble()) {

                double numero = sc.nextDouble();
                sc.nextLine();

                return numero;

            } else {

                System.out.println("Ingrese un numero valido.");
                sc.nextLine();
            }
        }
    }
}