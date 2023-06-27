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
            StringBuilder req = new StringBuilder();
            int c = dis.read();
            while (c != 0) {
                req.append((char) c);
                c = dis.read();
            }
            Scanner scanner = new Scanner(req.toString());
            String command = scanner.nextLine();
            String initdata = scanner.nextLine();
            String[] inits = initdata.split(",,");
            String data = "";
            String start = "â\u0094¤";
            String end = "â\u0094\u009C";
            int startIndex = inits[0].indexOf(start);
            int endIndex = inits[0].indexOf(end, startIndex + start.length());
            String user = (inits[0].substring(startIndex + start.length(), endIndex));
            int startIndexp = inits[1].indexOf(start);

            switch (command) {
                case "Authorize" -> {
                    int endIndexp = inits[1].indexOf(end, startIndexp + start.length());
                    String pass = (inits[1].substring(startIndexp + start.length(), endIndexp));
                    data = "User:" + user + ",,Pass:" + pass;
                }
                case "addToWallet" -> {
                    int endIndexp = inits[1].indexOf(end, startIndexp + start.length());
                    String pass = (inits[1].substring(startIndexp + start.length(), endIndexp));
                    data = "User:" + user + ",,Money:" + pass;
                }
                case "RateBook" -> {
                    String Name = (inits[0].substring(startIndex + start.length(), endIndex));
                    int startIndexppp = inits[1].indexOf(start);
                    int endIndexp = inits[1].indexOf(end, startIndexppp + start.length());
                    String Rate = (inits[1].substring(startIndexp + start.length(), endIndexp));
                    int startIndexpp = inits[2].indexOf(start);
                    int endIndexpp = inits[2].indexOf(end, startIndexpp + start.length());
                    String Type = (inits[2].substring(startIndexpp + start.length(), endIndexpp));
                    data = "Name:" + Name + ",,Rate:" + Rate + ",,Type:" + Type;
                }
                case "register" -> {
                    int endIndexppp = inits[1].indexOf(end, startIndexp + start.length());
                    String pass = (inits[1].substring(startIndexp + start.length(), endIndexppp));
                    int endIndexpp = inits[2].indexOf(end, startIndexp + start.length());
                    String email = (inits[2].substring(startIndexp + start.length()+1, endIndexpp));
                    data = "User:" + user + ",,Pass:" + pass + ",,Email:" + email;
                }
            }
            // command register
            //data:ali,,iranmanesh,,email

            String res = new Controller().run(command, data);

            dos.writeBytes(res);
            dos.flush();

            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
