package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Adoptante;

public class AdoptanteService {
    private static final int CAPACIDAD_INICIAL = 100;
    private Adoptante[] adoptantes;
    private int contador;

    public AdoptanteService() {
        this.adoptantes = new Adoptante[CAPACIDAD_INICIAL];
        this.contador = 0;
    }

    public boolean registrarAdoptante(Adoptante nuevo) {
        // Validar objeto nulo y atributos nulos o vacíos
        if (nuevo == null || nuevo.getCodigo() == null || nuevo.getDpi() == null) {
            return false;
        }

        // Evitar registros duplicados por código o DPI
        if (buscarPorCodigo(nuevo.getCodigo()) != null || buscarPorDpi(nuevo.getDpi()) != null) {
            return false; 
        }

        // Redimensionamiento dinámico opcional en lugar de rechazar el registro
        if (contador >= adoptantes.length) {
            redimensionar();
        }

        adoptantes[contador] = nuevo;
        contador++;
        return true;
    }

    public Adoptante buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < contador; i++) {
            if (adoptantes[i] != null && adoptantes[i].getCodigo().equalsIgnoreCase(codigo)) {
                return adoptantes[i];
            }
        }
        return null;
    }

    public Adoptante buscarPorDpi(String dpi) {
        if (dpi == null) return null;
        for (int i = 0; i < contador; i++) {
            if (adoptantes[i] != null && adoptantes[i].getDpi().equals(dpi)) {
                return adoptantes[i];
            }
        }
        return null;
    }

    private void redimensionar() {
        Adoptante[] nuevo = new Adoptante[adoptantes.length * 2];
        for (int i = 0; i < adoptantes.length; i++) {
            nuevo[i] = adoptantes[i];
        }
        adoptantes = nuevo;
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