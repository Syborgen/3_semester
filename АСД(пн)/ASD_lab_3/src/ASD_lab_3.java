import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.lang.Exception;
public class ASD_lab_3 {
    public static void main(String[] args) throws Exception{
        EventQueue.invokeLater(() ->{
            Frame frame = new Frame();
            JButton addButton = new JButton();
            JButton fatherButton = new JButton();
            JTextField addText = new JTextField(10);
            JPanel panelButtons = new JPanel();
            Tree tree = new Tree();
            addButton.setText("Add");
            fatherButton.setText("Find Father");
            GraphicsPanel graphicsPanel =new GraphicsPanel(tree);
            final JScrollPane scroll = new JScrollPane(graphicsPanel);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    graphicsPanel.p=0;
                    graphicsPanel.find=0;
                    try {
                        tree.add(Integer.parseInt(addText.getText()));
                    }catch (NumberFormatException e1){
                        JOptionPane.showMessageDialog(null, "Введите число от -1000000000 до 1000000000" );
                        return;
                    }
                    graphicsPanel.repaint();
                }
            });
            fatherButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                    graphicsPanel.find=Integer.parseInt(addText.getText());
                    }catch (NumberFormatException e1){
                        JOptionPane.showMessageDialog(null, "Введите число от -1000000000 до 1000000000" );
                        return;
                    }
                    graphicsPanel.p=1;
                    graphicsPanel.repaint();
                    System.out.println("Обход слева на право сниху вверх сверху вниз");
                    Tree.lr(tree.getHead());

                    System.out.println("Обход  снизу вверх");
                    Tree.dt(tree.getHead());

                    System.out.println("Обход сверху вниз");
                    Tree.td(tree.getHead());
                }
            });
            frame.setLayout(null);
            scroll.setBounds(0,0,1000,500);
            panelButtons.setBounds(0,500,1000,50);
            panelButtons.add(addText);
            panelButtons.add(addButton);
            panelButtons.add(fatherButton);
            panelButtons.setBackground(Color.yellow);
            frame.add(scroll);
            frame.add(panelButtons);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });//EventQueue.invokeLater
    }//Main
    static class Frame extends JFrame{
        private static final int DEFAULT_WIDTH = 1000;
        private static final int DEFAULT_HEIGHT = 600;
        public Frame(){
            setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
            setTitle("ASD_lab_3");
        }
    }//Frame

    static class Node{
        public int data;
        public Node leftChild;
        public Node rightChild;
        private Node(){
            data = 0;
            leftChild=null;
            rightChild=null;
        }
    }//Node
    static class Tree{
        private static int deep;
        public static Node head;
        private Tree(){
            head=null;
            deep=-1;
        }

        public static void lr (Node head){
            if(head.leftChild!=null)
            lr(head.leftChild);
            System.out.println(head.data);
            if(head.rightChild!=null)
            lr(head.rightChild);
        }
        public static void td (Node head){
            System.out.println(head.data);
            if(head.leftChild!=null)
            lr(head.leftChild);
            if(head.rightChild!=null)
            lr(head.rightChild);
        }
        public static void dt (Node head){
            if(head.leftChild!=null)
            lr(head.leftChild);
            if(head.rightChild!=null)
            lr(head.rightChild);
            System.out.println(head.data);
        }
        public static Node getHead(){
            return head;
        }
        public static int getDeep() {
            return deep;
        }
        private void readlr(Node cur){
            if (cur!=null) {
                readlr(cur.leftChild);
                System.out.println("Element: " + cur.data);
                readlr(cur.rightChild);
            }
        }
        public void show(){
            for (int i=0;i<deep;i++){
            }
        }
        public void add(int data){
            Node node = new Node();
            node.data = data;
            int curdeep=0;
            if(head==null){
                head=node;
                deep=0;
            }else{
                Node current = head;
                Node prev = null;
                while(true){
                    prev=current;
                    if(data<prev.data){
                        current=current.leftChild;
                        curdeep++;
                        if (current==null){
                            prev.leftChild=node;
                            if(curdeep>deep)
                            deep=curdeep;
                            return;
                        }//if
                    }else{
                        current=current.rightChild;
                        curdeep++;
                        if(current==null){
                            prev.rightChild=node;
                            if(curdeep>deep)
                            deep=curdeep;
                            return;
                        }//if
                    }//else
                }//while
            }//else
        }//add
    }//Tree
}//end