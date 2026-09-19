package edu.umg.peluqueria.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import edu.umg.peluqueria.dao.CitasDAO;
import edu.umg.peluqueria.dao.ClienteDAO;
import edu.umg.peluqueria.dao.ServiciosDAO;
import edu.umg.peluqueria.modelo.Citas;
import edu.umg.peluqueria.modelo.Cliente;
import edu.umg.peluqueria.modelo.Servicios;
import java.time.format.ResolverStyle;
public class VentanaPrincipal extends JFrame {

    // Colores de la interfaz
    private final Color LILA = new Color(190, 165, 205);
    private final Color LILA_CLARO = new Color(245, 239, 247);
    private final Color LILA_SUAVE = new Color(232, 219, 238);
    private final Color MORADO = new Color(70, 45, 90);
    private final Color BLANCO = Color.WHITE;

    private JPanel panelContenido;

    // Tabla de citas
    private JTable tablaCitas;

    // Formato de fecha
    private final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
    		.withResolverStyle(ResolverStyle.STRICT);
    public VentanaPrincipal() {

        setTitle("Peluquería PrettyHair");
        setSize(1050, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(LILA_CLARO);
        setLayout(new BorderLayout());

       crearMenu();

        panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBackground(LILA_CLARO);
        panelContenido.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        add(panelContenido, BorderLayout.CENTER);      
        mostrarCitas();
    }

    private void crearMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(LILA);
        panelMenu.setPreferredSize(new Dimension(250, 0));

        panelMenu.setLayout(new javax.swing.BoxLayout(
                panelMenu,
                javax.swing.BoxLayout.Y_AXIS
        ));

        JLabel nombre = new JLabel(
                "<html><center>Peluquería<br>PrettyHair</center></html>",
                SwingConstants.CENTER
        );

        nombre.setFont(new Font("SansSerif", Font.BOLD, 27));
        nombre.setForeground(MORADO);
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenu.add(Box.createVerticalStrut(35));
        panelMenu.add(nombre);
        panelMenu.add(Box.createVerticalStrut(45));

        JButton botonCitas = crearBoton("Citas");
        JButton botonAgendar = crearBoton("Agendar citas");
        JButton botonServicios = crearBoton("Servicios");
        JButton botonClientes = crearBoton("Clientes");

        botonCitas.addActionListener(e -> mostrarCitas());

        botonAgendar.addActionListener(e ->
                mostrarFormularioCita(false)
        );
   
        botonServicios.addActionListener(e ->
                mostrarServicios()
        );

        botonClientes.addActionListener(e ->
                mostrarClientes()
        );

        panelMenu.add(botonCitas);
        panelMenu.add(Box.createVerticalStrut(22));

        panelMenu.add(botonAgendar);
        panelMenu.add(Box.createVerticalStrut(22));
 
        panelMenu.add(botonServicios);
        panelMenu.add(Box.createVerticalStrut(22));

        panelMenu.add(botonClientes);

        add(panelMenu, BorderLayout.WEST);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("SansSerif", Font.BOLD, 16));
        boton.setForeground(MORADO);
        boton.setBackground(BLANCO);

        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        boton.setMaximumSize(new Dimension(215, 52));
        boton.setPreferredSize(new Dimension(215, 52));

        boton.setFocusPainted(false);
        boton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 195, 220), 1
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 15, 8, 15
                        )
                )
        );
        return boton;
    }

    private JLabel crearTitulo(String texto) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 23)
        );
        titulo.setForeground(MORADO);
        titulo.setOpaque(true);
        titulo.setBackground(LILA_SUAVE);

        titulo.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 10, 12, 10
                )
        );
        return titulo;
    }
    private void mostrarCitas() {

        panelContenido.removeAll();

        JLabel titulo = crearTitulo("Datos de Citas");

        panelContenido.add(titulo, BorderLayout.NORTH);

        String[] columnas = {
                "ID",
                "Cliente",
                "Servicio",
                "Fecha y Hora",
                "Duración",
                "Estado"
        };

        DefaultTableModel modelo = new DefaultTableModel(
                columnas, 0
        ) {
            @Override
            public boolean isCellEditable(
                    int fila,
                    int columna) {

                return false;
            }
        };

        tablaCitas = crearTabla(modelo);

        cargarCitas(modelo);

        JScrollPane scroll = new JScrollPane(tablaCitas);

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 195, 220)
                )
        );

        panelContenido.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        panelBotones.setBackground(LILA_CLARO);

        JButton botonEditar = crearBotonAccion(
                "Editar cita"
        );

        JButton botonEliminar = crearBotonAccion(
                "Eliminar cita"
        );

        botonEditar.addActionListener(e ->
                mostrarEditarCita()
        );

        botonEliminar.addActionListener(e ->
                eliminarCita()
        );

        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        panelContenido.add(
                panelBotones,
                BorderLayout.SOUTH
        );
        actualizarVentana();
    }

    private void cargarCitas(DefaultTableModel modelo) {
        try {
            CitasDAO citaDAO = new CitasDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            ServiciosDAO servicioDAO = new ServiciosDAO();

            List<Citas> citas = citaDAO.listarTodos();
            List<Cliente> clientes = clienteDAO.listarTodos();
            List<Servicios> servicios = servicioDAO.listarTodos();

            Map<Integer, String> nombresClientes =
                    new HashMap<>();

            for (Cliente cliente : clientes) {

                nombresClientes.put(
                        cliente.getIdCliente(),
                        cliente.getNombre()
                );
            }

            Map<Integer, String> nombresServicios =
                    new HashMap<>();

            for (Servicios servicio : servicios) {

                nombresServicios.put(
                        servicio.getIdServicio(),
                        servicio.getNombre()
                );
            }

            for (Citas cita : citas) {

                String cliente =
                        nombresClientes.get(
                                cita.getIdCliente()
                        );

                String servicio =
                        nombresServicios.get(
                                cita.getIdServicio()
                        );

                modelo.addRow(new Object[] {
                        cita.getIdCita(),
                        cliente,
                        servicio,
                        cita.getFechaHora()
                                .format(FORMATO_FECHA),
                        cita.getDuracion()
                                + " min",
                        cita.getEstado()
                });
            }

        } catch (Exception ex) {

            mostrarError(
                    "No se pudieron cargar las citas.",
                    ex
            );
        }
    }

    private void mostrarFormularioCita(boolean editar) {
       panelContenido.removeAll();

        String tituloTexto =
                editar
                ? "Editar cita"
                : "Agendar nueva cita";

        panelContenido.add(
                crearTitulo(tituloTexto),
                BorderLayout.NORTH
        );

        JPanel formulario = new JPanel(
                new GridBagLayout()
        );

        formulario.setBackground(BLANCO);

        formulario.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 60, 25, 60
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        JLabel labelCliente =
                crearLabel("Cliente:");

        JComboBox<Cliente> comboCliente =
                new JComboBox<>();

        cargarClientes(comboCliente);

        JLabel labelServicio =
                crearLabel("Servicio:");

        JComboBox<Servicios> comboServicio =
                new JComboBox<>();

        cargarServicios(comboServicio);

        JLabel labelFecha =
                crearLabel(
                        "Fecha y hora:"
                );

        JTextField campoFecha =
                crearCampo();

        campoFecha.setToolTipText(
                "Formato: uuuu-MM-dd HH:mm"
        );

        JLabel labelDuracion =
                crearLabel(
                        "Duración (minutos):"
                );

        JTextField campoDuracion =
                crearCampo();  
        
        JComboBox<String> comboEstado = new JComboBox<>(
                new String[] {"pendiente", "confirmada", "cancelada"}
        );

        agregarCampo(
                formulario,
                gbc,
                labelCliente,
                comboCliente,
                0
        );

        agregarCampo(
                formulario,
                gbc,
                labelServicio,
                comboServicio,
                1
        );

        agregarCampo(
                formulario,
                gbc,
                labelFecha,
                campoFecha,
                2
        );

        agregarCampo(
                formulario,
                gbc,
                labelDuracion,
                campoDuracion,
                3
        );
        if (editar) {
            JLabel labelEstado = crearLabel("Estado:");

            agregarCampo(
                    formulario,
                    gbc,
                    labelEstado,
                    comboEstado,
                    4
            );
        }
        panelContenido.add(
                formulario,
                BorderLayout.CENTER
        );

        JPanel panelBotones = new JPanel();

        panelBotones.setBackground(
                LILA_CLARO
        );

        JButton botonGuardar =
                crearBotonAccion(
                        editar
                        ? "Guardar cambios"
                        : "Agendar cita"
                );

        JButton botonCancelar =
                crearBotonAccion(
                        "Cancelar"
                );

        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        botonCancelar.addActionListener(
                e -> mostrarCitas()
        );

        botonGuardar.addActionListener(e -> {

            guardarCita(
                    editar,
                    comboCliente,
                    comboServicio,
                    campoFecha,
                    campoDuracion,
                    comboEstado
            );
        });

        panelContenido.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        actualizarVentana();
    }

    private void guardarCita(
            boolean editar,
            JComboBox<Cliente> comboCliente,
            JComboBox<Servicios> comboServicio,
            JTextField campoFecha,
            JTextField campoDuracion,
            JComboBox<String> comboEstado) {

        try {

            Cliente cliente =
                    (Cliente) comboCliente
                            .getSelectedItem();

            Servicios servicio =
                    (Servicios) comboServicio
                            .getSelectedItem();

            String fechaTexto =
                    campoFecha.getText().trim();

            String duracionTexto =
                    campoDuracion.getText().trim();

            if (cliente == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un cliente."
                );

                return;
            }

            // Validación servicio
            if (servicio == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un servicio."
                );

                return;
            }
            LocalDateTime fechaHora;
            try {
                fechaHora =
                        LocalDateTime.parse(
                                fechaTexto,
                                FORMATO_FECHA
                        );
            } catch (DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha debe tener el formato:\n"
                        + "Año-Mes-dia HH:mm",
                        "Fecha incorrecta",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (fechaHora.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        "La fecha y hora no pueden estar en el pasado.",
                        "Fecha incorrecta",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int duracion;

            try {

                duracion =
                        Integer.parseInt(
                                duracionTexto
                        );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "La duración debe ser un número entero.",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (duracion <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "La duración debe ser mayor que 0.",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String estado;

            if (editar) {
                estado = comboEstado.getSelectedItem().toString();
            } else {
                estado = "pendiente";
            }

            Citas cita = new Citas(
                    cliente.getIdCliente(),
                    servicio.getIdServicio(),
                    fechaHora,
                    duracion,
                    estado
            );
            CitasDAO dao = new CitasDAO();

            if (editar) {

                int id =
                        obtenerIdCitaSeleccionada();

                if (id == -1) {
                    return;
                }

                cita.setIdCita(id);

                dao.actualizar(cita);

                JOptionPane.showMessageDialog(
                        this,
                        "La cita se actualizó correctamente."
                );

            } else {

                dao.crear(cita);

                JOptionPane.showMessageDialog(
                        this,
                        "La cita fue agendada correctamente."
                );
            }

            mostrarCitas();

        } catch (Exception ex) {

            mostrarError(
                    "No se pudo guardar la cita.",
                    ex
            );
        }
    }

    private void mostrarEditarCita() {

        if (tablaCitas == null) {

            mostrarCitas();
            return;
        }

        int fila =
                tablaCitas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una cita de la tabla primero.",
                    "Seleccionar cita",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tablaCitas
                                .getValueAt(fila, 0)
                                .toString()
                );

        try {

            CitasDAO dao = new CitasDAO();

            java.util.Optional<Citas> resultado =
                    dao.buscarPorId(id);

            if (resultado.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró la cita."
                );

                return;
            }

            Citas cita = resultado.get();

            mostrarFormularioCita(true);
            JPanel formulario =
                    (JPanel) panelContenido
                            .getComponent(1);

            JComboBox<?> comboCliente =
                    (JComboBox<?>) formulario
                            .getComponent(1);

            JComboBox<?> comboServicio =
                    (JComboBox<?>) formulario
                            .getComponent(3);

            JTextField campoFecha =
                    (JTextField) formulario
                            .getComponent(5);

            JTextField campoDuracion =
                    (JTextField) formulario
                            .getComponent(7);

            seleccionarCliente(
                    comboCliente,
                    cita.getIdCliente()
            );

            seleccionarServicio(
                    comboServicio,
                    cita.getIdServicio()
            );

            campoFecha.setText(
                    cita.getFechaHora()
                            .format(FORMATO_FECHA)
            );

            campoDuracion.setText(
                    String.valueOf(
                            cita.getDuracion()
                    )
            );
            
        } catch (Exception ex) {

            mostrarError(
                    "No se pudo cargar la cita.",
                    ex
            );
        }
    }
    private void eliminarCita() {

        if (tablaCitas == null) {
            return;
        }

        int fila =
                tablaCitas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una cita primero.",
                    "Seleccionar cita",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tablaCitas
                                .getValueAt(fila, 0)
                                .toString()
                );

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar esta cita?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            CitasDAO dao = new CitasDAO();

            boolean eliminado =
                    dao.eliminar(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "La cita fue eliminada correctamente."
                );

                mostrarCitas();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar la cita."
                );
            }

        } catch (Exception ex) {

            mostrarError(
                    "No se pudo eliminar la cita.",
                    ex
            );
        }
    }

    private void mostrarServicios() {

        panelContenido.removeAll();

        panelContenido.add(
                crearTitulo("Servicios"),
                BorderLayout.NORTH
        );

        String[] columnas = {
                "ID",
                "Servicio",
                "Precio"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(
                        columnas, 0
                ) {
                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {
                        return false;
                    }
                };

        JTable tabla =
                crearTabla(modelo);

        try {

            ServiciosDAO dao =
                    new ServiciosDAO();

            List<Servicios> servicios =
                    dao.listarTodos();

            for (Servicios servicio : servicios) {

                modelo.addRow(
                        new Object[] {
                                servicio.getIdServicio(),
                                servicio.getNombre(),
                                String.format(
                                        "Q %.2f",
                                        servicio.getPrecio()
                                )
                        }
                );
            }

        } catch (Exception ex) {

            mostrarError(
                    "No se pudieron cargar los servicios.",
                    ex
            );
        }

        JScrollPane scroll =
                new JScrollPane(tabla);

        panelContenido.add(
                scroll,
                BorderLayout.CENTER
        );

        actualizarVentana();
    }
  private void mostrarClientes() {

        panelContenido.removeAll();

        panelContenido.add(
                crearTitulo("Clientes"),
                BorderLayout.NORTH
        );

        String[] columnas = {
                "ID",
                "Nombre",
                "Teléfono"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(
                        columnas, 0
                ) {
                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {
                        return false;
                    }
                };

        JTable tabla =
                crearTabla(modelo);

        try {

            ClienteDAO dao =
                    new ClienteDAO();

            List<Cliente> clientes =
                    dao.listarTodos();

            for (Cliente cliente : clientes) {

                modelo.addRow(
                        new Object[] {
                                cliente.getIdCliente(),
                                cliente.getNombre(),
                                cliente.getTelefono()
                        }
                );
            }

        } catch (Exception ex) {

            mostrarError(
                    "No se pudieron cargar los clientes.",
                    ex
            );
        }

        panelContenido.add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        JPanel botones =
                new JPanel();

        botones.setBackground(
                LILA_CLARO
        );

        JButton agregar =
                crearBotonAccion(
                        "Agregar cliente"
                );

        JButton editar =
                crearBotonAccion(
                        "Editar cliente"
                );

        JButton eliminar =
                crearBotonAccion(
                        "Eliminar cliente"
                );

        agregar.addActionListener(
                e -> agregarCliente()
        );

        editar.addActionListener(
                e -> editarCliente(tabla)
        );

        eliminar.addActionListener(
                e -> eliminarCliente(tabla)
        );

        botones.add(agregar);
        botones.add(editar);
        botones.add(eliminar);

        panelContenido.add(
                botones,
                BorderLayout.SOUTH
        );

        actualizarVentana();
    }

    private void agregarCliente() {

        JTextField nombre =
                crearCampo();

        JTextField telefono =
                crearCampo();

        JPanel panel =
                new JPanel(
                        new GridLayout(0, 1, 5, 5)
                );

        panel.add(
                new JLabel("Nombre:")
        );

        panel.add(nombre);

        panel.add(
                new JLabel("Teléfono:")
        );

        panel.add(telefono);

        int resultado =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Nuevo cliente",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (resultado !=
                JOptionPane.OK_OPTION) {
            return;
        }

        if (nombre.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre es obligatorio."
            );

            return;
        }
        String telefonoTexto = telefono.getText().trim();

        if (telefonoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El teléfono es obligatorio."
            );
            return;
        }

        if (!telefonoTexto.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "El teléfono debe tener exactamente 8 dígitos."
            );
            return;
        }

        try {

            Cliente cliente =
                    new Cliente(
                            nombre.getText().trim(),
                            telefonoTexto
                    );

            ClienteDAO dao =
                    new ClienteDAO();

            dao.crear(cliente);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente agregado correctamente."
            );

            mostrarClientes();

        } catch (Exception ex) {

            mostrarError(
                    "No se pudo agregar el cliente.",
                    ex
            );
        }
    }

    private void editarCliente(JTable tabla) {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un cliente."
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tabla
                                .getValueAt(fila, 0)
                                .toString()
                );

        JTextField nombre =
                crearCampo();

        JTextField telefono =
                crearCampo();

        nombre.setText(
                tabla
                        .getValueAt(fila, 1)
                        .toString()
        );

        telefono.setText(
                tabla
                        .getValueAt(fila, 2)
                        .toString()
        );

        JPanel panel =
                new JPanel(
                        new GridLayout(0, 1, 5, 5)
                );

        panel.add(
                new JLabel("Nombre:")
        );

        panel.add(nombre);

        panel.add(
                new JLabel("Teléfono:")
        );

        panel.add(telefono);

        int resultado =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Editar cliente",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (resultado !=
                JOptionPane.OK_OPTION) {
            return;
        }

        if (nombre.getText().trim().isEmpty() ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nombre Obligatorio."
            );

            return;
        }
        String telefonoTexto = telefono.getText().trim();

        if (telefonoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El teléfono es obligatorio."
            );
            return;
        }

        if (!telefonoTexto.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "El teléfono debe tener exactamente 8 dígitos."
            );
            return;
        }
        try {

            Cliente cliente =
                    new Cliente(
                            id,
                            nombre.getText().trim(),
                            telefonoTexto
                    );

            ClienteDAO dao =
                    new ClienteDAO();

            dao.actualizar(cliente);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente actualizado correctamente."
            );

            mostrarClientes();

        } catch (Exception ex) {

            mostrarError(
                    "No se pudo actualizar el cliente.",
                    ex
            );
        }
    }

    private void eliminarCliente(JTable tabla) {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un cliente."
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tabla
                                .getValueAt(fila, 0)
                                .toString()
                );

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar este cliente?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta !=
                JOptionPane.YES_OPTION) {
            return;
        }

        try {

            ClienteDAO dao =
                    new ClienteDAO();

            dao.eliminar(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente eliminado correctamente."
            );

            mostrarClientes();

        } catch (Exception ex) {

            mostrarError(
                    "No se pudo eliminar el cliente.",
                    ex
            );
        }
    }

    private void cargarClientes(
            JComboBox<Cliente> combo) {

        try {

            ClienteDAO dao =
                    new ClienteDAO();

            List<Cliente> clientes =
                    dao.listarTodos();

            for (Cliente cliente : clientes) {

                combo.addItem(cliente);
            }

        } catch (Exception ex) {

            mostrarError(
                    "No se pudieron cargar los clientes.",
                    ex
            );
        }
    }

    private void cargarServicios(
            JComboBox<Servicios> combo) {

        try {

            ServiciosDAO dao =
                    new ServiciosDAO();

            List<Servicios> servicios =
                    dao.listarTodos();

            for (Servicios servicio : servicios) {

                combo.addItem(servicio);
            }

        } catch (Exception ex) {

            mostrarError(
                    "No se pudieron cargar los servicios.",
                    ex
            );
        }
    }

    private void seleccionarCliente(
            JComboBox<?> combo,
            int id) {

        for (int i = 0;
             i < combo.getItemCount();
             i++) {

            Cliente cliente =
                    (Cliente) combo.getItemAt(i);

            if (cliente.getIdCliente() == id) {

                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void seleccionarServicio(
            JComboBox<?> combo,
            int id) {

        for (int i = 0;
             i < combo.getItemCount();
             i++) {

            Servicios servicio =
                    (Servicios) combo.getItemAt(i);

            if (servicio.getIdServicio() == id) {

                combo.setSelectedIndex(i);
                return;
            }
        }
    }

   private JTable crearTabla(
            DefaultTableModel modelo) {

        JTable tabla =
                new JTable(modelo);

        tabla.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        tabla.setRowHeight(34);

        tabla.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabla.setShowGrid(true);

        tabla.setGridColor(
                new Color(225, 215, 230)
        );

        tabla.setIntercellSpacing(
                new Dimension(0, 1)
        );

        tabla.setFillsViewportHeight(true);
      
        tabla.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

        tabla.getTableHeader()
                .setForeground(BLANCO);

        tabla.getTableHeader()
                .setBackground(MORADO);

        tabla.getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 40)
                );

        DefaultTableCellRenderer centrado =
                new DefaultTableCellRenderer();

        centrado.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        tabla.setDefaultRenderer(
                Object.class,
                new RendererTabla()
        );

        return tabla;
    }

    private class RendererTabla
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component componente =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            if (isSelected) {

                componente.setBackground(
                        LILA
                );

                componente.setForeground(
                        MORADO
                );

            } else {

                if (row % 2 == 0) {

                    componente.setBackground(
                            BLANCO
                    );

                } else {

                    componente.setBackground(
                            LILA_CLARO
                    );
                }

                componente.setForeground(
                        Color.DARK_GRAY
                );
            }

            setBorder(
                    BorderFactory.createEmptyBorder(
                            5, 8, 5, 8
                    )
            );

            return componente;
        }
    }

    private JLabel crearLabel(String texto) {

        JLabel label =
                new JLabel(texto);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        label.setForeground(MORADO);

        return label;
    }

    private JTextField crearCampo() {

        JTextField campo =
                new JTextField();

        campo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        campo.setPreferredSize(
                new Dimension(300, 38)
        );

        return campo;
    }

    private JButton crearBotonAccion(
            String texto) {

        JButton boton =
                new JButton(texto);

        boton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        boton.setForeground(MORADO);
        boton.setBackground(BLANCO);

        boton.setFocusPainted(false);

        boton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                LILA
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 20, 8, 20
                        )
                )
        );

        return boton;
    }

    private void agregarCampo(
            JPanel formulario,
            GridBagConstraints gbc,
            JLabel etiqueta,
            Component campo,
            int fila) {

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;

        formulario.add(
                etiqueta,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                campo,
                gbc
        );
    }

    private int obtenerIdCitaSeleccionada() {

        if (tablaCitas == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay una cita seleccionada."
            );

            return -1;
        }

        int fila =
                tablaCitas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una cita."
            );

            return -1;
        }

        return Integer.parseInt(
                tablaCitas
                        .getValueAt(fila, 0)
                        .toString()
        );
    }

    private void actualizarVentana() {

        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarError(
            String mensaje,
            Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                mensaje + "\n\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}