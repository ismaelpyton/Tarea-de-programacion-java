package vista;

import util.EstiloUI;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PantallaDashboard extends JPanel {

    private Usuario usuarioLogueado;

    public PantallaDashboard(Usuario usuario) {
        this.usuarioLogueado = usuario;
        
        //setTitle("Dashboard Principal - Sistema de Gestion");
        //setSize(500, 400); // UI minimalista compacta
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.setBackground(EstiloUI.COLOR_FONDO);

        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(EstiloUI.COLOR_PRIMARIO);
        panelHeader.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel lblTitulo = new JLabel("Panel de Control");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelHeader.add(lblTitulo);
        add(panelHeader, BorderLayout.NORTH);

        // Botones Centrales
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 20, 20));
        panelBotones.setBackground(EstiloUI.COLOR_FONDO);
        panelBotones.setBorder(new EmptyBorder(40, 60, 40, 60));

        JButton btnUsuarios = EstiloUI.crearBoton("Gestion de Usuarios", Color.DARK_GRAY, Color.GRAY);
        btnUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnUsuarios.addActionListener(e -> {
            GestorVentana.cambiarPantalla(new PantallaPrincipal(usuarioLogueado), "Gestion de Usuarios", 900, 600);
            //dispose();
        });

        JButton btnProductos = EstiloUI.crearBoton("Gestion de Productos", Color.DARK_GRAY, Color.GRAY);
        btnProductos.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnProductos.addActionListener(e -> {
            GestorVentana.cambiarPantalla(new PantallaGestionProductos(usuarioLogueado), "Inventario de Productos", 900, 600);
            //dispose();
        });

        panelBotones.add(btnUsuarios);
        panelBotones.add(btnProductos);

        add(panelBotones, BorderLayout.CENTER);

        // Footer con boton de logout
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFooter.setBackground(EstiloUI.COLOR_FONDO);
        panelFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton btnCerrar = EstiloUI.crearBoton("Cerrar Sesion", EstiloUI.COLOR_ERROR, EstiloUI.COLOR_ERROR_OSC);
        btnCerrar.addActionListener(e -> {
            GestorVentana.cambiarPantalla(new PantallaLogin(), "Iniciar Sesion", 400, 500);
            //dispose();
        });
        panelFooter.add(btnCerrar);
        
        add(panelFooter, BorderLayout.SOUTH);
    }
}
