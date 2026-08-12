import java.sql.SQLOutput;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    // Declarar variables, reservar un espacio de memoria
    String frase = "Hola Mundo";
    int vehiculos = 0;
    double tarifa = 0;
    String [] parqueo  = new String[9];
    String [][] parqueov2 = new String[8][8];
    /*
    for (int i = 0; i < 9; i++) {
        // 0,1,2,3,4,5,6,7
        parqueo[i]= "P001AAA";
    }

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            parqueov2[i][j] = "P001BBB";
        }
    }

    int contador = 0;
    while (contador < 9){
        System.out.print(parqueo[contador]);
        System.out.print(" ");
        if((contador+1)%3 == 0){
            System.out.println("");
        }
        contador++;
    }
    int fila = 0;
    int columna = 0;
    while (fila < 3){
        columna = 0;
        while (columna<3){
            System.out.print(parqueov2[fila][columna]);
            System.out.print(" ");
            columna = columna+1;
        }
        System.out.println("");
        fila++;
    }

    *//*
    int entradaX = 3;
    int entradaY = 0;
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            if(i>=1 && i<=8&& j>=1 && j<=8) {
                if (parqueov2[i-1][j-1] == null) {
                    System.out.print("L");
                } else {
                    System.out.print("A");
                }
            }else{
                if (i == entradaY && j == entradaX){
                    System.out.print("E");
                }else{
                    System.out.print("=");
                }
            }
            System.out.print(" ");
        }
        System.out.println("");
    }*/

    menu();
}

private void menu(){
    boolean continuaMenu = true;
    do {
        System.out.println("Ingresar Vehiculo");
        System.out.println("Retirar Vehiculo");
        Scanner sc = new Scanner(System.in);
        int opcion = sc.nextInt();
        if(opcion==5){
            continuaMenu = false;
        }
    }while (continuaMenu);

}

