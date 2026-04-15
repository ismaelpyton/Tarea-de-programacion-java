package servicio;

import modelo.Usuario;
import util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion del servicio de usuarios con SQLite.
 * Patron Singleton: una sola instancia del servicio en toda la aplicacion.
 * Demuestra: Polimorfismo (implementa la interfaz IServicioUsuario).
 */
public class ServicioUsuario implements IServicioUsuario {

    private static ServicioUsuario instancia;
    private Connection conexion;

    // Constructor privado (Singleton)
    private ServicioUsuario() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    // Metodo Singleton
    public static ServicioUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ServicioUsuario();
        }
        return instancia;
    }

    @Override
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (UserName, Nombre, Apellido, Telefono, Email, Password) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, usuario.getContrasena());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario en la nube: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario autenticar(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE UserName = ? AND Password = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY idUser ASC";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET Nombre = ?, Apellido = ?, Telefono = ?, "
                   + "Email = ?, UserName = ?, Password = ? WHERE idUser = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getNombreUsuario());
            ps.setString(6, usuario.getContrasena());
            ps.setInt(7, usuario.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE idUser = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario buscarPorUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE UserName = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario usando los nombres de las columnas en MySQL remotas.
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario(
                rs.getString("Nombre"),
                rs.getString("Apellido"),
                rs.getString("Telefono"),
                rs.getString("Email"),
                rs.getString("UserName"),
                rs.getString("Password")
        );
        u.setId(rs.getInt("idUser"));
        return u;
    }
}
