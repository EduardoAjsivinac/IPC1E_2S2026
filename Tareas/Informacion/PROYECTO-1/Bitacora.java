import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bitacora {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void accion(
            String usuario,
            String modulo,
            String evento,
            String descripcion) {

        guardar(
                "datos/bitacora_acciones.txt",
                fecha() + "|" +
                usuario + "|" +
                modulo + "|" +
                evento + "|" +
                limpiar(descripcion)
        );
    }

    public static void error(
            String usuario,
            String modulo,
            String evento,
            String motivo) {

        guardar(
                "datos/bitacora_errores.txt",
                fecha() + "|" +
                usuario + "|" +
                modulo + "|" +
                evento + "|" +
                limpiar(motivo)
        );
    }

    private static String fecha() {

        return LocalDateTime.now()
                .format(FORMATO);
    }

    private static String limpiar(String texto) {

        return texto
                .replace("|", "/")
                .replace("\n", " ");
    }

    private static void guardar(
            String ruta,
            String linea) {

        try {

            File archivo =
                    new File(ruta);

            File padre =
                    archivo.getParentFile();

            if (padre != null) {
                padre.mkdirs();
            }

            FileWriter fw =
                    new FileWriter(
                            archivo,
                            true
                    );

            fw.write(
                    linea +
                    System.lineSeparator()
            );

            fw.close();

        } catch (IOException e) {

            System.out.println(
                    "No se pudo guardar bitácora: "
                    + e.getMessage()
            );
        }
    }
}