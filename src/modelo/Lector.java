package modelo;

public class Lector extends Usuario {

    public Lector() {
        super();
        setTipo("Lector");
    }

    public Lector(int id, String nombre, String username, String password) {
        super(id, nombre, username, password, "Lector");
    }

    @Override
    public String obtenerPermisos() {
        return "Acceso para préstamos y consultas";
    }
}