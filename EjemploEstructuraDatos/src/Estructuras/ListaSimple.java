package Estructuras;

import Estructuras.Nodos.NodoSimple;

public class ListaSimple {
    private NodoSimple raiz;

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

    public NodoSimple obtenerRaiz(){
        return this.raiz;
    }

    public Object objetoEncontrado(Object dato){
        NodoSimple temporal = this.raiz;
        while (temporal != null){
            if(temporal.obtenerNodo().toString().equals(dato.toString())){
                return temporal.obtenerNodo();
            }
            temporal = temporal.obtenerSiguiente();
        }
        return null;
    }
}
