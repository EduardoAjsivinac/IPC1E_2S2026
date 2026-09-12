package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Animal;

public class AnimalService {

    private Animal[] animales;
    private int contador;
    private UbicacionService ubicacionService;
    private BitacoraService bitacoraService;

    public AnimalService() {
        this.animales = new Animal[100];
        this.contador = 0;
    }

    public void setUbicacionService(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    public void setBitacoraService(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    public boolean agregarAnimal(Animal animal) {
        if (animal == null || animal.getCodigo() == null || animal.getCodigo().trim().isEmpty()) {
            return false;
        }

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

    public boolean actualizarEstadoClinico(String codigo, String nuevoEstadoClinico) {
        Animal animal = buscarPorCodigo(codigo);
        if (animal == null) {
            return false;
        }

        animal.setEstadoClinico(nuevoEstadoClinico);

        if ("APTO".equalsIgnoreCase(nuevoEstadoClinico)) {
            animal.setEstadoAdopcion("DISPONIBLE");
        } else {
            animal.setEstadoAdopcion("NO_DISPONIBLE");
        }

        return true;
    }

    public boolean eliminarAnimal(String codigo) {
        int index = -1;
        for (int i = 0; i < contador; i++) {
            if (animales[i].getCodigo().equalsIgnoreCase(codigo)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        for (int i = index; i < contador - 1; i++) {
            animales[i] = animales[i + 1];
        }
        animales[contador - 1] = null;
        contador--;

        return true;
    }

    public Animal[] getAnimales() {
        Animal[] copia = new Animal[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = animales[i];
        }
        return copia;
    }

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