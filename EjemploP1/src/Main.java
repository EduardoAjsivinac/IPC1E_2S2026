//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


void main() {
    Administrador admin1 = new Administrador("user1", "123456");
    Auxiliar auxilar1 = new Auxiliar("Auxiliar1", "123456");
    admin1.getRol();
    auxilar1.getRol();
    auxilar1.adoptarMascota();
    admin1.adoptarMascota();
    admin1.eliminarMascota();
    admin1.login("user1", "123456");
    auxilar1.login("Auxiliar1", "123456");
    /*
    int fila = 10;
    int columna = 10;

    Animal [][] espacios = new  Animal[fila][columna];
    try {
        espacios[0][0] = new Animal("A-001", "Perro");
        espacios[0][1] = new Animal("A-002", "Perro");
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if(espacios[i][j] != null){
                    System.out.print(espacios[i][j].getCodigo());
                }
            }
            System.out.println("");
        }
    }catch (NullPointerException e){
        System.out.println("El animal en esta posicion no existe");
    }catch (IndexOutOfBoundsException e){
        System.out.println("Indice fuera de rango");
    }catch (Exception e){
        System.out.println("Error no controlado");
    }
*/

}
