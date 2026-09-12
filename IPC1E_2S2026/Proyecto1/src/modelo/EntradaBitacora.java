package IPC1E_2S2026.Proyecto1.src.modelo;

public class EntradaBitacora {

    private String fechaHora;
    private String usuario;
    private String modulo;
    private String tipoEvento;
    private String descripcion;

    public EntradaBitacora() {
    }

    public EntradaBitacora(String fechaHora, String usuario, String modulo, String tipoEvento, String descripcion) {
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.modulo = modulo;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return fechaHora + " | " + usuario + " | " + modulo + " | " + tipoEvento + " | " + descripcion;
    }
}