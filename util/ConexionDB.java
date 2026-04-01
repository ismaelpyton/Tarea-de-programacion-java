package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para manejar la conexion a SQLite.
 * Patron Singleton: solo una instancia de conexion en toda la aplicacion.
 */
public class ConexionDB {

    private static ConexionDB instancia;
    private Connection conexion;
    private static final String URL = "jdbc:sqlite:usuarios.db";

    // Constructor privado (Singleton)
    private ConexionDB() {
        try {
            // Fuerzo a cargar el Driver en memoria para evitar el error de Classpath en terminal
            Class.forName("org.sqlite.JDBC");
            
            conexion = DriverManager.getConnection(URL);
            crearTabla();
            System.out.println("Conexion a SQLite establecida correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Critico: El conector sqlite-jdbc.jar no esta cargado en memoria.");
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Error Fatal: No se encontro el controlador para la Base de Datos.\n" +
                "Si esta corriendo desde la terminal PowerShell intente:\n" +
                "java -cp '.;lib/sqlite-jdbc.jar' Main", 
                "Error de Base de Datos", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Cierra la app porque no puede arrancar sin base de datos
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null, 
                "No se pudo conectar a SQLite: " + e.getMessage(), 
                "Error de Conexion", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }
    }

    // Metodo Singleton - obtener la unica instancia
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    // Crea la tabla si no existe
    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_usuario TEXT UNIQUE NOT NULL, "
                + "nombre TEXT NOT NULL, "
                + "apellido TEXT NOT NULL, "
                + "telefono TEXT NOT NULL, "
                + "correo TEXT NOT NULL, "
                + "contrasena TEXT NOT NULL)";
        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}
