public class Sistema {

    public static final int MAX_ANIMALES = 100;
    public static final int MAX_ADOPTANTES = 100;
    public static final int MAX_SOLICITUDES = 100;
    public static final int MAX_RESCATES = 100;
    public static final int MAX_USUARIOS = 10;

    public static Animal[] animales = new Animal[MAX_ANIMALES];
    public static Adoptante[] adoptantes = new Adoptante[MAX_ADOPTANTES];
    public static Solicitud[] solicitudes = new Solicitud[MAX_SOLICITUDES];
    public static Rescate[] rescates = new Rescate[MAX_RESCATES];
    public static Usuario[] usuarios = new Usuario[MAX_USUARIOS];

    public static int cantidadAnimales = 0;
    public static int cantidadAdoptantes = 0;
    public static int cantidadSolicitudes = 0;
    public static int cantidadRescates = 0;
    public static int cantidadUsuarios = 0;

    public static String[][] ubicaciones = new String[4][8];

    public static Usuario usuarioActual;

    public static void iniciar() {
        cantidadAnimales = 0;
        cantidadAdoptantes = 0;
        cantidadSolicitudes = 0;
        cantidadRescates = 0;
        cantidadUsuarios = 0;

        usuarios[cantidadUsuarios++] =
                new Usuario("admin1", "Refugio2026", "ADMIN");

        usuarios[cantidadUsuarios++] =
                new Usuario("auxiliar1", "Auxiliar2026", "AUXILIAR");

        for (int i = 0; i < ubicaciones.length; i++) {
            for (int j = 0; j < ubicaciones[i].length; j++) {
                ubicaciones[i][j] = "";
            }
        }
    }

    // ==========================================
    // MÉTODOS DE ANIMALES Y ADOPTANTES
    // ==========================================

    public static boolean codigoAnimalExiste(String codigo) {
        if (codigo == null) return false;
        for (int i = 0; i < cantidadAnimales; i++) {
            if (animales[i] != null && animales[i].codigo != null && animales[i].codigo.equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean dpiExiste(String dpi) {
        if (dpi == null) return false;
        for (int i = 0; i < cantidadAdoptantes; i++) {
            if (adoptantes[i] != null && adoptantes[i].dpi != null && adoptantes[i].dpi.equals(dpi)) {
                return true;
            }
        }
        return false;
    }

    public static boolean codigoAdoptanteExiste(String codigo) {
        if (codigo == null) return false;
        for (int i = 0; i < cantidadAdoptantes; i++) {
            if (adoptantes[i] != null && adoptantes[i].codigo != null && adoptantes[i].codigo.equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean agregarAnimal(Animal animal) {
        if (animal == null) return false;
        if (cantidadAnimales < MAX_ANIMALES) {
            animales[cantidadAnimales] = animal;
            cantidadAnimales++;
            return true;
        }
        return false;
    }

    public static boolean agregarAdoptante(Adoptante adoptante) {
        if (adoptante == null) return false;
        if (cantidadAdoptantes < MAX_ADOPTANTES) {
            adoptantes[cantidadAdoptantes] = adoptante;
            cantidadAdoptantes++;
            return true;
        }
        return false;
    }

    public static Animal buscarAnimal(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < cantidadAnimales; i++) {
            if (animales[i] != null && animales[i].codigo != null && animales[i].codigo.equalsIgnoreCase(codigo)) {
                return animales[i];
            }
        }
        return null;
    }

    public static Adoptante buscarAdoptante(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < cantidadAdoptantes; i++) {
            if (adoptantes[i] != null && adoptantes[i].codigo != null && adoptantes[i].codigo.equalsIgnoreCase(codigo)) {
                return adoptantes[i];
            }
        }
        return null;
    }

    // ==========================================
    // MÉTODOS DE SOLICITUDES Y RESCATES (COMMIT 3)
    // ==========================================

    public static boolean codigoSolicitudExiste(String codigo) {
        if (codigo == null) return false;
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes[i] != null && solicitudes[i].codigo != null && solicitudes[i].codigo.equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean codigoRescateExiste(String codigo) {
        if (codigo == null) return false;
        for (int i = 0; i < cantidadRescates; i++) {
            if (rescates[i] != null && rescates[i].codigo != null && rescates[i].codigo.equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean agregarSolicitud(Solicitud solicitud) {
        if (solicitud == null) return false;
        if (cantidadSolicitudes < MAX_SOLICITUDES) {
            solicitudes[cantidadSolicitudes] = solicitud;
            cantidadSolicitudes++;
            return true;
        }
        return false;
    }

    public static boolean agregarRescate(Rescate rescate) {
        if (rescate == null) return false;
        if (cantidadRescates < MAX_RESCATES) {
            rescates[cantidadRescates] = rescate;
            cantidadRescates++;
            return true;
        }
        return false;
    }

    public static Solicitud buscarSolicitud(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes[i] != null && solicitudes[i].codigo != null && solicitudes[i].codigo.equalsIgnoreCase(codigo)) {
                return solicitudes[i];
            }
        }
        return null;
    }

    public static Rescate buscarRescate(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < cantidadRescates; i++) {
            if (rescates[i] != null && rescates[i].codigo != null && rescates[i].codigo.equalsIgnoreCase(codigo)) {
                return rescates[i];
            }
        }
        return null;
    }

    public static boolean animalTieneSolicitudAprobada(String codigoAnimal) {
        if (codigoAnimal == null) return false;
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes[i] != null && solicitudes[i].codigoAnimal != null && solicitudes[i].estado != null) {
                if (solicitudes[i].codigoAnimal.equalsIgnoreCase(codigoAnimal) && solicitudes[i].estado.equals("APROBADA")) {
                    return true;
                }
            }
        }
        return false;
    }
}