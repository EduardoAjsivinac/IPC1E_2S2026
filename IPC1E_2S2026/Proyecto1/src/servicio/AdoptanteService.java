package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Adoptante;

public class AdoptanteService {
    private static final int CAPACIDAD_MAXIMA = 100;
    private Adoptante[] adoptantes;
    private int contador;

    public AdoptanteService() {
        this.adoptantes = new Adoptante[CAPACIDAD_MAXIMA];
        this.contador = 0;
    }

    public boolean registrarAdoptante(Adoptante nuevo) {
        if (contador >= CAPACIDAD_MAXIMA) {
            return false;
        }
        if (buscarPorCodigo(nuevo.getCodigo()) != null || buscarPorDpi(nuevo.getDpi()) != null) {
            return false; // Evita duplicados por código o DPI
        }
        adoptantes[contador] = nuevo;
        contador++;
        return true;
    }

    public Adoptante buscarPorCodigo(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (adoptantes[i].getCodigo().equalsIgnoreCase(codigo)) {
                return adoptantes[i];
            }
        }
        return null;
    }

    public Adoptante buscarPorDpi(String dpi) {
        for (int i = 0; i < contador; i++) {
            if (adoptantes[i].getDpi().equals(dpi)) {
                return adoptantes[i];
            }
        }
        return null;
    }

    public Adoptante[] getAdoptantes() {
        Adoptante[] copia = new Adoptante[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = adoptantes[i];
        }
        return copia;
    }

    public int getContador() {
        return contador;
    }
}