import javax.swing.*;
import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        SocketTest a = new SocketTest();
        //  SocketTest b = new SocketTest();

        a.setTitle("Пользователь 1");
        //   b.setTitle("B");

        a.setVisible(true);
        //  b.setVisible(true);

        a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //  b.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
