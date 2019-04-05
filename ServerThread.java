import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.net.SocketException;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private LinkedBlockingDeque colaMensajes;
    private BufferedReader inClient;

    public ServerThread(Socket socket, LinkedBlockingDeque cola) {
        this.clientSocket = socket;
        this.colaMensajes = cola;
    }

    public ServerThread() {
    }

    @Override
    public void run() {
        System.out.println("Iniciando thread escucha");
        try {
            inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String msg = inClient.readLine();
                System.out.println("Mensaje recibido: " + msg);
                if (msg!=null) {
                    colaMensajes.addLast(msg);
                }
            }
        } catch (SocketException ex) {
            System.out.println("Cliente desconectado");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}