public class Administrador extends Usuario{

    public Administrador(String nombreUsuario, String clave) {
        super(nombreUsuario, clave, "ADMINISTRADOR");
    }

    public void eliminarMascota(){
        System.out.println("Se elimna la mascota");
    }
}
