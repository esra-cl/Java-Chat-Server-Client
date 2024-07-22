import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static java.lang.Thread.sleep;


public class serverForm extends JFrame {
    public static JFrame frame;
    public static JTextArea msjGoster;
    public static JPanel arkaplani,ekran;
    public static JLabel lab1 ,lab2;
    public static JTextField port,msjbox;
    public static JButton baslat,gonder;
    public static int holder;
    public static ServerSocket server;
    public static Socket client ;
    public static DataInputStream in;
    public static DataOutputStream out;
    public static String oku;
    //şimdiki zaman için
    public static Date now = new Date();
    //okuma flag
    public static boolean flag=false;
    //to connect
    public static String gelenmsj="";


    public static void serverFrm(){
        //set frame
        frame= new JFrame("Chat");
        frame.setResizable(false);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE );
        frame.getContentPane().setBackground(Color.white);
        frame.setLocation(10,10);

        // set the BackGround panel
        arkaplani= new JPanel();
        arkaplani.setLayout(null);
        arkaplani.setBackground(Color.WHITE);
        arkaplani.setBounds(10,40,480,460);

        //set SERVER UYGULAMASI label lets be lab1
        lab1= new JLabel("                                                             SERVER UYGULAMASI                       ");
        lab1.setBounds(0,0,500,30);
        lab1.setForeground(Color.BLACK);
        lab1.setOpaque(true);
        lab1.setBackground(new java.awt.Color(204, 215, 250));
        frame.add(lab1);


        // set the screen panel and join it all the other components
        ekran= new JPanel();
        ekran.setLayout(null);
        ekran.setBackground(Color.lightGray);
        ekran.setBounds(10,50,460,400);
        arkaplani.add(ekran);

        // set Lab2
        lab2= new JLabel("Dinlenecek Port Numarası: ");
        lab2.setBackground(Color.BLACK);
        lab2.setBounds(10,12,200,28);
        ekran.add(lab2);
        //set port textbox
        port= new JTextField();
        port.setBounds(180,15,50,30);
        port.setBackground(Color.white);
        port.setForeground(Color.BLACK);
        ekran.add(port);
        //set the server Start button

        baslat = new JButton("Serverı Başlat");
        baslat.setBounds(240,15,210,30);
        baslat.setBackground(new java.awt.Color(239, 205, 167));
        baslat.setBorder(new LineBorder(new java.awt.Color(243, 190, 130)));
        ekran.add(baslat);

        //set the show msj box
        msjGoster= new JTextArea();
        msjGoster.setForeground(Color.BLACK);
        msjGoster.setBackground(Color.white);
        msjGoster.setBounds(10,50,440,250);
        msjGoster.setRequestFocusEnabled(false);
        ekran.add(msjGoster);
        //set msj box
        msjbox= new JTextField("");
        msjbox.setForeground(Color.BLACK);
        msjbox.setBackground(Color.white);
        msjbox.setBounds(10,310,360,80);
        ekran.add(msjbox);
        //set send Button
        gonder = new JButton("GÖNDER");
        gonder.setBounds(380,310,70,80);
        gonder.setBackground(new java.awt.Color(239, 205, 167));
        gonder.setBorder(new LineBorder(new java.awt.Color(243, 190, 130)));
        ekran.add(gonder);
        frame.add(arkaplani);
        frame.setVisible(true);
        //başlat işlemi
        baslat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!port.getText().equals("")) {
                    try {
                        if (flag == false) {
                            BufferedReader bring = new BufferedReader(new FileReader("MesajlasmaTarihi"));
                            while ((oku = bring.readLine()) != null) {
                                msjGoster.setText(msjGoster.getText() + "\n" + oku.substring(31, 37) + ":" + oku.substring(39, (oku.length() - 1)));
                            }
                            bring.close();
                            flag = true;
                        }

                    }catch(Exception exe) {
                        //TODO:handle
                    }
                    holder= Integer.valueOf(port.getText());
                }
                else{
                    JOptionPane.showMessageDialog(null,"tekrar deneyin");
                    port.setText("");
                }
            }
        });
        //gonder bottunun işlemi
        gonder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Writer gets = new BufferedWriter(new FileWriter("MesajlasmaTarihi", true));
                    String cikanmsj = "";
                    cikanmsj = msjbox.getText().trim();
                    msjGoster.setText(msjGoster.getText() + "\nServer:" + cikanmsj);
                    gets.append("[" + now.toString() + "]" + "[Server]" + "[" + cikanmsj + "]");
                    gets.append("\n");
                    out.writeUTF(sifrele(cikanmsj));
                    gets.close();
                } catch (Exception exe) {
                    System.out.println("have problem");
                }
            }

        });
        //connection
        int start=0;
        while(start<3){
            try{
                sleep(1500);
            }
            catch(Exception exe){
                System.out.println("have a problem");
            }
            start++;
            if(start==3 && holder!=0){
                try{
                    System.out.println("port:"+holder);
                    server= new ServerSocket(holder);
                    System.out.println("start");
                    client = server.accept();
                    System.out.println("accepted");
                    in= new DataInputStream(client.getInputStream());
                    System.out.println("devam");
                    out= new DataOutputStream(client.getOutputStream());
                    System.out.println("devam");
                    while(!gelenmsj.equals("cik")) {
                        //dosyaya yaz
                        Writer gets =new BufferedWriter(new FileWriter("MesajlasmaTarihi",true));
                        gelenmsj = in.readUTF();
                        gets.append("[" + now.toString() + "]" + "[Client]" + "[" + Coz(gelenmsj) + "]");
                        gets.append("\n");
                        msjGoster.setText(msjGoster.getText() + "\nClient:" + Coz(gelenmsj));
                        gets.close();
                    }
                }
                catch(Exception exe){
                    System.out.println("bağıntı kesildi");
                }

            }
            else if(holder==0){
                start=0;
            }
        }
    }

    public static String sifrele(String Metin) {
        String temp="" ;
        char arr[] = Metin.toCharArray();

        for (int i = 1; i <= arr.length; i++) {
            temp +=arr[(arr.length-i)];
        }
        temp+="0101";
        return temp;
    }

    public static String Coz(String temp) {
        String temp1="" ;
        char arr[] = temp.toCharArray(); //aarse

        for (int i = 1; i <= (arr.length-4); i++) {
            temp1 +=arr[((arr.length-4)-i)];
        }
        return temp1;
    }

    public static void main(String[] args){

        serverFrm();
    }

}