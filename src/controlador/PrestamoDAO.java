package controlador;

import modelo.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean registrarPrestamo(String nombreUsuario, String isbn, String fechaPrestamo, String fechaDevolucion) {
        String sqlUsuario = "SELECT id FROM usuarios WHERE nombre = ?";
        String sqlLibro = "SELECT id FROM libros WHERE isbn = ?";
        String sqlPrestamo = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo, fecha_devolucion, estado) VALUES (?, ?, ?, ?, 'Activo')";
        
        try (Connection con = Conexion.conectar()) {
            // 1. Buscar ID del usuario por su nombre
            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
            psUsuario.setString(1, nombreUsuario);
            ResultSet rsUsuario = psUsuario.executeQuery();
            
            if (!rsUsuario.next()) {
                System.out.println("Error: No se encontró ningún usuario con el nombre: " + nombreUsuario);
                return false;
            }
            int idUsuarioReal = rsUsuario.getInt("id");

            // 2. Buscar ID real del libro por su ISBN
            PreparedStatement psLibro = con.prepareStatement(sqlLibro);
            psLibro.setString(1, isbn);
            ResultSet rsLibro = psLibro.executeQuery();
            
            if (!rsLibro.next()) {
                System.out.println("Error: No se encontró ningún libro con el ISBN: " + isbn);
                return false;
            }
            int idLibroReal = rsLibro.getInt("id");

            // 3. Registrar el préstamo
            PreparedStatement psPrestamo = con.prepareStatement(sqlPrestamo);
            psPrestamo.setInt(1, idUsuarioReal);
            psPrestamo.setInt(2, idLibroReal);
            psPrestamo.setString(3, fechaPrestamo);
            psPrestamo.setString(4, fechaDevolucion);
            
            psPrestamo.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> listarPrestamos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Prestamo p = new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_libro"),
                    rs.getString("fecha_prestamo"),
                    rs.getString("fecha_devolucion"),
                    rs.getString("estado")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminarPrestamo(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar préstamo: " + e.getMessage());
            return false;
        }
    }
}