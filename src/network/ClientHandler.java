package network;

import controller.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Socket socket;


    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String req = dis.readUTF();
            Scanner scanner = new Scanner(req);
            // command money
            //data:ali,,iranmanesh,,3000
            String command = scanner.nextLine();
            String data = scanner.nextLine();

            String res = new Controller().run(command, data);

            dos.writeUTF(res);
            dos.flush();

            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
