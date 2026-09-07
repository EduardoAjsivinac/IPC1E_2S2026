import java.io.*;

public class Persistencia {

    static final String DIR = "datos";


    public static void guardar(
            Sistema s) {

        new File(DIR).mkdirs();

        guardarAnimales(s);
        guardarAdoptantes(s);
        guardarSolicitudes(s);
        guardarRescates(s);
        guardarMatriz(s);
    }


    private static void guardarAnimales(
            Sistema s) {

        try (PrintWriter p =
                     new PrintWriter(
                             new File(
                                     DIR,
                                     "animales.txt"
                             )
                     )) {

            for (int i = 0;
                 i < s.totalAnimales;
                 i++) {

                Animal a =
                        s.animales[i];

                p.println(
                        a.codigo + "|"
                        + Util.seguro(a.nombre)
                        + "|"
                        + a.especie
                        + "|"
                        + a.edad
                        + "|"
                        + a.estadoClinico
                        + "|"
                        + a.estadoAdopcion
                );
            }

        } catch (IOException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }


    private static void guardarAdoptantes(
            Sistema s) {

        try (PrintWriter p =
                     new PrintWriter(
                             new File(
                                     DIR,
                                     "adoptantes.txt"
                             )
                     )) {

            for (int i = 0;
                 i < s.totalAdoptantes;
                 i++) {

                Adoptante a =
                        s.adoptantes[i];

                p.println(
                        a.codigo + "|"
                        + Util.seguro(a.nombre)
                        + "|"
                        + a.dpi
                        + "|"
                        + a.telefono
                );
            }

        } catch (IOException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }


    private static void guardarSolicitudes(
            Sistema s) {

        try (PrintWriter p =
                     new PrintWriter(
                             new File(
                                     DIR,
                                     "solicitudes.txt"
                             )
                     )) {

            for (int i = 0;
                 i < s.totalSolicitudes;
                 i++) {

                Solicitud x =
                        s.solicitudes[i];

                p.println(
                        x.codigo + "|"
                        + x.codigoAnimal
                        + "|"
                        + x.codigoAdoptante
                        + "|"
                        + x.fecha
                        + "|"
                        + x.estado
                );
            }

        } catch (IOException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }


    private static void guardarRescates(
            Sistema s) {

        try (PrintWriter p =
                     new PrintWriter(
                             new File(
                                     DIR,
                                     "rescates.txt"
                             )
                     )) {

            for (int i = 0;
                 i < s.totalRescates;
                 i++) {

                Rescate r =
                        s.rescates[i];

                p.println(
                        r.codigo + "|"
                        + Util.seguro(
                                r.descripcion
                        )
                        + "|"
                        + r.prioridad
                        + "|"
                        + r.estado
                        + "|"
                        + r.fecha
                        + "|"
                        + r.codigoAnimalVinculado
                );
            }

        } catch (IOException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }


    private static void guardarMatriz(
            Sistema s) {

        try (PrintWriter p =
                     new PrintWriter(
                             new File(
                                     DIR,
                                     "ubicaciones.txt"
                             )
                     )) {

            for (int i = 0;
                 i < EspacioRefugio.FILAS;
                 i++) {

                for (int j = 0;
                     j < EspacioRefugio.COLUMNAS;
                     j++) {

                    if (j > 0) {
                        p.print("|");
                    }

                    p.print(
                            s.espacios.matriz[i][j]
                    );
                }

                p.println();
            }

        } catch (IOException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }


    public static void cargar(
            Sistema s) {

        cargarAnimales(s);
        cargarAdoptantes(s);
        cargarSolicitudes(s);
        cargarRescates(s);
        cargarMatriz(s);
    }


    private static String[] partes(
            String linea) {

        return linea.split(
                "\\|",
                -1
        );
    }


    private static void cargarAnimales(
            Sistema s) {

        File f =
                new File(
                        DIR,
                        "animales.txt"
                );

        if (!f.exists()) {
            return;
        }


        try (BufferedReader b =
                     new BufferedReader(
                             new FileReader(f)
                     )) {

            String l;

            while (
                    (l = b.readLine()) != null
                    && s.totalAnimales
                    < Sistema.MAX_ANIMALES
            ) {

                String[] x =
                        partes(l);


                if (x.length == 6) {

                    s.animales[
                            s.totalAnimales++
                    ] =
                            new Animal(
                                    x[0],
                                    x[1],
                                    x[2],
                                    Integer.parseInt(x[3]),
                                    x[4],
                                    x[5]
                            );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargando animales: "
                            + e.getMessage()
            );
        }
    }


    private static void cargarAdoptantes(
            Sistema s) {

        File f =
                new File(
                        DIR,
                        "adoptantes.txt"
                );

        if (!f.exists()) {
            return;
        }


        try (BufferedReader b =
                     new BufferedReader(
                             new FileReader(f)
                     )) {

            String l;

            while (
                    (l = b.readLine()) != null
                    && s.totalAdoptantes
                    < Sistema.MAX_ADOPTANTES
            ) {

                String[] x =
                        partes(l);


                if (x.length == 4) {

                    s.adoptantes[
                            s.totalAdoptantes++
                    ] =
                            new Adoptante(
                                    x[0],
                                    x[1],
                                    x[2],
                                    x[3]
                            );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargando adoptantes: "
                            + e.getMessage()
            );
        }
    }


    private static void cargarSolicitudes(
            Sistema s) {

        File f =
                new File(
                        DIR,
                        "solicitudes.txt"
                );

        if (!f.exists()) {
            return;
        }


        try (BufferedReader b =
                     new BufferedReader(
                             new FileReader(f)
                     )) {

            String l;

            while (
                    (l = b.readLine()) != null
                    && s.totalSolicitudes
                    < Sistema.MAX_SOLICITUDES
            ) {

                String[] x =
                        partes(l);


                if (x.length == 5) {

                    s.solicitudes[
                            s.totalSolicitudes++
                    ] =
                            new Solicitud(
                                    x[0],
                                    x[1],
                                    x[2],
                                    x[3],
                                    x[4]
                            );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargando solicitudes: "
                            + e.getMessage()
            );
        }
    }


    private static void cargarRescates(
            Sistema s) {

        File f =
                new File(
                        DIR,
                        "rescates.txt"
                );

        if (!f.exists()) {
            return;
        }


        try (BufferedReader b =
                     new BufferedReader(
                             new FileReader(f)
                     )) {

            String l;

            while (
                    (l = b.readLine()) != null
                    && s.totalRescates
                    < Sistema.MAX_RESCATES
            ) {

                String[] x =
                        partes(l);


                if (x.length == 6) {

                    s.rescates[
                            s.totalRescates++
                    ] =
                            new Rescate(
                                    x[0],
                                    x[1],
                                    x[2],
                                    x[3],
                                    x[4],
                                    x[5]
                            );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargando rescates: "
                            + e.getMessage()
            );
        }
    }


    private static void cargarMatriz(
            Sistema s) {

        File f =
                new File(
                        DIR,
                        "ubicaciones.txt"
                );

        if (!f.exists()) {
            return;
        }


        try (BufferedReader b =
                     new BufferedReader(
                             new FileReader(f)
                     )) {

            String l;
            int i = 0;


            while (
                    (l = b.readLine()) != null
                    && i < EspacioRefugio.FILAS
            ) {

                String[] x =
                        partes(l);


                for (int j = 0;
                     j < x.length
                     && j < EspacioRefugio.COLUMNAS;
                     j++) {

                    s.espacios.matriz[i][j] =
                            x[j];
                }

                i++;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargando ubicaciones: "
                            + e.getMessage()
            );
        }
    }


    public static void exportarCSV(
            Sistema s) {

        new File("reportes").mkdirs();


        String nombre =
                "reportes/animales_"
                        + Reportes.marcaTiempo()
                        + ".csv";


        try (PrintWriter p =
                     new PrintWriter(
                             new File(nombre)
                     )) {

            p.println(
                    "codigo,nombre,especie,edad,estadoClinico,estadoAdopcion"
            );


            for (int i = 0;
                 i < s.totalAnimales;
                 i++) {

                Animal a =
                        s.animales[i];


                p.println(
                        a.codigo
                        + ",\""
                        + a.nombre.replace(
                                "\"",
                                "\"\""
                        )
                        + "\","
                        + a.especie
                        + ","
                        + a.edad
                        + ","
                        + a.estadoClinico
                        + ","
                        + a.estadoAdopcion
                );
            }


            System.out.println(
                    "CSV generado: "
                            + nombre
            );

        } catch (IOException e) {

            System.out.println(
                    "Error CSV: "
                            + e.getMessage()
            );
        }
    }


    public static int importarCSV(
            Sistema s,
            File archivo) {

        int agregados = 0;


        try (BufferedReader b =
                     new BufferedReader(
                             new FileReader(
                                     archivo
                             )
                     )) {

            String l =
                    b.readLine();


            while (
                    (l = b.readLine()) != null
                    && s.totalAnimales
                    < Sistema.MAX_ANIMALES
            ) {

                String[] x =
                        l.split(",", -1);


                if (x.length != 6) {
                    continue;
                }


                String nombre =
                        x[1]
                                .replace("\"", "")
                                .trim();


                int edad;

                try {

                    edad =
                            Integer.parseInt(
                                    x[3].trim()
                            );

                } catch (Exception e) {

                    continue;
                }


                if (!Util.codigo(
                        x[0].trim(),
                        "A"
                )
                        || s.buscarAnimal(
                        x[0].trim()
                ) != -1
                        || !Util.soloLetras(nombre)
                        || !(x[2].trim()
                        .equals("Perro")
                        || x[2].trim()
                        .equals("Gato"))
                        || edad < 0
                        || edad > 25
                        || !s.estadoClinicoValido(
                        x[4].trim()
                )
                        || !s.estadoAdopcionValido(
                        x[5].trim()
                )) {

                    continue;
                }


                s.animales[
                        s.totalAnimales++
                ] =
                        new Animal(
                                x[0].trim(),
                                nombre,
                                x[2].trim(),
                                edad,
                                x[4].trim(),
                                x[5].trim()
                        );


                agregados++;
            }


            guardar(s);

        } catch (Exception e) {

            System.out.println(
                    "Error importando CSV: "
                            + e.getMessage()
            );
        }


        return agregados;
    }
}