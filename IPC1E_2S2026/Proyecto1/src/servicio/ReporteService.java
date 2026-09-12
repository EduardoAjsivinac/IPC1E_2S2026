package IPC1E_2S2026.Proyecto1.src.servicio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import IPC1E_2S2026.Proyecto1.src.modelo.Animal;
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
        if (animalService == null) {
            return false;
        }

        FileWriter escritor = null;
        try {
            File archivo = new File(nombreArchivo);
            escritor = new FileWriter(archivo);

            escritor.write("<!DOCTYPE html>\n");
            escritor.write("<html lang=\"es\">\n");
            escritor.write("<head>\n");
            escritor.write("    <meta charset=\"UTF-8\">\n");
            escritor.write("    <title>Reporte de Animales</title>\n");
            escritor.write("    <style>\n");
            escritor.write("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f9; }\n");
            escritor.write("        h1 { color: #333; text-align: center; }\n");
            escritor.write("        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }\n");
            escritor.write("        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }\n");
            escritor.write("        th { background-color: #007bff; color: white; }\n");
            escritor.write("        tr:nth-child(even) { background-color: #f2f2f2; }\n");
            escritor.write("    </style>\n");
            escritor.write("</head>\n");
            escritor.write("<body>\n");
            escritor.write("    <h1>Reporte General de Animales - Centro de Rescate</h1>\n");
            escritor.write("    <table>\n");
            escritor.write("        <tr>\n");
            escritor.write("            <th>Código</th>\n");
            escritor.write("            <th>Especie</th>\n");
            escritor.write("            <th>Edad (años)</th>\n");
            escritor.write("            <th>Estado Clínico</th>\n");
            escritor.write("            <th>Estado Adopción</th>\n");
            escritor.write("        </tr>\n");

            Animal[] animales = animalService.getAnimales();
            for (int i = 0; i < animales.length; i++) {
                Animal a = animales[i];
                if (a != null) {
                    escritor.write("        <tr>\n");
                    escritor.write("            <td>" + a.getCodigo() + "</td>\n");
                    escritor.write("            <td>" + a.getEspecie() + "</td>\n");
                    escritor.write("            <td>" + a.getEdad() + "</td>\n");
                    escritor.write("            <td>" + a.getEstadoClinico() + "</td>\n");
                    escritor.write("            <td>" + a.getEstadoAdopcion() + "</td>\n");
                    escritor.write("        </tr>\n");
                }
            }

            escritor.write("    </table>\n");
            escritor.write("</body>\n");
            escritor.write("</html>\n");

            escritor.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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