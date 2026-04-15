package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexion a MySQL remota (Aiven Cloud).
 * Patron Singleton con reconexion automatica si la conexion se pierde.
 */
public class ConexionDB {

    private static ConexionDB instancia;
    private Connection conexion;

    private static final String URL =
        "jdbc:mysql://almacenitla-db-itla-3837.e.aivencloud.com:25037/almacenitlafinal" +
        "?useSSL=true&autoReconnect=true&connectTimeout=10000&socketTimeout=30000";
    private static final String USER = "avnadmin";
    private static final String PASS = "AVNS_pPa2xcIg1UbjOzcsoMg";

    private ConexionDB() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexion a MySQL remota establecida correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Critico: El conector mysql-connector-j no esta en el classpath.");
            javax.swing.JOptionPane.showMessageDialog(null,
                "Error Fatal: No se encontro el controlador para MySQL.\n" +
                "Ejecute la app con: java -cp \".;lib/mysql-connector-j.jar\" Main",
                "Error de Base de Datos", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la DB remota: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null,
                "No se pudo conectar al servidor MySQL en la nube.\nVerifique su conexion a internet.",
                "Error de Conexion", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    /**
     * Devuelve la conexion activa. Si la conexion se perdio, reconecta automaticamente.
     */
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed() || !conexion.isValid(3)) {
                System.out.println("Conexion perdida. Reconectando...");
                conectar();
            }
        } catch (SQLException e) {
            conectar();
        }
        return conexion;
    }
}
