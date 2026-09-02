package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.BitacoraEntry;

public class BitacoraService {
    private static final int CAPACIDAD_MAXIMA = 500;
    private BitacoraEntry[] registros;
    private int contador;

    public BitacoraService() {
        this.registros = new BitacoraEntry[CAPACIDAD_MAXIMA];
        this.contador = 0;
    }

    public void registrar(String fechaHora, String usuario, String modulo, String evento, String descripcion) {
        if (contador < CAPACIDAD_MAXIMA) {
            registros[contador] = new BitacoraEntry(fechaHora, usuario, modulo, evento, descripcion);
            contador++;
        }
    }

    public BitacoraEntry[] getRegistros() {
        BitacoraEntry[] copia = new BitacoraEntry[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = registros[i];
        }
        return copia;
    }

    public int getContador() {
        return contador;
    }
}