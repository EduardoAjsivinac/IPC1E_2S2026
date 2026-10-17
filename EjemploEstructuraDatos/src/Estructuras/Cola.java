package Estructuras;

import Estructuras.Nodos.NodoSimple;

public class Cola {
    private NodoSimple raiz;

    public Cola(){

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

    public Object desencolar(){
        if(this.raiz != null) {
            NodoSimple temporal = this.raiz;
            if (this.raiz != null) {
                this.raiz = temporal.obtenerSiguiente();
            }
            return temporal.obtenerNodo();
        }
        return null;
    }
}