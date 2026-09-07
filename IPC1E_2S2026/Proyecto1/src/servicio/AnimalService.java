package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Animal;

public class AnimalService {

    private Animal[] animales;
    private int contador;

    public AnimalService() {
        this.animales = new Animal[100];
        this.contador = 0;
    }

    public boolean agregarAnimal(Animal animal) {
        if (animal == null || animal.getCodigo() == null || animal.getCodigo().trim().isEmpty()) {
            return false;
        }

        // Evitar duplicados por código
        for (int i = 0; i < contador; i++) {
            if (animales[i].getCodigo().equalsIgnoreCase(animal.getCodigo())) {
                return false;
            }
        }

        if (contador < animales.length) {
            animales[contador++] = animal;
            return true;
        }
        return false;
    }

    public Animal[] getAnimales() {
        Animal[] copia = new Animal[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = animales[i];
        }
        return copia;
    }

    // Método solicitado por PanelAnimales y ReporteService
    public Animal[] getAnimalesActivos() {
        return getAnimales();
    }

    public Animal buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < contador; i++) {
            if (animales[i].getCodigo().equalsIgnoreCase(codigo)) {
                return animales[i];
            }
        }
        return null;
    }

    public int getContador() {
        return contador;
    }
}