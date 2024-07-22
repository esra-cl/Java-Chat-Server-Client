import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class Server {

    public static void main(String [] args) throws Exception{

        ServerSocket server = new ServerSocket(1111);

            Socket client = server.accept();System.out.println("Esra Online!");

            ObjectInputStream ois= new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

            try{
                Scanner scn = new Scanner(System.in);
                Object Clientmsg= ois.readObject();
                System.out.println("Esra c:"+(String)Clientmsg);
                System.out.println("You:");
                String ServerMsj=scn.nextLine();
                oos.writeObject(ServerMsj);
            }
            catch (ClassNotFoundException ex){
                //TODO: handle
            }
    }

}
