package IPC1E_2S2026.Proyecto1.src.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import IPC1E_2S2026.Proyecto1.src.modelo.Animal;
import IPC1E_2S2026.Proyecto1.src.modelo.Solicitud;
import IPC1E_2S2026.Proyecto1.src.servicio.AnimalService;
import IPC1E_2S2026.Proyecto1.src.servicio.SolicitudService;

public class ArchivoManager {

    // CARGAR Y GUARDAR ANIMALES
    public static void cargarAnimales(String ruta, AnimalService service) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    String codigo = partes[0].trim();
                    String especie = partes[1].trim();
                    int edad = Integer.parseInt(partes[2].trim());
                    String estadoClinico = partes[3].trim();
                    String estadoAdopcion = partes[4].trim();

                    Animal animal = new Animal(codigo, especie, edad, estadoClinico, estadoAdopcion);
                    service.agregarAnimal(animal);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar animales: " + e.getMessage());
        }
    }

    public static void guardarAnimales(String ruta, AnimalService service) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            Animal[] lista = service.getAnimales();
            if (lista != null) {
                for (Animal a : lista) {
                    if (a != null) {
                        bw.write(a.getCodigo() + "," +
                                 a.getEspecie() + "," +
                                 a.getEdad() + "," +
                                 a.getEstadoClinico() + "," +
                                 a.getEstadoAdopcion());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar animales: " + e.getMessage());
        }
    }

    // CARGAR Y GUARDAR SOLICITUDES
    public static void cargarSolicitudes(String ruta, SolicitudService service) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    String codigo = partes[0].trim();
                    String codAnimal = partes[1].trim();
                    String codAdoptante = partes[2].trim();
                    String fecha = partes[3].trim();
                    String estado = partes[4].trim();

                    service.registrarSolicitud(codigo, codAnimal, codAdoptante, fecha, estado);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar solicitudes: " + e.getMessage());
        }
    }

    public static void guardarSolicitudes(String ruta, SolicitudService service) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            Solicitud[] lista = service.getSolicitudes();
            if (lista != null) {
                for (Solicitud s : lista) {
                    if (s != null) {
                        bw.write(s.getCodigo() + "," +
                                 s.getCodigoAnimal() + "," +
                                 s.getCodigoAdoptante() + "," +
                                 s.getFecha() + "," +
                                 s.getEstado());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar solicitudes: " + e.getMessage());
        }
    }
}