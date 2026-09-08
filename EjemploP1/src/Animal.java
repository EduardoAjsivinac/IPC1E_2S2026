public class Animal {
    private String codigo;
    private String especie;
    private Integer edad;
    private String estadoClinico;
    private String estadoAdopcion;

    public Animal(String codigo, String especie){
        if(this.codigoValido(codigo)){
            this.codigo = codigo;
            this.especie = especie;
            this.estadoAdopcion = "DISPONIBLE";
            this.estadoClinico = "EN_OBSERVACION";
        }else{
            System.out.println("El codigo no es valido");
        }

    }

    public String getCodigo(){
        return this.codigo;
    }

    private boolean codigoValido(String codigo){
        if(codigo.charAt(0) == 'A' && codigo.charAt(1)=='-'){
            return true;
        }
        return false;
    }






}
