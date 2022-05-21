import java.awt.*;
import java.io.IOException;
public class Kursovaya {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(() -> {
            try {
                new Thread(new MT()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new GrafikOdnolentochnayaFrame();
            try {
                new Thread(new MnogolentochnayaMT()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GrafikMnogolentochnayaFrame();
        });
    }//main
    public static char[] insert(char[] a){
        for(int i=a.length-1;i>=0;i--){
            a[i]=plus1(a[i]);
            if(a[i]!='a') {
                return a;
            }
        }
        return a;
    }
    public static char plus1(char a){
        switch (a){
            case 'a':
                a='b';
                return a;
            case 'b':
                a='c';
                return a;
            case 'c':
                a='a';
                return a;
        }
        return a;
    }
}//end
