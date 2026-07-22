package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FrmMenu extends JFrame {

    private JButton btnLibros, btnPrestamos, btnMultas;

    public FrmMenu() {
        setTitle("Menú Principal - Sistema de Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("PANEL DE CONTROL", SwingConstants.CENTER);
        lblTitulo.setBounds(50, 20, 300, 30);
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        add(lblTitulo);

        btnLibros = new JButton("Gestionar Libros");
        btnLibros.setBounds(100, 70, 200, 35);
        add(btnLibros);

        btnPrestamos = new JButton("Gestionar Préstamos");
        btnPrestamos.setBounds(100, 120, 200, 35);
        add(btnPrestamos);

        btnMultas = new JButton("Gestionar Multas");
        btnMultas.setBounds(100, 170, 200, 35);
        add(btnMultas);

        // Eventos para abrir cada ventana y cerrar el menú actual
        btnLibros.addActionListener(e -> {
            new FrmLibros().setVisible(true);
            this.dispose();
        });

        btnPrestamos.addActionListener(e -> {
            new FrmPrestamos().setVisible(true);
            this.dispose();
        });

        btnMultas.addActionListener(e -> {
            new FrmMultas().setVisible(true);
            this.dispose();
        });
    }
}