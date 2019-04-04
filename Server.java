import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        System.out.println("Inicio el server...");
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                System.out.println("Listening for a client connection");
                Socket socket = serverSocket.accept();
                System.out.println("Connected to a client");
                new Thread(new ServerThread(socket)).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}