import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private final int PORT = 1234;
    private String Group = "224.0.0.1";
    private byte TTL = 3;
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
        try {
            Thread threadEscucha = new Thread() {
                @Override
                public void run() {
                    System.out.println("Iniciando thread escucha");
                    try {
                        inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        while (true) {
                            String msg = inClient.readLine();
                            System.out.println("Mensaje recibido: " + msg);
                            colaMensajes.addLast(msg);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            Thread threadPublica = new Thread() {
                @Override
                public void run() {
                    System.out.println("Iniciando thread publicacion");
                    try {
                        String msg;
                        MulticastSocket ms = new MulticastSocket();
                        byte buf[] = new byte[1024];
                        DatagramPacket pack;
                        while (true) {
                            msg = "Server heartbeat";
                            if (!colaMensajes.isEmpty()) {
                                msg = (String) colaMensajes.pollFirst();
                            }
                            pack = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(Group), PORT);
                            ms.send(pack, TTL);
                            sleep(10000);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            threadEscucha.start();
            threadPublica.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}