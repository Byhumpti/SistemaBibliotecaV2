package vista;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class FrmMenu extends JFrame {

    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPermisos;

    private JButton btnLibros;
    private JButton btnPrestamos;
    private JButton btnMultas;

    private Usuario usuarioActual;


    // Constructor usado cuando inicia sesión
    public FrmMenu(Usuario usuario) {

        this.usuarioActual = usuario;

        configurarVentana();

        lblUsuario.setText(
                "Usuario: " + usuario.getNombre() +
                " | Rol: " + usuario.getTipo()
        );

        lblPermisos.setText(
                "Permisos: " + usuario.obtenerPermisos()
        );
    }


    // Constructor vacío para evitar errores en otras ventanas
    public FrmMenu() {

        configurarVentana();

        lblUsuario.setText("Usuario: Sin sesión");
        lblPermisos.setText("Permisos: No definidos");
    }


    private void configurarVentana() {

        setTitle("Menú Principal - Sistema de Biblioteca");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        lblTitulo = new JLabel(
                "PANEL DE CONTROL",
                SwingConstants.CENTER
        );

        lblTitulo.setBounds(50, 30, 350, 30);
        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 18)
        );
        add(lblTitulo);



        lblUsuario = new JLabel();
        lblUsuario.setBounds(30, 70, 380, 25);
        lblUsuario.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        add(lblUsuario);



        lblPermisos = new JLabel();
        lblPermisos.setBounds(30, 95, 380, 25);
        lblPermisos.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );
        add(lblPermisos);



        btnLibros = new JButton("Gestionar Libros");
        btnLibros.setBounds(120, 140, 200, 35);
        add(btnLibros);



        btnPrestamos = new JButton("Gestionar Préstamos");
        btnPrestamos.setBounds(120, 190, 200, 35);
        add(btnPrestamos);



        btnMultas = new JButton("Gestionar Multas");
        btnMultas.setBounds(120, 240, 200, 35);
        add(btnMultas);



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