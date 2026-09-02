package IPC1E_2S2026.Proyecto1.src.modelo;

public class Solicitud {
    private String codigo;          // Formato: "S-001"
    private String codigoAnimal;    // Código del animal ("A-001")
    private String codigoAdoptante; // Código del adoptante ("AD-001")
    private String fecha;           // Formato: "dd/mm/aaaa"
    private String estado;          // "PENDIENTE", "APROBADA", "RECHAZADA", "COMPLETADA"

    public Solicitud(String codigo, String codigoAnimal, String codigoAdoptante, String fecha, String estado) {
        this.codigo = codigo;
        this.codigoAnimal = codigoAnimal;
        this.codigoAdoptante = codigoAdoptante;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCodigoAnimal() { return codigoAnimal; }
    public void setCodigoAnimal(String codigoAnimal) { this.codigoAnimal = codigoAnimal; }

    public String getCodigoAdoptante() { return codigoAdoptante; }
    public void setCodigoAdoptante(String codigoAdoptante) { this.codigoAdoptante = codigoAdoptante; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}