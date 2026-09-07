package IPC1E_2S2026.Proyecto1.src.modelo;

public class BitacoraEntry {

    private String fechaHora;
    private String usuario;
    private String modulo;
    private String tipoEvento;
    private String descripcion;
    private String motivoRechazo;
    private boolean esError;

    public BitacoraEntry(String fechaHora, String usuario, String modulo, String tipoEvento, String descripcion, String motivoRechazo, boolean esError) {
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.modulo = modulo;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.motivoRechazo = motivoRechazo;
        this.esError = esError;
    }

    public String getFechaHora() { return fechaHora; }
    public String getUsuario() { return usuario; }
    public String getModulo() { return modulo; }
    public String getTipoEvento() { return tipoEvento; }
    
    // Método solicitado por ReporteService.java
    public String getEvento() { return tipoEvento; }

    public String getDescripcion() { return descripcion; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public boolean isEsError() { return esError; }

    public String aFormatoTexto() {
        if (esError && motivoRechazo != null && !motivoRechazo.isEmpty()) {
            return fechaHora + " | " + usuario + " | " + modulo + " | " + tipoEvento + " | " + descripcion + " | Motivo: " + motivoRechazo;
        }
        return fechaHora + " | " + usuario + " | " + modulo + " | " + tipoEvento + " | " + descripcion;
    }
}