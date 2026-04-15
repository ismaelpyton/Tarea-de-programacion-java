package servicio;

import modelo.Producto;
import util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioProducto implements IServicioProducto {

    private static ServicioProducto instancia;

    private ServicioProducto() {}

    public static ServicioProducto getInstancia() {
        if (instancia == null) {
            instancia = new ServicioProducto();
        }
        return instancia;
    }

    private Connection con() {
        return ConexionDB.getInstancia().getConexion();
    }

    @Override
    public boolean registrar(Producto p) {
        String sql = "INSERT INTO productos (NombreProducto, marcaProducto, categoriaProducto, precioProducto, stockProducto) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setInt(4, (int) p.getPrecio());
            ps.setInt(5, p.getCantidadDisponible());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY idProducto ASC";
        try (Statement stmt = con().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto(
                        rs.getString("NombreProducto"),
                        rs.getString("marcaProducto"),
                        rs.getString("categoriaProducto"),
                        rs.getDouble("precioProducto"),
                        rs.getInt("stockProducto")
                );
                p.setId(rs.getInt("idProducto"));
                productos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET NombreProducto = ?, marcaProducto = ?, categoriaProducto = ?, "
                   + "precioProducto = ?, stockProducto = ? WHERE idProducto = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setInt(4, (int) p.getPrecio());
            ps.setInt(5, p.getCantidadDisponible());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE idProducto = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
