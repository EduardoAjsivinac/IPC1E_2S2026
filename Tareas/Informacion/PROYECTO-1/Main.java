import java.awt.*;
import javax.swing.*;

class Animal {

    String codigo;
    String nombre;
    String especie;
    int edad;
    String estadoClinico;
    String estadoAdopcion;

    public Animal(String codigo, String nombre, String especie, int edad,
                  String estadoClinico, String estadoAdopcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.estadoClinico = estadoClinico;
        this.estadoAdopcion = estadoAdopcion;
    }
}

class Adoptante {

    String codigo;
    String nombre;
    String dpi;
    String telefono;

    public Adoptante(String codigo, String nombre, String dpi, String telefono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dpi = dpi;
        this.telefono = telefono;
    }
}

class Solicitud {

    String codigo;
    String codigoAnimal;
    String codigoAdoptante;
    String fecha;
    String estado;

    public Solicitud(String codigo, String codigoAnimal,
                     String codigoAdoptante, String fecha, String estado) {
        this.codigo = codigo;
        this.codigoAnimal = codigoAnimal;
        this.codigoAdoptante = codigoAdoptante;
        this.fecha = fecha;
        this.estado = estado;
    }
}

class Rescate {

    String codigo;
    String descripcion;
    String prioridad;
    String estado;
    String fecha;
    String codigoAnimalVinculado;

    public Rescate(String codigo, String descripcion, String prioridad,
                   String estado, String fecha) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fecha = fecha;
        this.codigoAnimalVinculado = "";
    }
}

class Usuario {

    String usuario;
    String contrasena;
    String rol;

    public Usuario(String usuario, String contrasena, String rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }
}

class Sistema {

    static final int MAX_ANIMALES = 100;
    static final int MAX_ADOPTANTES = 100;
    static final int MAX_SOLICITUDES = 100;
    static final int MAX_RESCATES = 100;
    static final int MAX_USUARIOS = 10;

    static Animal[] animales = new Animal[MAX_ANIMALES];
    static Adoptante[] adoptantes = new Adoptante[MAX_ADOPTANTES];
    static Solicitud[] solicitudes = new Solicitud[MAX_SOLICITUDES];
    static Rescate[] rescates = new Rescate[MAX_RESCATES];
    static Usuario[] usuarios = new Usuario[MAX_USUARIOS];

    static int cantidadAnimales = 0;
    static int cantidadAdoptantes = 0;
    static int cantidadSolicitudes = 0;
    static int cantidadRescates = 0;
    static int cantidadUsuarios = 0;

    static String[][] ubicaciones = new String[4][8];

    static Usuario usuarioActual;

    public static void iniciar() {

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

    static boolean codigoAnimalExiste(String codigo) {

        for (int i = 0; i < cantidadAnimales; i++) {
            if (animales[i].codigo.equals(codigo)) {
                return true;
            }
        }

        return false;
    }

    static boolean dpiExiste(String dpi) {

        for (int i = 0; i < cantidadAdoptantes; i++) {
            if (adoptantes[i].dpi.equals(dpi)) {
                return true;
            }
        }

        return false;
    }

    static boolean codigoAdoptanteExiste(String codigo) {

        for (int i = 0; i < cantidadAdoptantes; i++) {
            if (adoptantes[i].codigo.equals(codigo)) {
                return true;
            }
        }

        return false;
    }

    static void agregarAnimal(Animal animal) {

        if (cantidadAnimales < MAX_ANIMALES) {
            animales[cantidadAnimales] = animal;
            cantidadAnimales++;
        }
    }

    static void agregarAdoptante(Adoptante adoptante) {

        if (cantidadAdoptantes < MAX_ADOPTANTES) {
            adoptantes[cantidadAdoptantes] = adoptante;
            cantidadAdoptantes++;
        }
    }

    static Animal buscarAnimal(String codigo) {

        for (int i = 0; i < cantidadAnimales; i++) {

            if (animales[i].codigo.equals(codigo)) {
                return animales[i];
            }
        }

        return null;
    }

    static Adoptante buscarAdoptante(String codigo) {

        for (int i = 0; i < cantidadAdoptantes; i++) {

            if (adoptantes[i].codigo.equals(codigo)) {
                return adoptantes[i];
            }
        }

        return null;
    }
}

public class Main {

    static JFrame ventana;

    public static void main(String[] args) {

        Sistema.iniciar();

        SwingUtilities.invokeLater(() -> mostrarMenu());
    }

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

        solicitudes.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        ventana,
                        "Este módulo estará disponible en el Commit 3."
                )
        );

        rescates.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        ventana,
                        "Este módulo estará disponible en el Commit 3."
                )
        );

        ubicaciones.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        ventana,
                        "Este módulo estará disponible en el Commit 4."
                )
        );

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

        String codigo = JOptionPane.showInputDialog(
                ventana, "Código (ej. A-014):"
        );

        if (codigo == null) return;

        if (!codigo.matches("A-[0-9]{3}")) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Código inválido."
            );
            return;
        }

        if (Sistema.codigoAnimalExiste(codigo)) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Ese código ya existe."
            );
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                ventana, "Nombre:"
        );

        if (nombre == null) return;

        String especie = JOptionPane.showInputDialog(
                ventana, "Especie (Perro/Gato):"
        );

        if (especie == null) return;

        if (!especie.equals("Perro") &&
                !especie.equals("Gato")) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "La especie debe ser Perro o Gato."
            );
            return;
        }

        String edadTexto = JOptionPane.showInputDialog(
                ventana, "Edad estimada:"
        );

        if (edadTexto == null) return;

        int edad;

        try {
            edad = Integer.parseInt(edadTexto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "Edad inválida."
            );
            return;
        }

        if (edad < 0 || edad > 25) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "La edad debe estar entre 0 y 25."
            );
            return;
        }

        String estado = JOptionPane.showInputDialog(
                ventana,
                "Estado clínico:\nEN_OBSERVACION\nEN_TRATAMIENTO\nAPTO"
        );

        if (estado == null) return;

        if (!estado.equals("EN_OBSERVACION") &&
                !estado.equals("EN_TRATAMIENTO") &&
                !estado.equals("APTO")) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Estado inválido."
            );
            return;
        }

        Animal animal = new Animal(
                codigo,
                nombre,
                especie,
                edad,
                estado,
                "DISPONIBLE"
        );

        Sistema.agregarAnimal(animal);

        JOptionPane.showMessageDialog(
                ventana,
                "Animal registrado correctamente."
        );
    }

    static void buscarAnimal() {

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

        String texto = "";

        for (int i = 0; i < Sistema.cantidadAnimales; i++) {

            Animal a = Sistema.animales[i];

            if (!a.estadoAdopcion.equals("ELIMINADO")) {

                texto += a.codigo + " | "
                        + a.nombre + " | "
                        + a.especie + " | "
                        + a.estadoAdopcion + "\n";
            }
        }

        if (texto.equals("")) {
            texto = "No hay animales registrados.";
        }

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                ventana,
                new JScrollPane(area),
                "Animales",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    static void editarAnimal() {

        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código:"
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

        String estado = JOptionPane.showInputDialog(
                ventana,
                "Nuevo estado clínico:\nEN_OBSERVACION\nEN_TRATAMIENTO\nAPTO"
        );

        if (estado == null) return;

        if (estado.equals("EN_OBSERVACION") ||
                estado.equals("EN_TRATAMIENTO") ||
                estado.equals("APTO")) {

            animal.estadoClinico = estado;

            JOptionPane.showMessageDialog(
                    ventana,
                    "Estado actualizado."
            );

        } else {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Estado inválido."
            );
        }
    }

    static void eliminarAnimal() {

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

        animal.estadoAdopcion = "ELIMINADO";

        JOptionPane.showMessageDialog(
                ventana,
                "Animal dado de baja correctamente."
        );
    }

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

        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código (ej. AD-007):"
        );

        if (codigo == null ||
                !codigo.matches("AD-[0-9]{3}")) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Código inválido."
            );
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                ventana,
                "Nombre:"
        );

        if (nombre == null ||
                !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Nombre inválido."
            );
            return;
        }

        String dpi = JOptionPane.showInputDialog(
                ventana,
                "DPI:"
        );

        if (dpi == null ||
                !dpi.matches("[0-9]{13}")) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "El DPI debe tener 13 dígitos."
            );
            return;
        }

        if (Sistema.dpiExiste(dpi)) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Ese DPI ya existe."
            );
            return;
        }

        String telefono = JOptionPane.showInputDialog(
                ventana,
                "Teléfono:"
        );

        if (telefono == null ||
                !telefono.matches("[0-9]{8}")) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "El teléfono debe tener 8 dígitos."
            );
            return;
        }

        Adoptante adoptante =
                new Adoptante(codigo, nombre, dpi, telefono);

        Sistema.agregarAdoptante(adoptante);

        JOptionPane.showMessageDialog(
                ventana,
                "Adoptante registrado correctamente."
        );
    }

    static void buscarAdoptante() {

        String codigo = JOptionPane.showInputDialog(
                ventana,
                "Código:"
        );

        if (codigo == null) return;

        Adoptante a = Sistema.buscarAdoptante(codigo);

        if (a == null) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Adoptante no encontrado."
            );

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

        String texto = "";

        for (int i = 0;
             i < Sistema.cantidadAdoptantes;
             i++) {

            Adoptante a = Sistema.adoptantes[i];

            texto += a.codigo + " | "
                    + a.nombre + " | "
                    + a.dpi + " | "
                    + a.telefono + "\n";
        }

        if (texto.equals("")) {
            texto = "No hay adoptantes.";
        }

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                ventana,
                new JScrollPane(area),
                "Adoptantes",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}