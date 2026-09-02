package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Animal;

public class AnimalService {
    private static final int CAPACIDAD_MAXIMA = 100;
    private Animal[] animales;
    private int contador;

    public AnimalService() {
        this.animales = new Animal[CAPACIDAD_MAXIMA];
        this.contador = 0;
    }

    public boolean agregarAnimal(Animal nuevoAnimal) {
        if (contador >= CAPACIDAD_MAXIMA) {
            return false;
        }
        if (buscarPorCodigo(nuevoAnimal.getCodigo()) != null) {
            return false;
        }
        animales[contador] = nuevoAnimal;
        contador++;
        return true;
    }

    public Animal buscarPorCodigo(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (animales[i].getCodigo().equalsIgnoreCase(codigo) && 
                !animales[i].getEstadoAdopcion().equals("ELIMINADO")) {
                return animales[i];
            }
        }
        return null;
    }

    public boolean eliminarLogicamente(String codigo) {
        Animal a = buscarPorCodigo(codigo);
        if (a != null) {
            a.setEstadoAdopcion("ELIMINADO");
            return true;
        }
        return false;
    }

    public Animal[] getAnimalesActivos() {
        int activosCount = 0;
        for (int i = 0; i < contador; i++) {
            if (!animales[i].getEstadoAdopcion().equals("ELIMINADO")) {
                activosCount++;
            }
        }

        Animal[] activos = new Animal[activosCount];
        int idx = 0;
        for (int i = 0; i < contador; i++) {
            if (!animales[i].getEstadoAdopcion().equals("ELIMINADO")) {
                activos[idx++] = animales[i];
            }
        }
        return activos;
    }

    public int getContador() {
        return contador;
    }
}