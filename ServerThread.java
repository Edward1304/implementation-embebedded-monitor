import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream input = socket.getInputStream();
            var reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            FileWriter csvWriter = new FileWriter("cpu_usage_data.csv", true);
        ) {
            String text;
            while ((text = reader.readLine()) != null) {
                // Verificar si la cadena representa una fecha y hora válida
                if (DateTimeValidator.esFormatoValido(text)) {
                    // Es una fecha y hora valida, proceder con el procesamiento
                    csvWriter.write(text + "," + "\n");
                    csvWriter.flush();
                    var res = (text + ",");
                    writer.println("Server: " + text + "\n" + "Writed As:" + res);
                } else {
                    // No es una fecha y hora valida, manejar el caso de error
                    writer.println("Server: El formato de fecha y hora no es válido.");
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
