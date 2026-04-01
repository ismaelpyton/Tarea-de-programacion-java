package servicio;

import modelo.Usuario;
import java.util.List;

/**
 * Interfaz que define las operaciones del servicio de usuarios.
 * Demuestra: Abstraccion (contrato que las implementaciones deben cumplir).
 */
public interface IServicioUsuario {

    boolean registrar(Usuario usuario);

    Usuario autenticar(String nombreUsuario, String contrasena);

    List<Usuario> obtenerTodos();

    boolean actualizar(Usuario usuario);

    boolean eliminar(int id);

    Usuario buscarPorUsuario(String nombreUsuario);
}
