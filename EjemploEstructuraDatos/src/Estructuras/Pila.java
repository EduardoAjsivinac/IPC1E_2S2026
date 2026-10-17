package Estructuras;

import Estructuras.Nodos.NodoSimple;

public class Pila {
    private NodoSimple raiz;

    public Pila(){

    }

    public void agregarNodo(Object dato){
        NodoSimple nuevo = new NodoSimple(dato);
        if (this.raiz == null){
            this.raiz = nuevo;
            return;
        }
        NodoSimple temporal = this.raiz;
        while (temporal.obtenerSiguiente() != null){
            temporal = temporal.obtenerSiguiente();
        }
        temporal.agregarNodo(nuevo);
    }

    public void imprimir(){
        NodoSimple temporal = this.raiz;
        while (temporal != null){
            System.out.println(temporal.obtenerNodo().toString());
            temporal = temporal.obtenerSiguiente();
        }
    }

    public Object desapilar(){
        if(this.raiz != null) {
            NodoSimple temporal = this.raiz;
            NodoSimple temporal2 = null;
            while (temporal.obtenerSiguiente() != null){
                temporal2 = temporal;
                temporal = temporal.obtenerSiguiente();
            }
            if(temporal2== null){
                this.raiz = null;
            }else{
                temporal2.agregarNodo(null);
            }
            return temporal.obtenerNodo();
        }
        return null;
    }

}