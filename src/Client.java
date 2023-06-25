import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8000);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF("Authorize\nUser:AliIranmanesh,,Pass:2213");
        dos.flush();
        System.out.println(dis.readUTF());
        dos.close();
        dis.close();
        socket.close();
    }
}
