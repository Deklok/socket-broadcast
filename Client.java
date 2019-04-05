import java.io.*;
import java.net.*;

// Cliente echo TCP

public class Client {
    // IP del Servidor
    private static InetAddress host;
    // Puerto del servidor
    private static final int RECEIVERPORT = 1234;
    private static final int SENDERPORT = 5000;
    private static final String GROUP = "224.0.0.1";
    private static Socket serverSocket;

    public static void main(String[] args) {
        System.out.println("Abriendo puerto \n");
        try {
            host = InetAddress.getLocalHost();
            // obtener la dirección IP del Servidor
            serverSocket = new Socket(InetAddress.getLocalHost(), SENDERPORT);
        } catch (UnknownHostException e) {
            System.out.println("Error al abrir el puerto!");
            System.exit(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new Thread(new ClientReceiverThread(RECEIVERPORT, GROUP)).start();
        run();
    }

    private static void run() {
        try {

            // Crear el lector y escritor de socket
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            // Configurar para la entrada del suario
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Almacenamiento para el mensaje y respuesta
            String msgOut;

            do {
                System.out.println("Mi mensaje > ");
                // Leer mensaje del usuario
                msgOut = reader.readLine();
                // Enviar el mensaje
                out.println(msgOut);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el socket de datos
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}