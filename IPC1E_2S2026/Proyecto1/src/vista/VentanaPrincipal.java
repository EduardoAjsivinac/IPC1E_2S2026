package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import IPC1E_2S2026.Proyecto1.src.servicio.AnimalService;
import IPC1E_2S2026.Proyecto1.src.servicio.AdoptanteService;
import IPC1E_2S2026.Proyecto1.src.servicio.SolicitudService;
import IPC1E_2S2026.Proyecto1.src.servicio.RescateService;
import IPC1E_2S2026.Proyecto1.src.servicio.UbicacionService;
import IPC1E_2S2026.Proyecto1.src.servicio.BitacoraService;
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
    private BitacoraService bitacoraService;

    // Paneles
    private PanelAnimales panelAnimales;
    private PanelAdoptantes panelAdoptantes;
    private PanelSolicitudes panelSolicitudes;
    private PanelRescates panelRescates;
    private PanelUbicaciones panelUbicaciones;

    // Rol de sesión activa
    private String rolUsuario = "";

    public VentanaPrincipal() {
        setTitle("Sistema Centro de Rescate Animal");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Inicialización de servicios backend
        this.animalService = new AnimalService();
        this.adoptanteService = new AdoptanteService();
        this.bitacoraService = new BitacoraService();
        
        // SolicitudService recibe la misma instancia de AnimalService y BitacoraService
        this.solicitudService = new SolicitudService(this.animalService, this.bitacoraService);
        
        this.rescateService = new RescateService();
        this.ubicacionService = new UbicacionService(5, 5);

        // Carga inicial de datos desde archivos planos
        cargarDatosDesdeArchivos();

        // Configuración de interfaz Swing con CardLayout
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Panel de Bienvenida
        JPanel panelBienvenida = crearPanelBienvenida();

        // 2. Inicializar Paneles
        this.panelAnimales = new PanelAnimales(this.animalService);
        this.panelAdoptantes = new PanelAdoptantes(this.adoptanteService);
        this.panelSolicitudes = new PanelSolicitudes(this.solicitudService);
        this.panelRescates = new PanelRescates(this.rescateService, this.animalService);
        this.panelUbicaciones = new PanelUbicaciones(this.ubicacionService);

        // Agregar paneles al contenedor principal
        panelContenedor.add(panelBienvenida, "Bienvenida");
        panelContenedor.add(this.panelAnimales, "Animales");
        panelContenedor.add(this.panelAdoptantes, "Adoptantes");
        panelContenedor.add(this.panelSolicitudes, "Solicitudes");
        panelContenedor.add(this.panelRescates, "Rescates");
        panelContenedor.add(this.panelUbicaciones, "Ubicaciones");

        add(panelContenedor, BorderLayout.CENTER);

        // Menú superior de navegación
        crearBarraMenu();

        // Guardar automáticamente datos al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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

        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemInicio = new JMenuItem("Inicio / Bienvenida");
        JMenuItem itemCargar = new JMenuItem("Cargar Animales desde Archivo");
        JMenuItem itemGuardar = new JMenuItem("Guardar Animales en Archivo");

        itemInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "Bienvenida");
            }
        });

        itemCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosDesdeArchivos();
                panelAnimales.actualizarTabla();
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Datos de animales cargados correctamente.");
            }
        });

        itemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatosEnArchivos();
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Datos de animales guardados correctamente.");
            }
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

        itemAnimales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAnimales.actualizarTabla();
                cardLayout.show(panelContenedor, "Animales");
            }
        });

        itemAdoptantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "Adoptantes");
            }
        });

        itemSolicitudes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSolicitudes.actualizarTabla();
                cardLayout.show(panelContenedor, "Solicitudes");
            }
        });

        itemRescates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "Rescates");
            }
        });

        itemUbicaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "Ubicaciones");
            }
        });

        menuModulos.add(itemAnimales);
        menuModulos.add(itemAdoptantes);
        menuModulos.add(itemSolicitudes);
        menuModulos.add(itemRescates);
        menuModulos.add(itemUbicaciones);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteGeneral = new JMenuItem("Generar Reporte HTML Animales");
        JMenuItem itemReporteBitacora = new JMenuItem("Generar Reportes de Bitácora");

        itemReporteGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exito = ReporteService.generarReporteAnimales("reporte_animales.html", animalService);
                if (exito) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Reporte generado exitosamente: reporte_animales.html");
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al generar el reporte HTML", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        itemReporteBitacora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReporteService reporteService = new ReporteService(bitacoraService);
                reporteService.generarReportesBitacora();
                JOptionPane.showMessageDialog(VentanaPrincipal.this, 
                    "¡Reportes HTML de Acciones y Errores generados con éxito!", 
                    "Bitácora", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuReportes.add(itemReporteGeneral);
        menuReportes.add(itemReporteBitacora);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal();
                LoginDialog login = new LoginDialog(ventana);
                login.setVisible(true);

                if (login.isAutenticado()) {
                    ventana.setRolUsuario(login.getRolUsuario());
                    ventana.setVisible(true);
                } else {
                    System.exit(0);
                }
            }
        });
    }
}