package Objetos;

public class Residente {
    private String nombre;

    public Residente(String nombre){
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
