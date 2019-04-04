import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private final int PORT = 1234;
    private String Group = "224.0.0.1";
    private byte TTL = 3;
    
    public ServerThread (Socket socket) {
        this.clientSocket = socket;
    }

    public ServerThread() {
    }

    @Override
    public void run() {
        System.out.println("Iniciando thread");
        try {
            String lol = "Tu mama hehe xd";
            MulticastSocket ms = new MulticastSocket();
            byte buf[] = new byte[1024];
            DatagramPacket pack = new DatagramPacket(lol.getBytes(), lol.length(), InetAddress.getByName(Group), PORT);
            ms.send(pack,TTL);
            //ms.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}