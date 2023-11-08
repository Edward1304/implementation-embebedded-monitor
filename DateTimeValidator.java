import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeValidator {
    private static final String FORMATO_FECHA_HORA = "yyyy-MM-dd HH:mm:ss";

    public static boolean esFormatoValido(String fechaHora) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_HORA);
        sdf.setLenient(false); // Esto hace que la validación sea estricta

        try {
            sdf.parse(fechaHora);
            return true; // Si no lanza una excepción, entonces la fecha es válida
        } catch (ParseException e) {
            return false; // Si se lanza una excepción, la fecha no es válida
        }
    }
}
