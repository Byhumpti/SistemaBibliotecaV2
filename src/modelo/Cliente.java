package modelo;

public class Cliente extends Usuario {

    public Cliente() {
        super();
        setTipo("Cliente");
    }

    public Cliente(int id, String nombre, String username, String password) {
        super(id, nombre, username, password, "Cliente");
    }

    @Override
    public String obtenerPermisos() {
        return "Puede realizar préstamos y consultas";
    }
}