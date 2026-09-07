import java.time.format.DateTimeFormatter;

public class Sistema {

    static final int MAX_ANIMALES = 100;
    static final int MAX_ADOPTANTES = 100;
    static final int MAX_SOLICITUDES = 200;
    static final int MAX_RESCATES = 100;


    Animal[] animales =
            new Animal[MAX_ANIMALES];

    Adoptante[] adoptantes =
            new Adoptante[MAX_ADOPTANTES];

    Solicitud[] solicitudes =
            new Solicitud[MAX_SOLICITUDES];

    Rescate[] rescates =
            new Rescate[MAX_RESCATES];


    EspacioRefugio espacios =
            new EspacioRefugio();


    int totalAnimales = 0;
    int totalAdoptantes = 0;
    int totalSolicitudes = 0;
    int totalRescates = 0;


    String usuarioActual;
    String rolActual;


    DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy"
            );


    public Sistema() {

        Persistencia.cargar(this);
    }


    public Usuario autenticar(
            String usuario,
            String clave) {

        Usuario[] usuarios = {

                new Usuario(
                        "admin1",
                        "Refugio2026",
                        "ADMIN"
                ),

                new Usuario(
                        "auxiliar1",
                        "Auxiliar2026",
                        "AUXILIAR"
                )
        };


        for (int i = 0;
             i < usuarios.length;
             i++) {

            if (usuarios[i].usuario.equals(usuario)
                    && usuarios[i].contrasena.equals(clave)) {

                return usuarios[i];
            }
        }

        return null;
    }


    public boolean registrarAnimal(
            String codigo,
            String nombre,
            String especie,
            int edad,
            String clinico,
            String adopcion) {

        if (!Util.codigo(codigo, "A")
                || Util.vacio(nombre)
                || !Util.soloLetras(nombre)
                || !(especie.equals("Perro")
                || especie.equals("Gato"))
                || edad < 0
                || edad > 25
                || !estadoClinicoValido(clinico)
                || !estadoAdopcionValido(adopcion)) {

            return false;
        }


        if (buscarAnimal(codigo) != -1
                || totalAnimales >= MAX_ANIMALES) {

            return false;
        }


        animales[totalAnimales++] =
                new Animal(
                        codigo,
                        nombre,
                        especie,
                        edad,
                        clinico,
                        adopcion
                );


        guardar();


        Bitacora.accion(
                usuarioActual,
                "ANIMALES",
                "ALTA",
                "Animal " + codigo
                        + " registrado"
        );


        return true;
    }


    public int buscarAnimal(
            String codigo) {

        for (int i = 0;
             i < totalAnimales;
             i++) {

            if (animales[i] != null
                    && animales[i].codigo
                    .equalsIgnoreCase(codigo)) {

                return i;
            }
        }

        return -1;
    }


    public boolean editarAnimal(
            String codigo,
            String clinico,
            String adopcion) {

        int i =
                buscarAnimal(codigo);


        if (i == -1
                || !estadoClinicoValido(clinico)
                || !estadoAdopcionValido(adopcion)) {

            return false;
        }


        animales[i].estadoClinico =
                clinico;

        animales[i].estadoAdopcion =
                adopcion;


        if (adopcion.equals("ELIMINADO")) {

            liberarAnimal(codigo);
        }


        guardar();


        Bitacora.accion(
                usuarioActual,
                "ANIMALES",
                "EDITAR",
                "Animal " + codigo
                        + " actualizado"
        );


        return true;
    }


    public boolean eliminarAnimal(
            String codigo) {

        if (!"ADMIN".equals(rolActual)) {

            return false;
        }


        int i =
                buscarAnimal(codigo);


        if (i == -1
                || animales[i]
                .estadoAdopcion
                .equals("ELIMINADO")) {

            return false;
        }


        animales[i].estadoAdopcion =
                "ELIMINADO";


        liberarAnimal(codigo);


        guardar();


        Bitacora.accion(
                usuarioActual,
                "ANIMALES",
                "BAJA_LOGICA",
                "Animal " + codigo
                        + " eliminado lógicamente"
        );


        return true;
    }


    public boolean registrarAdoptante(
            String codigo,
            String nombre,
            String dpi,
            String telefono) {

        if (!Util.codigo(codigo, "AD")
                || !Util.soloLetras(nombre)
                || !Util.digitos(dpi, 13)
                || !Util.digitos(telefono, 8)
                || totalAdoptantes >= MAX_ADOPTANTES) {

            return false;
        }


        if (buscarAdoptante(codigo) != -1
                || buscarDpi(dpi) != -1) {

            return false;
        }


        adoptantes[totalAdoptantes++] =
                new Adoptante(
                        codigo,
                        nombre,
                        dpi,
                        telefono
                );


        guardar();


        Bitacora.accion(
                usuarioActual,
                "ADOPTANTES",
                "ALTA",
                "Adoptante " + codigo
                        + " registrado"
        );


        return true;
    }


    public int buscarAdoptante(
            String codigo) {

        for (int i = 0;
             i < totalAdoptantes;
             i++) {

            if (adoptantes[i] != null
                    && adoptantes[i]
                    .codigo
                    .equalsIgnoreCase(codigo)) {

                return i;
            }
        }

        return -1;
    }


    public int buscarDpi(
            String dpi) {

        for (int i = 0;
             i < totalAdoptantes;
             i++) {

            if (adoptantes[i] != null
                    && adoptantes[i]
                    .dpi
                    .equals(dpi)) {

                return i;
            }
        }

        return -1;
    }


    public boolean editarAdoptante(
            String codigo,
            String nombre,
            String telefono) {

        int i =
                buscarAdoptante(codigo);


        if (i == -1
                || !Util.soloLetras(nombre)
                || !Util.digitos(telefono, 8)) {

            return false;
        }


        adoptantes[i].nombre =
                nombre;

        adoptantes[i].telefono =
                telefono;


        guardar();


        Bitacora.accion(
                usuarioActual,
                "ADOPTANTES",
                "EDITAR",
                "Adoptante " + codigo
                        + " actualizado"
        );


        return true;
    }


    public boolean puedeEliminarAdoptante(
            String codigo) {

        for (int i = 0;
             i < totalSolicitudes;
             i++) {

            if (solicitudes[i]
                    .codigoAdoptante
                    .equals(codigo)
                    && solicitudes[i]
                    .estado
                    .equals("APROBADA")) {

                return false;
            }
        }

        return true;
    }


    public boolean registrarSolicitud(
            String codigo,
            String animal,
            String adoptante,
            String fecha) {

        int a =
                buscarAnimal(animal);

        int ad =
                buscarAdoptante(adoptante);


        if (!Util.codigo(codigo, "S")
                || buscarSolicitud(codigo) != -1
                || a == -1
                || ad == -1
                || !Util.fechaValida(fecha)
                || !animales[a]
                .estadoAdopcion
                .equals("DISPONIBLE")
                || totalSolicitudes >= MAX_SOLICITUDES) {

            return false;
        }


        solicitudes[totalSolicitudes++] =
                new Solicitud(
                        codigo,
                        animal,
                        adoptante,
                        fecha,
                        "PENDIENTE"
                );


        guardar();


        Bitacora.accion(
                usuarioActual,
                "SOLICITUDES",
                "ALTA",
                "Solicitud " + codigo
                        + " registrada"
        );


        return true;
    }


    public int buscarSolicitud(
            String codigo) {

        for (int i = 0;
             i < totalSolicitudes;
             i++) {

            if (solicitudes[i]
                    .codigo
                    .equalsIgnoreCase(codigo)) {

                return i;
            }
        }

        return -1;
    }


    public boolean cambiarSolicitud(
            String codigo,
            String estado) {

        int i =
                buscarSolicitud(codigo);


        if (i == -1
                || !estadoSolicitudValido(estado)) {

            return false;
        }


        if (estado.equals("APROBADA")) {

            int a =
                    buscarAnimal(
                            solicitudes[i]
                                    .codigoAnimal
                    );


            if (a == -1
                    || !animales[a]
                    .estadoAdopcion
                    .equals("DISPONIBLE")) {

                return false;
            }


            solicitudes[i].estado =
                    "APROBADA";

            animales[a].estadoAdopcion =
                    "ADOPTADO";


            for (int j = 0;
                 j < totalSolicitudes;
                 j++) {

                if (j != i
                        && solicitudes[j]
                        .codigoAnimal
                        .equals(
                                solicitudes[i]
                                        .codigoAnimal
                        )
                        && solicitudes[j]
                        .estado
                        .equals("PENDIENTE")) {

                    solicitudes[j].estado =
                            "RECHAZADA";
                }
            }


            liberarAnimal(
                    solicitudes[i]
                            .codigoAnimal
            );

        } else {

            solicitudes[i].estado =
                    estado;
        }


        guardar();


        Bitacora.accion(
                usuarioActual,
                "SOLICITUDES",
                "CAMBIO_ESTADO",
                "Solicitud " + codigo
                        + " -> "
                        + estado
        );


        return true;
    }


    public boolean registrarRescate(
            String codigo,
            String descripcion,
            String prioridad,
            String estado,
            String fecha) {

        if (!Util.codigo(codigo, "R")
                || buscarRescate(codigo) != -1
                || Util.vacio(descripcion)
                || !prioridadValida(prioridad)
                || !estadoRescateValido(estado)
                || !Util.fechaValida(fecha)
                || totalRescates >= MAX_RESCATES) {

            return false;
        }


        rescates[totalRescates++] =
                new Rescate(
                        codigo,
                        descripcion,
                        prioridad,
                        estado,
                        fecha,
                        ""
                );


        guardar();


        Bitacora.accion(
                usuarioActual,
                "RESCATES",
                "ALTA",
                "Rescate " + codigo
                        + " registrado"
        );


        return true;
    }


    public int buscarRescate(
            String codigo) {

        for (int i = 0;
             i < totalRescates;
             i++) {

            if (rescates[i]
                    .codigo
                    .equalsIgnoreCase(codigo)) {

                return i;
            }
        }

        return -1;
    }


    public boolean atenderRescate(
            String codigo,
            String animalExistente,
            String nombre,
            String especie,
            int edad) {

        int r =
                buscarRescate(codigo);


        if (r == -1
                || rescates[r]
                .estado
                .equals("ATENDIDO")) {

            return false;
        }


        String animalCodigo =
                animalExistente;


        if (Util.vacio(animalCodigo)) {

            animalCodigo =
                    "A-" +
                    codigo.substring(2);


            if (buscarAnimal(animalCodigo)
                    == -1) {

                if (!registrarAnimal(
                        animalCodigo,
                        nombre,
                        especie,
                        edad,
                        "EN_TRATAMIENTO",
                        "DISPONIBLE")) {

                    return false;
                }
            }

        } else if (
                buscarAnimal(animalCodigo)
                        == -1) {

            return false;
        }


        rescates[r]
                .codigoAnimalVinculado =
                animalCodigo;

        rescates[r].estado =
                "ATENDIDO";


        guardar();


        Bitacora.accion(
                usuarioActual,
                "RESCATES",
                "ATENDER",
                "Rescate " + codigo
                        + " atendido; animal "
                        + animalCodigo
        );


        return true;
    }


    public boolean asignarEspacio(
            String codigoAnimal,
            int fila,
            int columna) {

        if (fila < 0
                || fila >= EspacioRefugio.FILAS
                || columna < 0
                || columna >= EspacioRefugio.COLUMNAS
                || buscarAnimal(codigoAnimal)
                == -1) {

            return false;
        }


        if (!espacios.matriz[fila][columna]
                .equals("")) {

            return false;
        }


        if (buscarPosicionAnimal(
                codigoAnimal
        )[0] != -1) {

            return false;
        }


        espacios.matriz[fila][columna] =
                codigoAnimal;


        guardar();


        Bitacora.accion(
                usuarioActual,
                "UBICACIONES",
                "ASIGNAR",
                codigoAnimal
                        + " asignado a ["
                        + fila
                        + "]["
                        + columna
                        + "]"
        );


        return true;
    }


    public boolean liberarEspacio(
            int fila,
            int columna) {

        if (fila < 0
                || fila >= EspacioRefugio.FILAS
                || columna < 0
                || columna >= EspacioRefugio.COLUMNAS
                || espacios.matriz[fila][columna]
                .equals("")) {

            return false;
        }


        String codigo =
                espacios.matriz[fila][columna];


        espacios.matriz[fila][columna] =
                "";


        guardar();


        Bitacora.accion(
                usuarioActual,
                "UBICACIONES",
                "LIBERAR",
                codigo
                        + " liberado de ["
                        + fila
                        + "]["
                        + columna
                        + "]"
        );


        return true;
    }


    private void liberarAnimal(
            String codigo) {

        for (int i = 0;
             i < EspacioRefugio.FILAS;
             i++) {

            for (int j = 0;
                 j < EspacioRefugio.COLUMNAS;
                 j++) {

                if (espacios.matriz[i][j]
                        .equals(codigo)) {

                    espacios.matriz[i][j] =
                            "";

                    Bitacora.accion(
                            usuarioActual,
                            "UBICACIONES",
                            "LIBERAR",
                            codigo
                                    + " liberado automáticamente de ["
                                    + i
                                    + "]["
                                    + j
                                    + "]"
                    );
                }
            }
        }
    }


    public int[] buscarPosicionAnimal(
            String codigo) {

        for (int i = 0;
             i < EspacioRefugio.FILAS;
             i++) {

            for (int j = 0;
                 j < EspacioRefugio.COLUMNAS;
                 j++) {

                if (espacios.matriz[i][j]
                        .equals(codigo)) {

                    return new int[]{
                            i,
                            j
                    };
                }
            }
        }

        return new int[]{
                -1,
                -1
        };
    }


    public void guardar() {

        Persistencia.guardar(this);
    }


    boolean estadoClinicoValido(
            String s) {

        return s.equals(
                "EN_OBSERVACION"
        )
                || s.equals(
                        "EN_TRATAMIENTO"
                )
                || s.equals("APTO");
    }


    boolean estadoAdopcionValido(
            String s) {

        return s.equals("DISPONIBLE")
                || s.equals("ADOPTADO")
                || s.equals("ELIMINADO");
    }


    boolean estadoSolicitudValido(
            String s) {

        return s.equals("PENDIENTE")
                || s.equals("APROBADA")
                || s.equals("RECHAZADA")
                || s.equals("COMPLETADA");
    }


    boolean prioridadValida(
            String s) {

        return s.equals("ALTA")
                || s.equals("MEDIA")
                || s.equals("BAJA");
    }


    boolean estadoRescateValido(
            String s) {

        return s.equals("PENDIENTE")
                || s.equals("ATENDIDO");
    }
}