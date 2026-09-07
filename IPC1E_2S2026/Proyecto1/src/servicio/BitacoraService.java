package IPC1E_2S2026.Proyecto1.src.servicio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import IPC1E_2S2026.Proyecto1.src.modelo.BitacoraEntry;

public class BitacoraService {

    private BitacoraEntry[] acciones;
    private int contadorAcciones;

    private BitacoraEntry[] errores;
    private int contadorErrores;

    private final String RUTA_ACCIONES = "bitacora_acciones.txt";
    private final String RUTA_ERRORES = "bitacora_errores.txt";

    public BitacoraService() {
        this.acciones = new BitacoraEntry[500];
        this.contadorAcciones = 0;

        this.errores = new BitacoraEntry[500];
        this.contadorErrores = 0;
    }

    public void registrarAccion(String fechaHora, String usuario, String modulo, String tipoEvento, String descripcion) {
        if (contadorAcciones < acciones.length) {
            BitacoraEntry entrada = new BitacoraEntry(fechaHora, usuario, modulo, tipoEvento, descripcion, "", false);
            acciones[contadorAcciones++] = entrada;
            guardarEnArchivo(RUTA_ACCIONES, entrada.aFormatoTexto());
        }
    }

    public void registrarError(String fechaHora, String usuario, String modulo, String tipoEvento, String descripcion, String motivoRechazo) {
        if (contadorErrores < errores.length) {
            BitacoraEntry entrada = new BitacoraEntry(fechaHora, usuario, modulo, tipoEvento, descripcion, motivoRechazo, true);
            errores[contadorErrores++] = entrada;
            guardarEnArchivo(RUTA_ERRORES, entrada.aFormatoTexto());
        }
    }

    private void guardarEnArchivo(String ruta, String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir bitácora: " + e.getMessage());
        }
    }

    public BitacoraEntry[] getRegistros() {
        int total = contadorAcciones + contadorErrores;
        BitacoraEntry[] todos = new BitacoraEntry[total];
        int index = 0;

        for (int i = 0; i < contadorAcciones; i++) {
            todos[index++] = acciones[i];
        }
        for (int i = 0; i < contadorErrores; i++) {
            todos[index++] = errores[i];
        }

        return todos;
    }

    public BitacoraEntry[] getAcciones() {
        BitacoraEntry[] copia = new BitacoraEntry[contadorAcciones];
        for (int i = 0; i < contadorAcciones; i++) copia[i] = acciones[i];
        return copia;
    }

    public BitacoraEntry[] getErrores() {
        BitacoraEntry[] copia = new BitacoraEntry[contadorErrores];
        for (int i = 0; i < contadorErrores; i++) copia[i] = errores[i];
        return copia;
    }

    public boolean exportarHTML(String rutaSalida, String tipoFiltro) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
            bw.write("<html><head><meta charset='UTF-8'><title>Reporte de Bitácora</title>");
            bw.write("<style>table {border-collapse: collapse; width: 100%;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #4CAF50; color: white;}</style>");
            bw.write("</head><body>");
            bw.write("<h2>Reporte de Bitácora - " + tipoFiltro.toUpperCase() + "</h2>");
            bw.write("<table><tr><th>Fecha/Hora</th><th>Usuario</th><th>Módulo</th><th>Evento</th><th>Descripción / Detalle</th></tr>");

            if (tipoFiltro.equalsIgnoreCase("ACCIONES") || tipoFiltro.equalsIgnoreCase("AMBOS")) {
                for (int i = 0; i < contadorAcciones; i++) {
                    BitacoraEntry b = acciones[i];
                    bw.write("<tr><td>" + b.getFechaHora() + "</td><td>" + b.getUsuario() + "</td><td>" + b.getModulo() + "</td><td>" + b.getTipoEvento() + "</td><td>" + b.getDescripcion() + "</td></tr>");
                }
            }

            if (tipoFiltro.equalsIgnoreCase("ERRORES") || tipoFiltro.equalsIgnoreCase("AMBOS")) {
                for (int i = 0; i < contadorErrores; i++) {
                    BitacoraEntry b = errores[i];
                    bw.write("<tr style='background-color:#ffe6e6;'><td>" + b.getFechaHora() + "</td><td>" + b.getUsuario() + "</td><td>" + b.getModulo() + "</td><td>" + b.getTipoEvento() + "</td><td>" + b.getDescripcion() + " - Motivo: " + b.getMotivoRechazo() + "</td></tr>");
                }
            }

            bw.write("</table></body></html>");
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar reporte HTML: " + e.getMessage());
            return false;
        }
    }
}