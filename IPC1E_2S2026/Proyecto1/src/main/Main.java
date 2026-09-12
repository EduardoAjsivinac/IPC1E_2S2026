package IPC1E_2S2026.Proyecto1.src.main;

import IPC1E_2S2026.Proyecto1.src.modelo.*;
import IPC1E_2S2026.Proyecto1.src.servicio.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== VERIFICACION DE ESTRUCTURA DEL SISTEMA ===");

        // 1. Prueba de AnimalService
        AnimalService animalService = new AnimalService();
        Animal animal1 = new Animal("A-001", "Perro", 3, "APTO", "DISPONIBLE");
        animalService.agregarAnimal(animal1);
        System.out.println("Animal registrado: " + animalService.buscarPorCodigo("A-001").getEspecie());

        // 2. Prueba de AdoptanteService
        AdoptanteService adoptanteService = new AdoptanteService();
        Adoptante adoptante1 = new Adoptante("AD-001", "Juan Perez", "1234567890123", "55554444");
        adoptanteService.registrarAdoptante(adoptante1);
        System.out.println("Adoptante registrado: " + adoptanteService.buscarPorCodigo("AD-001").getNombre());

        // 3. Prueba de UbicacionService (Matriz 2D)
        UbicacionService ubicacionService = new UbicacionService(5, 5);
        ubicacionService.asignarUbicacion(0, 0, "A-001");
        System.out.println("Ubicacion [0][0]: " + ubicacionService.getMapaRefugio()[0][0]);

        System.out.println("----------------------------------------------");
        System.out.println("¡Compilacion exitosa! Todos los servicios y modelos responden correctamente.");
    }
}