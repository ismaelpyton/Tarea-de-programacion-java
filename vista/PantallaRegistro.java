package vista;

import fabricas.FabricaUsuario;
import modelo.Usuario;
import servicio.ServicioUsuario;
import util.EstiloUI;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla de Registro - Permite crear una nueva cuenta de usuario.
 * Todos los campos son obligatorios. Las contrasenas se muestran ocultas.
 */
public class PantallaRegistro extends JFrame {

    private JTextField campoNombreUsuario;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmarContrasena;
    private JButton botonRegistrar;
    private JButton botonVolver;
    private PantallaLogin pantallaLogin;

    public PantallaRegistro(PantallaLogin pantallaLogin) {
        this.pantallaLogin = pantallaLogin;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro de Usuario");
        setSize(520, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloUI.COLOR_FONDO);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        panelCentral.setBackground(EstiloUI.COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        ));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = GridBagConstraints.REMAINDER;

        // Titulo
        JLabel titulo = new JLabel("REGISTRO", SwingConstants.CENTER);
        titulo.setFont(EstiloUI.FUENTE_TITULO);
        titulo.setForeground(EstiloUI.COLOR_EXITO);
        gc.insets = new Insets(0, 0, 5, 0);
        panelCentral.add(titulo, gc);

        // Subtitulo
        JLabel subtitulo = new JLabel("Complete todos los campos para crear su cuenta", SwingConstants.CENTER);
        subtitulo.setFont(EstiloUI.FUENTE_PEQUENA);
        subtitulo.setForeground(EstiloUI.COLOR_TEXTO_CLARO);
        gc.insets = new Insets(0, 0, 18, 0);
        panelCentral.add(subtitulo, gc);

        // --- Campos del formulario ---
        gc.insets = new Insets(3, 0, 1, 0);

        panelCentral.add(EstiloUI.crearEtiqueta("Nombre de Usuario:"), gc);
        gc.insets = new Insets(0, 0, 8, 0);
        campoNombreUsuario = EstiloUI.crearCampoTexto();
        panelCentral.add(campoNombreUsuario, gc);

        gc.insets = new Insets(3, 0, 1, 0);
        panelCentral.add(EstiloUI.crearEtiqueta("Nombre:"), gc);
        gc.insets = new Insets(0, 0, 8, 0);
        campoNombre = EstiloUI.crearCampoTexto();
        panelCentral.add(campoNombre, gc);

        gc.insets = new Insets(3, 0, 1, 0);
        panelCentral.add(EstiloUI.crearEtiqueta("Apellido:"), gc);
        gc.insets = new Insets(0, 0, 8, 0);
        campoApellido = EstiloUI.crearCampoTexto();
        panelCentral.add(campoApellido, gc);

        gc.insets = new Insets(3, 0, 1, 0);
        panelCentral.add(EstiloUI.crearEtiqueta("Numero de Telefono:"), gc);
        gc.insets = new Insets(0, 0, 8, 0);
        campoTelefono = EstiloUI.crearCampoTexto();
        panelCentral.add(campoTelefono, gc);

        gc.insets = new Insets(3, 0, 1, 0);
        panelCentral.add(EstiloUI.crearEtiqueta("Correo Electronico:"), gc);
        gc.insets = new Insets(0, 0, 8, 0);
        campoCorreo = EstiloUI.crearCampoTexto();
        panelCentral.add(campoCorreo, gc);

        gc.insets = new Insets(3, 0, 1, 0);
        panelCentral.add(EstiloUI.crearEtiqueta("Contrasena:"), gc);
        gc.insets = new Insets(0, 0, 8, 0);
        campoContrasena = EstiloUI.crearCampoContrasena();
        panelCentral.add(campoContrasena, gc);

        gc.insets = new Insets(3, 0, 1, 0);
        panelCentral.add(EstiloUI.crearEtiqueta("Confirmar Contrasena:"), gc);
        gc.insets = new Insets(0, 0, 18, 0);
        campoConfirmarContrasena = EstiloUI.crearCampoContrasena();
        panelCentral.add(campoConfirmarContrasena, gc);

        // Boton Registrar
        gc.insets = new Insets(0, 0, 8, 0);
        botonRegistrar = EstiloUI.crearBoton("Registrar", EstiloUI.COLOR_EXITO, EstiloUI.COLOR_EXITO_OSC);
        panelCentral.add(botonRegistrar, gc);

        // Boton Volver
        gc.insets = new Insets(0, 0, 0, 0);
        botonVolver = EstiloUI.crearBoton("Volver al Login", EstiloUI.COLOR_PRIMARIO, EstiloUI.COLOR_PRIMARIO_OSC);
        panelCentral.add(botonVolver, gc);

        // Agregar panel al frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 20);
        add(panelCentral, gbc);

        // === EVENTOS ===
        botonRegistrar.addActionListener(e -> registrarUsuario());
        botonVolver.addActionListener(e -> volverAlLogin());
    }

    private void registrarUsuario() {
        String nombreUsuario = campoNombreUsuario.getText();
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        String telefono = campoTelefono.getText();
        String correo = campoCorreo.getText();
        String contrasena = new String(campoContrasena.getPassword());
        String confirmar = new String(campoConfirmarContrasena.getPassword());

        // Validar campos usando la Fabrica
        String error = FabricaUsuario.validarCampos(nombre, apellido, telefono,
                correo, nombreUsuario, contrasena, confirmar);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Campo Faltante", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si el usuario ya existe
        if (ServicioUsuario.getInstancia().buscarPorUsuario(nombreUsuario) != null) {
            JOptionPane.showMessageDialog(this,
                    "El nombre de usuario '" + nombreUsuario + "' ya existe.\nPor favor elija otro.",
                    "Usuario Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear y registrar usuario usando la Fabrica
        Usuario nuevo = FabricaUsuario.crearUsuario(nombre, apellido, telefono,
                correo, nombreUsuario, contrasena);

        if (ServicioUsuario.getInstancia().registrar(nuevo)) {
            JOptionPane.showMessageDialog(this,
                    "Usuario registrado exitosamente!\nAhora puede iniciar sesion.",
                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            volverAlLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar el usuario. Intente de nuevo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAlLogin() {
        this.dispose();
        pantallaLogin.limpiarCampos();
        pantallaLogin.setVisible(true);
    }
}
