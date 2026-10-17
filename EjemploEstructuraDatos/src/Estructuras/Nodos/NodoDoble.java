package Estructuras.Nodos;

public class NodoDoble {
    private NodoDoble anterior;
    private NodoDoble siguiente;
    private Object dato;

    public NodoDoble(Object dato){
        this.dato = dato;
    }

    public void agregarSiguiente(NodoDoble nuevo){
        this.siguiente = nuevo;
        nuevo.anterior = this;
    }

    public void agregarAntes(NodoDoble nuevo){
        this.anterior = nuevo;
        nuevo.siguiente = this;
    }

    public NodoDoble obtenerSiguiente(){
        return this.siguiente;
    }

    public NodoDoble obtenerAnterior(){
        return this.anterior;
    }

    public Object obtenerObjeto(){
        return this.dato;
    }

    public void actualizarObjeto(Object dato){
        this.dato = dato;
    }
}
