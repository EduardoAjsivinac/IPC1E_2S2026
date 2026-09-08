import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    Sistema sistema =
            new Sistema();

    JTextField usuario =
            new JTextField(15);

    JPasswordField clave =
            new JPasswordField(15);

    JButton entrar =
            new JButton(
                    "Iniciar sesión"
            );

    int intentos = 0;

    public LoginFrame() {

        setTitle(
                "Centro de Rescate Animal - Inicio de sesión"
        );

        setSize(
                430,
                260
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );


        JPanel p =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints c =
                new GridBagConstraints();

        c.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );


        c.gridx = 0;
        c.gridy = 0;

        p.add(
                new JLabel("Usuario:"),
                c
        );


        c.gridx = 1;

        p.add(
                usuario,
                c
        );


        c.gridx = 0;
        c.gridy = 1;

        p.add(
                new JLabel("Contraseña:"),
                c
        );


        c.gridx = 1;

        p.add(
                clave,
                c
        );


        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;

        p.add(
                entrar,
                c
        );


        c.gridy = 3;

        p.add(
                new JLabel(
                        "ADMIN: admin1 / Refugio2026"
                ),
                c
        );


        c.gridy = 4;

        p.add(
                new JLabel(
                        "AUXILIAR: auxiliar1 / Auxiliar2026"
                ),
                c
        );


        add(p);


        entrar.addActionListener(
                e -> login()
        );

        setVisible(true);
    }


    void login() {

        if (intentos >= 3) {
            return;
        }


        Usuario u =
                sistema.autenticar(
                        usuario.getText().trim(),
                        new String(
                                clave.getPassword()
                        )
                );


        if (u != null) {

            sistema.usuarioActual =
                    u.usuario;

            sistema.rolActual =
                    u.rol;


            Bitacora.accion(
                    u.usuario,
                    "AUTENTICACION",
                    "LOGIN_OK",
                    "Inicio de sesión correcto"
            );


            new MainFrame(
                    sistema
            ).setVisible(true);

            dispose();

        } else {

            intentos++;

            Bitacora.error(
                    usuario.getText().trim(),
                    "AUTENTICACION",
                    "LOGIN_FALLIDO",
                    "Contraseña o usuario incorrecto (intento "
                            + intentos
                            + " de 3)"
            );


            if (intentos >= 3) {

                entrar.setEnabled(false);

                JOptionPane.showMessageDialog(
                        this,
                        "Sesión bloqueada, reinicie la aplicación"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Datos incorrectos. Intento "
                                + intentos
                                + " de 3"
                );
            }
        }
    }
}