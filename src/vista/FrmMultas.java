package vista;

import controlador.MultaDAO;
import modelo.Multa;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmMultas extends JFrame {
    private JTextField txtIdPrestamo, txtMonto;
    private JButton btnRegistrar, btnPagar, btnEliminar, btnRegresar;
    private JTable jTableMultas;
    private DefaultTableModel modeloTabla;

    public FrmMultas() {
        setTitle("Gestión de Multas - Biblioteca");
        setSize(650, 360);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("ID Préstamo:");
        lbl1.setBounds(20, 20, 90, 25);
        add(lbl1);
        txtIdPrestamo = new JTextField();
        txtIdPrestamo.setBounds(110, 20, 130, 25);
        add(txtIdPrestamo);

        JLabel lbl2 = new JLabel("Monto ($):");
        lbl2.setBounds(20, 60, 90, 25);
        add(lbl2);
        txtMonto = new JTextField();
        txtMonto.setBounds(110, 60, 130, 25);
        add(txtMonto);

        btnRegistrar = new JButton("Registrar Multa");
        btnRegistrar.setBounds(20, 105, 220, 28);
        add(btnRegistrar);

        btnPagar = new JButton("Marcar como Pagada");
        btnPagar.setBounds(20, 140, 220, 28);
        add(btnPagar);

        // Botón Eliminar Multa
        btnEliminar = new JButton("Eliminar Multa");
        btnEliminar.setBounds(20, 175, 220, 28);
        add(btnEliminar);

        btnRegresar = new JButton("Volver al Menú");
        btnRegresar.setBounds(20, 210, 220, 28);
        add(btnRegresar);

        // Tabla de multas
        jTableMultas = new JTable();
        modeloTabla = new DefaultTableModel(new String[]{"ID Multa", "ID Préstamo", "Monto", "Estado"}, 0);
        jTableMultas.setModel(modeloTabla);
        JScrollPane scroll = new JScrollPane(jTableMultas);
        scroll.setBounds(260, 20, 350, 240);
        add(scroll);

        btnRegistrar.addActionListener(e -> registrarMulta());
        btnPagar.addActionListener(e -> pagarMultaSelected());
        btnEliminar.addActionListener(e -> eliminarMultaSelected());
        
        btnRegresar.addActionListener(e -> {
            new FrmMenu().setVisible(true);
            this.dispose();
        });

        listarMultas();
    }

    private void registrarMulta() {
        try {
            int idPrestamo = Integer.parseInt(txtIdPrestamo.getText().trim());
            double monto = Double.parseDouble(txtMonto.getText().trim());

            Multa m = new Multa(0, idPrestamo, monto, "Pendiente");
            MultaDAO dao = new MultaDAO();

            if (dao.registrarMulta(m)) {
                JOptionPane.showMessageDialog(this, "¡Multa registrada correctamente!");
                txtIdPrestamo.setText("");
                txtMonto.setText("");
                listarMultas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la multa.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifica que el ID y el monto sean numéricos.");
        }
    }

    private void pagarMultaSelected() {
        int filaSeleccionada = jTableMultas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idMulta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            MultaDAO dao = new MultaDAO();
            if (dao.pagarMulta(idMulta)) {
                JOptionPane.showMessageDialog(this, "¡Multa marcada como pagada y préstamo finalizado!");
                listarMultas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la multa.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una multa de la tabla para pagar.");
        }
    }

    private void eliminarMultaSelected() {
        int filaSeleccionada = jTableMultas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idMulta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que deseas eliminar esta multa?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirmacion == JOptionPane.YES_OPTION) {
                MultaDAO dao = new MultaDAO();
                if (dao.eliminarMulta(idMulta)) {
                    JOptionPane.showMessageDialog(this, "¡Multa eliminada con éxito!");
                    listarMultas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la multa.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una multa de la tabla para eliminar.");
        }
    }

    private void listarMultas() {
        MultaDAO dao = new MultaDAO();
        List<Multa> lista = dao.listarMultas();
        modeloTabla.setRowCount(0);
        for (Multa m : lista) {
            modeloTabla.addRow(new Object[]{
                m.getId(),
                m.getIdPrestamo(),
                m.getMonto(),
                m.getEstado()
            });
        }
    }
}