import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class VVP_lab_10 {
    public static Socket first,second;
    public static void main(String[] args) throws IOException {
        try(ServerSocket s = new ServerSocket(8189)){
            int count=0;
            while(true) {
                Socket incoming = s.accept();
                if (count < 2) {
                    if (first == null) {
                        System.out.println("Spawning " + (count + 1));
                        count++;
                        first = incoming;
                    } else {
                        if (second == null) {
                            System.out.println("Spawning " + (count + 1));
                            count++;
                            second = incoming;
                        }
                        if (count == 2) {
                            System.out.println("Activated!");
                            new Thread(new ThreadedEchoHandler(first, second)).start();
                            new Thread(new ThreadedEchoHandler(second, first)).start();
                        }
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
class ThreadedEchoHandler implements Runnable {
    private Socket inSocket,outSocket;
    public ThreadedEchoHandler(Socket inSocket1, Socket outSocket1) {
        inSocket=inSocket1;
        outSocket=outSocket1;
    }

    public void run() {
        try (InputStream inStream = inSocket.getInputStream(); OutputStream outStream = outSocket.getOutputStream();OutputStream outputStream = inSocket.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");
            PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);
            PrintWriter out1 = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            while (true) {
                out1.println("Enter you nickname");
                if (in.hasNextLine()) {
                    String line = in.nextLine();
                    out1.println("Enter email");
                    if (in.hasNextLine()) {
                    String line1 = "New message from "+line+": "+in.nextLine();
                    out.println(line1);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error");
        }
    }//run
}//telnet localhost 8189