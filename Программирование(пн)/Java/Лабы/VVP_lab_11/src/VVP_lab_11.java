import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class VVP_lab_11  {
    public static void main(String[] args) throws Exception {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 11");
        System.out.println("Задание: нарисовать треугольник Серпинского");
        EventQueue.invokeLater(()->{
            JFrame frame = new JFrame();
            frame.setLayout(null);
            frame.setSize(600,600);
            frame.setTitle("VVP_lab_11");
            frame.setLocationRelativeTo(null);

            GraphicsPanel graphicsPanel = new GraphicsPanel();
            graphicsPanel.setBounds(0,0,600,520);
            graphicsPanel.setSize(600,520);
            graphicsPanel.setBackground(Color.PINK);
            frame.add(graphicsPanel);

            JTextField nTextField = new JTextField();
            nTextField.setBounds(200,525,100,25);
            frame.add(nTextField);

            JLabel nLabel = new JLabel();
            nLabel.setText("Число повторений рекурсии:");
            nLabel.setBounds(0,525,200,25);
            frame.add(nLabel);

            JButton goButton = new JButton();
            goButton.setText("Start");
            goButton.setBounds(450,525,100,25);
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        graphicsPanel.n = Integer.parseInt(nTextField.getText());
                    }catch (NumberFormatException e1){
                        JOptionPane.showMessageDialog(null,e1.getMessage());
                        return;
                    }
                    if (graphicsPanel.n<0)
                        JOptionPane.showMessageDialog(null,"Введите неотрицательное число");
                    graphicsPanel.repaint();
                }
            });
            frame.add(goButton);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}



