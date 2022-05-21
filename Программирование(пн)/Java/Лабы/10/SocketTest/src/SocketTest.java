
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class SocketTest extends JFrame
{
    public JButton send;
    public JTextArea dialogue;
    public JTextField sendText;
    static PrintWriter out = null;
    public SocketTest() throws UnknownHostException, IOException
    {
        setSize(600,600);
        setLayout(null);
        Runnable r = new Runnable()
        {
            public void run()
            {
                try(Socket s = new Socket(InetAddress.getLocalHost(),8189);
                    Scanner in = new Scanner(s.getInputStream(),"UTF-8");)
                {
                    OutputStream outStream = s.getOutputStream();
                    out = new PrintWriter(new OutputStreamWriter(outStream,"UTF-8"),true);

                    while(in.hasNextLine())
                    {
                        String line = in.nextLine();
                        dialogue.append(line + "\n");
                    }
                } catch (UnknownHostException e) {
                    JOptionPane.showMessageDialog(null, "Connection error");
                    System.exit(0);
                    e.printStackTrace();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Connection error");
                    System.exit(0);
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
        JPanel panel = new JPanel();
        send = new JButton("Send");
        send.setEnabled(true);

        send.addActionListener(event ->
                {
                    if(sendText.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Поле ввода пустое!");
                        return;
                    }
                    if(sendText.getText().replaceAll(" ", "").equals(""))
                    {
                        sendText.setText("");
                        return;
                    }
                    dialogue.append(sendText.getText() + "\n");
                    out.println(sendText.getText());
                    sendText.setText("");
                }
        );

        dialogue = new JTextArea(20,35);
        dialogue.setEditable(false);
        dialogue.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        dialogue.setLocation(0,0);
        dialogue.setSize(600,470);



        sendText = new JTextField(30);
        sendText.setLocation(0,500);
        sendText.setSize(400,100);
        send.setLocation(400,500);
        send.setSize(200,100);
        sendText.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        panel.setSize(new Dimension(600,600));
        panel.setLocation(new Point(0,0));

        panel.add(sendText);
        panel.add(send);
        panel.add(new JScrollPane(dialogue));

        add(panel);
        //}
    }
}