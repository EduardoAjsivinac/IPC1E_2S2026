import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reportes {

    public static String marcaTiempo() {

        return LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern(
                                "yyyyMMdd_HHmmss"
                        )
                );
    }


    private static String html(
            String titulo,
            String cuerpo) {

        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<title>"
                + titulo
                + "</title>"
                + "<style>"
                + "body{font-family:Arial;margin:30px}"
                + "table{border-collapse:collapse;width:100%}"
                + "th,td{border:1px solid #999;padding:8px}"
                + "th{background:#eee}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>"
                + titulo
                + "</h1>"
                + cuerpo
                + "</body>"
                + "</html>";
    }


    private static void guardar(
            String nombre,
            String contenido) {

        new File("reportes").mkdirs();


        try (PrintWriter p =
                     new PrintWriter(
                             new File(
                                     "reportes",
                                     nombre
                                             + "_"
                                             + marcaTiempo()
                                             + ".html"
                             ),
                             "UTF-8"
                     )) {

            p.print(contenido);

        } catch (Exception e) {

            System.out.println(
                    "Error reporte: "
                            + e.getMessage()
            );
        }
    }


    public static void animales(
            Sistema s) {

        StringBuilder b =
                new StringBuilder(
                        "<table>"
                        + "<tr>"
                        + "<th>Código</th>"
                        + "<th>Nombre</th>"
                        + "<th>Especie</th>"
                        + "<th>Edad</th>"
                        + "<th>Clínico</th>"
                        + "<th>Adopción</th>"
                        + "</tr>"
                );


        for (int i = 0;
             i < s.totalAnimales;
             i++) {

            Animal a =
                    s.animales[i];


            if (!a.estadoAdopcion
                    .equals("ELIMINADO")) {

                b.append(
                        "<tr>"
                        + "<td>"
                        + a.codigo
                        + "</td>"
                        + "<td>"
                        + a.nombre
                        + "</td>"
                        + "<td>"
                        + a.especie
                        + "</td>"
                        + "<td>"
                        + a.edad
                        + "</td>"
                        + "<td>"
                        + a.estadoClinico
                        + "</td>"
                        + "<td>"
                        + a.estadoAdopcion
                        + "</td>"
                        + "</tr>"
                );
            }
        }


        b.append(
                "</table>"
        );


        guardar(
                "animales",
                html(
                        "Reporte de Animales",
                        b.toString()
                )
        );
    }


    public static void adopciones(
            Sistema s) {

        StringBuilder b =
                new StringBuilder(
                        "<table>"
                        + "<tr>"
                        + "<th>Código</th>"
                        + "<th>Animal</th>"
                        + "<th>Adoptante</th>"
                        + "<th>Fecha</th>"
                        + "<th>Estado</th>"
                        + "</tr>"
                );


        for (int i = 0;
             i < s.totalSolicitudes;
             i++) {

            Solicitud x =
                    s.solicitudes[i];


            b.append(
                    "<tr>"
                    + "<td>"
                    + x.codigo
                    + "</td>"
                    + "<td>"
                    + x.codigoAnimal
                    + "</td>"
                    + "<td>"
                    + x.codigoAdoptante
                    + "</td>"
                    + "<td>"
                    + x.fecha
                    + "</td>"
                    + "<td>"
                    + x.estado
                    + "</td>"
                    + "</tr>"
            );
        }


        b.append(
                "</table>"
        );


        guardar(
                "adopciones",
                html(
                        "Reporte de Adopciones",
                        b.toString()
                )
        );
    }


    public static void ocupacion(
            Sistema s) {

        StringBuilder b =
                new StringBuilder(
                        "<p>Capacidad: "
                        + (
                                EspacioRefugio.FILAS
                                * EspacioRefugio.COLUMNAS
                        )
                        + " espacios</p>"
                        + "<table>"
                        + "<tr>"
                        + "<th>Fila</th>"
                        + "<th>Columna</th>"
                        + "<th>Estado</th>"
                        + "<th>Animal</th>"
                        + "</tr>"
                );


        for (int i = 0;
             i < EspacioRefugio.FILAS;
             i++) {

            for (int j = 0;
                 j < EspacioRefugio.COLUMNAS;
                 j++) {

                String v =
                        s.espacios.matriz[i][j];


                b.append(
                        "<tr>"
                        + "<td>"
                        + i
                        + "</td>"
                        + "<td>"
                        + j
                        + "</td>"
                        + "<td>"
                        + (
                                v.equals("")
                                ? "LIBRE"
                                : "OCUPADO"
                        )
                        + "</td>"
                        + "<td>"
                        + v
                        + "</td>"
                        + "</tr>"
                );
            }
        }


        b.append(
                "</table>"
        );


        guardar(
                "ocupacion",
                html(
                        "Reporte de Ocupación",
                        b.toString()
                )
        );
    }


    public static void rescates(
            Sistema s) {

        StringBuilder b =
                new StringBuilder(
                        "<table>"
                        + "<tr>"
                        + "<th>Código</th>"
                        + "<th>Descripción</th>"
                        + "<th>Prioridad</th>"
                        + "<th>Estado</th>"
                        + "<th>Fecha</th>"
                        + "<th>Animal</th>"
                        + "</tr>"
                );


        String[] orden = {
                "ALTA",
                "MEDIA",
                "BAJA"
        };


        for (int k = 0;
             k < 3;
             k++) {

            for (int i = 0;
                 i < s.totalRescates;
                 i++) {

                Rescate r =
                        s.rescates[i];


                if (r.prioridad
                        .equals(orden[k])) {

                    b.append(
                            "<tr>"
                            + "<td>"
                            + r.codigo
                            + "</td>"
                            + "<td>"
                            + r.descripcion
                            + "</td>"
                            + "<td>"
                            + r.prioridad
                            + "</td>"
                            + "<td>"
                            + r.estado
                            + "</td>"
                            + "<td>"
                            + r.fecha
                            + "</td>"
                            + "<td>"
                            + r.codigoAnimalVinculado
                            + "</td>"
                            + "</tr>"
                    );
                }
            }
        }


        b.append(
                "</table>"
        );


        guardar(
                "rescates",
                html(
                        "Reporte de Rescates (prioridad)",
                        b.toString()
                )
        );
    }


    public static void bitacora(
            String tipo) {

        String archivo =
                tipo.equals("ACCIONES")
                ? "datos/bitacora_acciones.txt"
                : "datos/bitacora_errores.txt";


        StringBuilder b =
                new StringBuilder(
                        "<table>"
                        + "<tr>"
                        + "<th>Fecha</th>"
                        + "<th>Usuario</th>"
                        + "<th>Módulo</th>"
                        + "<th>Evento</th>"
                        + "<th>Descripción/Motivo</th>"
                        + "</tr>"
                );


        try (BufferedReader r =
                     new BufferedReader(
                             new FileReader(
                                     archivo
                             )
                     )) {

            String l;


            while (
                    (l = r.readLine()) != null
            ) {

                String[] x =
                        l.split(
                                "\\|",
                                -1
                        );


                if (x.length >= 5) {

                    b.append(
                            "<tr>"
                    );


                    for (int i = 0;
                         i < 5;
                         i++) {

                        b.append(
                                "<td>"
                                + x[i]
                                + "</td>"
                        );
                    }


                    b.append(
                            "</tr>"
                    );
                }
            }

        } catch (Exception e) {

            b.append(
                    "<tr>"
                    + "<td colspan='5'>"
                    + "Sin registros"
                    + "</td>"
                    + "</tr>"
            );
        }


        b.append(
                "</table>"
        );


        guardar(
                "bitacora_"
                        + tipo.toLowerCase(),
                html(
                        "Bitácora de "
                                + tipo,
                        b.toString()
                )
        );
    }
}