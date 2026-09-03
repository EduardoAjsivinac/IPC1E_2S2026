public class Solicitud {

    String codigo;
    String codigoAnimal;
    String codigoAdoptante;
    String fecha;
    String estado;

    public Solicitud(String codigo, String codigoAnimal,
                     String codigoAdoptante, String fecha, String estado) {

        this.codigo = codigo;
        this.codigoAnimal = codigoAnimal;
        this.codigoAdoptante = codigoAdoptante;
        this.fecha = fecha;
        this.estado = estado;
    }
}