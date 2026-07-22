package controlador;

import modelo.Multa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultaDAO {

    // Método para registrar una multa por retraso
    public boolean registrarMulta(Multa multa) {
        String sql = "INSERT INTO multas (id_prestamo, monto, estado) VALUES (?, ?, ?)";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, multa.getIdPrestamo());
            ps.setDouble(2, multa.getMonto());
            ps.setString(3, multa.getEstado());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar multa: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todas las multas
    public List<Multa> listarMultas() {
        List<Multa> lista = new ArrayList<>();
        String sql = "SELECT * FROM multas";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Multa m = new Multa();
                m.setId(rs.getInt("id"));
                m.setIdPrestamo(rs.getInt("id_prestamo"));
                m.setMonto(rs.getDouble("monto"));
                m.setEstado(rs.getString("estado"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar multas: " + e.getMessage());
        }
        
        return lista;
    }

    // Método para cambiar el estado de la multa a 'Pagada'
public boolean pagarMulta(int idMulta) {
    String sqlMulta = "UPDATE multas SET estado = 'Pagada' WHERE id = ?";
    // Esta consulta busca el id_prestamo de esta multa para actualizar también el préstamo
    String sqlBuscarPrestamo = "SELECT id_prestamo FROM multas WHERE id = ?";
    String sqlPrestamo = "UPDATE prestamos SET estado = 'Finalizado' WHERE id = ?";

    try (Connection con = Conexion.conectar()) {
        // 1. Obtener el ID del préstamo asociado a esta multa
        PreparedStatement psBuscar = con.prepareStatement(sqlBuscarPrestamo);
        psBuscar.setInt(1, idMulta);
        ResultSet rs = psBuscar.executeQuery();
        
        int idPrestamo = -1;
        if (rs.next()) {
            idPrestamo = rs.getInt("id_prestamo");
        }

        // 2. Actualizar el estado de la multa a 'Pagada'
        PreparedStatement psMulta = con.prepareStatement(sqlMulta);
        psMulta.setInt(1, idMulta);
        psMulta.executeUpdate();

        // 3. Si encontramos el préstamo asociado, actualizamos su estado automáticamente
        if (idPrestamo != -1) {
            PreparedStatement psPrest = con.prepareStatement(sqlPrestamo);
            psPrest.setInt(1, idPrestamo);
            psPrest.executeUpdate();
        }

        return true;
    } catch (SQLException e) {
        System.out.println("Error al pagar multa y actualizar préstamo: " + e.getMessage());
        return false;
    }
}
public boolean eliminarMulta(int id) {
    String sql = "DELETE FROM multas WHERE id = ?";
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al eliminar multa: " + e.getMessage());
        return false;
    }
}
}