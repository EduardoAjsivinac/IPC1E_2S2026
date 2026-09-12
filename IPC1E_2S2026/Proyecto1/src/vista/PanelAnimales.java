package IPC1E_2S2026.Proyecto1.src.vista;

import IPC1E_2S2026.Proyecto1.src.modelo.Animal;
import IPC1E_2S2026.Proyecto1.src.servicio.AnimalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAnimales extends JPanel {

    private JTextField txtCodigo;
    private JTextField txtEspecie;
    private JTextField txtEdad;
    private JComboBox<String> cbEstadoClinico;
    private JComboBox<String> cbEstadoAdopcion;
    private JTable tablaAnimales;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar;
    private JButton btnApto;
    private JButton btnEliminar;

    private AnimalService animalService;

    public PanelAnimales(AnimalService animalService) {
        this.animalService = animalService;
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: FORMULARIO DE INGRESO ---
        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Animal"));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Especie:"));
        txtEspecie = new JTextField();
        panelFormulario.add(txtEspecie);

        panelFormulario.add(new JLabel("Edad (años):"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Estado Clínico:"));
        String[] opcionesClinico = { "EN_OBSERVACION", "EN_TRATAMIENTO", "APTO" };
        cbEstadoClinico = new JComboBox<>(opcionesClinico);
        panelFormulario.add(cbEstadoClinico);

        panelFormulario.add(new JLabel("Estado Adopción:"));
        String[] opcionesAdopcion = { "DISPONIBLE", "NO_DISPONIBLE", "ADOPTADO" };
        cbEstadoAdopcion = new JComboBox<>(opcionesAdopcion);
        panelFormulario.add(cbEstadoAdopcion);

        btnRegistrar = new JButton("Registrar Animal");
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA DE DATOS ---
        String[] columnas = { "Código", "Especie", "Edad", "Estado Clínico", "Estado Adopción" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };

        tablaAnimales = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaAnimales);
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR: BOTONES DE ACCIÓN (Apto y Eliminar) ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnApto = new JButton("Cambiar a APTO");
        btnEliminar = new JButton("Eliminar Animal");

        panelAcciones.add(btnApto);
        panelAcciones.add(btnEliminar);
        add(panelAcciones, BorderLayout.SOUTH);

        // --- EVENTOS ---
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAnimal();
            }
        });

        btnApto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarEstadoApto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarAnimalSeleccionado();
            }
        });

        actualizarTabla();
    }

    private void registrarAnimal() {
        String codigo = txtCodigo.getText().trim();
        String especie = txtEspecie.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String estadoClinico = (String) cbEstadoClinico.getSelectedItem();
        String estadoAdopcion = (String) cbEstadoAdopcion.getSelectedItem();

        if (codigo.isEmpty() || especie.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            if (edad < 0) {
                JOptionPane.showMessageDialog(this, "La edad no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Animal nuevoAnimal = new Animal(codigo, especie, edad, estadoClinico, estadoAdopcion);
            boolean exito = animalService.agregarAnimal(nuevoAnimal);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Animal registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el animal (Código duplicado o espacio lleno).", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarEstadoApto() {
        int filaSeleccionada = tablaAnimales.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un animal de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean exito = animalService.actualizarEstadoClinico(codigo, "APTO");

        if (exito) {
            JOptionPane.showMessageDialog(this, "El animal " + codigo + " ahora está APTO y DISPONIBLE.");
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarAnimalSeleccionado() {
        int filaSeleccionada = tablaAnimales.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un animal de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean exito = animalService.eliminarAnimal(codigo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Animal eliminado y celda liberada automáticamente.");
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el animal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtEspecie.setText("");
        txtEdad.setText("");
        cbEstadoClinico.setSelectedIndex(0);
        cbEstadoAdopcion.setSelectedIndex(0);
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar filas anteriores
        Animal[] animales = animalService.getAnimales();
        int total = animalService.getContador();

        for (int i = 0; i < total; i++) {
            Animal a = animales[i];
            Object[] fila = {
                a.getCodigo(),
                a.getEspecie(),
                a.getEdad(),
                a.getEstadoClinico(),
                a.getEstadoAdopcion()
            };
            modeloTabla.addRow(fila);
        }
    }
}