package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import IPC1E_2S2026.Proyecto1.src.servicio.SolicitudService;
import IPC1E_2S2026.Proyecto1.src.modelo.Solicitud;

public class PanelSolicitudes extends JPanel {

    private SolicitudService solicitudService;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigoSolicitud;
    private JTextField txtCodigoAnimal;
    private JTextField txtCodigoAdoptante;
    private JTextField txtFecha;
    private JComboBox<String> cbEstadoInicial;

    public PanelSolicitudes(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 8, 8));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Solicitud"));

        panelForm.add(new JLabel("Código Solicitud:"));
        txtCodigoSolicitud = new JTextField();
        panelForm.add(txtCodigoSolicitud);

        panelForm.add(new JLabel("Código Animal:"));
        txtCodigoAnimal = new JTextField();
        panelForm.add(txtCodigoAnimal);

        panelForm.add(new JLabel("Código Adoptante:"));
        txtCodigoAdoptante = new JTextField();
        panelForm.add(txtCodigoAdoptante);

        panelForm.add(new JLabel("Fecha (DD/MM/AAAA):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);

        panelForm.add(new JLabel("Estado Inicial:"));
        cbEstadoInicial = new JComboBox<>(new String[]{"PENDIENTE", "APROBADA", "RECHAZADA"});
        panelForm.add(cbEstadoInicial);

        JButton btnRegistrar = new JButton("Registrar Solicitud");
        panelForm.add(new JLabel());
        panelForm.add(btnRegistrar);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Código", "Cod. Animal", "Cod. Adoptante", "Fecha", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botón de aprobación
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAprobar = new JButton("Aprobar Solicitud Seleccionada");
        panelAcciones.add(btnAprobar);
        add(panelAcciones, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarSolicitud();
            }
        });

        btnAprobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aprobarSolicitudSeleccionada();
            }
        });

        actualizarTabla();
    }

    private void registrarSolicitud() {
        String codSol = txtCodigoSolicitud.getText().trim();
        String codAni = txtCodigoAnimal.getText().trim();
        String codAdo = txtCodigoAdoptante.getText().trim();
        String fecha = txtFecha.getText().trim();
        String estado = (String) cbEstadoInicial.getSelectedItem();

        if (codSol.isEmpty() || codAni.isEmpty() || codAdo.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = solicitudService.registrarSolicitud(codSol, codAni, codAdo, fecha, estado);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Solicitud registrada correctamente.");
            txtCodigoSolicitud.setText("");
            txtCodigoAnimal.setText("");
            txtCodigoAdoptante.setText("");
            txtFecha.setText("");
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la solicitud o el código ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aprobarSolicitudSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoSolicitud = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String estadoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 4);

        if (!"PENDIENTE".equalsIgnoreCase(estadoActual)) {
            JOptionPane.showMessageDialog(this, "Solo se pueden aprobar solicitudes en estado PENDIENTE.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = solicitudService.aprobarSolicitud(codigoSolicitud);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Solicitud " + codigoSolicitud + " aprobada con éxito.\nEl animal pasa a ADOPTADO y las solicitudes restantes a RECHAZADAS.");
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al procesar la aprobación.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Solicitud[] lista = solicitudService.getSolicitudes();
        if (lista != null) {
            for (Solicitud s : lista) {
                if (s != null) {
                    modeloTabla.addRow(new Object[]{
                        s.getCodigo(),
                        s.getCodigoAnimal(),
                        s.getCodigoAdoptante(),
                        s.getFecha(),
                        s.getEstado()
                    });
                }
            }
        }
    }
}