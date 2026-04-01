package modelo;

/**
 * Clase abstracta que representa una Persona.
 * Demuestra: Abstraccion (clase abstracta + metodo abstracto) y Encapsulamiento (atributos privados).
 */
public abstract class Persona {

    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    // Constructor vacio
    public Persona() {}

    // Constructor con parametros
    public Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    // --- Getters y Setters (Encapsulamiento) ---
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Metodo abstracto - Abstraccion (las subclases DEBEN implementarlo)
    public abstract String getInfoResumida();

    // Polimorfismo - Override de toString()
    @Override
    public String toString() {
        return "Persona{nombre='" + nombre + "', apellido='" + apellido + "'}";
    }
}
