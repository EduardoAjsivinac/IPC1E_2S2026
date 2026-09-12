package IPC1E_2S2026.Proyecto1.src.modelo;

public class Animal {

    private String codigo;
    private String especie;
    private int edad;
    private String estadoClinico; // "EN_OBSERVACION", "EN_TRATAMIENTO", "APTO"
    private String estadoAdopcion; // "DISPONIBLE", "NO_DISPONIBLE", "ADOPTADO"

    public Animal() {
    }

    public Animal(String codigo, String especie, int edad, String estadoClinico, String estadoAdopcion) {
        this.codigo = codigo;
        this.especie = especie;
        this.edad = edad;
        setEstadoClinico(estadoClinico);
        this.estadoAdopcion = estadoAdopcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstadoClinico() {
        return estadoClinico;
    }

    public void setEstadoClinico(String estadoClinico) {
        if (estadoClinico != null && (estadoClinico.equals("EN_OBSERVACION") || 
            estadoClinico.equals("EN_TRATAMIENTO") || 
            estadoClinico.equals("APTO"))) {
            this.estadoClinico = estadoClinico;
        } else {
            this.estadoClinico = "EN_OBSERVACION"; // Valor por defecto en caso de invalidez
        }
    }

    public String getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public void setEstadoAdopcion(String estadoAdopcion) {
        this.estadoAdopcion = estadoAdopcion;
    }

    @Override
    public String toString() {
        return codigo + "|" + especie + "|" + edad + "|" + estadoClinico + "|" + estadoAdopcion;
    }
}