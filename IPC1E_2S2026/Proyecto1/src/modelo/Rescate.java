package IPC1E_2S2026.Proyecto1.src.modelo;

public class Rescate {
    private String codigo;
    private String prioridad;
    private String estado;
    private String fecha;
    private String codigoAnimalVinculado;

    // Constructor completo
    public Rescate(String codigo, String prioridad, String estado, String fecha, String codigoAnimalVinculado) {
        this.codigo = codigo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fecha = fecha;
        this.codigoAnimalVinculado = codigoAnimalVinculado;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigoAnimalVinculado() {
        return codigoAnimalVinculado;
    }

    public void setCodigoAnimalVinculado(String codigoAnimalVinculado) {
        this.codigoAnimalVinculado = codigoAnimalVinculado;
    }
}