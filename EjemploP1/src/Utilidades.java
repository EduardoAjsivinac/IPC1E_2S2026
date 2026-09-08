import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Utilidades {

    public void bitacoraAcciones(String fecha,
                            String usuario,
                            String modulo,
                            String tipoEvento,
                            String descripcion){
        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter("bitacoraAcciones.txt", true));
            escritor.write(LocalDateTime.now().toString() + "|" + usuario + "|" + modulo +
                    "|"+ tipoEvento+ "|"+ descripcion+ "\n");
            escritor.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
