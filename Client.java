import java.io.*;
import java.net.*;

// Cliente echo TCP

public class Client {
    // IP del Servidor
    private static InetAddress host;
    // Puerto del servidor
    private static final int PORT = 1234;
    // Socket Datagram
    private static DatagramSocket dgramSocket;
    // Paquetes de entrada y salida
    private static DatagramPacket inPkt, outPkt;
    // Buffer de los paquetes
    private static byte[] buff;
    // Para almacenar el contenido de los mensjaes
    private static String msg = "", msgIn = "";
    private static final String GROUP = "224.0.0.1";

    public static void main(String[] args) {
        System.out.println("Abriendo puerto \n");
        try {
            host = InetAddress.getLocalHost();
            // obtener la dirección IP del Servidor
        } catch (UnknownHostException e) {
            System.out.println("Error al abrir el puerto!");
            System.exit(1);
        }

        try {

            MulticastSocket ms = new MulticastSocket(PORT);
            ms.joinGroup(InetAddress.getByName(GROUP));
            // Crear el socket datagrama
            dgramSocket = new DatagramSocket();
            do {
                /*allocate the packet buffer*/
                byte buf[] = new byte[1024];
                /*create the packet*/
                DatagramPacket pack =
                new DatagramPacket(buf, buf.length);
                /*receive a packet from the multicast socket*/
                ms.receive(pack);
                /* print the details of the sender (server) */
                System.out.println("Received data from: " + pack.getAddress().toString() + ":" + pack.getPort() + " with length: " + pack.getLength());
                /* print the message */
                String entrada = new String(pack.getData(),0,pack.getLength());
                System.out.println("SERVIDOR > " + entrada);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el socket yliberar sus recursos
        }
    }

    private static void run() {
    }

    public class ReceiverThread implements Runnable {

        @Override
        public void run() {

        }
    }
}