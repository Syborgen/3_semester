import com.sun.xml.internal.ws.util.StringUtils;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class Spisok {
    public static void main(String[] args){
       EventQueue.invokeLater(() ->{
           Frame frame = new Frame();
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setVisible(true);
       });
    }
}
//struktura
class Node{
    private Node next;
    private Node prev;
    private int value;

    public Node(int val, Node ne, Node pr){//konstruktor
        next=ne;
        prev=pr;
        value=val;
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
//frame
class Frame extends JFrame{
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 200;
    //konstruktor
    public Frame(){
        setTitle("ASD_lab_2");
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        JButton addButton = new JButton("Add");
        JButton swapButton = new JButton("Swap");
        JTextField listField = new JTextField( "Пока список пуст" );
        JTextField addField = new JTextField("Введите значение нового элемента");
        Node head = null;
        Node tail = null;
        Node cur = head;
        LinkedList<Integer> list = new LinkedList<>();
        //dobavleniye
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    list.add(Integer.parseInt(addField.getText()));
                    listField.setText(list.toString());
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null,"Введите число");
                }
            }
        });
        //swap
        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int max=-19990;
                int min = 19990;
                int mini=0,maxi=0;
                int y;
                if (list.size()<2){
                   JOptionPane.showMessageDialog(null,"Слишком мало элементов для выполнения действия");
                }else{
                    for (int i=0;i<list.size();i++){
                       if (list.get(i)<min){
                           min=list.get(i);
                           mini=i;
                       }//if
                       if (list.get(i)>max){
                           max = list.get(i);
                           maxi = i;
                       }//if
                    }//for
                    y=list.get(mini);
                    list.set(mini,list.get(maxi));
                    list.set(maxi,y);
                    listField.setText(list.toString());
                }//else
            }
        });
        JPanel all = new JPanel();
        getContentPane().add(listField);
        listField.setEditable(false);
        all.add(addField);
        all.add(addButton);
        all.add(swapButton);
        getContentPane().add(all,"South");
        setVisible(true);
    }
}