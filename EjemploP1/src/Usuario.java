public class Usuario {
    private String nombreUsuario;
    private String clave;
    private String rol;
    private Utilidades utilidades;

    public Usuario(String nombreUsuario, String clave, String rol){
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.rol = rol;
        this.utilidades = new Utilidades();
    }

    public boolean login (String nombreUsuario, String password){
        boolean loginCorrecto = this.nombreUsuario == nombreUsuario && this.clave == password;
        if(loginCorrecto){
            utilidades.bitacoraAcciones("05/09/2026",this.nombreUsuario, "login", "Logueo", "Login exitoso");
        }else{
            utilidades.bitacoraAcciones("05/09/2026",this.nombreUsuario, "login", "Logueo", "Login fallido");
        }
        return loginCorrecto;
    }

    public void getRol(){
        System.out.println(this.rol);
    }

    public void adoptarMascota(){
        System.out.println("Se adopta la mascota");
    }



}
