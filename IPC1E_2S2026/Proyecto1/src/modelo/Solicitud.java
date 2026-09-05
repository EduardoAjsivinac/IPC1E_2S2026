package IPC1E_2S2026.Proyecto1.src.modelo;

public class Solicitud {
    private String codigo;
    private String codigoAnimal;
    private String codigoAdoptante;
    private String estado; // "PENDIENTE", "APROBADA", "RECHAZADA"

    public Solicitud(String codigo, String codigoAnimal, String codigoAdoptante, String estado) {
        this.codigo = codigo;
        this.codigoAnimal = codigoAnimal;
        this.codigoAdoptante = codigoAdoptante;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoAnimal() {
        return codigoAnimal;
    }

    public void setCodigoAnimal(String codigoAnimal) {
        this.codigoAnimal = codigoAnimal;
    }

    public String getCodigoAdoptante() {
        return codigoAdoptante;
    }

    public void setCodigoAdoptante(String codigoAdoptante) {
        this.codigoAdoptante = codigoAdoptante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}