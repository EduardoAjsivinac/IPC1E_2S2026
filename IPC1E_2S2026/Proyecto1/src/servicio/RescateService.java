package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Rescate;
import IPC1E_2S2026.Proyecto1.src.modelo.Animal;

public class RescateService {

    private Rescate[] rescates;
    private int contador;

    public RescateService() {
        this.rescates = new Rescate[50];
        this.contador = 0;
    }

    public boolean registrarRescate(String codigo, String prioridad, String fecha, String codigoAnimalVinculado) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        if (!prioridad.equals("ALTA") && !prioridad.equals("MEDIA") && !prioridad.equals("BAJA")) {
            return false;
        }

        if (fecha == null || !fecha.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            return false;
        }

        if (contador < rescates.length) {
            Rescate nuevo = new Rescate(codigo, prioridad, "PENDIENTE", fecha, codigoAnimalVinculado);
            rescates[contador++] = nuevo;
            return true;
        }
        return false;
    }

    public boolean atenderRescate(String codigoRescate, AnimalService animalService, String especieAnimal) {
        for (int i = 0; i < contador; i++) {
            if (rescates[i].getCodigo().equalsIgnoreCase(codigoRescate)) {
                if (rescates[i].getEstado().equals("ATENDIDO")) {
                    return false;
                }

                rescates[i].setEstado("ATENDIDO");

                if (rescates[i].getCodigoAnimalVinculado() == null || rescates[i].getCodigoAnimalVinculado().trim().isEmpty()) {
                    String codigoAnimalGenerado = rescates[i].getCodigo().replace("R", "A");
                    
                    Animal nuevoAnimal = new Animal(codigoAnimalGenerado, especieAnimal, 0, "EN_TRATAMIENTO", "NO_DISPONIBLE");
                    animalService.agregarAnimal(nuevoAnimal);
                    
                    rescates[i].setCodigoAnimalVinculado(codigoAnimalGenerado);
                }
                return true;
            }
        }
        return false;
    }

    public Rescate[] getRescatesOrdenados() {
        Rescate[] copia = new Rescate[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = rescates[i];
        }

        for (int i = 0; i < contador - 1; i++) {
            for (int j = 0; j < contador - i - 1; j++) {
                if (getValorPrioridad(copia[j].getPrioridad()) < getValorPrioridad(copia[j + 1].getPrioridad())) {
                    Rescate temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    private int getValorPrioridad(String prioridad) {
        switch (prioridad) {
            case "ALTA": return 3;
            case "MEDIA": return 2;
            case "BAJA": return 1;
            default: return 0;
        }
    }
}