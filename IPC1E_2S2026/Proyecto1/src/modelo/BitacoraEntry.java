package IPC1E_2S2026.Proyecto1.src.modelo;

public class BitacoraEntry {
    private String fechaHora;
    private String usuario;
    private String modulo;
    private String evento;
    private String descripcion;

    public BitacoraEntry(String fechaHora, String usuario, String modulo, String evento, String descripcion) {
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.modulo = modulo;
        this.evento = evento;
        this.descripcion = descripcion;
    }

    // Getters
    public String getFechaHora() { return fechaHora; }
    public String getUsuario() { return usuario; }
    public String getModulo() { return modulo; }
    public String getEvento() { return evento; }
    public String getDescripcion() { return descripcion; }
}