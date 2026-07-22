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

public class FrmLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;

    public FrmLogin() {
        initComponentsCustom();
    }

    private void initComponentsCustom() {
        setTitle("Acceso al Sistema - Biblioteca");
        setSize(380, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(40, 30, 80, 25);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 180, 25);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(40, 70, 80, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 180, 25);
        add(txtPassword);

        btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setBounds(40, 120, 130, 30);
        add(btnIngresar);

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(190, 120, 110, 30);
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
            FrmMenu menu = new FrmMenu(); // <-- Abrimos el panel de control general
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

            Usuario nuevoUsuario = new Usuario(0, nombreReal, u, p, "Cliente");
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