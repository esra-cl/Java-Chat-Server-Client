import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;


public class ClientForm extends JFrame{
    public static JFrame frame;
    public static JTextArea msjGoster;

    public static JPanel arkaplani,ekran;
    public static JLabel lab1 ,lab2;
    public static JTextField ip,port,msjbox;
    public static JButton baslat,gonder;
    public static Socket client ;
    public static DataInputStream in;
    public static DataOutputStream out;
    public static String cikanmsj="";
    static boolean flag=false;
    static String oku;
    static  int portHolder ;
    static  String ipHolder ;
    static String gelenmsj="";



    public static void ClientFrm(){
        //set frame
        frame= new JFrame("Chat");
        frame.setResizable(false);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE );
        //frame.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
        frame.setLocation(510,10);

        // set the BackGround panel
        arkaplani= new JPanel();

        arkaplani.setLayout(null);
        arkaplani.setBackground(Color.WHITE);
        arkaplani.setBounds(10,40,480,460);

        //set SERVER UYGULAMASI label lets be lab1
        lab1= new JLabel("                                                             CLİENT UYGULAMASI                       ");
        lab1.setBounds(0,0,500,30);
        lab1.setForeground(Color.BLACK);
        lab1.setOpaque(true);
        lab1.setBackground(new java.awt.Color(209, 250, 204));
        frame.add(lab1);

        // set the screen panel and join it all the other components
        ekran= new JPanel();
        ekran.setLayout(null);
        ekran.setBackground(Color.lightGray);
        ekran.setBounds(10,50,460,400);
        arkaplani.add(ekran);

        // set Lab2
        lab2= new JLabel("Server IP:");
        lab2.setBackground(Color.BLACK);
        lab2.setBounds(10,12,120,28);
        ekran.add(lab2);

        //ip text field
        ip= new JTextField();
        ip.setBounds(70,15,110,25);
        ip.setBackground(Color.white);
        ip.setForeground(Color.BLACK);
        ekran.add(ip);

        lab2= new JLabel("Port: ");
        lab2.setBackground(Color.BLACK);
        lab2.setBounds(200,12,40,28);
        ekran.add(lab2);
        //ip text field
        port= new JTextField();
        port.setBounds(230,15,60,25);
        port.setBackground(Color.white);
        port.setForeground(Color.BLACK);
        ekran.add(port);

        //set the show msj box
        msjGoster= new JTextArea();
        msjGoster.setForeground(Color.BLACK);
        msjGoster.setBackground(Color.white);
        msjGoster.setBounds(10,50,440,250);
        msjGoster.setRequestFocusEnabled(false);
        ekran.add(msjGoster);

        //set msj box
        msjbox= new JTextField("");
        msjbox.setForeground(Color.lightGray);
        msjbox.setBackground(Color.white);
        msjbox.setBounds(10,310,360,80);


        ekran.add(msjbox);
        //set send Button
        gonder = new JButton("GÖNDER");
        gonder.setBounds(380,310,70,80);
        gonder.setBackground(new java.awt.Color(239, 205, 167));
        gonder.setBorder(new LineBorder(new java.awt.Color(243, 190, 130)));
        ekran.add(gonder);

        //set the server bağlan button

        baslat = new JButton("BAĞLAN");
        baslat.setBounds(320,15,130,30);
        baslat.setBackground(new java.awt.Color(239, 205, 167));
        baslat.setBorder(new LineBorder(new java.awt.Color(243, 190, 130)));
        ekran.add(baslat);

        frame.add(arkaplani);
        frame.setVisible(true);

        baslat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( port.getText().equals("")==false &&  ip.getText().equals("")==false) {
                    ipHolder=String.valueOf(ip.getText());
                    portHolder=Integer.valueOf(port.getText());

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
                        System.out.println("139have problem");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"tekrar deneyin");
                    port.setText("");
                }
            }
        });

        gonder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    cikanmsj=msjbox.getText().trim();
                    msjGoster.setText(msjGoster.getText()+"\nClient:"+cikanmsj);
                    out.writeUTF(sifrele(cikanmsj));

                }catch(Exception exe){
                    System.out.println("have a problem");
                }
            }
        });
        int start=0;
        while(start<3){
            try{
                sleep(2000);
            }
            catch(Exception exe){
                System.out.println("168have a problem");
            }
            start++;
            if(start==3 && portHolder!=0){
                try{
                    System.out.println("port:"+Integer.valueOf(port.getText())+"\nadres ipi"+ip.getText());
                    System.out.println("start");
                    client = new Socket(ipHolder,portHolder);
                    System.out.println("devam");
                    in = new DataInputStream(client.getInputStream());
                    System.out.println("devam");
                    out = new DataOutputStream(client.getOutputStream());
                    System.out.println("devam");
                    while(!gelenmsj.equals("cik")){
                        gelenmsj= in.readUTF();
                        msjGoster.setText(msjGoster.getText()+"\nServer:"+ Coz(gelenmsj));
                    }
                }catch(Exception exe){
                    System.out.println("bağıntı kesildi");
                }

            }
            else if(portHolder==0 ){
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
    public static void main(String [] args){

        ClientFrm();

    }
}
