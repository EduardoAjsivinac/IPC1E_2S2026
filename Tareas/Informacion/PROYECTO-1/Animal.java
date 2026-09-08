public class Animal {

    String codigo;
    String nombre;
    String especie;
    int edad;
    String estadoClinico;
    String estadoAdopcion;

    public Animal(String codigo, String nombre, String especie,
                  int edad, String estadoClinico,
                  String estadoAdopcion) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.estadoClinico = estadoClinico;
        this.estadoAdopcion = estadoAdopcion;
    }
}