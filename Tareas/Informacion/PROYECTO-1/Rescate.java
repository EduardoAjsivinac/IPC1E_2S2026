public class Rescate {

    String codigo;
    String descripcion;
    String prioridad;
    String estado;
    String fecha;
    String codigoAnimalVinculado;

    public Rescate(
            String codigo,
            String descripcion,
            String prioridad,
            String estado,
            String fecha,
            String codigoAnimalVinculado) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fecha = fecha;
        this.codigoAnimalVinculado =
                codigoAnimalVinculado;
    }
}