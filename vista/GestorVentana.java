package vista;

import javax.swing.*;

public class GestorVentana {
    private static JFrame marcoPrincipal;

    public static void iniciar() {
        marcoPrincipal = new JFrame();
        marcoPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marcoPrincipal.setResizable(false);
        marcoPrincipal.setLocationRelativeTo(null);
        PantallaLogin login = new PantallaLogin();
        cambiarPantalla(login, "Iniciar Sesion", 400, 500);
        marcoPrincipal.setVisible(true);
    }

    public static void cambiarPantalla(JPanel nuevaPantalla, String titulo, int ancho, int alto) {
        marcoPrincipal.setTitle(titulo);
        marcoPrincipal.setSize(ancho, alto);
        marcoPrincipal.setContentPane(nuevaPantalla);
        marcoPrincipal.setLocationRelativeTo(null); 
        marcoPrincipal.revalidate();
        marcoPrincipal.repaint();
    }
    
    public static JFrame getMarco() {
        return marcoPrincipal;
    }
}
