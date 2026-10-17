package Objetos;

public class Vehiculo {
    private String placa;
    private String marca;

    public Vehiculo(String placa, String marca){
        this.placa = placa;
        this.marca = marca;
    }

    @Override
    public String toString() {
        return this.placa;
    }

    public void imprimir(){
        System.out.println("Se encontro el vehículo con placa " + placa + " de la marca " + marca);
    }
}
