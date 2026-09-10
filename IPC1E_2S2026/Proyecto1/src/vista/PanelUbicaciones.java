package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import IPC1E_2S2026.Proyecto1.src.servicio.UbicacionService;

public class PanelUbicaciones extends JPanel {

    private UbicacionService ubicacionService;
    private JPanel panelMatriz;
    private JTextField txtFila;
    private JTextField txtColumna;
    private JTextField txtCodigoAnimal;

    public PanelUbicaciones(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
        setLayout(new BorderLayout(10, 10));

        // --- Panel Formulario Asignación (Norte) ---
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Asignar Animal a Jaula"));

        panelFormulario.add(new JLabel("Fila (0-4):"));
        txtFila = new JTextField(3);
        panelFormulario.add(txtFila);

        panelFormulario.add(new JLabel("Columna (0-4):"));
        txtColumna = new JTextField(3);
        panelFormulario.add(txtColumna);

        panelFormulario.add(new JLabel("Código Animal:"));
        txtCodigoAnimal = new JTextField(8);
        panelFormulario.add(txtCodigoAnimal);

        JButton btnAsignar = new JButton("Asignar");
        panelFormulario.add(btnAsignar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- Panel Cuadrícula Mapa (Centro) ---
        panelMatriz = new JPanel(new GridLayout(5, 5, 5, 5));
        panelMatriz.setBorder(BorderFactory.createTitledBorder("Mapa de Jaulas del Refugio (5x5)"));
        add(panelMatriz, BorderLayout.CENTER);

        // --- Evento del Botón ---
        btnAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarUbicacion();
            }
        });

        // Dibujar estado inicial del mapa
        actualizarMapa();
    }

    private void asignarUbicacion() {
        try {
            int fila = Integer.parseInt(txtFila.getText().trim());
            int col = Integer.parseInt(txtColumna.getText().trim());
            String codigo = txtCodigoAnimal.getText().trim();

            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el código del animal.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean exito = ubicacionService.asignarUbicacion(fila, col, codigo);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Ubicación asignada correctamente.");
                txtFila.setText("");
                txtColumna.setText("");
                txtCodigoAnimal.setText("");
                actualizarMapa();
            } else {
                JOptionPane.showMessageDialog(this, "Coordenadas fuera de rango o casilla ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Las coordenadas deben ser números enteros válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarMapa() {
        panelMatriz.removeAll();
        String[][] mapa = ubicacionService.getMapaRefugio();

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                String val = mapa[i][j];
                
                // Determinar si la celda está libre
                boolean esLibre = (val == null || val.trim().isEmpty() || val.equalsIgnoreCase("LIBRE"));
                
                // Texto limpio sin coordenadas cuando está libre
                String textoBoton = esLibre ? "Libre" : val;
                JButton btnCasilla = new JButton(textoBoton);
                
                // Forzar a Swing a renderizar los colores de fondo correctamente
                btnCasilla.setOpaque(true);
                btnCasilla.setContentAreaFilled(true);
                btnCasilla.setBorderPainted(false);

                // Asignar colores: Verde si está libre, Rojo si está ocupado
                if (esLibre) {
                    btnCasilla.setBackground(Color.GREEN);
                    btnCasilla.setForeground(Color.BLACK);
                } else {
                    btnCasilla.setBackground(Color.RED);
                    btnCasilla.setForeground(Color.WHITE);
                }
                
                panelMatriz.add(btnCasilla);
            }
        }
        panelMatriz.revalidate();
        panelMatriz.repaint();
    }
}