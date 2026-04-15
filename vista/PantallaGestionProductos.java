package vista;

import modelo.Producto;
import servicio.ServicioProducto;
import fabricas.FabricaProducto;
import util.EstiloUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PantallaGestionProductos extends JPanel {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private ServicioProducto servicioProducto;
    private modelo.Usuario usuarioLogueado;

    public PantallaGestionProductos(modelo.Usuario usuario) {
        this.usuarioLogueado = usuario;
        servicioProducto = ServicioProducto.getInstancia();

        //setTitle("Gestion de Productos");
        //setSize(900, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        this.setBackground(EstiloUI.COLOR_FONDO);

        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(EstiloUI.COLOR_PRIMARIO);
        panelHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Inventario de Productos");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        JButton btnVolver = EstiloUI.crearBoton("Volver", Color.DARK_GRAY, Color.GRAY);
        btnVolver.addActionListener(e -> {
            GestorVentana.cambiarPantalla(new PantallaDashboard(usuarioLogueado), "Panel de Control", 500, 350);
            //dispose();
        });

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(btnVolver, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Marca", "Categoria", "Precio", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        EstiloUI.aplicarEstiloTabla(tablaProductos);

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(EstiloUI.COLOR_FONDO, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior / Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(EstiloUI.COLOR_FONDO);

        JButton btnNuevo = EstiloUI.crearBoton("Nuevo Producto", EstiloUI.COLOR_EXITO, EstiloUI.COLOR_EXITO_OSC);
        JButton btnEditar = EstiloUI.crearBoton("Editar", EstiloUI.COLOR_PRIMARIO, EstiloUI.COLOR_PRIMARIO_OSC);
        JButton btnEliminar = EstiloUI.crearBoton("Eliminar", EstiloUI.COLOR_ERROR, EstiloUI.COLOR_ERROR_OSC);

        btnNuevo.addActionListener(e -> mostrarFormulario(null));
        btnEditar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                Producto p = obtenerProductoPorId(id);
                if (p != null) mostrarFormulario(p);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar el producto?",
                        "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (servicioProducto.eliminar(id)) {
                        JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
                        cargarDatos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();
    }

    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Producto> productos = servicioProducto.obtenerTodos();
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getMarca(),
                    p.getCategoria(), p.getPrecio() + "$", p.getCantidadDisponible()
            });
        }
    }

    private Producto obtenerProductoPorId(int id) {
        for (Producto p : servicioProducto.obtenerTodos()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private void mostrarFormulario(Producto productoObj) {
        JDialog dialog = new JDialog(GestorVentana.getMarco(), productoObj == null ? "Nuevo Producto" : "Editar Producto", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 15));
        panelForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField txtNombre = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();

        if (productoObj != null) {
            txtNombre.setText(productoObj.getNombre());
            txtMarca.setText(productoObj.getMarca());
            txtCategoria.setText(productoObj.getCategoria());
            txtPrecio.setText(String.valueOf(productoObj.getPrecio()));
            txtStock.setText(String.valueOf(productoObj.getCantidadDisponible()));
        }

        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("Marca:")); panelForm.add(txtMarca);
        panelForm.add(new JLabel("Categoria:")); panelForm.add(txtCategoria);
        panelForm.add(new JLabel("Precio ($):")); panelForm.add(txtPrecio);
        panelForm.add(new JLabel("Stock:")); panelForm.add(txtStock);

        dialog.add(panelForm, BorderLayout.CENTER);

        JButton btnGuardar = EstiloUI.crearBoton("Guardar", EstiloUI.COLOR_PRIMARIO, EstiloUI.COLOR_PRIMARIO_OSC);
        btnGuardar.addActionListener(e -> {
            try {
                Producto p = FabricaProducto.crearProducto(
                        txtNombre.getText(), txtMarca.getText(),
                        txtCategoria.getText(), txtPrecio.getText(), txtStock.getText()
                );
                
                boolean exito;
                if (productoObj == null) {
                    exito = servicioProducto.registrar(p);
                } else {
                    p.setId(productoObj.getId());
                    exito = servicioProducto.actualizar(p);
                }

                if (exito) {
                    JOptionPane.showMessageDialog(dialog, "Guardado exitosamente.");
                    dialog.dispose();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al guardar en BD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error de Validacion", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel panelFooter = new JPanel();
        panelFooter.add(btnGuardar);
        dialog.add(panelFooter, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
