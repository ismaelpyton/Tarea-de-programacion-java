package servicio;

import modelo.Producto;
import java.util.List;

public interface IServicioProducto {
    boolean registrar(Producto producto);
    List<Producto> obtenerTodos();
    boolean actualizar(Producto producto);
    boolean eliminar(int id);
}
