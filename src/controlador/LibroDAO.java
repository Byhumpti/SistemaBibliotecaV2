package controlador;

import modelo.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // Método para listar todos los libros del inventario
    public List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setStock(rs.getInt("stock"));
                libro.setCategoria(rs.getString("categoria"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
        
        return lista;
    }

    // Método para registrar un nuevo libro
    public boolean registrarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, stock, categoria) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setInt(4, libro.getStock());
            ps.setString(5, libro.getCategoria());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar el stock de un libro (útil al realizar un préstamo o devolución)
    public boolean actualizarStock(int idLibro, int nuevoStock) {
        String sql = "UPDATE libros SET stock = ? WHERE id = ?";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idLibro);
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminarLibro(int id) {
    String sql = "DELETE FROM libros WHERE id = ?";
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        return false;
    }
}
}