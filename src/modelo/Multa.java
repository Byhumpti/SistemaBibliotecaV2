package modelo;

public class Multa {
    private int id;
    private int idPrestamo;
    private double monto;
    private String estado;

    public Multa() {}

    public Multa(int id, int idPrestamo, double monto, String estado) {
        this.id = id;
        this.idPrestamo = idPrestamo;
        this.monto = monto;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}