package vista;

import controlador.PrestamoDAO;
import modelo.Prestamo;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmPrestamos extends JFrame {
    private JTextField txtNombreUsuario, txtIsbnLibro, txtFechaPrestamo, txtFechaDevolucion;
    private JButton btnRegistrar, btnEliminar, btnRegresar;
    private JTable jTablePrestamos;
    private DefaultTableModel modeloTabla;

    public FrmPrestamos() {
        setTitle("Gestión de Préstamos - Biblioteca");
        setSize(780, 360);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("Nombre Usuario:");
        lbl1.setBounds(20, 20, 180, 25);
        add(lbl1);
        txtNombreUsuario = new JTextField();
        txtNombreUsuario.setBounds(200, 20, 140, 25);
        add(txtNombreUsuario);

        JLabel lbl2 = new JLabel("ISBN del Libro:");
        lbl2.setBounds(20, 60, 180, 25);
        add(lbl2);
        txtIsbnLibro = new JTextField();
        txtIsbnLibro.setBounds(200, 60, 140, 25);
        add(txtIsbnLibro);

        JLabel lbl3 = new JLabel("F. Préstamo (YYYY-MM-DD):");
        lbl3.setBounds(20, 100, 180, 25);
        add(lbl3);
        txtFechaPrestamo = new JTextField();
        txtFechaPrestamo.setBounds(200, 100, 140, 25);
        add(txtFechaPrestamo);

        JLabel lbl4 = new JLabel("F. Devolución (YYYY-MM-DD):");
        lbl4.setBounds(20, 140, 180, 25);
        add(lbl4);
        txtFechaDevolucion = new JTextField();
        txtFechaDevolucion.setBounds(200, 140, 140, 25);
        add(txtFechaDevolucion);

        btnRegistrar = new JButton("Registrar Préstamo");
        btnRegistrar.setBounds(20, 185, 320, 28);
        add(btnRegistrar);

        btnEliminar = new JButton("Eliminar Préstamo");
        btnEliminar.setBounds(20, 220, 320, 28);
        add(btnEliminar);

        btnRegresar = new JButton("Volver al Menú");
        btnRegresar.setBounds(20, 255, 320, 28);
        add(btnRegresar);

        // Tabla de préstamos
        jTablePrestamos = new JTable();
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Usu.", "ID Libro", "F. Préstamo", "F. Devolución", "Estado"}, 0);
        jTablePrestamos.setModel(modeloTabla);
        JScrollPane scroll = new JScrollPane(jTablePrestamos);
        scroll.setBounds(360, 20, 395, 265);
        add(scroll);

        btnRegistrar.addActionListener(e -> registrarPrestamo());
        btnEliminar.addActionListener(e -> eliminarPrestamoSelected());
        
        btnRegresar.addActionListener(e -> {
            new FrmMenu().setVisible(true);
            this.dispose();
        });

        listarPrestamos();
    }

private void registrarPrestamo() {
        try {
            String nombre = txtNombreUsuario.getText().trim(); // Se captura como Texto
            String isbn = txtIsbnLibro.getText().trim();
            String fPrestamo = txtFechaPrestamo.getText().trim();
            String fDevolucion = txtFechaDevolucion.getText().trim();

            if (nombre.isEmpty() || isbn.isEmpty() || fPrestamo.isEmpty() || fDevolucion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            PrestamoDAO dao = new PrestamoDAO();

            if (dao.registrarPrestamo(nombre, isbn, fPrestamo, fDevolucion)) {
                JOptionPane.showMessageDialog(this, "¡Préstamo registrado con éxito!");
                txtNombreUsuario.setText("");
                txtIsbnLibro.setText("");
                txtFechaPrestamo.setText("");
                txtFechaDevolucion.setText("");
                listarPrestamos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Verifique que el nombre de usuario y el ISBN existan en la BD.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al registrar el préstamo.");
        }
    }

    private void eliminarPrestamoSelected() {
        int filaSeleccionada = jTablePrestamos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPrestamo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que deseas eliminar este préstamo?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirmacion == JOptionPane.YES_OPTION) {
                PrestamoDAO dao = new PrestamoDAO();
                if (dao.eliminarPrestamo(idPrestamo)) {
                    JOptionPane.showMessageDialog(this, "¡Préstamo eliminado con éxito!");
                    listarPrestamos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el préstamo.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo de la tabla para eliminar.");
        }
    }

    private void listarPrestamos() {
        PrestamoDAO dao = new PrestamoDAO();
        List<Prestamo> lista = dao.listarPrestamos();
        modeloTabla.setRowCount(0);
        for (Prestamo p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getIdUsuario(),
                p.getIdLibro(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.getEstado()
            });
        }
    }
}