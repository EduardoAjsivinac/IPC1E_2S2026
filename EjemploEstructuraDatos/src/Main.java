import Estructuras.*;
import Estructuras.Nodos.NodoDoble;
import Estructuras.Nodos.NodoSimple;
import Objetos.EspacioParqueo;
import Objetos.Residente;
import Objetos.Vehiculo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    /*
    // Lista doble enlazada
    ListaDoble residentes = new ListaDoble();
    residentes.agregarNodo(new Residente("Juan"));
    residentes.agregarNodo(new Residente("Pedro"));
    residentes.agregarNodo(new Residente("Esteban"));
    residentes.agregarNodo(new Residente("Brenda"));
    residentes.agregarNodo(new Residente("Alejandra"));
    residentes.agregarNodo(new Residente("Josue"));
    residentes.agregarNodo(new Residente("Roberto"));

    residentes.imprimirInicioFin();
    residentes.imprimirFinInicio();
    */
    /*
    // Lista Simple
    ListaSimple residentes = new ListaSimple();
    residentes.agregarNodo(new Vehiculo("P001","Toyota"));
    residentes.agregarNodo(new Vehiculo("P002","Mazda"));
    residentes.agregarNodo(new Vehiculo("P003","Kia"));
    residentes.agregarNodo(new Vehiculo("P004","Lexus"));
    residentes.agregarNodo(new Vehiculo("P005","Honda"));
    residentes.agregarNodo(new Vehiculo("P006","Hyunday"));
    residentes.agregarNodo(new Vehiculo("P007","Chevrolet"));
    residentes.imprimir();
    Vehiculo vehiculoEncontrado = (Vehiculo) residentes.objetoEncontrado("P006");
    if(vehiculoEncontrado != null){
        vehiculoEncontrado.imprimir();
    }
     */

    /*
    ListaDoble parqueo = new ListaDoble();
    iniciarParqueo(parqueo);
    estacionar(parqueo,"B",10,new Vehiculo("P001","Mitsubishi"));

    NodoDoble temporalDoble = parqueo.obtenerInicio();
    for (int i = 0; i < 10; i++) {
        ListaSimple listaSimple = (ListaSimple) temporalDoble.obtenerObjeto();
        NodoSimple nodoSimple = listaSimple.obtenerRaiz();
        for (int j = 0; j < 10; j++) {
            EspacioParqueo temp = (EspacioParqueo) nodoSimple.obtenerNodo();
            temp.imprimir();
            nodoSimple = nodoSimple.obtenerSiguiente();
        }
        temporalDoble = temporalDoble.obtenerSiguiente();
    }
     */

/*
    Cola vehiculos = new Cola();
    vehiculos.agregarNodo("Toyota");
    vehiculos.agregarNodo("Mazda");
    vehiculos.agregarNodo("Kia");
    vehiculos.agregarNodo("Suzuki");
    vehiculos.agregarNodo("Chevrolet");
    vehiculos.imprimir();
    Object vehiculo = vehiculos.desencolar();
    System.out.println("Se quito de la cola el vehiculo " +vehiculo.toString());
    vehiculos.imprimir();
    vehiculo = vehiculos.desencolar();
    System.out.println("Se quito de la cola el vehiculo " +vehiculo.toString());
    vehiculos.imprimir();
    vehiculo = vehiculos.desencolar();
    System.out.println("Se quito de la cola el vehiculo " +vehiculo.toString());
    vehiculos.imprimir();
    vehiculo = vehiculos.desencolar();
    System.out.println("Se quito de la cola el vehiculo " +vehiculo.toString());
    vehiculos.imprimir();
    vehiculo = vehiculos.desencolar();
    System.out.println("Se quito de la cola el vehiculo " +vehiculo.toString());
    vehiculos.imprimir();
 */
/*
    Pila historial = new Pila();
    historial.agregarNodo("Vehiculo ingreso");
    historial.agregarNodo("Vehiculo aparco");
    historial.agregarNodo("Vehiculo asignado al espacio X");
    historial.agregarNodo("Vehiculo salio");

    System.out.println("La ultima acción en el historial es: " + historial.desapilar());
    System.out.println("La ultima acción en el historial es: " + historial.desapilar());
    System.out.println("La ultima acción en el historial es: " + historial.desapilar());
    System.out.println("La ultima acción en el historial es: " + historial.desapilar());
    System.out.println("La ultima acción en el historial es: " + historial.desapilar());
 */
}


private void iniciarParqueo(ListaDoble parqueo){
    // Creamos la lista de listas de parqueos
    for (int i = 0; i < 10; i++) {
        ListaSimple fila = new  ListaSimple();
        for (int j = 0; j < 10; j++) {
            fila.agregarNodo(new EspacioParqueo(String.valueOf((char)(65 + i )),j+1));
        }
        parqueo.agregarNodo(fila);
    }
}

private void estacionar(ListaDoble parqueo, String letra, int numero, Vehiculo nuevoVehiculo){
    NodoDoble filaparqueo = parqueo.obtenerInicio();
    for (int i = 0; i < (int)letra.charAt(0)-65; i++) {
        filaparqueo = filaparqueo.obtenerSiguiente();
    }

    ListaSimple espaciosParqueo = (ListaSimple) filaparqueo.obtenerObjeto();
    NodoSimple temporalNodoRecorrido = espaciosParqueo.obtenerRaiz();
    for (int i = 1; i < numero; i++) {
        temporalNodoRecorrido = temporalNodoRecorrido.obtenerSiguiente();
    }
    EspacioParqueo espacioEncontrado = (EspacioParqueo)temporalNodoRecorrido.obtenerNodo();
    espacioEncontrado.ocuparEspacio(nuevoVehiculo);
}