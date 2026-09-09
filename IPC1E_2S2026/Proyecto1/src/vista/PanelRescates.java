package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import IPC1E_2S2026.Proyecto1.src.servicio.RescateService;
import IPC1E_2S2026.Proyecto1.src.servicio.AnimalService;
import IPC1E_2S2026.Proyecto1.src.modelo.Rescate;

public class PanelRescates extends JPanel {

    private RescateService rescateService;
    private AnimalService animalService;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo;
    private JComboBox<String> cbPrioridad;
    private JComboBox<String> cbEstado;
    private JTextField txtFecha;

    public PanelRescates(RescateService rescateService, AnimalService animalService) {
        this.rescateService = rescateService;
        this.animalService = animalService;
        setLayout(new BorderLayout());

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Rescate Urgente"));

        panelForm.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Prioridad:"));
        cbPrioridad = new JComboBox<>(new String[]{"ALTA", "MEDIA", "BAJA"});
        panelForm.add(cbPrioridad);

        panelForm.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"PENDIENTE", "ATENDIDO"});
        panelForm.add(cbEstado);

        panelForm.add(new JLabel("Fecha (DD/MM/AAAA):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);

        JButton btnGuardar = new JButton("Registrar Reporte");
        JButton btnAtender = new JButton("Atender Caso Seleccionado");

        panelForm.add(btnGuardar);
        panelForm.add(btnAtender);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Código", "Prioridad", "Estado", "Fecha Reporte", "Animal Vinculado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos
        btnGuardar.addActionListener(e -> registrarRescate());
        btnAtender.addActionListener(e -> atenderRescate());

        actualizarTabla();
    }

    private void registrarRescate() {
        String cod = txtCodigo.getText().trim();
        String prio = (String) cbPrioridad.getSelectedItem();
        String est = (String) cbEstado.getSelectedItem();
        String fec = txtFecha.getText().trim();

        // Se registra el rescate tomando el estado seleccionado del JComboBox
        boolean exito = rescateService.registrarRescate(cod, prio, fec, "");
        if (exito) {
            // Si seleccionó ATENDIDO desde el registro, actualiza su estado en el servicio
            if (est.equals("ATENDIDO")) {
                rescateService.atenderRescate(cod, animalService, "Desconocida");
            }
            JOptionPane.showMessageDialog(this, "Rescate registrado exitosamente.");
            txtCodigo.setText("");
            txtFecha.setText("");
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error en los campos. Verifique la fecha (DD/MM/AAAA).", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atenderRescate() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un rescate de la tabla para atender.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoRescate = (String) modeloTabla.getValueAt(fila, 0);
        String especie = JOptionPane.showInputDialog(this, "Ingrese la especie del animal rescatado (ej. Gato, Perro):");

        if (especie != null && !especie.trim().isEmpty()) {
            boolean exito = rescateService.atenderRescate(codigoRescate, animalService, especie.trim());
            if (exito) {
                JOptionPane.showMessageDialog(this, "Caso atendido. Se ha actualizado/creado el registro en el módulo de Animales.");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "El caso ya se encuentra en estado ATENDIDO.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Rescate[] lista = rescateService.getRescatesOrdenados();
        for (Rescate r : lista) {
            modeloTabla.addRow(new Object[]{
                r.getCodigo(),
                r.getPrioridad(),
                r.getEstado(),
                r.getFecha(),
                r.getCodigoAnimalVinculado() != null && !r.getCodigoAnimalVinculado().trim().isEmpty() ? r.getCodigoAnimalVinculado() : "N/A"
            });
        }
    }
}