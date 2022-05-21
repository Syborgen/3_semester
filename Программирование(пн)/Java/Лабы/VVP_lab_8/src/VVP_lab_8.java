import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VVP_lab_8 {
    public static void main(String[] args) throws Exception{
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 8");
        System.out.println("Задание: создать калькулятор на свинге");
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setLayout(null);
            frame.setSize(315, 340);
            frame.setTitle("Calculator");


            JRadioButton normalButton = new JRadioButton("Normal",true);
            normalButton.setBounds(0,50,100,50);

            JRadioButton boldButton= new JRadioButton("Bold",false);
            boldButton.setBounds(100,50,100,50);

            JRadioButton italicButton=new JRadioButton("Italic",false);
            italicButton.setBounds(200,50,100,50);

            ButtonGroup group = new ButtonGroup();
            group.add(normalButton);
            group.add(boldButton);
            group.add(italicButton);


            JLabel firstl = new JLabel();
            firstl.setText("Число 1");
            firstl.setBounds(0, 0, 100, 25);

            JLabel secondl = new JLabel();
            secondl.setText("Число 2");
            secondl.setBounds(100, 0, 100, 25);

            JLabel resultl = new JLabel();
            resultl.setText("Результат");
            resultl.setBounds(200, 0, 100, 25);

            JTextField firstNum = new JTextField();
            firstNum.setBounds(0, 25, 100, 25);

            JTextField secondNum = new JTextField();
            secondNum.setBounds(100, 25, 100, 25);

            JTextField result = new JTextField();
            result.setBounds(200, 25, 100, 25);
            result.setEditable(false);

            JButton plusButton = new JButton();
            plusButton.setText("+");
            plusButton.setBounds(0, 100, 150, 100);
            plusButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int a=0;
                    try {
                        a = Integer.parseInt(firstNum.getText()) + Integer.parseInt(secondNum.getText());
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null,"Введите два числа");
                    }
                    result.setText(Integer.toString(a));
                    setfont(result,normalButton.isSelected(),boldButton.isSelected(),italicButton.isSelected());

                }
            });

            JButton minusButton = new JButton();
            minusButton.setText("-");
            minusButton.setBounds(150, 100, 150, 100);
            minusButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int a=0;
                    try {
                        a = Integer.parseInt(firstNum.getText()) - Integer.parseInt(secondNum.getText());
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null,"Введите два числа");
                    }
                    result.setText(Integer.toString(a));
                    setfont(result,normalButton.isSelected(),boldButton.isSelected(),italicButton.isSelected());
                }
            });

            JButton multiButton = new JButton();
            multiButton.setText("*");
            multiButton.setBounds(0, 200, 150, 100);
            multiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int a=0;
                    try {
                        a = Integer.parseInt(firstNum.getText()) * Integer.parseInt(secondNum.getText());
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null,"Введите два числа");
                    }
                    result.setText(Integer.toString(a));
                    setfont(result,normalButton.isSelected(),boldButton.isSelected(),italicButton.isSelected());


                }
            });

            JButton delitButton = new JButton();
            delitButton.setText("/");
            delitButton.setBounds(150, 200, 150, 100);
            delitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int a=0;
                    try {
                        a = Integer.parseInt(firstNum.getText()) / Integer.parseInt(secondNum.getText());
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null,"Введите два числа (второй аргумент не 0)");
                    }
                    result.setText(Integer.toString(a));
                    setfont(result,normalButton.isSelected(),boldButton.isSelected(),italicButton.isSelected());

                }
            });


            frame.add(firstl);
            frame.add(secondl);
            frame.add(resultl);
            frame.add(firstNum);
            frame.add(secondNum);
            frame.add(result);
            frame.add(normalButton);
            frame.add(boldButton);
            frame.add(italicButton);
            frame.add(plusButton);
            frame.add(minusButton);
            frame.add(multiButton);
            frame.add(delitButton);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }//main
    public static void setfont(JTextField field,boolean normal,boolean bold,boolean italic){
        if (normal){
            field.setFont(new Font("Calibri",Font.PLAIN,field.getFont().getSize()));
        }
        if (bold){
            field.setFont(new Font("Calibri",Font.BOLD,field.getFont().getSize()));
        }
        if (italic){
            field.setFont(new Font("Calibri",Font.ITALIC,field.getFont().getSize()));
        }
    }
}
