package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void start() throws IOException {
        ServerSocket ss = new ServerSocket(8000);
        while (true) {
            Socket socket = ss.accept();
            new ClientHandler(socket).start();
        }
    }
}
