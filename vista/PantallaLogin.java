package vista;

import servicio.ServicioUsuario;
import modelo.Usuario;
import util.EstiloUI;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla de Login - Ventana inicial de la aplicacion.
 * Permite ingresar con usuario y contrasena.
 */
public class PantallaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonEntrar;
    private JButton botonRegistrarse;

    public PantallaLogin() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestion de Usuarios - Login");
        setSize(480, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloUI.COLOR_FONDO);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 30, 6, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel central blanco con sombra visual
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        panelCentral.setBackground(EstiloUI.COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 0, 6, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = GridBagConstraints.REMAINDER;

        // Icono de usuario (emoji-like)
        JLabel iconoUsuario = new JLabel("\u2B50", SwingConstants.CENTER);
        iconoUsuario.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconoUsuario.setForeground(EstiloUI.COLOR_PRIMARIO);
        panelCentral.add(iconoUsuario, gc);

        // Titulo
        JLabel titulo = new JLabel("INICIAR SESION", SwingConstants.CENTER);
        titulo.setFont(EstiloUI.FUENTE_TITULO);
        titulo.setForeground(EstiloUI.COLOR_PRIMARIO);
        gc.insets = new Insets(2, 0, 15, 0);
        panelCentral.add(titulo, gc);

        // Subtitulo
        JLabel subtitulo = new JLabel("Ingrese sus credenciales para acceder", SwingConstants.CENTER);
        subtitulo.setFont(EstiloUI.FUENTE_PEQUENA);
        subtitulo.setForeground(EstiloUI.COLOR_TEXTO_CLARO);
        gc.insets = new Insets(0, 0, 20, 0);
        panelCentral.add(subtitulo, gc);

        // Label Usuario
        gc.insets = new Insets(4, 0, 2, 0);
        JLabel lblUsuario = EstiloUI.crearEtiqueta("Nombre de Usuario:");
        panelCentral.add(lblUsuario, gc);

        // Campo usuario
        gc.insets = new Insets(0, 0, 10, 0);
        campoUsuario = EstiloUI.crearCampoTexto();
        panelCentral.add(campoUsuario, gc);

        // Label Contrasena
        gc.insets = new Insets(4, 0, 2, 0);
        JLabel lblContrasena = EstiloUI.crearEtiqueta("Contrasena:");
        panelCentral.add(lblContrasena, gc);

        // Campo contrasena (oculto)
        gc.insets = new Insets(0, 0, 20, 0);
        campoContrasena = EstiloUI.crearCampoContrasena();
        panelCentral.add(campoContrasena, gc);

        // Boton Entrar
        gc.insets = new Insets(0, 0, 10, 0);
        botonEntrar = EstiloUI.crearBoton("Entrar", EstiloUI.COLOR_PRIMARIO, EstiloUI.COLOR_PRIMARIO_OSC);
        botonEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panelCentral.add(botonEntrar, gc);

        // Boton Registrarse
        gc.insets = new Insets(0, 0, 0, 0);
        botonRegistrarse = EstiloUI.crearBoton("Registrarse", EstiloUI.COLOR_EXITO, EstiloUI.COLOR_EXITO_OSC);
        botonRegistrarse.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panelCentral.add(botonRegistrarse, gc);

        // Agregar panel central al frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelCentral, gbc);

        // === EVENTOS ===
        botonEntrar.addActionListener(e -> iniciarSesion());
        botonRegistrarse.addActionListener(e -> abrirRegistro());

        // Enter key para login
        campoContrasena.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        // Validar campos vacios
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contrasena.\nSi no esta registrado, debe registrarse.",
                    "Error de Inicio de Sesion",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Autenticar
        Usuario usuarioAutenticado = ServicioUsuario.getInstancia().autenticar(usuario, contrasena);
        if (usuarioAutenticado != null) {
            this.dispose(); // Cierra la ventana de login
            new PantallaPrincipal(usuarioAutenticado).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contrasena incorrectos.",
                    "Error de Autenticacion",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        this.setVisible(false);
        new PantallaRegistro(this).setVisible(true);
    }

    /**
     * Limpia los campos al volver del registro.
     */
    public void limpiarCampos() {
        campoUsuario.setText("");
        campoContrasena.setText("");
    }
}
