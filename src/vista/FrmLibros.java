package vista;

import controlador.LibroDAO;
import modelo.Libro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmLibros extends JFrame {
    private JTextField txtTitulo, txtAutor, txtIsbn, txtStock, txtCategoria;
    private JButton btnRegistrar, btnEliminar, btnRegresar;
    private JTable jTable1;
    private DefaultTableModel modeloTabla;

    public FrmLibros() {
        setTitle("Gestión de Catálogo de Libros - Biblioteca");
        setSize(720, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("Título:");
        lbl1.setBounds(20, 20, 80, 25);
        add(lbl1);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(100, 20, 130, 25);
        add(txtTitulo);

        JLabel lbl2 = new JLabel("Autor:");
        lbl2.setBounds(20, 60, 80, 25);
        add(lbl2);
        txtAutor = new JTextField();
        txtAutor.setBounds(100, 60, 130, 25);
        add(txtAutor);

        JLabel lbl3 = new JLabel("ISBN:");
        lbl3.setBounds(20, 100, 80, 25);
        add(lbl3);
        txtIsbn = new JTextField();
        txtIsbn.setBounds(100, 100, 130, 25);
        add(txtIsbn);

        JLabel lbl4 = new JLabel("Stock:");
        lbl4.setBounds(20, 140, 80, 25);
        add(lbl4);
        txtStock = new JTextField();
        txtStock.setBounds(100, 140, 130, 25);
        add(txtStock);

        JLabel lbl5 = new JLabel("Categoría:");
        lbl5.setBounds(20, 180, 80, 25);
        add(lbl5);
        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 180, 130, 25);
        add(txtCategoria);

        btnRegistrar = new JButton("Registrar Libro");
        btnRegistrar.setBounds(20, 220, 210, 28);
        add(btnRegistrar);

        // Botón para eliminar el libro seleccionado en la tabla
        btnEliminar = new JButton("Eliminar Libro");
        btnEliminar.setBounds(20, 255, 210, 28);
        add(btnEliminar);

        // Botón para volver al menú
        btnRegresar = new JButton("Volver al Menú");
        btnRegresar.setBounds(20, 290, 210, 28);
        add(btnRegresar);

        // Tabla con las columnas
        jTable1 = new JTable();
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN", "Stock", "Categoría"}, 0);
        jTable1.setModel(modeloTabla);
        JScrollPane scroll = new JScrollPane(jTable1);
        scroll.setBounds(250, 20, 435, 295);
        add(scroll);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                registrarLibro();
            }
        });

        // Evento del botón eliminar
        btnEliminar.addActionListener(e -> eliminarLibroSelected());

        btnRegresar.addActionListener(e -> {
            new FrmMenu().setVisible(true);
            this.dispose();
        });

        listarLibros();
    }

    private void registrarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            int stock = Integer.parseInt(txtStock.getText().trim());
            String categoria = txtCategoria.getText().trim();

            if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos de texto.");
                return;
            }

            Libro libro = new Libro(0, titulo, autor, isbn, stock, categoria);
            LibroDAO dao = new LibroDAO();

            if (dao.registrarLibro(libro)) {
                JOptionPane.showMessageDialog(this, "¡Libro registrado con éxito!");
                txtTitulo.setText("");
                txtAutor.setText("");
                txtIsbn.setText("");
                txtStock.setText("");
                txtCategoria.setText("");
                listarLibros();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el libro en la base de datos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número válido.");
        }
    }

    private void eliminarLibroSelected() {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idLibro = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que deseas eliminar este libro?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirmacion == JOptionPane.YES_OPTION) {
                LibroDAO dao = new LibroDAO();
                if (dao.eliminarLibro(idLibro)) { // Asegúrate de tener este método en tu LibroDAO
                    JOptionPane.showMessageDialog(this, "¡Libro eliminado con éxito!");
                    listarLibros();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el libro (puede tener préstamos asociados).");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla para eliminar.");
        }
    }

    private void listarLibros() {
        LibroDAO dao = new LibroDAO();
        List<Libro> lista = dao.listarLibros();
        modeloTabla.setRowCount(0);
        for (Libro l : lista) {
            modeloTabla.addRow(new Object[]{
                l.getId(),
                l.getTitulo(),
                l.getAutor(),
                l.getIsbn(),
                l.getStock(),
                l.getCategoria()
            });
        }
    }
}