import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GrafikOdnolentochnayaFrame extends JFrame {

    public GrafikOdnolentochnayaFrame() {
        System.out.println("Frame: " + Thread.currentThread().getName());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(450, 50, 1000, 450);
        setLayout(null);
        setTitle("График сложности для одноленточной");
        GrafikOdnolentochnayaPanel grafic = new GrafikOdnolentochnayaPanel();
        JTextField toCount = new JTextField();
        toCount.setBounds(10, 10, 200, 25);
        toCount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if ((c<'0'||c>'9') && (c != KeyEvent.VK_BACK_SPACE))
                    e.consume();
            }
        });
        JButton goButton = new JButton("Пуск");
        //goButton.setBackground(Color.green);
        goButton.setBounds(10, 50, 200, 25);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(toCount.getText()) > 17) {
                    JOptionPane.showMessageDialog(null, "Рассчет займет лишком много времени. Введите число до 17");
                    return;
                }
                grafic.vershiny.clear();
                VershinaOdnolentochnaya.number=-1;
                for (int i = 0; i <= Integer.parseInt(toCount.getText()); i++) {
                    Runnable r = () -> {
                        synchronized (this) {
                            if (VershinaOdnolentochnaya.number++ < Integer.parseInt(toCount.getText())) {
                                VershinaOdnolentochnaya v = new VershinaOdnolentochnaya(MT.countOfMoves(VershinaOdnolentochnaya.number));
                                grafic.add(v);
                                grafic.paint(grafic.getGraphics());
                            }
                        }
                    };
                    Thread t = new Thread(r);
                    t.start();
                }
            }
        });


        JScrollPane scroll = new JScrollPane(grafic);
        scroll.setBounds(220, 10, 750, 390);


        add(scroll);
        add(toCount);
        add(goButton);

    }


}
