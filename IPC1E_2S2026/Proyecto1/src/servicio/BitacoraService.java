package IPC1E_2S2026.Proyecto1.src.servicio;

import IPC1E_2S2026.Proyecto1.src.modelo.EntradaBitacora;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BitacoraService {

    private EntradaBitacora[] acciones;
    private int contAcciones;

    private EntradaBitacora[] errores;
    private int contErrores;

    private static final String FILE_ACCIONES_TXT = "bitacora_acciones.txt";
    private static final String FILE_ERRORES_TXT = "bitacora_errores.txt";

    public BitacoraService() {
        this.acciones = new EntradaBitacora[100];
        this.contAcciones = 0;
        this.errores = new EntradaBitacora[100];
        this.contErrores = 0;
    }

    public void registrarAccion(String usuario, String modulo, String tipoEvento, String descripcion) {
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        EntradaBitacora entrada = new EntradaBitacora(fechaHora, usuario, modulo, tipoEvento, descripcion);

        if (contAcciones >= acciones.length) {
            acciones = redimensionar(acciones);
        }
        acciones[contAcciones++] = entrada;
        escribirEnArchivo(FILE_ACCIONES_TXT, entrada.toString());
    }

    public void registrarError(String usuario, String modulo, String tipoEvento, String motivoRechazo) {
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        EntradaBitacora entrada = new EntradaBitacora(fechaHora, usuario, modulo, tipoEvento, motivoRechazo);

        if (contErrores >= errores.length) {
            errores = redimensionar(errores);
        }
        errores[contErrores++] = entrada;
        escribirEnArchivo(FILE_ERRORES_TXT, entrada.toString());
    }

    private EntradaBitacora[] redimensionar(EntradaBitacora[] arregloOriginal) {
        EntradaBitacora[] nuevo = new EntradaBitacora[arregloOriginal.length * 2];
        for (int i = 0; i < arregloOriginal.length; i++) {
            nuevo[i] = arregloOriginal[i];
        }
        return nuevo;
    }

    private void escribirEnArchivo(String nombreArchivo, String linea) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, true))) {
            pw.println(linea);
        } catch (IOException e) {
            System.err.println("Error al escribir en " + nombreArchivo + ": " + e.getMessage());
        }
    }

    public void generarReporteAccionesHTML() {
        generarHTML("reporte_acciones.html", "Bitácora de Acciones", FILE_ACCIONES_TXT, "#1b5e20");
    }

    public void generarReporteErroresHTML() {
        generarHTML("reporte_errores.html", "Bitácora de Errores", FILE_ERRORES_TXT, "#b71c1c");
    }

    private void generarHTML(String nombreHTML, String titulo, String archivoTxt, String colorHeader) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreHTML))) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html lang='es'>");
            pw.println("<head><meta charset='UTF-8'><title>" + titulo + "</title>");
            pw.println("<style>");
            pw.println("body { font-family: Arial, sans-serif; margin: 30px; background-color: #f8f9fa; }");
            pw.println("h1 { color: " + colorHeader + "; border-bottom: 2px solid " + colorHeader + "; padding-bottom: 10px; }");
            pw.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }");
            pw.println("th, td { border: 1px solid #dddddd; padding: 12px; text-align: left; }");
            pw.println("th { background-color: " + colorHeader + "; color: white; }");
            pw.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            pw.println("</style></head><body>");

            pw.println("<h1>" + titulo + "</h1>");
            pw.println("<table>");
            pw.println("<tr><th>Fecha / Hora</th><th>Usuario</th><th>Módulo</th><th>Tipo Evento</th><th>Descripción / Motivo</th></tr>");

            File file = new File(archivoTxt);
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] partes = linea.split(" \\| ");
                        if (partes.length >= 5) {
                            pw.println("<tr>");
                            pw.println("<td>" + partes[0] + "</td>");
                            pw.println("<td>" + partes[1] + "</td>");
                            pw.println("<td>" + partes[2] + "</td>");
                            pw.println("<td><b>" + partes[3] + "</b></td>");
                            pw.println("<td>" + partes[4] + "</td>");
                            pw.println("</tr>");
                        }
                    }
                }
            }

            pw.println("</table></body></html>");
        } catch (IOException e) {
            System.err.println("Error al generar " + nombreHTML + ": " + e.getMessage());
        }
    }

    public EntradaBitacora[] getAcciones() {
        return acciones;
    }

    public int getContAcciones() {
        return contAcciones;
    }

    public EntradaBitacora[] getErrores() {
        return errores;
    }

    public int getContErrores() {
        return contErrores;
    }

    public EntradaBitacora[] getRegistros() {
        return acciones;
    }

    public int getContador() {
        return contAcciones;
    }
}