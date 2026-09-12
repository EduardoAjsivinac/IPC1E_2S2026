import java.util.Scanner;

public class Estacionamiento {

    static char[][] parqueo = new char[10][10];
    static String[][] placas = new String[10][10];
    static double totalRecaudado = 0.0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarParqueo();
        int opcion = 0;

        do {
            System.out.println("\n========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Registrar Ingreso");
            System.out.println("2. Registrar Salida de Vehículo");
            System.out.println("3. Ver Mapa del Parqueo");
            System.out.println("4. Buscar Vehículo por Placa");
            System.out.println("5. Mostrar Ruta Más Corta a la Salida");
            System.out.println("6. Ver Total Recaudado");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    registrarIngreso();
                    break;
                case 2:
                    registrarSalida();
                    break;
                case 3:
                    mostrarMapa();
                    break;
                case 4:
                    buscarVehiculo();
                    break;
                case 5:
                    calcularRuta();
                    break;
                case 6:
                    System.out.println("\nTotal acumulado en caja: Q" + String.format("%.2f", totalRecaudado));
                    break;
                case 7:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    // funcion inicializar parqueo
    
    public static void inicializarParqueo() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    parqueo[i][j] = '=';
                } else {
                    parqueo[i][j] = 'L';
                }
            }
        }
        parqueo[1][0] = 'E';
        parqueo[8][9] = 'S';
    }

    // funcion registrar vehiculo

    public static void registrarIngreso() {
        System.out.print("Ingrese la placa del vehículo: ");
        String placa = scanner.next();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (placas[i][j] != null && placas[i][j].equalsIgnoreCase(placa)) {
                    System.out.println("Error: El vehículo con placa " + placa + " ya está dentro.");
                    return;
                }
            }
        }

        System.out.print("Ingrese numero de fila (1-8): ");
        int x = scanner.nextInt();
        System.out.print("Ingrese numero de columna (1-8): ");
        int y = scanner.nextInt();

        if (x < 1 || x > 8 || y < 1 || y > 8) {
            System.out.println("Error: Coordenada fuera de rango.");
        } else if (parqueo[x][y] != 'L') {
            System.out.println("Error: La casilla asignada no está disponible.");
        } else {
            parqueo[x][y] = 'A';
            placas[x][y] = placa;
            System.out.println("\nVehículo con placa " + placa + " registrado exitosamente en (" + x + ", " + y + ").");
        }
    }

    // funcion registrar salida por placa

    public static void registrarSalida() {
        System.out.print("Ingrese la placa del vehículo a retirar: ");
        String placaBuscar = scanner.next();

        int posX = -1;
        int posY = -1;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (placas[i][j] != null && placas[i][j].equalsIgnoreCase(placaBuscar)) {
                    posX = i;
                    posY = j;
                    break;
                }
            }
            if (posX != -1) break;
        }

        if (posX == -1) {
            System.out.println("Error: El vehículo con placa " + placaBuscar + " no se encuentra en el parqueo.");
            return;
        }

        final double TARIFA = 10.0;
        double pagoCliente = 0.0;

        System.out.println("\nVehículo encontrado en la casilla (" + posX + ", " + posY + ")");
        System.out.println("Costo de parqueo a cancelar: Q10.00");

        while (true) {
            System.out.print("Ingrese el efectivo: Q");
            pagoCliente = scanner.nextDouble();

            if (pagoCliente >= TARIFA) {
                double cambio = pagoCliente - TARIFA;
                String placaSalida = placas[posX][posY];

                parqueo[posX][posY] = '.';
                placas[posX][posY] = null;
                totalRecaudado += TARIFA;

                System.out.println("\nRECIBO DE PAGO Y SALIDA");
                System.out.println("Placa: " + placaSalida);
                System.out.println("Monto cobrado: Q10.00");
                System.out.println("Efectivo recibido: Q" + String.format("%.2f", pagoCliente));
                System.out.println("Cambio: Q" + String.format("%.2f", cambio));
                System.out.println("Ubicación libre: (" + posX + ", " + posY + ")");
                System.out.println("________________________");
                System.out.println("Vehículo " + placaSalida + " retirado exitosamente.");
                break;
            } else {
                double faltante = TARIFA - pagoCliente;
                System.out.println("Monto insuficiente. Falta ingresar: Q" + String.format("%.2f", faltante));
            }
        }
    }

    // funcion mostar datos

    public static void mostrarMapa() {
        System.out.println("\n   0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + parqueo[i][j]);
            }
            System.out.println();
        }
    }

    // funcion buscar vehiculo por placa

    public static void buscarVehiculo() {
        System.out.print("\nIngrese la placa a buscar: ");
        String placaBuscar = scanner.next();
        boolean encontrado = false;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (placas[i][j] != null && placas[i][j].equalsIgnoreCase(placaBuscar)) {
                    System.out.println("\nVehículo encontrado:");
                    System.out.println("Placa: " + placas[i][j]);
                    System.out.println("Ubicación: Fila " + i + ", Columna " + j);
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) break;
        }

        if (!encontrado) {
            System.out.println("El vehículo con placa " + placaBuscar + " no se encuentra en el parqueo.");
        }
    }

    // calcular ruta mas corta

    public static void calcularRuta() {
        System.out.print("\nIngrese fila del vehículo (1-8): ");
        int inicioX = scanner.nextInt();
        System.out.print("Ingrese columna del vehículo (1-8): ");
        int inicioY = scanner.nextInt();

        if (parqueo[inicioX][inicioY] != 'A') {
            System.out.println("Error: No hay un vehículo en esa posición.");
            return;
        }

        int destinoX = 8, destinoY = 9;

        int[][] cola = new int[100][2];
        int frente = 0;
        int fin = 0;

        boolean[][] visitado = new boolean[10][10];
        int[][][] padre = new int[10][10][2];

        cola[fin][0] = inicioX;
        cola[fin][1] = inicioY;
        fin++;
        visitado[inicioX][inicioY] = true;

        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean rutaEncontrada = false;

        while (frente < fin) {
            int cx = cola[frente][0];
            int cy = cola[frente][1];
            frente++;

            if (cx == destinoX && cy == destinoY) {
                rutaEncontrada = true;
                break;
            }

            for (int k = 0; k < 4; k++) {
                int nx = cx + direcciones[k][0];
                int ny = cy + direcciones[k][1];

                if (nx >= 0 && nx < 10 && ny >= 0 && ny < 10) {
                    // CORRECCIÓN: Solo se avanza por espacios Libres ('L') o la Salida ('S')
                    if (!visitado[nx][ny] && (parqueo[nx][ny] == 'L' || parqueo[nx][ny] == 'S')) {
                        visitado[nx][ny] = true;
                        padre[nx][ny][0] = cx;
                        padre[nx][ny][1] = cy;

                        cola[fin][0] = nx;
                        cola[fin][1] = ny;
                        fin++;
                    }
                }
            }
        }

        if (rutaEncontrada) {
            System.out.println("\nRuta a la salida encontrada:");
            int currX = destinoX;
            int currY = destinoY;
            String ruta = "";

            while (currX != inicioX || currY != inicioY) {
                ruta = " -> (" + currX + ", " + currY + ")" + ruta;
                int px = padre[currX][currY][0];
                int py = padre[currX][currY][1];
                currX = px;
                currY = py;
            }
            ruta = "(" + inicioX + ", " + inicioY + ")" + ruta;
            System.out.println("Camino: " + ruta);
        } else {
            System.out.println("No existe un camino libre hacia la salida.");
        }
    }
}