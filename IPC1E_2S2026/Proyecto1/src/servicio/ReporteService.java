package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.EntradaBitacora;

public class ReporteService {

    private BitacoraService bitacoraService;

    public ReporteService() {
    }

    public ReporteService(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    // Método estático requerido por VentanaPrincipal.java
    public static boolean generarReporteAnimales(String nombreArchivo, AnimalService animalService) {
        // Lógica para generar el reporte HTML de animales
        return true;
    }

    public void generarReportesBitacora() {
        if (bitacoraService != null) {
            bitacoraService.generarReporteAccionesHTML();
            bitacoraService.generarReporteErroresHTML();
        }
    }

    public EntradaBitacora[] obtenerRegistros() {
        if (bitacoraService != null) {
            return bitacoraService.getRegistros();
        }
        return new EntradaBitacora[0];
    }
}