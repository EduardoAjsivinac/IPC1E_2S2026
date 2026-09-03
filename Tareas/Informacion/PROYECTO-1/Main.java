import javax.swing.*;
import java.awt.*;

public class Main {

    static JFrame ventana;
    static int intentos = 0;
    static boolean bloqueado = false;

    public static void main(String[] args) {
        Sistema.iniciar();
        SwingUtilities.invokeLater(() -> login());
    }

    // ==========================================
    // AUTENTICACIÓN Y AUTORIZACIÓN
    // ==========================================

    static void login() {
        if (bloqueado) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sesión bloqueada, reinicie la aplicación."
            );
            System.exit(0);
            return;
        }

        // Carga preventiva en el arreglo de Sistema si no existen usuarios registrados
        if (Sistema.cantidadUsuarios == 0 && Sistema.usuarios != null) {
            Sistema.usuarios[0] = new Usuario("admin", "1234", "ADMINISTRADOR");
            Sistema.cantidadUsuarios = 1;
        }

        String usuario = JOptionPane.showInputDialog(
                null,
                "Usuario (usar: admin):"
        );

        if (usuario == null) {
            System.exit(0);
            return;
        }

        String contrasena = JOptionPane.showInputDialog(
                null,
                "Contraseña (usar: 1234):"
        );

        if (contrasena == null) {
            System.exit(0);
            return;
        }

        usuario = usuario.trim();
        contrasena = contrasena.trim();
        boolean encontrado = false;

        // 1. Búsqueda directa en el arreglo de usuarios
        for (int i = 0; i < Sistema.cantidadUsuarios; i++) {
            Usuario u = Sistema.usuarios[i];
            if (u != null && u.usuario != null && u.contrasena != null 
                    && u.usuario.equalsIgnoreCase(usuario) && u.contrasena.equals(contrasena)) {
                Sistema.usuarioActual = u;
                encontrado = true;
                break;
            }
        }

        // 2. Respaldo directo en Main para garantizado de acceso
        if (!encontrado && usuario.equalsIgnoreCase("admin") && (contrasena.equals("1234") || contrasena.equals("admin"))) {
            Sistema.usuarioActual = new Usuario("admin", "1234", "ADMINISTRADOR");
            encontrado = true;
        }

        if (encontrado) {
            intentos = 0;
            mostrarMenu();
        } else {
            intentos++;
            JOptionPane.showMessageDialog(
                    null,
                    "Usuario o contraseña incorrectos.\n"
                    + "Intento " + intentos + " de 3."
            );

            if (intentos >= 3) {
                bloqueado = true;
                JOptionPane.showMessageDialog(
                        null,
                        "Sesión bloqueada por sobrepasar intentos fallidos."
                );
                System.exit(0);
            } else {
                login();
            }
        }
    }

    // ==========================================
    // MENÚ PRINCIPAL
    // ==========================================

    static void mostrarMenu() {
        ventana = new JFrame("Centro de Rescate Animal");
        ventana.setSize(700, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel titulo = new JLabel(
                "CENTRO DE RESCATE ANIMAL",
                SwingConstants.CENTER
        );

        JButton animales = new JButton("Animales");
        JButton adoptantes = new JButton("Adoptantes");
        JButton solicitudes = new JButton("Solicitudes");
        JButton rescates = new JButton("Rescates");
        JButton ubicaciones = new JButton("Ubicaciones");
        JButton reportes = new JButton("Reportes");
        JButton salir = new JButton("Salir");

        animales.addActionListener(e -> moduloAnimales());
        adoptantes.addActionListener(e -> moduloAdoptantes());
        solicitudes.addActionListener(e -> moduloSolicitudes());
        rescates.addActionListener(e -> moduloRescates());
        ubicaciones.addActionListener(e -> moduloUbicaciones());

        reportes.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        ventana,
                        "Este módulo estará disponible en el Commit 5."
                )
        );

        salir.addActionListener(e -> System.exit(0));

        panel.add(animales);
        panel.add(adoptantes);
        panel.add(solicitudes);
        panel.add(rescates);
        panel.add(ubicaciones);
        panel.add(reportes);
        panel.add(salir);

        ventana.setLayout(new BorderLayout());
        ventana.add(titulo, BorderLayout.NORTH);
        ventana.add(panel, BorderLayout.CENTER);

        ventana.setVisible(true);
    }

    // ==========================================
    // MÓDULO DE ANIMALES
    // ==========================================

    static void moduloAnimales() {
        String[] opciones = {
                "Registrar",
                "Buscar",
                "Listar",
                "Editar estado",
                "Eliminar",
                "Volver"
        };

        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    ventana,
                    "Módulo de Animales",
                    "Animales",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (opcion == 0) registrarAnimal();
            if (opcion == 1) buscarAnimal();
            if (opcion == 2) listarAnimales();
            if (opcion == 3) editarAnimal();
            if (opcion == 4) eliminarAnimal();

        } while (opcion != 5 && opcion != -1);
    }

    static void registrarAnimal() {
        String codigo = JOptionPane.showInputDialog(ventana, "Código (ej. A-014):");
        if (codigo == null) return;

        if (!codigo.matches("A-[0-9]{3}")) {
            JOptionPane.showMessageDialog(ventana, "Código inválido.");
            return;
        }

        if (Sistema.codigoAnimalExiste(codigo)) {
            JOptionPane.showMessageDialog(ventana, "Ese código ya existe.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(ventana, "Nombre:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String especie = JOptionPane.showInputDialog(ventana, "Especie (Perro/Gato):");
        if (especie == null) return;

        if (!especie.equalsIgnoreCase("Perro") && !especie.equalsIgnoreCase("Gato")) {
            JOptionPane.showMessageDialog(ventana, "La especie debe ser Perro o Gato.");
            return;
        }
        especie = especie.substring(0, 1).toUpperCase() + especie.substring(1).toLowerCase();

        String edadTexto = JOptionPane.showInputDialog(ventana, "Edad estimada:");
        if (edadTexto == null) return;

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Edad inválida.");
            return;
        }

        if (edad < 0 || edad > 25) {
            JOptionPane.showMessageDialog(ventana, "La edad debe estar entre 0 y 25.");
            return;
        }

        String estado = JOptionPane.showInputDialog(
                ventana,
                "Estado clínico:\nEN_OBSERVACION\nEN_TRATAMIENTO\nAPTO"
        );
        if (estado == null) return;
        estado = estado.toUpperCase().trim();

        if (!estado.equals("EN_OBSERVACION") && !estado.equals("EN_TRATAMIENTO") && !estado.equals("APTO")) {
            JOptionPane.showMessageDialog(ventana, "Estado inválido.");
            return;
        }

        Animal animal = new Animal(codigo, nombre, especie, edad, estado, "DISPONIBLE");

        if (Sistema.agregarAnimal(animal)) {
            JOptionPane.showMessageDialog(ventana, "Animal registrado correctamente.");
        } else {
            JOptionPane.showMessageDialog(ventana, "Error: Capacidad máxima de animales alcanzada.");
        }
    }

    static void buscarAnimal() {
        String codigo = JOptionPane.showInputDialog(ventana, "Código del animal:");
        if (codigo == null) return;

        Animal animal = Sistema.buscarAnimal(codigo);
        if (animal == null) {
            JOptionPane.showMessageDialog(ventana, "Animal no encontrado.");
            return;
        }

        JOptionPane.showMessageDialog(
                ventana,
                "Código: " + animal.codigo +
                "\nNombre: " + animal.nombre +
                "\nEspecie: " + animal.especie +
                "\nEdad: " + animal.edad +
                "\nEstado clínico: " + animal.estadoClinico +
                "\nEstado adopción: " + animal.estadoAdopcion
        );
    }

    static void listarAnimales() {
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < Sistema.cantidadAnimales; i++) {
            Animal a = Sistema.animales[i];
            if (a != null && !a.estadoAdopcion.equals("ELIMINADO")) {
                texto.append(a.codigo).append(" | ")
                     .append(a.nombre).append(" | ")
                     .append(a.especie).append(" | ")
                     .append(a.estadoAdopcion).append("\n");
            }
        }

        String resultado = texto.length() == 0 ? "No hay animales registrados." : texto.toString();

        JTextArea area = new JTextArea(resultado);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                ventana,
                new JScrollPane(area),
                "Animales",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    static void editarAnimal() {
        String codigo = JOptionPane.showInputDialog(ventana, "Código:");
        if (codigo == null) return;

        Animal animal = Sistema.buscarAnimal(codigo);
        if (animal == null) {
            JOptionPane.showMessageDialog(ventana, "Animal no encontrado.");
            return;
        }

        String estado = JOptionPane.showInputDialog(
                ventana,
                "Nuevo estado clínico:\nEN_OBSERVACION\nEN_TRATAMIENTO\nAPTO"
        );
        if (estado == null) return;
        estado = estado.toUpperCase().trim();

        if (estado.equals("EN_OBSERVACION") || estado.equals("EN_TRATAMIENTO") || estado.equals("APTO")) {
            animal.estadoClinico = estado;
            JOptionPane.showMessageDialog(ventana, "Estado actualizado.");
        } else {
            JOptionPane.showMessageDialog(ventana, "Estado inválido.");
        }
    }

    static void eliminarAnimal() {
        String codigo = JOptionPane.showInputDialog(ventana, "Código del animal:");
        if (codigo == null) return;

        Animal animal = Sistema.buscarAnimal(codigo);
        if (animal == null) {
            JOptionPane.showMessageDialog(ventana, "Animal no encontrado.");
            return;
        }

        animal.estadoAdopcion = "ELIMINADO";
        JOptionPane.showMessageDialog(ventana, "Animal dado de baja correctamente.");
    }

    // ==========================================
    // MÓDULO DE ADOPTANTES
    // ==========================================

    static void moduloAdoptantes() {
        String[] opciones = {
                "Registrar",
                "Buscar",
                "Listar",
                "Volver"
        };

        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    ventana,
                    "Módulo de Adoptantes",
                    "Adoptantes",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (opcion == 0) registrarAdoptante();
            if (opcion == 1) buscarAdoptante();
            if (opcion == 2) listarAdoptantes();

        } while (opcion != 3 && opcion != -1);
    }

    static void registrarAdoptante() {
        String codigo = JOptionPane.showInputDialog(ventana, "Código (ej. AD-007):");
        if (codigo == null || !codigo.matches("AD-[0-9]{3}")) {
            JOptionPane.showMessageDialog(ventana, "Código inválido.");
            return;
        }

        if (Sistema.codigoAdoptanteExiste(codigo)) {
            JOptionPane.showMessageDialog(ventana, "Ese código de adoptante ya existe.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(ventana, "Nombre:");
        if (nombre == null || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(ventana, "Nombre inválido.");
            return;
        }

        String dpi = JOptionPane.showInputDialog(ventana, "DPI:");
        if (dpi == null || !dpi.matches("[0-9]{13}")) {
            JOptionPane.showMessageDialog(ventana, "El DPI debe tener 13 dígitos.");
            return;
        }

        if (Sistema.dpiExiste(dpi)) {
            JOptionPane.showMessageDialog(ventana, "Ese DPI ya existe.");
            return;
        }

        String telefono = JOptionPane.showInputDialog(ventana, "Teléfono:");
        if (telefono == null || !telefono.matches("[0-9]{8}")) {
            JOptionPane.showMessageDialog(ventana, "El teléfono debe tener 8 dígitos.");
            return;
        }

        Adoptante adoptante = new Adoptante(codigo, nombre, dpi, telefono);

        if (Sistema.agregarAdoptante(adoptante)) {
            JOptionPane.showMessageDialog(ventana, "Adoptante registrado correctamente.");
        } else {
            JOptionPane.showMessageDialog(ventana, "Error: Capacidad máxima de adoptantes alcanzada.");
        }
    }

    static void buscarAdoptante() {
        String codigo = JOptionPane.showInputDialog(ventana, "Código:");
        if (codigo == null) return;

        Adoptante a = Sistema.buscarAdoptante(codigo);
        if (a == null) {
            JOptionPane.showMessageDialog(ventana, "Adoptante no encontrado.");
            return;
        }

        JOptionPane.showMessageDialog(
                ventana,
                "Código: " + a.codigo +
                "\nNombre: " + a.nombre +
                "\nDPI: " + a.dpi +
                "\nTeléfono: " + a.telefono
        );
    }

    static void listarAdoptantes() {
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < Sistema.cantidadAdoptantes; i++) {
            Adoptante a = Sistema.adoptantes[i];
            if (a != null) {
                texto.append(a.codigo).append(" | ")
                     .append(a.nombre).append(" | ")
                     .append(a.dpi).append(" | ")
                     .append(a.telefono).append("\n");
            }
        }

        String resultado = texto.length() == 0 ? "No hay adoptantes." : texto.toString();

        JTextArea area = new JTextArea(resultado);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                ventana,
                new JScrollPane(area),
                "Adoptantes",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================================
    // MÓDULO DE SOLICITUDES
    // ==========================================

    static void moduloSolicitudes() {
        String[] opciones = {
                "Registrar",
                "Cambiar estado",
                "Listar",
                "Volver"
        };

        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    ventana,
                    "Módulo de Solicitudes",
                    "Solicitudes",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (opcion == 0) registrarSolicitud();
            if (opcion == 1) cambiarSolicitud();
            if (opcion == 2) listarSolicitudes();

        } while (opcion != 3 && opcion != -1);
    }

    static void registrarSolicitud() {
        String codigo = JOptionPane.showInputDialog(
                ventana,  "Código (ej. S-001):"
                
        );

        if (codigo == null || !codigo.matches("S-[0-9]{3}")) {
            JOptionPane.showMessageDialog(ventana, "Código inválido.");
            return;
        }

        if (Sistema.codigoSolicitudExiste(codigo)) {
            JOptionPane.showMessageDialog(ventana, "La solicitud ya existe.");
            return;
        }

        String codigoAnimal = JOptionPane.showInputDialog(
                ventana,
                "Código del animal:"
        );

        Animal animal = Sistema.buscarAnimal(codigoAnimal);

        if (animal == null || !animal.estadoAdopcion.equals("DISPONIBLE")) {
            JOptionPane.showMessageDialog(ventana, "El animal no está disponible.");
            return;
        }

        String codigoAdoptante = JOptionPane.showInputDialog(
                ventana,
                "Código del adoptante:"
        );

        Adoptante adoptante = Sistema.buscarAdoptante(codigoAdoptante);

        if (adoptante == null) {
            JOptionPane.showMessageDialog(ventana, "El adoptante no existe.");
            return;
        }

        String fecha = JOptionPane.showInputDialog(
                ventana,
                "Fecha (dd/mm/aaaa):"
        );

        if (fecha == null || !fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
            JOptionPane.showMessageDialog(ventana, "Fecha inválida.");
            return;
        }

        Solicitud solicitud = new Solicitud(
                codigo,
                codigoAnimal,
                codigoAdoptante,
                fecha,
                "PENDIENTE"
        );

        Sistema.agregarSolicitud(solicitud);
        JOptionPane.showMessageDialog(ventana, "Solicitud registrada.");
    }

    static void cambiarSolicitud() {
        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código de solicitud:"
        );
        if (codigo == null) return;

        Solicitud s = Sistema.buscarSolicitud(codigo);

        if (s == null) {
            JOptionPane.showMessageDialog(ventana, "Solicitud no encontrada.");
            return;
        }

        String estado = JOptionPane.showInputDialog(
                ventana,
                "Nuevo estado:\nAPROBADA\nRECHAZADA\nCOMPLETADA"
        );

        if (estado == null) return;
        estado = estado.toUpperCase().trim();

        if (estado.equals("APROBADA")) {
            if (Sistema.animalTieneSolicitudAprobada(s.codigoAnimal)) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "El animal ya tiene una solicitud aprobada."
                );
                return;
            }

            Animal animal = Sistema.buscarAnimal(s.codigoAnimal);
            if (animal != null) {
                animal.estadoAdopcion = "ADOPTADO";
            }

            for (int i = 0; i < Sistema.cantidadSolicitudes; i++) {
                Solicitud otra = Sistema.solicitudes[i];

                if (otra != null && !otra.codigo.equalsIgnoreCase(s.codigo)
                        && otra.codigoAnimal.equalsIgnoreCase(s.codigoAnimal)
                        && otra.estado.equals("PENDIENTE")) {

                    otra.estado = "RECHAZADA";
                }
            }
        }

        if (estado.equals("APROBADA") || estado.equals("RECHAZADA") || estado.equals("COMPLETADA")) {
            s.estado = estado;
            JOptionPane.showMessageDialog(ventana, "Estado actualizado.");
        } else {
            JOptionPane.showMessageDialog(ventana, "Estado inválido.");
        }
    }

    static void listarSolicitudes() {
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < Sistema.cantidadSolicitudes; i++) {
            Solicitud s = Sistema.solicitudes[i];
            if (s != null) {
                texto.append(s.codigo).append(" | ")
                     .append(s.codigoAnimal).append(" | ")
                     .append(s.codigoAdoptante).append(" | ")
                     .append(s.estado).append("\n");
            }
        }

        String resultado = texto.length() == 0 ? "No hay solicitudes." : texto.toString();

        JTextArea area = new JTextArea(resultado);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                ventana,
                new JScrollPane(area),
                "Solicitudes",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================================
    // MÓDULO DE RESCATES
    // ==========================================

    static void moduloRescates() {
        String[] opciones = {
                "Registrar",
                "Atender",
                "Listar",
                "Volver"
        };

        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    ventana,
                    "Módulo de Rescates",
                    "Rescates",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (opcion == 0) registrarRescate();
            if (opcion == 1) atenderRescate();
            if (opcion == 2) listarRescates();

        } while (opcion != 3 && opcion != -1);
    }

    static void registrarRescate() {
        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código (ej. R-009):"
        );

        if (codigo == null || !codigo.matches("R-[0-9]{3}")) {
            JOptionPane.showMessageDialog(ventana, "Código inválido.");
            return;
        }

        String descripcion = JOptionPane.showInputDialog(
                ventana,
                "Descripción:"
        );
        if (descripcion == null) return;

        String prioridad = JOptionPane.showInputDialog(
                ventana,
                "Prioridad:\nALTA\nMEDIA\nBAJA"
        );
        if (prioridad == null) return;
        prioridad = prioridad.toUpperCase().trim();

        if (!prioridad.equals("ALTA") && !prioridad.equals("MEDIA") && !prioridad.equals("BAJA")) {
            JOptionPane.showMessageDialog(ventana, "Prioridad inválida.");
            return;
        }

        String fecha = JOptionPane.showInputDialog(
                ventana,
                "Fecha (dd/mm/aaaa):"
        );
        if (fecha == null || !fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
            JOptionPane.showMessageDialog(ventana, "Fecha inválida.");
            return;
        }

        Rescate r = new Rescate(
                codigo,
                descripcion,
                prioridad,
                "PENDIENTE",
                fecha
        );

        Sistema.agregarRescate(r);

        JOptionPane.showMessageDialog(ventana, "Rescate registrado.");
    }

    static void atenderRescate() {
        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código del rescate:"
        );
        if (codigo == null) return;

        Rescate r = Sistema.buscarRescate(codigo);

        if (r == null) {
            JOptionPane.showMessageDialog(ventana, "Rescate no encontrado.");
            return;
        }

        String codigoAnimal = JOptionPane.showInputDialog(
                ventana,
                "Código del animal vinculado:"
        );
        if (codigoAnimal == null) return;

        Animal animal = Sistema.buscarAnimal(codigoAnimal);

        if (animal == null) {
            JOptionPane.showMessageDialog(ventana, "Animal no encontrado.");
            return;
        }

        r.codigoAnimalVinculado = codigoAnimal;
        r.estado = "ATENDIDO";

        animal.estadoClinico = "EN_TRATAMIENTO";
        animal.estadoAdopcion = "DISPONIBLE";

        JOptionPane.showMessageDialog(
                ventana,
                "Rescate atendido y animal vinculado."
        );
    }

    static void listarRescates() {
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < Sistema.cantidadRescates; i++) {
            Rescate r = Sistema.rescates[i];
            if (r != null) {
                texto.append(r.codigo).append(" | ")
                     .append(r.prioridad).append(" | ")
                     .append(r.estado).append("\n");
            }
        }

        String resultado = texto.length() == 0 ? "No hay rescates registrados." : texto.toString();

        JTextArea area = new JTextArea(resultado);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                ventana,
                new JScrollPane(area),
                "Rescates",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================================
    // MÓDULO DE UBICACIONES
    // ==========================================

    static void moduloUbicaciones() {
        String[] opciones = {
                "Mostrar matriz",
                "Asignar animal",
                "Liberar espacio",
                "Volver"
        };

        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    ventana,
                    "Módulo de Ubicaciones",
                    "Ubicaciones",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (opcion == 0) mostrarMatriz();
            if (opcion == 1) asignarAnimal();
            if (opcion == 2) liberarEspacio();

        } while (opcion != 3 && opcion != -1);
    }

    static void mostrarMatriz() {
        StringBuilder texto = new StringBuilder("MATRIZ DEL REFUGIO\n\n");

        for (int i = 0; i < Sistema.ubicaciones.length; i++) {
            texto.append("Zona ").append(i).append(": ");

            for (int j = 0; j < Sistema.ubicaciones[i].length; j++) {
                if (Sistema.ubicaciones[i][j] == null || Sistema.ubicaciones[i][j].equals("")) {
                    texto.append("[LIBRE] ");
                } else {
                    texto.append("[").append(Sistema.ubicaciones[i][j]).append("] ");
                }
            }

            texto.append("\n");
        }

        JOptionPane.showMessageDialog(
                ventana,
                texto.toString()
        );
    }

    static void asignarAnimal() {
        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código del animal:"
        );
        if (codigo == null) return;

        Animal animal = Sistema.buscarAnimal(codigo);

        if (animal == null) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Animal no encontrado."
            );
            return;
        }

        String filaStr = JOptionPane.showInputDialog(ventana, "Fila (0-3):");
        if (filaStr == null) return;

        String colStr = JOptionPane.showInputDialog(ventana, "Columna (0-7):");
        if (colStr == null) return;

        int fila, columna;
        try {
            fila = Integer.parseInt(filaStr);
            columna = Integer.parseInt(colStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Posición inválida.");
            return;
        }

        if (fila < 0 || fila >= 4 || columna < 0 || columna >= 8) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Posición inválida."
            );
            return;
        }

        if (Sistema.ubicaciones[fila][columna] != null && !Sistema.ubicaciones[fila][columna].equals("")) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Ese espacio ya está ocupado."
            );
            return;
        }

        Sistema.ubicaciones[fila][columna] = codigo;

        JOptionPane.showMessageDialog(
                ventana,
                "Animal asignado correctamente."
        );
    }

    static void liberarEspacio() {
        String filaStr = JOptionPane.showInputDialog(ventana, "Fila (0-3):");
        if (filaStr == null) return;

        String colStr = JOptionPane.showInputDialog(ventana, "Columna (0-7):");
        if (colStr == null) return;

        int fila, columna;
        try {
            fila = Integer.parseInt(filaStr);
            columna = Integer.parseInt(colStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Posición inválida.");
            return;
        }

        if (fila < 0 || fila >= 4 || columna < 0 || columna >= 8) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Posición inválida."
            );
            return;
        }

        if (Sistema.ubicaciones[fila][columna] == null || Sistema.ubicaciones[fila][columna].equals("")) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "El espacio ya está libre."
            );
            return;
        }

        Sistema.ubicaciones[fila][columna] = "";

        JOptionPane.showMessageDialog(
                ventana,
                "Espacio liberado."
        );
    }
}