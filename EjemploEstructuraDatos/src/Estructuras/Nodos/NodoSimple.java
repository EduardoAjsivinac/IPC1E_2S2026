package Estructuras.Nodos;

public class NodoSimple {
    private NodoSimple siguiente;
    private Object dato;

    public NodoSimple(Object dato){
        this.dato = dato;
    }

    public void agregarNodo(NodoSimple nuevo){
        this.siguiente = nuevo;
    }

    public NodoSimple obtenerSiguiente(){
        return this.siguiente;
    }

    public Object obtenerNodo(){
        return this.dato;
    }
}
