import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame {

    Sistema s;

    JTabbedPane tabs =
            new JTabbedPane();

    JTable tablaAnimales =
            new JTable();

    JTable tablaAdoptantes =
            new JTable();

    JTable tablaSolicitudes =
            new JTable();

    JTable tablaRescates =
            new JTable();

    JTable tablaMatriz =
            new JTable();


    String[] encabezados = {
            "Código",
            "Nombre",
            "Especie",
            "Edad",
            "Estado clínico",
            "Estado adopción"
    };


    public MainFrame(
            Sistema sistema) {

        s = sistema;


        setTitle(
                "Centro de Rescate Animal | "
                        + s.usuarioActual
                        + " ("
                        + s.rolActual
                        + ")"
        );


        setSize(
                1050,
                680
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );


        setJMenuBar(
                menu()
        );


        tabs.addTab(
                "Animales",
                panelAnimales()
        );

        tabs.addTab(
                "Adoptantes",
                panelAdoptantes()
        );

        tabs.addTab(
                "Solicitudes",
                panelSolicitudes()
        );

        tabs.addTab(
                "Rescates",
                panelRescates()
        );

        tabs.addTab(
                "Ubicaciones",
                panelUbicaciones()
        );

        tabs.addTab(
                "Reportes",
                panelReportes()
        );

        tabs.addTab(
                "Estudiante",
                panelEstudiante()
        );


        add(tabs);


        refrescarTodo();
    }


    JMenuBar menu() {

        JMenuBar mb =
                new JMenuBar();

        JMenu archivo =
                new JMenu("Sesión");

        JMenuItem salir =
                new JMenuItem(
                        "Cerrar sesión"
                );


        salir.addActionListener(
                e -> {

                    Bitacora.accion(
                            s.usuarioActual,
                            "AUTENTICACION",
                            "LOGOUT",
                            "Cierre de sesión"
                    );

                    dispose();

                    new LoginFrame();
                }
        );


        archivo.add(salir);

        mb.add(archivo);

        return mb;
    }


    JPanel base() {

        JPanel p =
                new JPanel(
                        new BorderLayout(
                                8,
                                8
                        )
                );


        p.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        return p;
    }


    JButton b(String t) {

        return new JButton(t);
    }


    void msg(String m) {

        JOptionPane.showMessageDialog(
                this,
                m
        );
    }


    boolean ok(
            boolean x,
            String bien,
            String mal) {

        if (x) {

            msg(bien);

        } else {

            Bitacora.error(
                    s.usuarioActual,
                    "SISTEMA",
                    "VALIDACION",
                    mal
            );

            msg(mal);
        }


        return x;
    }


    JPanel panelAnimales() {

        JPanel p = base();


        JPanel norte =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );


        JPanel f =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField codigo =
                new JTextField(7);

        JTextField nombre =
                new JTextField(10);

        JTextField edad =
                new JTextField(3);


        JComboBox<String> especie =
                new JComboBox<>(
                        new String[]{
                                "Perro",
                                "Gato"
                        }
                );


        JComboBox<String> clinico =
                new JComboBox<>(
                        new String[]{
                                "EN_OBSERVACION",
                                "EN_TRATAMIENTO",
                                "APTO"
                        }
                );


        JComboBox<String> adop =
                new JComboBox<>(
                        new String[]{
                                "DISPONIBLE",
                                "ADOPTADO",
                                "ELIMINADO"
                        }
                );


        f.add(
                new JLabel("Código")
        );

        f.add(codigo);


        f.add(
                new JLabel("Nombre")
        );

        f.add(nombre);


        f.add(
                new JLabel("Especie")
        );

        f.add(especie);


        f.add(
                new JLabel("Edad")
        );

        f.add(edad);


        f.add(
                new JLabel("Clínico")
        );

        f.add(clinico);


        f.add(
                new JLabel("Adopción")
        );

        f.add(adop);


        JButton agregar =
                b("Registrar");

        JButton editar =
                b("Editar");

        JButton eliminar =
                b("Eliminar");


        f.add(agregar);
        f.add(editar);
        f.add(eliminar);


        JPanel bus =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField texto =
                new JTextField(15);


        JComboBox<String> criterio =
                new JComboBox<>(
                        new String[]{
                                "Código",
                                "Nombre",
                                "Especie",
                                "Estado"
                        }
                );


        JButton buscar =
                b("Buscar");

        JButton todos =
                b("Mostrar todos");


        bus.add(
                new JLabel("Buscar por:")
        );

        bus.add(criterio);
        bus.add(texto);
        bus.add(buscar);
        bus.add(todos);


        norte.add(f);
        norte.add(bus);


        p.add(
                norte,
                BorderLayout.NORTH
        );


        p.add(
                new JScrollPane(
                        tablaAnimales
                ),
                BorderLayout.CENTER
        );


        agregar.addActionListener(
                e -> {

                    try {

                        boolean x =
                                s.registrarAnimal(
                                        codigo.getText().trim(),
                                        nombre.getText().trim(),
                                        (String)
                                                especie.getSelectedItem(),
                                        Integer.parseInt(
                                                edad.getText().trim()
                                        ),
                                        (String)
                                                clinico.getSelectedItem(),
                                        (String)
                                                adop.getSelectedItem()
                                );


                        ok(
                                x,
                                "Animal registrado",
                                "Datos inválidos, código duplicado o capacidad de registros llena"
                        );


                        refrescarAnimales();

                    } catch (Exception ex) {

                        msg(
                                "La edad debe ser un entero entre 0 y 25"
                        );
                    }
                }
        );


        editar.addActionListener(
                e -> {

                    boolean x =
                            s.editarAnimal(
                                    codigo.getText().trim(),
                                    (String)
                                            clinico.getSelectedItem(),
                                    (String)
                                            adop.getSelectedItem()
                            );


                    ok(
                            x,
                            "Animal actualizado",
                            "No se encontró el animal o el estado es inválido"
                    );


                    refrescarAnimales();
                }
        );


        eliminar.addActionListener(
                e -> {

                    boolean x =
                            s.eliminarAnimal(
                                    codigo.getText().trim()
                            );


                    ok(
                            x,
                            "Animal eliminado lógicamente",
                            "Solo ADMIN puede eliminar y el código debe existir"
                    );


                    refrescarAnimales();
                }
        );


        buscar.addActionListener(
                e ->
                        buscarAnimales(
                                texto.getText().trim(),
                                (String)
                                        criterio.getSelectedItem()
                        )
        );


        todos.addActionListener(
                e ->
                        refrescarAnimales()
        );


        return p;
    }


    void buscarAnimales(
            String texto,
            String criterio) {

        if (Util.vacio(texto)) {

            refrescarAnimales();

            return;
        }


        int cantidad = 0;


        for (int i = 0;
             i < s.totalAnimales;
             i++) {

            Animal a =
                    s.animales[i];


            if (a.estadoAdopcion
                    .equals("ELIMINADO")) {

                continue;
            }


            boolean coincide =
                    false;


            if (criterio.equals("Código")) {

                coincide =
                        a.codigo.equalsIgnoreCase(
                                texto
                        );

            } else if (
                    criterio.equals("Nombre")
            ) {

                coincide =
                        a.nombre
                                .toLowerCase()
                                .contains(
                                        texto.toLowerCase()
                                );

            } else if (
                    criterio.equals("Especie")
            ) {

                coincide =
                        a.especie.equalsIgnoreCase(
                                texto
                        );

            } else {

                coincide =
                        a.estadoClinico
                                .equalsIgnoreCase(texto)
                        || a.estadoAdopcion
                                .equalsIgnoreCase(texto);
            }


            if (coincide) {
                cantidad++;
            }
        }


        Object[][] d =
                new Object[cantidad][6];


        int n = 0;


        for (int i = 0;
             i < s.totalAnimales;
             i++) {

            Animal a =
                    s.animales[i];


            if (a.estadoAdopcion
                    .equals("ELIMINADO")) {

                continue;
            }


            boolean coincide =
                    false;


            if (criterio.equals("Código")) {

                coincide =
                        a.codigo.equalsIgnoreCase(
                                texto
                        );

            } else if (
                    criterio.equals("Nombre")
            ) {

                coincide =
                        a.nombre
                                .toLowerCase()
                                .contains(
                                        texto.toLowerCase()
                                );

            } else if (
                    criterio.equals("Especie")
            ) {

                coincide =
                        a.especie.equalsIgnoreCase(
                                texto
                        );

            } else {

                coincide =
                        a.estadoClinico
                                .equalsIgnoreCase(texto)
                        || a.estadoAdopcion
                                .equalsIgnoreCase(texto);
            }


            if (coincide) {

                d[n++] =
                        new Object[]{
                                a.codigo,
                                a.nombre,
                                a.especie,
                                a.edad,
                                a.estadoClinico,
                                a.estadoAdopcion
                        };
            }
        }


        modelo(
                tablaAnimales,
                encabezados,
                d
        );
    }


    JPanel panelAdoptantes() {

        JPanel p = base();


        JPanel norte =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );


        JPanel f =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField c =
                new JTextField(7);

        JTextField n =
                new JTextField(12);

        JTextField dpi =
                new JTextField(13);

        JTextField tel =
                new JTextField(8);


        JButton reg =
                b("Registrar");

        JButton edit =
                b("Editar");


        f.add(
                new JLabel("Código")
        );

        f.add(c);


        f.add(
                new JLabel("Nombre")
        );

        f.add(n);


        f.add(
                new JLabel("DPI")
        );

        f.add(dpi);


        f.add(
                new JLabel("Teléfono")
        );

        f.add(tel);


        f.add(reg);
        f.add(edit);


        JPanel bus =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField q =
                new JTextField(15);


        JComboBox<String> crit =
                new JComboBox<>(
                        new String[]{
                                "Código",
                                "DPI",
                                "Nombre"
                        }
                );


        JButton buscar =
                b("Buscar");

        JButton todos =
                b("Mostrar todos");


        bus.add(
                new JLabel("Buscar:")
        );

        bus.add(crit);
        bus.add(q);
        bus.add(buscar);
        bus.add(todos);


        norte.add(f);
        norte.add(bus);


        p.add(
                norte,
                BorderLayout.NORTH
        );


        p.add(
                new JScrollPane(
                        tablaAdoptantes
                )
        );


        reg.addActionListener(
                e -> {

                    boolean x =
                            s.registrarAdoptante(
                                    c.getText().trim(),
                                    n.getText().trim(),
                                    dpi.getText().trim(),
                                    tel.getText().trim()
                            );


                    ok(
                            x,
                            "Adoptante registrado",
                            "Datos inválidos, código o DPI duplicado"
                    );


                    refrescarAdoptantes();
                }
        );


        edit.addActionListener(
                e -> {

                    boolean x =
                            s.editarAdoptante(
                                    c.getText().trim(),
                                    n.getText().trim(),
                                    tel.getText().trim()
                            );


                    ok(
                            x,
                            "Adoptante actualizado",
                            "Código no encontrado o datos inválidos"
                    );


                    refrescarAdoptantes();
                }
        );


        buscar.addActionListener(
                e ->
                        buscarAdoptantes(
                                q.getText().trim(),
                                (String)
                                        crit.getSelectedItem()
                        )
        );


        todos.addActionListener(
                e ->
                        refrescarAdoptantes()
        );


        return p;
    }


    void buscarAdoptantes(
            String texto,
            String criterio) {

        if (Util.vacio(texto)) {

            refrescarAdoptantes();

            return;
        }


        int n = 0;


        for (int i = 0;
             i < s.totalAdoptantes;
             i++) {

            Adoptante a =
                    s.adoptantes[i];


            if (coincideAdoptante(
                    a,
                    texto,
                    criterio
            )) {

                n++;
            }
        }


        Object[][] d =
                new Object[n][4];


        int k = 0;


        for (int i = 0;
             i < s.totalAdoptantes;
             i++) {

            Adoptante a =
                    s.adoptantes[i];


            if (coincideAdoptante(
                    a,
                    texto,
                    criterio
            )) {

                d[k++] =
                        new Object[]{
                                a.codigo,
                                a.nombre,
                                a.dpi,
                                a.telefono
                        };
            }
        }


        modelo(
                tablaAdoptantes,
                new String[]{
                        "Código",
                        "Nombre",
                        "DPI",
                        "Teléfono"
                },
                d
        );
    }


    boolean coincideAdoptante(
            Adoptante a,
            String t,
            String c) {

        if (c.equals("Código")) {

            return a.codigo.equalsIgnoreCase(t);
        }


        if (c.equals("DPI")) {

            return a.dpi.equals(t);
        }


        return a.nombre
                .toLowerCase()
                .contains(
                        t.toLowerCase()
                );
    }


    JPanel panelSolicitudes() {

        JPanel p = base();


        JPanel f =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField c =
                new JTextField(7);

        JTextField a =
                new JTextField(7);

        JTextField ad =
                new JTextField(7);

        JTextField fecha =
                new JTextField(10);


        JComboBox<String> estado =
                new JComboBox<>(
                        new String[]{
                                "PENDIENTE",
                                "APROBADA",
                                "RECHAZADA",
                                "COMPLETADA"
                        }
                );


        JButton reg =
                b("Registrar");

        JButton cambiar =
                b("Cambiar estado");


        fecha.setText(
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy"
                                )
                        )
        );


        f.add(
                new JLabel("Código")
        );

        f.add(c);


        f.add(
                new JLabel("Animal")
        );

        f.add(a);


        f.add(
                new JLabel("Adoptante")
        );

        f.add(ad);


        f.add(
                new JLabel("Fecha")
        );

        f.add(fecha);


        f.add(reg);
        f.add(estado);
        f.add(cambiar);


        p.add(
                f,
                BorderLayout.NORTH
        );


        p.add(
                new JScrollPane(
                        tablaSolicitudes
                )
        );


        reg.addActionListener(
                e -> {

                    boolean x =
                            s.registrarSolicitud(
                                    c.getText().trim(),
                                    a.getText().trim(),
                                    ad.getText().trim(),
                                    fecha.getText().trim()
                            );


                    ok(
                            x,
                            "Solicitud registrada",
                            "No disponible, código inexistente/duplicado o fecha inválida"
                    );


                    refrescarSolicitudes();
                    refrescarAnimales();
                }
        );


        cambiar.addActionListener(
                e -> {

                    boolean x =
                            s.cambiarSolicitud(
                                    c.getText().trim(),
                                    (String)
                                            estado.getSelectedItem()
                            );


                    ok(
                            x,
                            "Estado actualizado",
                            "No se puede aprobar: animal no disponible o código inválido"
                    );


                    refrescarSolicitudes();
                    refrescarAnimales();
                }
        );


        return p;
    }


    JPanel panelRescates() {

        JPanel p = base();


        JPanel f =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField c =
                new JTextField(7);

        JTextField desc =
                new JTextField(18);

        JTextField fecha =
                new JTextField(10);

        JTextField animal =
                new JTextField(7);

        JTextField nombre =
                new JTextField(10);

        JTextField edad =
                new JTextField(3);


        JComboBox<String> pri =
                new JComboBox<>(
                        new String[]{
                                "ALTA",
                                "MEDIA",
                                "BAJA"
                        }
                );


        JComboBox<String> est =
                new JComboBox<>(
                        new String[]{
                                "PENDIENTE",
                                "ATENDIDO"
                        }
                );


        JComboBox<String> esp =
                new JComboBox<>(
                        new String[]{
                                "Perro",
                                "Gato"
                        }
                );


        JButton reg =
                b("Registrar");

        JButton at =
                b("Atender");

        JButton activos =
                b("Solo pendientes");


        fecha.setText(
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy"
                                )
                        )
        );


        f.add(
                new JLabel("Código")
        );

        f.add(c);


        f.add(
                new JLabel("Descripción")
        );

        f.add(desc);


        f.add(
                new JLabel("Prioridad")
        );

        f.add(pri);


        f.add(
                new JLabel("Fecha")
        );

        f.add(fecha);


        f.add(reg);


        f.add(
                new JLabel(
                        "Animal existente (opcional)"
                )
        );

        f.add(animal);


        f.add(
                new JLabel("Nombre")
        );

        f.add(nombre);


        f.add(esp);


        f.add(
                new JLabel("Edad")
        );

        f.add(edad);


        f.add(at);
        f.add(activos);


        p.add(
                f,
                BorderLayout.NORTH
        );


        p.add(
                new JScrollPane(
                        tablaRescates
                )
        );


        reg.addActionListener(
                e -> {

                    boolean x =
                            s.registrarRescate(
                                    c.getText().trim(),
                                    desc.getText().trim(),
                                    (String)
                                            pri.getSelectedItem(),
                                    "PENDIENTE",
                                    fecha.getText().trim()
                            );


                    ok(
                            x,
                            "Rescate registrado",
                            "Datos inválidos o código duplicado"
                    );


                    refrescarRescates();
                }
        );


        activos.addActionListener(
                e ->
                        refrescarRescatesPendientes()
        );


        at.addActionListener(
                e -> {

                    try {

                        boolean x =
                                s.atenderRescate(
                                        c.getText().trim(),
                                        animal.getText().trim(),
                                        nombre.getText().trim(),
                                        (String)
                                                esp.getSelectedItem(),
                                        Integer.parseInt(
                                                edad.getText().trim()
                                        )
                                );


                        ok(
                                x,
                                "Rescate atendido",
                                "No se pudo atender: verifique datos y animal"
                        );


                        refrescarRescates();
                        refrescarAnimales();

                    } catch (Exception ex) {

                        msg(
                                "La edad debe ser un entero entre 0 y 25"
                        );
                    }
                }
        );


        return p;
    }


    void refrescarRescatesPendientes() {

        int n = 0;


        for (int i = 0;
             i < s.totalRescates;
             i++) {

            if (s.rescates[i]
                    .estado
                    .equals("PENDIENTE")) {

                n++;
            }
        }


        Object[][] d =
                new Object[n][6];


        int k = 0;


        for (int i = 0;
             i < s.totalRescates;
             i++) {

            Rescate r =
                    s.rescates[i];


            if (r.estado
                    .equals("PENDIENTE")) {

                d[k++] =
                        new Object[]{
                                r.codigo,
                                r.descripcion,
                                r.prioridad,
                                r.estado,
                                r.fecha,
                                r.codigoAnimalVinculado
                        };
            }
        }


        modelo(
                tablaRescates,
                new String[]{
                        "Código",
                        "Descripción",
                        "Prioridad",
                        "Estado",
                        "Fecha",
                        "Animal vinculado"
                },
                d
        );
    }


    JPanel panelUbicaciones() {

        JPanel p = base();


        JPanel f =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JTextField animal =
                new JTextField(7);

        JTextField fila =
                new JTextField(3);

        JTextField col =
                new JTextField(3);


        JButton asignar =
                b("Asignar");

        JButton liberar =
                b("Liberar");


        f.add(
                new JLabel("Animal")
        );

        f.add(animal);


        f.add(
                new JLabel("Fila 0-3")
        );

        f.add(fila);


        f.add(
                new JLabel("Columna 0-7")
        );

        f.add(col);


        f.add(asignar);
        f.add(liberar);


        p.add(
                f,
                BorderLayout.NORTH
        );


        p.add(
                new JScrollPane(
                        tablaMatriz
                )
        );


        asignar.addActionListener(
                e -> {

                    try {

                        boolean x =
                                s.asignarEspacio(
                                        animal.getText().trim(),
                                        Integer.parseInt(
                                                fila.getText().trim()
                                        ),
                                        Integer.parseInt(
                                                col.getText().trim()
                                        )
                                );


                        ok(
                                x,
                                "Espacio asignado",
                                "Celda ocupada, índice inválido o animal inexistente"
                        );


                        refrescarMatriz();

                    } catch (Exception ex) {

                        msg(
                                "Fila y columna deben ser números"
                        );
                    }
                }
        );


        liberar.addActionListener(
                e -> {

                    try {

                        boolean x =
                                s.liberarEspacio(
                                        Integer.parseInt(
                                                fila.getText().trim()
                                        ),
                                        Integer.parseInt(
                                                col.getText().trim()
                                        )
                                );


                        ok(
                                x,
                                "Espacio liberado",
                                "La celda está libre o el índice es inválido"
                        );


                        refrescarMatriz();

                    } catch (Exception ex) {

                        msg(
                                "Fila y columna deben ser números"
                        );
                    }
                }
        );


        return p;
    }


    JPanel panelReportes() {

        JPanel p = base();


        JTextArea info =
                new JTextArea(
                        "Los reportes se guardan automáticamente en la carpeta reportes/.\n"
                        + "Los archivos incluyen fecha y hora en el nombre."
                );


        info.setEditable(false);


        p.add(
                info,
                BorderLayout.NORTH
        );


        JPanel b =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );


        JButton a =
                new JButton(
                        "Reporte animales"
                );

        JButton ad =
                new JButton(
                        "Reporte adopciones"
                );

        JButton o =
                new JButton(
                        "Reporte ocupación"
                );

        JButton rr =
                new JButton(
                        "Reporte rescates"
                );

        JButton ba =
                new JButton(
                        "Bitácora acciones"
                );

        JButton be =
                new JButton(
                        "Bitácora errores"
                );

        JButton csv =
                new JButton(
                        "Exportar animales CSV"
                );

        JButton imp =
                new JButton(
                        "Importar animales CSV"
                );


        b.add(a);
        b.add(ad);
        b.add(o);
        b.add(rr);
        b.add(ba);
        b.add(be);
        b.add(csv);
        b.add(imp);


        p.add(
                b,
                BorderLayout.CENTER
        );


        a.addActionListener(
                e -> {

                    Reportes.animales(s);

                    msg(
                            "Reporte de animales generado."
                    );
                }
        );


        ad.addActionListener(
                e -> {

                    Reportes.adopciones(s);

                    msg(
                            "Reporte de adopciones generado."
                    );
                }
        );


        o.addActionListener(
                e -> {

                    Reportes.ocupacion(s);

                    msg(
                            "Reporte de ocupación generado."
                    );
                }
        );


        rr.addActionListener(
                e -> {

                    Reportes.rescates(s);

                    msg(
                            "Reporte de rescates generado."
                    );
                }
        );


        ba.addActionListener(
                e -> {

                    Reportes.bitacora(
                            "ACCIONES"
                    );

                    msg(
                            "Bitácora de acciones exportada."
                    );
                }
        );


        be.addActionListener(
                e -> {

                    Reportes.bitacora(
                            "ERRORES"
                    );

                    msg(
                            "Bitácora de errores exportada."
                    );
                }
        );


        csv.addActionListener(
                e -> {

                    Persistencia.exportarCSV(s);

                    msg(
                            "CSV de animales generado en reportes/."
                    );
                }
        );


        imp.addActionListener(
                e -> {

                    JFileChooser fc =
                            new JFileChooser();


                    if (
                            fc.showOpenDialog(this)
                                    == JFileChooser.APPROVE_OPTION
                    ) {

                        int n =
                                Persistencia.importarCSV(
                                        s,
                                        fc.getSelectedFile()
                                );


                        msg(
                                "Animales importados: "
                                        + n
                        );


                        refrescarAnimales();
                    }
                }
        );


        return p;
    }


    JPanel panelEstudiante() {

        JPanel p = base();


        JTextArea t =
                new JTextArea();


        t.setEditable(false);


        t.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        16
                )
        );


        t.setText(
                "CENTRO DE RESCATE ANIMAL\n\n"
                + "Proyecto IPC1 - Segundo Semestre 2026\n\n"
                + "Estudiante: Oscar Monzon\n"
                + "Carné: 202402543\n"
                + "Curso: Introducción a la Programación y Computación 1\n\n"
                + "Tecnologías: Java 17+ y Swing\n"
                + "Matriz: 4 filas x 8 columnas\n"
                + "Capacidad total: 32 espacios\n\n"
                
        );


        p.add(
                t,
                BorderLayout.CENTER
        );


        return p;
    }


    void refrescarTodo() {

        refrescarAnimales();
        refrescarAdoptantes();
        refrescarSolicitudes();
        refrescarRescates();
        refrescarMatriz();
    }


    void modelo(
            JTable t,
            String[] cols,
            Object[][] data) {

        t.setModel(
                new DefaultTableModel(
                        data,
                        cols
                ) {

                    public boolean isCellEditable(
                            int r,
                            int c) {

                        return false;
                    }
                }
        );
    }


    void refrescarAnimales() {

        Object[][] d =
                new Object[
                        s.totalAnimales
                ][6];


        int n = 0;


        for (int i = 0;
             i < s.totalAnimales;
             i++) {

            Animal a =
                    s.animales[i];


            if (!a.estadoAdopcion
                    .equals("ELIMINADO")) {

                d[n++] =
                        new Object[]{
                                a.codigo,
                                a.nombre,
                                a.especie,
                                a.edad,
                                a.estadoClinico,
                                a.estadoAdopcion
                        };
            }
        }


        Object[][] z =
                new Object[n][6];


        for (int i = 0;
             i < n;
             i++) {

            z[i] = d[i];
        }


        modelo(
                tablaAnimales,
                encabezados,
                z
        );
    }


    void refrescarAdoptantes() {

        Object[][] d =
                new Object[
                        s.totalAdoptantes
                ][4];


        for (int i = 0;
             i < s.totalAdoptantes;
             i++) {

            Adoptante a =
                    s.adoptantes[i];


            d[i] =
                    new Object[]{
                            a.codigo,
                            a.nombre,
                            a.dpi,
                            a.telefono
                    };
        }


        modelo(
                tablaAdoptantes,
                new String[]{
                        "Código",
                        "Nombre",
                        "DPI",
                        "Teléfono"
                },
                d
        );
    }


    void refrescarSolicitudes() {

        Object[][] d =
                new Object[
                        s.totalSolicitudes
                ][5];


        for (int i = 0;
             i < s.totalSolicitudes;
             i++) {

            Solicitud x =
                    s.solicitudes[i];


            d[i] =
                    new Object[]{
                            x.codigo,
                            x.codigoAnimal,
                            x.codigoAdoptante,
                            x.fecha,
                            x.estado
                    };
        }


        modelo(
                tablaSolicitudes,
                new String[]{
                        "Código",
                        "Animal",
                        "Adoptante",
                        "Fecha",
                        "Estado"
                },
                d
        );
    }


    void refrescarRescates() {

        Object[][] d =
                new Object[
                        s.totalRescates
                ][6];


        for (int i = 0;
             i < s.totalRescates;
             i++) {

            Rescate r =
                    s.rescates[i];


            d[i] =
                    new Object[]{
                            r.codigo,
                            r.descripcion,
                            r.prioridad,
                            r.estado,
                            r.fecha,
                            r.codigoAnimalVinculado
                    };
        }


        modelo(
                tablaRescates,
                new String[]{
                        "Código",
                        "Descripción",
                        "Prioridad",
                        "Estado",
                        "Fecha",
                        "Animal vinculado"
                },
                d
        );
    }


    void refrescarMatriz() {

        Object[][] d =
                new Object[
                        EspacioRefugio.FILAS
                ][
                        EspacioRefugio.COLUMNAS + 1
                ];


        for (int i = 0;
             i < EspacioRefugio.FILAS;
             i++) {

            d[i][0] =
                    "Zona " + i;


            for (int j = 0;
                 j < EspacioRefugio.COLUMNAS;
                 j++) {

                d[i][j + 1] =
                        s.espacios
                                .matriz[i][j]
                                .equals("")
                        ? "LIBRE"
                        : s.espacios
                                .matriz[i][j];
            }
        }


        String[] c =
                new String[
                        EspacioRefugio.COLUMNAS + 1
                ];


        c[0] = "Área";


        for (int j = 0;
             j < EspacioRefugio.COLUMNAS;
             j++) {

            c[j + 1] =
                    "Jaula " + j;
        }


        modelo(
                tablaMatriz,
                c,
                d
        );
    }
}