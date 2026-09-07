import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Util {

    public static boolean vacio(String s) {

        return s == null ||
               s.trim().isEmpty();
    }

    public static boolean codigo(
            String s,
            String prefijo) {

        return s != null &&
               s.matches(
                       prefijo + "-\\d{3}"
               );
    }

    public static boolean soloLetras(
            String s) {

        return s != null &&
               s.matches(
                       "[A-Za-zÁÉÍÓÚáéíóúÑñ ]+"
               );
    }

    public static boolean digitos(
            String s,
            int cantidad) {

        return s != null &&
               s.matches(
                       "\\d{" + cantidad + "}"
               );
    }

    public static boolean fechaValida(
            String s) {

        if (s == null ||
            !s.matches(
                    "\\d{2}/\\d{2}/\\d{4}"
            )) {

            return false;
        }

        SimpleDateFormat f =
                new SimpleDateFormat(
                        "dd/MM/yyyy"
                );

        f.setLenient(false);

        try {

            f.parse(s);
            return true;

        } catch (ParseException e) {

            return false;
        }
    }

    public static String seguro(String s) {

        return s == null
                ? ""
                : s.replace("|", "/")
                   .replace("\n", " ");
    }
}