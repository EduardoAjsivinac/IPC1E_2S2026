package IPC1E_2S2026.Proyecto1.src.vista;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMensaje;

    private int intentosFallidos = 0;
    private boolean autenticado = false;
    private String rolUsuario = ""; // "ADMIN" o "AUXILIAR"

    public LoginDialog(Frame parent) {
        super(parent, "Inicio de Sesión - Centro de Rescate", true);
        setSize(380, 220);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelForm.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelForm.add(txtUsuario);

        panelForm.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelForm.add(txtPassword);

        btnLogin = new JButton("Iniciar Sesión");
        panelForm.add(new JLabel()); // Espacio vacío
        panelForm.add(btnLogin);

        add(panelForm, BorderLayout.CENTER);

        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        add(lblMensaje, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validaciones de formato
        if (usuario.length() < 4 || usuario.length() > 15 || usuario.contains(" ")) {
            lblMensaje.setText("Usuario inválido (4-15 caracteres alfanuméricos).");
            registrarIntentoFallido();
            return;
        }

        if (password.length() < 6) {
            lblMensaje.setText("La contraseña debe tener al menos 6 caracteres.");
            registrarIntentoFallido();
            return;
        }

        // Credenciales predefinidas de prueba
        if (usuario.equalsIgnoreCase("admin1") && password.equals("Refugio2026")) {
            autenticado = true;
            rolUsuario = "ADMIN";
            dispose();
        } else if (usuario.equalsIgnoreCase("auxiliar1") && password.equals("Refugio2026")) {
            autenticado = true;
            rolUsuario = "AUXILIAR";
            dispose();
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
            registrarIntentoFallido();
        }
    }

    private void registrarIntentoFallido() {
        intentosFallidos++;
        if (intentosFallidos >= 3) {
            btnLogin.setEnabled(false);
            txtUsuario.setEnabled(false);
            txtPassword.setEnabled(false);
            lblMensaje.setText("Sesión bloqueada, reinicie la aplicación.");
        }
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }
}