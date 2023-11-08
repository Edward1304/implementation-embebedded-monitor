import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MonitorServer {
    private static final int DEFAULT_PORT = 65432;
    public static void main(String[] args) {
        int port;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = DEFAULT_PORT;
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new ServerThread(socket).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }
}
