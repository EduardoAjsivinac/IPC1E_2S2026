public class EspacioRefugio {

    static final int FILAS = 4;
    static final int COLUMNAS = 8;

    String[][] matriz =
            new String[FILAS][COLUMNAS];

    public EspacioRefugio() {

        for (int i = 0; i < FILAS; i++) {

            for (int j = 0; j < COLUMNAS; j++) {

                matriz[i][j] = "";
            }
        }
    }
}