import java.io.*;
import java.net.*;

public class ClientReceiverThread extends Thread {
    private int port;
    private String group;
    private static DatagramSocket dgramSocket;

    public ClientReceiverThread (int port, String group) {
        this.port = port;
        this.group = group;
    }


    @Override
    public void run() {
        try {

            MulticastSocket ms = new MulticastSocket(this.port);
            ms.joinGroup(InetAddress.getByName(this.group));
            // Crear el socket datagrama
            dgramSocket = new DatagramSocket();
            do {
                /* allocate the packet buffer */
                byte buf[] = new byte[1024];
                /* create the packet */
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                /* receive a packet from the multicast socket */
                ms.receive(pack);
                /* print the details of the sender (server) */
                // System.out.println("Received data from: " + pack.getAddress().toString() +
                // ":" + pack.getPort() + " with length: " + pack.getLength());
                /* print the message */
                String entrada = new String(pack.getData(), 0, pack.getLength());
                System.out.println("SERVER > " + entrada);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el socket yliberar sus recursos
        }
    }
}