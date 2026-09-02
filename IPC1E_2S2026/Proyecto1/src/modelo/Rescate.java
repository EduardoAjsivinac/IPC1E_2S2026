package IPC1E_2S2026.Proyecto1.src.modelo;

public class Rescate {
    private String codigo;               // Formato: "R-001"
    private String prioridad;            // "ALTA", "MEDIA", "BAJA"
    private String estado;               // "PENDIENTE", "ATENDIDO"
    private String fechaReporte;         // Formato: "dd/mm/aaaa"
    private String codigoAnimalVinculado; // Código del animal asignado ("A-001")

    public Rescate(String codigo, String prioridad, String estado, String fechaReporte, String codigoAnimalVinculado) {
        this.codigo = codigo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.codigoAnimalVinculado = codigoAnimalVinculado;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(String fechaReporte) { this.fechaReporte = fechaReporte; }

    public String getCodigoAnimalVinculado() { return codigoAnimalVinculado; }
    public void setCodigoAnimalVinculado(String codigoAnimalVinculado) { this.codigoAnimalVinculado = codigoAnimalVinculado; }
}