package Estructuras;

import Estructuras.Nodos.NodoDoble;

public class ListaDoble {
    private NodoDoble nodoInicio;
    private NodoDoble nodoFin;

    public void agregarNodo(Object dato){
        NodoDoble nuevoNodo = new NodoDoble(dato);
        if(nodoInicio == null){
            this.nodoInicio = nuevoNodo;
            this.nodoFin = nuevoNodo;
            return;
        }
        this.nodoFin.agregarSiguiente(nuevoNodo);
        this.nodoFin = nuevoNodo;
    }

    public void imprimirInicioFin(){
        NodoDoble temporal = this.nodoInicio;
        while (temporal!= null){
            System.out.println(temporal.obtenerObjeto().toString());
            temporal = temporal.obtenerSiguiente();
        }
    }

    public void imprimirFinInicio(){
        NodoDoble temporal = this.nodoFin;
        while (temporal!= null){
            System.out.println(temporal.obtenerObjeto().toString());
            temporal = temporal.obtenerAnterior();
        }
    }

    public NodoDoble obtenerInicio(){
        return this.nodoInicio;
    }

    public NodoDoble obtenerFin(){
        return this.nodoFin;
    }

}