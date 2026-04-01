import vista.PantallaLogin;
import javax.swing.*;

/**
 * Clase principal - Punto de entrada de la aplicacion.
 * Configura el Look and Feel y lanza la pantalla de Login.
 */
public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema para apariencia nativa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, usa el default
        }

        // Lanzar la aplicacion en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            PantallaLogin login = new PantallaLogin();
            login.setVisible(true);
        });
    }
}
