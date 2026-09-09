package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import java.awt.*;

import IPC1E_2S2026.Proyecto1.src.servicio.AnimalService;
import IPC1E_2S2026.Proyecto1.src.servicio.AdoptanteService;
import IPC1E_2S2026.Proyecto1.src.servicio.SolicitudService;
import IPC1E_2S2026.Proyecto1.src.servicio.RescateService;
import IPC1E_2S2026.Proyecto1.src.servicio.UbicacionService;
import IPC1E_2S2026.Proyecto1.src.servicio.ReporteService;
import IPC1E_2S2026.Proyecto1.src.persistencia.ArchivoManager;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContenedor;

    // Servicios
    private AnimalService animalService;
    private AdoptanteService adoptanteService;
    private SolicitudService solicitudService;
    private RescateService rescateService;
    private UbicacionService ubicacionService;

    // Rol de sesión activa
    private String rolUsuario = "";

    public VentanaPrincipal() {
        setTitle("Sistema Centro de Rescate Animal");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialización de servicios backend
        this.animalService = new AnimalService();
        this.adoptanteService = new AdoptanteService();
        this.solicitudService = new SolicitudService();
        this.rescateService = new RescateService();
        this.ubicacionService = new UbicacionService(5, 5);

        // Carga inicial de datos desde archivos planos
        cargarDatosDesdeArchivos();

        // Configuración de interfaz Swing con CardLayout
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Panel de Bienvenida
        JPanel panelBienvenida = crearPanelBienvenida();

        // Vistas/Paneles
        PanelAnimales panelAnimales = new PanelAnimales(animalService);
        PanelAdoptantes panelAdoptantes = new PanelAdoptantes(adoptanteService);
        PanelSolicitudes panelSolicitudes = new PanelSolicitudes(solicitudService);
        PanelRescates panelRescates = new PanelRescates(rescateService, animalService);
        PanelUbicaciones panelUbicaciones = new PanelUbicaciones(ubicacionService);

        // Agregar paneles al contenedor principal
        panelContenedor.add(panelBienvenida, "Bienvenida");
        panelContenedor.add(panelAnimales, "Animales");
        panelContenedor.add(panelAdoptantes, "Adoptantes");
        panelContenedor.add(panelSolicitudes, "Solicitudes");
        panelContenedor.add(panelRescates, "Rescates");
        panelContenedor.add(panelUbicaciones, "Ubicaciones");

        add(panelContenedor, BorderLayout.CENTER);

        // Menú superior de navegación
        crearBarraMenu();

        // Guardar automáticamente datos al cerrar la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                guardarDatosEnArchivos();
            }
        });
    }

    public void setRolUsuario(String rol) {
        this.rolUsuario = rol;
        setTitle("Sistema Centro de Rescate Animal - Rol: " + rol);
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Bienvenido al Centro de Rescate Animal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSubtitulo = new JLabel("Seleccione un módulo en la barra de menú superior para empezar.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        panel.add(lblSubtitulo, gbc);

        return panel;
    }

    private void crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo (Cargar / Guardar / Inicio)
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemInicio = new JMenuItem("Inicio / Bienvenida");
        JMenuItem itemCargar = new JMenuItem("Cargar Animales desde Archivo");
        JMenuItem itemGuardar = new JMenuItem("Guardar Animales en Archivo");

        itemInicio.addActionListener(e -> cardLayout.show(panelContenedor, "Bienvenida"));
        
        itemCargar.addActionListener(e -> {
            cargarDatosDesdeArchivos();
            JOptionPane.showMessageDialog(this, "Datos de animales cargados correctamente.");
        });

        itemGuardar.addActionListener(e -> {
            guardarDatosEnArchivos();
            JOptionPane.showMessageDialog(this, "Datos de animales guardados correctamente.");
        });

        menuArchivo.add(itemInicio);
        menuArchivo.addSeparator();
        menuArchivo.add(itemCargar);
        menuArchivo.add(itemGuardar);

        // Menú Módulos
        JMenu menuModulos = new JMenu("Módulos");
        
        JMenuItem itemAnimales = new JMenuItem("Gestión de Animales");
        JMenuItem itemAdoptantes = new JMenuItem("Gestión de Adoptantes");
        JMenuItem itemSolicitudes = new JMenuItem("Solicitudes de Adopción");
        JMenuItem itemRescates = new JMenuItem("Rescates Urgentes");
        JMenuItem itemUbicaciones = new JMenuItem("Mapa del Refugio");

        itemAnimales.addActionListener(e -> cardLayout.show(panelContenedor, "Animales"));
        itemAdoptantes.addActionListener(e -> cardLayout.show(panelContenedor, "Adoptantes"));
        itemSolicitudes.addActionListener(e -> cardLayout.show(panelContenedor, "Solicitudes"));
        itemRescates.addActionListener(e -> cardLayout.show(panelContenedor, "Rescates"));
        itemUbicaciones.addActionListener(e -> cardLayout.show(panelContenedor, "Ubicaciones"));

        menuModulos.add(itemAnimales);
        menuModulos.add(itemAdoptantes);
        menuModulos.add(itemSolicitudes);
        menuModulos.add(itemRescates);
        menuModulos.add(itemUbicaciones);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteGeneral = new JMenuItem("Generar Reporte HTML Animales");
        
        itemReporteGeneral.addActionListener(e -> {
            boolean exito = ReporteService.generarReporteAnimales("reporte_animales.html", animalService);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Reporte generado exitosamente: reporte_animales.html");
            } else {
                JOptionPane.showMessageDialog(this, "Error al generar el reporte HTML", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        menuReportes.add(itemReporteGeneral);

        menuBar.add(menuArchivo);
        menuBar.add(menuModulos);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }

    private void cargarDatosDesdeArchivos() {
        ArchivoManager.cargarAnimales("animales.txt", animalService);
    }

    private void guardarDatosEnArchivos() {
        ArchivoManager.guardarAnimales("animales.txt", animalService);
    }

    // Punto de entrada principal con flujo de autenticación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            LoginDialog login = new LoginDialog(ventana);
            login.setVisible(true);

            if (login.isAutenticado()) {
                ventana.setRolUsuario(login.getRolUsuario());
                ventana.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}