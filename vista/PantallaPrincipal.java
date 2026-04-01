package vista;

import modelo.Usuario;
import servicio.ServicioUsuario;
import util.EstiloUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * Pantalla Principal - Muestra la tabla de usuarios registrados.
 * Permite actualizar y eliminar usuarios, y cerrar sesion.
 */
public class PantallaPrincipal extends JFrame {

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton botonActualizar;
    private JButton botonEliminar;
    private JButton botonCerrarSesion;
    private JButton botonNuevoUsuario;
    private Usuario usuarioActual;

    public PantallaPrincipal(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Panel Principal - Usuarios Registrados");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(EstiloUI.COLOR_FONDO);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(0, 0));

        // ===== PANEL SUPERIOR (Header) =====
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(EstiloUI.COLOR_PRIMARIO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel titulo = new JLabel("Clientes Registrados");
        titulo.setFont(EstiloUI.FUENTE_TITULO);
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo, BorderLayout.WEST);

        JLabel bienvenida = new JLabel("Bienvenido, " + usuarioActual.getNombre());
        bienvenida.setFont(EstiloUI.FUENTE_SUBTITULO);
        bienvenida.setForeground(new Color(220, 235, 250));
        panelSuperior.add(bienvenida, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        // ===== PANEL CENTRAL (Tabla) =====
        String[] columnas = {"ID", "Nombre", "Apellido", "Telefono", "Correo Electronico", "Usuario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable directamente
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        EstiloUI.aplicarEstiloTabla(tablaUsuarios);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ocultar columna ID (para uso interno)
        tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
        tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaUsuarios.getColumnModel().getColumn(0).setWidth(0);

        // Centrar el texto de las celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < tablaUsuarios.getColumnCount(); i++) {
            tablaUsuarios.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // ===== PANEL INFERIOR (Botones) =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        panelBotones.setBackground(EstiloUI.COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        botonNuevoUsuario = EstiloUI.crearBoton("Nuevo", EstiloUI.COLOR_EXITO, EstiloUI.COLOR_EXITO_OSC);
        botonActualizar = EstiloUI.crearBoton("Actualizar", EstiloUI.COLOR_PRIMARIO, EstiloUI.COLOR_PRIMARIO_OSC);
        botonEliminar = EstiloUI.crearBoton("Eliminar", EstiloUI.COLOR_ERROR, EstiloUI.COLOR_ERROR_OSC);
        botonCerrarSesion = EstiloUI.crearBoton("Cerrar Sesion", new Color(149, 165, 166), new Color(127, 140, 141));

        panelBotones.add(botonNuevoUsuario);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonCerrarSesion);
        add(panelBotones, BorderLayout.SOUTH);

        // === EVENTOS ===
        botonNuevoUsuario.addActionListener(e -> crearNuevoUsuario());
        botonActualizar.addActionListener(e -> actualizarUsuario());
        botonEliminar.addActionListener(e -> eliminarUsuario());
        botonCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    /**
     * Carga todos los usuarios de la base de datos en la tabla.
     */
    public void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<Usuario> usuarios = ServicioUsuario.getInstancia().obtenerTodos();
        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getTelefono(),
                    u.getCorreo(),
                    u.getNombreUsuario()
            });
        }
    }

    /**
     * Abre un dialogo inline para crear un nuevo usuario directamente desde el panel principal.
     */
    private void crearNuevoUsuario() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField cNombre = new JTextField();
        JTextField cApellido = new JTextField();
        JTextField cTelefono = new JTextField();
        JTextField cCorreo = new JTextField();
        JTextField cUsuario = new JTextField();
        JPasswordField cContrasena = new JPasswordField();

        panel.add(new JLabel("Nombre:"));         panel.add(cNombre);
        panel.add(new JLabel("Apellido:"));        panel.add(cApellido);
        panel.add(new JLabel("Telefono:"));        panel.add(cTelefono);
        panel.add(new JLabel("Correo:"));          panel.add(cCorreo);
        panel.add(new JLabel("Usuario:"));         panel.add(cUsuario);
        panel.add(new JLabel("Contrasena:"));      panel.add(cContrasena);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Crear Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String passTexto = new String(cContrasena.getPassword()).trim();
            // Usamos el Factory para validar los campos y aplicar logica de negocio
            String error = fabricas.FabricaUsuario.validarCampos(
                cNombre.getText().trim(), cApellido.getText().trim(),
                cTelefono.getText().trim(), cCorreo.getText().trim(),
                cUsuario.getText().trim(), passTexto, passTexto
            );

            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ServicioUsuario.getInstancia().buscarPorUsuario(cUsuario.getText().trim()) != null) {
                JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya esta tomado.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario nuevo = fabricas.FabricaUsuario.crearUsuario(
                cNombre.getText().trim(), cApellido.getText().trim(),
                cTelefono.getText().trim(), cCorreo.getText().trim(),
                cUsuario.getText().trim(), passTexto
            );

            if (ServicioUsuario.getInstancia().registrar(nuevo)) {
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al guardar en SQLite.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre un dialogo para actualizar los datos del usuario seleccionado.
     */
    private void actualizarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario de la tabla para actualizar.",
                    "Sin Seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Usuario usuario = null;

        // Buscar el usuario completo en la BD
        List<Usuario> todos = ServicioUsuario.getInstancia().obtenerTodos();
        for (Usuario u : todos) {
            if (u.getId() == id) {
                usuario = u;
                break;
            }
        }
        if (usuario == null) return;

        // Crear dialogo de edicion
        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField cNombre = new JTextField(usuario.getNombre());
        JTextField cApellido = new JTextField(usuario.getApellido());
        JTextField cTelefono = new JTextField(usuario.getTelefono());
        JTextField cCorreo = new JTextField(usuario.getCorreo());
        JTextField cUsuario = new JTextField(usuario.getNombreUsuario());
        JPasswordField cContrasena = new JPasswordField(usuario.getContrasena());

        panel.add(new JLabel("Nombre:"));         panel.add(cNombre);
        panel.add(new JLabel("Apellido:"));        panel.add(cApellido);
        panel.add(new JLabel("Telefono:"));        panel.add(cTelefono);
        panel.add(new JLabel("Correo:"));          panel.add(cCorreo);
        panel.add(new JLabel("Usuario:"));         panel.add(cUsuario);
        panel.add(new JLabel("Contrasena:"));      panel.add(cContrasena);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Actualizar Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            // Validar que ningun campo este vacio
            if (cNombre.getText().trim().isEmpty() || cApellido.getText().trim().isEmpty() ||
                cTelefono.getText().trim().isEmpty() || cCorreo.getText().trim().isEmpty() ||
                cUsuario.getText().trim().isEmpty() ||
                new String(cContrasena.getPassword()).trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuario.setNombre(cNombre.getText().trim());
            usuario.setApellido(cApellido.getText().trim());
            usuario.setTelefono(cTelefono.getText().trim());
            usuario.setCorreo(cCorreo.getText().trim());
            usuario.setNombreUsuario(cUsuario.getText().trim());
            usuario.setContrasena(new String(cContrasena.getPassword()).trim());

            if (ServicioUsuario.getInstancia().actualizar(usuario)) {
                JOptionPane.showMessageDialog(this,
                        "Usuario actualizado correctamente.",
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); // Refrescar tabla automaticamente
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar el usuario.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Elimina el usuario seleccionado de la base de datos.
     */
    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario de la tabla para eliminar.",
                    "Sin Seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "Esta seguro de que desea eliminar al usuario '" + nombre + "'?",
                "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ServicioUsuario.getInstancia().eliminar(id)) {
                JOptionPane.showMessageDialog(this,
                        "Usuario eliminado correctamente.",
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); // Refrescar tabla automaticamente
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar el usuario.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Cierra sesion y vuelve a la pantalla de login.
     */
    private void cerrarSesion() {
        this.dispose();
        PantallaLogin login = new PantallaLogin();
        login.setVisible(true);
    }
}
