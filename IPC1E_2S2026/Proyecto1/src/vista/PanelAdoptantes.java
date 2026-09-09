package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import IPC1E_2S2026.Proyecto1.src.modelo.Adoptante;
import IPC1E_2S2026.Proyecto1.src.servicio.AdoptanteService;

public class PanelAdoptantes extends JPanel {

    private AdoptanteService adoptanteService;
    private JTable tablaAdoptantes;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtDpi;
    private JTextField txtTelefono;

    public PanelAdoptantes(AdoptanteService adoptanteService) {
        this.adoptanteService = adoptanteService;
        setLayout(new BorderLayout(10, 10));

        // --- Panel Formulario (Norte) ---
        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Adoptante"));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("DPI:"));
        txtDpi = new JTextField();
        panelFormulario.add(txtDpi);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        JButton btnAgregar = new JButton("Registrar Adoptante");
        panelFormulario.add(btnAgregar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- Tabla de Datos (Centro) ---
        String[] columnas = {"Código", "Nombre", "DPI", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaAdoptantes = new JTable(modeloTabla);
        add(new JScrollPane(tablaAdoptantes), BorderLayout.CENTER);

        // --- Evento del Botón ---
        btnAgregar.addActionListener(e -> registrarAdoptante());

        // Cargar registros existentes
        actualizarTabla();
    }

    private void registrarAdoptante() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String dpi = txtDpi.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || dpi.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Adoptante nuevo = new Adoptante(codigo, nombre, dpi, telefono);
        boolean exito = adoptanteService.registrarAdoptante(nuevo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Adoptante registrado exitosamente.");
            limpiarCampos();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "El código o DPI del adoptante ya existe o la capacidad está llena.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Adoptante[] lista = adoptanteService.getAdoptantes();
        for (Adoptante a : lista) {
            Object[] fila = {
                a.getCodigo(),
                a.getNombre(),
                a.getDpi(),
                a.getTelefono()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDpi.setText("");
        txtTelefono.setText("");
    }
}