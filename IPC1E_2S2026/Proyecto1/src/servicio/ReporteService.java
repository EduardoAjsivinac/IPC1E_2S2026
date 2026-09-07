package IPC1E_2S2026.Proyecto1.src.servicio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import IPC1E_2S2026.Proyecto1.src.modelo.*;

public class ReporteService {

    // Generar reporte HTML de Animales
    public static boolean generarReporteAnimales(String rutaSalida, AnimalService animalService) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
            bw.write("<!DOCTYPE html>\n<html>\n<head>\n<title>Reporte de Animales</title>\n");
            bw.write("<style>table {width: 100%; border-collapse: collapse;} th, td {border: 1px solid black; padding: 8px; text-align: left;} th {background-color: #f2f2f2;}</style>\n");
            bw.write("</head>\n<body>\n");
            bw.write("<h2>Listado de Animales Registrados</h2>\n");
            bw.write("<table>\n<tr><th>Código</th><th>Especie</th><th>Edad</th><th>Estado Clínico</th><th>Estado Adopción</th></tr>\n");

            Animal[] lista = animalService.getAnimalesActivos();
            for (Animal a : lista) {
                bw.write("<tr>");
                bw.write("<td>" + a.getCodigo() + "</td>");
                bw.write("<td>" + a.getEspecie() + "</td>");
                bw.write("<td>" + a.getEdad() + "</td>");
                bw.write("<td>" + a.getEstadoClinico() + "</td>");
                bw.write("<td>" + a.getEstadoAdopcion() + "</td>");
                bw.write("</tr>\n");
            }

            bw.write("</table>\n</body>\n</html>");
            return true;
        } catch (IOException e) {
            System.err.println("Error al generar reporte HTML: " + e.getMessage());
            return false;
        }
    }

    // Generar reporte HTML de Bitácora
    public static boolean generarReporteBitacora(String rutaSalida, BitacoraService bitacoraService) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
            bw.write("<!DOCTYPE html>\n<html>\n<head>\n<title>Reporte de Bitácora</title>\n");
            bw.write("<style>table {width: 100%; border-collapse: collapse;} th, td {border: 1px solid black; padding: 8px; text-align: left;} th {background-color: #f2f2f2;}</style>\n");
            bw.write("</head>\n<body>\n");
            bw.write("<h2>Bitácora de Eventos del Sistema</h2>\n");
            bw.write("<table>\n<tr><th>Fecha/Hora</th><th>Usuario</th><th>Módulo</th><th>Evento</th><th>Descripción</th></tr>\n");

            BitacoraEntry[] registros = bitacoraService.getRegistros();
            for (BitacoraEntry b : registros) {
                bw.write("<tr>");
                bw.write("<td>" + b.getFechaHora() + "</td>");
                bw.write("<td>" + b.getUsuario() + "</td>");
                bw.write("<td>" + b.getModulo() + "</td>");
                bw.write("<td>" + b.getEvento() + "</td>");
                bw.write("<td>" + b.getDescripcion() + "</td>");
                bw.write("</tr>\n");
            }

            bw.write("</table>\n</body>\n</html>");
            return true;
        } catch (IOException e) {
            System.err.println("Error al generar reporte de bitácora: " + e.getMessage());
            return false;
        }
    }
}