package modelo;

public class Animal {
    private String codigo;        // Formato: "A-001"
    private String especie;       // "Perro" o "Gato"
    private int edad;             // 0 a 25
    private String estadoClinico; // "EN_OBSERVACION", "EN_TRATAMIENTO", "APTO"
    private String estadoAdopcion;// "DISPONIBLE", "ADOPTADO", "ELIMINADO"

    public Animal(String codigo, String especie, int edad, String estadoClinico, String estadoAdopcion) {
        this.codigo = codigo;
        this.especie = especie;
        this.edad = edad;
        this.estadoClinico = estadoClinico;
        this.estadoAdopcion = estadoAdopcion;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getEstadoClinico() { return estadoClinico; }
    public void setEstadoClinico(String estadoClinico) { this.estadoClinico = estadoClinico; }

    public String getEstadoAdopcion() { return estadoAdopcion; }
    public void setEstadoAdopcion(String estadoAdopcion) { this.estadoAdopcion = estadoAdopcion; }
}