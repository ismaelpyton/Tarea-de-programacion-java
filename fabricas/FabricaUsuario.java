package fabricas;

import modelo.Usuario;

/**
 * Patron de diseno Factory.
 * Centraliza la creacion de objetos Usuario con validacion.
 */
public class FabricaUsuario {

    /**
     * Crea un nuevo Usuario validando que los campos no esten vacios.
     * Retorna null si algun campo esta vacio, junto con un mensaje del campo faltante.
     */
    public static Usuario crearUsuario(String nombre, String apellido, String telefono,
                                        String correo, String nombreUsuario,
                                        String contrasena) {
        return new Usuario(nombre, apellido, telefono, correo, nombreUsuario, contrasena);
    }

    /**
     * Valida los campos del formulario de registro.
     * Retorna un mensaje con el campo faltante, o null si todo esta correcto.
     */
    public static String validarCampos(String nombre, String apellido, String telefono,
                                        String correo, String nombreUsuario,
                                        String contrasena, String confirmarContrasena) {
        if (nombreUsuario.trim().isEmpty()) return "El campo 'Nombre de Usuario' es obligatorio.";
        if (nombre.trim().isEmpty()) return "El campo 'Nombre' es obligatorio.";
        if (apellido.trim().isEmpty()) return "El campo 'Apellido' es obligatorio.";
        if (telefono.trim().isEmpty()) return "El campo 'Telefono' es obligatorio.";
        if (correo.trim().isEmpty()) return "El campo 'Correo Electronico' es obligatorio.";
        if (contrasena.trim().isEmpty()) return "El campo 'Contrasena' es obligatorio.";
        if (confirmarContrasena.trim().isEmpty()) return "Debe confirmar la contrasena.";
        if (!contrasena.equals(confirmarContrasena)) return "Las contrasenas no coinciden.";
        return null; // Todo valido
    }
}
