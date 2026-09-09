package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import IPC1E_2S2026.Proyecto1.src.modelo.Animal;
import IPC1E_2S2026.Proyecto1.src.servicio.AnimalService;

public class PanelAnimales extends JPanel {

    private AnimalService animalService;
    private JTable tablaAnimales;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo;
    private JTextField txtEspecie;
    private JTextField txtEdad;
    private JComboBox<String> comboEstadoClinico;
    private JComboBox<String> comboEstadoAdopcion;

    public PanelAnimales(AnimalService animalService) {
        this.animalService = animalService;
        setLayout(new BorderLayout(10, 10));

        // --- Panel Formulario (Norte) ---
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
        comboEstadoClinico = new JComboBox<>(new String[]{"APTO", "EN_TRATAMIENTO", "CUARENTENA"});
        panelFormulario.add(comboEstadoClinico);

        panelFormulario.add(new JLabel("Estado Adopción:"));
        comboEstadoAdopcion = new JComboBox<>(new String[]{"DISPONIBLE", "EN_PROCESO", "ADOPTADO"});
        panelFormulario.add(comboEstadoAdopcion);

        JButton btnAgregar = new JButton("Registrar Animal");
        panelFormulario.add(btnAgregar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- Tabla de Datos (Centro) ---
        String[] columnas = {"Código", "Especie", "Edad", "Estado Clínico", "Estado Adopción"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaAnimales = new JTable(modeloTabla);
        add(new JScrollPane(tablaAnimales), BorderLayout.CENTER);

        // --- Evento del Botón ---
        btnAgregar.addActionListener(e -> registrarAnimal());

        // Cargar registros existentes
        actualizarTabla();
    }

    private void registrarAnimal() {
        try {
            String codigo = txtCodigo.getText().trim();
            String especie = txtEspecie.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            String clinico = (String) comboEstadoClinico.getSelectedItem();
            String adopcion = (String) comboEstadoAdopcion.getSelectedItem();

            if (codigo.isEmpty() || especie.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Animal nuevo = new Animal(codigo, especie, edad, clinico, adopcion);
            boolean exito = animalService.agregarAnimal(nuevo);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Animal registrado exitosamente.");
                limpiarCampos();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "El código del animal ya existe o la capacidad está llena.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar filas anteriores
        Animal[] lista = animalService.getAnimalesActivos();
        for (Animal a : lista) {
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

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtEspecie.setText("");
        txtEdad.setText("");
        comboEstadoClinico.setSelectedIndex(0);
        comboEstadoAdopcion.setSelectedIndex(0);
    }
}