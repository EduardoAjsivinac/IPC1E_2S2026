package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Solicitud;
import IPC1E_2S2026.Proyecto1.src.modelo.Animal;

public class SolicitudService {

    private Solicitud[] solicitudes;
    private int contador;
    private AnimalService animalService;
    private BitacoraService bitacoraService;

    public SolicitudService() {
        this.solicitudes = new Solicitud[100];
        this.contador = 0;
    }

    public SolicitudService(AnimalService animalService, BitacoraService bitacoraService) {
        this.solicitudes = new Solicitud[100];
        this.contador = 0;
        this.animalService = animalService;
        this.bitacoraService = bitacoraService;
    }

    public void setAnimalService(AnimalService animalService) {
        this.animalService = animalService;
    }

    public void setBitacoraService(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    public boolean registrarSolicitud(String codigo, String codigoAnimal, String codigoAdoptante, String fecha, String estado) {
        if (contador >= solicitudes.length) {
            return false;
        }

        if (buscarPorCodigo(codigo) != null) {
            return false;
        }

        Solicitud nueva = new Solicitud(codigo, codigoAnimal, codigoAdoptante, fecha, estado);
        solicitudes[contador] = nueva;
        contador++;

        return true;
    }

    public boolean aprobarSolicitud(String codigoSolicitud) {
        Solicitud solicitudAprobar = buscarPorCodigo(codigoSolicitud);

        if (solicitudAprobar == null) {
            return false;
        }

        // 1. Cambiar estado de la solicitud seleccionada a APROBADA
        solicitudAprobar.setEstado("APROBADA");

        String codigoAnimal = solicitudAprobar.getCodigoAnimal();

        // 2. Cambiar estado del animal a ADOPTADO en la misma instancia de AnimalService
        if (animalService != null) {
            Animal animal = animalService.buscarPorCodigo(codigoAnimal);
            if (animal != null) {
                animal.setEstadoAdopcion("ADOPTADO");
            }
        }

        // 3. Cambiar el estado de las demás solicitudes PENDIENTES del mismo animal a RECHAZADA
        for (int i = 0; i < contador; i++) {
            Solicitud s = solicitudes[i];
            if (s != null && !s.getCodigo().equalsIgnoreCase(codigoSolicitud)) {
                if (s.getCodigoAnimal().equalsIgnoreCase(codigoAnimal) && "PENDIENTE".equalsIgnoreCase(s.getEstado())) {
                    s.setEstado("RECHAZADA");
                }
            }
        }

        return true;
    }

    public Solicitud buscarPorCodigo(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (solicitudes[i] != null && solicitudes[i].getCodigo().equalsIgnoreCase(codigo)) {
                return solicitudes[i];
            }
        }
        return null;
    }

    public Solicitud[] getSolicitudes() {
        return solicitudes;
    }

    public int getContador() {
        return contador;
    }
}