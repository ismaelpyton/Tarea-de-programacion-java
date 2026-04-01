package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase utilitaria con estilos visuales para toda la aplicacion.
 * Centraliza colores, fuentes y metodos de creacion de componentes.
 */
public class EstiloUI {

    // === COLORES ===
    public static final Color COLOR_PRIMARIO     = new Color(41, 128, 185);
    public static final Color COLOR_PRIMARIO_OSC  = new Color(31, 97, 141);
    public static final Color COLOR_SECUNDARIO    = new Color(52, 152, 219);
    public static final Color COLOR_FONDO         = new Color(236, 240, 241);
    public static final Color COLOR_PANEL         = new Color(255, 255, 255);
    public static final Color COLOR_TEXTO         = new Color(44, 62, 80);
    public static final Color COLOR_TEXTO_CLARO   = new Color(127, 140, 141);
    public static final Color COLOR_EXITO         = new Color(39, 174, 96);
    public static final Color COLOR_EXITO_OSC     = new Color(30, 132, 73);
    public static final Color COLOR_ERROR         = new Color(231, 76, 60);
    public static final Color COLOR_ERROR_OSC     = new Color(192, 57, 43);
    public static final Color COLOR_BORDE         = new Color(189, 195, 199);
    public static final Color COLOR_FONDO_CAMPO   = new Color(248, 249, 250);

    // === FUENTES ===
    public static final Font FUENTE_TITULO    = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FUENTE_NORMAL    = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_BOTON     = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_PEQUENA   = new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Crea un boton estilizado con efecto hover.
     */
    public static JButton crearBoton(String texto, Color colorFondo, Color colorHover) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 42));

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorFondo);
            }
        });

        return boton;
    }

    /**
     * Crea un campo de texto estilizado.
     */
    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_NORMAL);
        campo.setForeground(COLOR_TEXTO);
        campo.setBackground(COLOR_FONDO_CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        campo.setPreferredSize(new Dimension(300, 40));
        return campo;
    }

    /**
     * Crea un campo de contrasena estilizado (texto oculto).
     */
    public static JPasswordField crearCampoContrasena() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(FUENTE_NORMAL);
        campo.setForeground(COLOR_TEXTO);
        campo.setBackground(COLOR_FONDO_CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        campo.setPreferredSize(new Dimension(300, 40));
        return campo;
    }

    /**
     * Crea una etiqueta estilizada.
     */
    public static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_NORMAL);
        etiqueta.setForeground(COLOR_TEXTO);
        return etiqueta;
    }

    /**
     * Aplica estilos modernos a un JTable.
     */
    public static void aplicarEstiloTabla(JTable tabla) {
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(38);
        tabla.setSelectionBackground(COLOR_SECUNDARIO);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(224, 224, 224));
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setFillsViewportHeight(true);

        // Estilo del header (Forzamos el pintado con un Renderer en Windows)
        tabla.getTableHeader().setFont(FUENTE_BOTON);
        tabla.getTableHeader().setPreferredSize(
                new Dimension(tabla.getTableHeader().getWidth(), 45));
        tabla.getTableHeader().setReorderingAllowed(false);
        
        javax.swing.table.DefaultTableCellRenderer headerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        headerRenderer.setBackground(COLOR_PRIMARIO);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(FUENTE_BOTON);
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getTableHeader().setDefaultRenderer(headerRenderer);
    }
}
