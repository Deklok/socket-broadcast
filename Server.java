import java.net.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.io.*;

public class Server {

    private static LinkedBlockingDeque colaMensajes;
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    private static String Group = "224.0.0.1";
    private static byte TTL = 3;
    public static void main(String[] args) {
        System.out.println("Inicio el server...");
        colaMensajes = new LinkedBlockingDeque<String>();

        try {
            serverSocket = new ServerSocket(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread threadPublica = new Thread() {
            @Override
            public void run() {
                System.out.println("Iniciando server publicacion");
                try {
                    String msg;
                    MulticastSocket ms = new MulticastSocket();
                    byte buf[] = new byte[1024];
                    DatagramPacket pack;
                    while (true) {
                        msg = "Server heartbeat";
                        if (!colaMensajes.isEmpty()) {
                            if (colaMensajes.peekFirst().toString().equals("null")) {
                                //Mensaje null = ultimo mensaje de cliente desconectado
                                colaMensajes.removeFirst();
                            }
                            if (!colaMensajes.isEmpty()) {
                                msg = (String) colaMensajes.pollFirst();
                            }
                        }
                        pack = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(Group), PORT);
                        System.out.println("Enviando mensaje multicast: " + msg);
                        ms.send(pack, TTL);
                        sleep(5000);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Thread threadEscucha = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Socket socket = serverSocket.accept();
                        System.out.println("Cliente conectado");
                        new Thread(new ServerThread(socket,colaMensajes)).start();
                    }
                } catch (SocketException ex) {
                    System.out.println("Excepcion socket exception");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        threadPublica.start();
        threadEscucha.start();
        System.out.println("Escuchando conexiones de clientes...");
    }
}


