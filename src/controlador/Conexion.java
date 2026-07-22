package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String HOST = "mysql-mau.alwaysdata.net"; 
    private static final String PUERTO = "3306";
    private static final String BD = "mau_v2"; 
    private static final String USER = "mau"; 
    private static final String PASS = "Libro2026*Secure"; 

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BD 
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";

    public static Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("¡CONEXIÓN EXITOSA A ALWAYSDATA!");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR DRIVER: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR SQL: " + e.getMessage());
        }
        return con;
    }
}