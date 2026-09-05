package IPC1E_2S2026.Proyecto1.src.modelo;

public class Rescate {
    private String codigo;
    private String lugar;
    private String fecha;
    private String codigoAnimal;

    public Rescate(String codigo, String lugar, String fecha, String codigoAnimal) {
        this.codigo = codigo;
        this.lugar = lugar;
        this.fecha = fecha;
        this.codigoAnimal = codigoAnimal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigoAnimal() {
        return codigoAnimal;
    }

    public void setCodigoAnimal(String codigoAnimal) {
        this.codigoAnimal = codigoAnimal;
    }
}