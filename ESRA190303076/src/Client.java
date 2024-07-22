import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{
        while(true){
            Socket client = new Socket("10.62.233.12",1111);
            ObjectOutputStream OboStr= new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ObiStr = new ObjectInputStream(client.getInputStream());

            Scanner scn= new Scanner(System.in);
            System.out.println("You:");
            String msg= scn.nextLine();
            OboStr.writeObject(msg);
            try{
                Object send = ObiStr.readObject();
                if((String)send!=null){
                    System.out.println("Betül s:"+(String)send);
                }

            }
            catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
    }
}