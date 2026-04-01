package modelo;

/**
 * Clase Usuario que hereda de Persona.
 * Demuestra: Herencia (extends Persona) y Polimorfismo (override de getInfoResumida y toString).
 */
public class Usuario extends Persona {

    private int id;
    private String nombreUsuario;
    private String contrasena;

    // Constructor vacio
    public Usuario() {}

    // Constructor con parametros
    public Usuario(String nombre, String apellido, String telefono,
                   String correo, String nombreUsuario, String contrasena) {
        super(nombre, apellido, telefono, correo); // Llama al constructor de Persona (Herencia)
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // --- Getters y Setters (Encapsulamiento) ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Polimorfismo - Implementacion del metodo abstracto de Persona
    @Override
    public String getInfoResumida() {
        return getNombre() + " " + getApellido() + " (" + nombreUsuario + ")";
    }

    // Polimorfismo - Override de toString()
    @Override
    public String toString() {
        return "Usuario{usuario='" + nombreUsuario + "', nombre='" +
                getNombre() + " " + getApellido() + "'}";
    }
}
