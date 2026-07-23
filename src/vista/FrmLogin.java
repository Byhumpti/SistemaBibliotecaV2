package vista;

import controlador.UsuarioDAO;
import modelo.Usuario;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import modelo.Cliente;

public class FrmLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;

    public FrmLogin() {
        initComponentsCustom();
    }

    private void initComponentsCustom() {

    setTitle("Sistema de Biblioteca - Inicio de Sesión");
    setSize(420, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);

    JLabel lblTitulo = new JLabel("BIBLIOTECA VIRTUAL", JLabel.CENTER);
    lblTitulo.setBounds(50, 20, 320, 30);
    lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
    add(lblTitulo);


    JLabel lblUser = new JLabel("Usuario:");
    lblUser.setBounds(60, 75, 80, 25);
    lblUser.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
    add(lblUser);

    txtUsuario = new JTextField();
    txtUsuario.setBounds(150, 75, 180, 28);
    add(txtUsuario);


    JLabel lblPass = new JLabel("Contraseña:");
    lblPass.setBounds(60, 115, 90, 25);
    lblPass.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
    add(lblPass);

    txtPassword = new JPasswordField();
    txtPassword.setBounds(150, 115, 180, 28);
    add(txtPassword);


    btnIngresar = new JButton("Ingresar");
    btnIngresar.setBounds(70, 180, 120, 35);
    add(btnIngresar);


    btnRegistrar = new JButton("Registrarse");
    btnRegistrar.setBounds(220, 180, 120, 35);
    add(btnRegistrar);


    btnIngresar.addActionListener(e -> validarLogin());
    btnRegistrar.addActionListener(e -> registrarNuevoUsuario());
}

    private void validarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Usamos UsuarioDAO para validar
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.login(usuario, password);

if (u != null) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido, " + u.getNombre() + "!");
            FrmMenu menu = new FrmMenu(u);
menu.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarNuevoUsuario() {
        JTextField txtNombreReal = new JTextField();
        JTextField newUser = new JTextField();
        JPasswordField newPass = new JPasswordField();

        Object[] fields = {
            "Nombre Completo:", txtNombreReal,
            "Nuevo Usuario:", newUser,
            "Nueva Contraseña:", newPass
        };

        int option = JOptionPane.showConfirmDialog(
            this, 
            fields, 
            "Registro de Nuevo Usuario", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String nombreReal = txtNombreReal.getText().trim();
            String u = newUser.getText().trim();
            String p = new String(newPass.getPassword()).trim();

            if (nombreReal.isEmpty() || u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario nuevoUsuario = new Cliente(0, nombreReal, u, p);
            UsuarioDAO dao = new UsuarioDAO();

            if (dao.registrarUsuario(nuevoUsuario)) {
                JOptionPane.showMessageDialog(this, "¡Usuario creado exitosamente! Ya puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtUsuario.setText(u);
                txtPassword.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar. El usuario ya podría existir.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmLogin().setVisible(true));
    }
}