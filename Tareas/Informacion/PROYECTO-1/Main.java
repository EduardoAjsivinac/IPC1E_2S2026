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
}

public class Main {

    public static void main(String[] args) {

        Sistema.iniciar();

        SwingUtilities.invokeLater(() -> {

            JFrame ventana = new JFrame("Centro de Rescate Animal");

            ventana.setSize(600, 400);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);

            JPanel panel = new JPanel();

            JLabel titulo = new JLabel(
                    "CENTRO DE RESCATE ANIMAL"
            );

            JButton iniciar = new JButton("Iniciar");

            iniciar.addActionListener(e -> {

                JOptionPane.showMessageDialog(
                        ventana,
                        "Sistema iniciado correctamente."
                );
            });

            panel.add(titulo);
            panel.add(iniciar);

            ventana.add(panel);
            ventana.setVisible(true);
        });
    }
}