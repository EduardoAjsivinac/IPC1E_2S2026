package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.Solicitud;

public class SolicitudService {

    private Solicitud[] solicitudes;
    private int contador;

    public SolicitudService() {
        this.solicitudes = new Solicitud[50];
        this.contador = 0;
    }

    public boolean registrarSolicitud(String codigo, String codigoAnimal, String codigoAdoptante, String fecha, String estado) {
        if (codigo == null || codigo.trim().isEmpty() ||
            codigoAnimal == null || codigoAnimal.trim().isEmpty() ||
            codigoAdoptante == null || codigoAdoptante.trim().isEmpty() ||
            fecha == null || fecha.trim().isEmpty()) {
            return false;
        }

        if (contador < solicitudes.length) {
            Solicitud nueva = new Solicitud(codigo, codigoAnimal, codigoAdoptante, fecha, estado);
            solicitudes[contador++] = nueva;
            return true;
        }
        return false;
    }

    public Solicitud[] getSolicitudes() {
        Solicitud[] copia = new Solicitud[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = solicitudes[i];
        }
        return copia;
    }
}