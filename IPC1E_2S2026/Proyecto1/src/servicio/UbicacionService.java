package IPC1E_2S2026.Proyecto1.src.servicio;

public class UbicacionService {
    private String[][] mapaRefugio;
    private int filas;
    private int columnas;

    public UbicacionService(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.mapaRefugio = new String[filas][columnas];
        inicializarMapa();
    }

    private void inicializarMapa() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                mapaRefugio[i][j] = "LIBRE";
            }
        }
    }

    public boolean asignarUbicacion(int fila, int columna, String codigoAnimal) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            if (mapaRefugio[fila][columna].equals("LIBRE")) {
                mapaRefugio[fila][columna] = codigoAnimal;
                return true;
            }
        }
        return false;
    }

    public boolean desocuparUbicacion(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            mapaRefugio[fila][columna] = "LIBRE";
            return true;
        }
        return false;
    }

    public String[][] getMapaRefugio() {
        return mapaRefugio;
    }
}