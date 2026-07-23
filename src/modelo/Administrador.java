package modelo;

public class Administrador extends Usuario {

    public Administrador() {
        super();
        setTipo("Administrador");
    }

    public Administrador(int id, String nombre, String username, String password) {
        super(id, nombre, username, password, "Administrador");
    }

    @Override
    public String obtenerPermisos() {
        return "Acceso total al sistema";
    }
}