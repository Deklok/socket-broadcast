import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        System.out.println("Inicio el server...");
        try {
            //DatagramSocket serverSocket = new DatagramSocket(1234);
            new ServerThread().start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}