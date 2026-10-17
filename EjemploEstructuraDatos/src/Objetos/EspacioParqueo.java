package Objetos;

public class EspacioParqueo {
    private String letra;
    private int numero;
    private Vehiculo vehiculo;

    public EspacioParqueo(String letra, int numero){
        this.letra=letra;
        this.numero=numero;
        vehiculo=null;
    }

    public void imprimir(){
        String espacio = (vehiculo==null?"vacio":"lleno");
        System.out.println("El espacio de parqueo es: "+ letra + "-"+String.valueOf(numero) + " y está "+espacio);
    }

    public void ocuparEspacio(Vehiculo nuevoVehiculo){
        this.vehiculo = nuevoVehiculo;
    }

    public Vehiculo vaciar(){
        Vehiculo vehiculoActual = this.vehiculo;
        this.vehiculo = null;
        return vehiculoActual;
    }
}
