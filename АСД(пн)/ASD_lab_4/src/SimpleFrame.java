import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;
    private DefaultTableModel tableModel;
    private Object[] columnsHeader = new String[]{"Key", "Name"};
    private Object[] container = new Object[2];
    ArrayList arrayList = new ArrayList();
    public SimpleFrame() throws Exception {
        setLayout(null);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setSize(700,950);
        table.setBounds(0,10,700,950);
        table.setModel(tableModel);
        scrollPane.setSize(700,950);
        scrollPane.setBounds(0,0,700,950);
        add(scrollPane);
        JButton setButton = new JButton();
        setButton.setText("Set row count");
        setButton.setBounds(750,100,200,50);
        add(setButton);
        JTextField setTextField = new JTextField();
        setTextField.setBounds(750,25,200,50);
        add(setTextField);
        JTextField addTextField = new JTextField();
        addTextField.setBounds(750,200,200,50);
        add(addTextField);
        JButton addButton = new JButton();
        addButton.setText("Add");
        addButton.setBounds(750, 275,200,50);
        add(addButton);
        container[0]= new Integer(1);
        container[1]= new ArrayList<>();
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = table.getSelectedRow();
                int n;
                try {
                    n = Integer.parseInt(setTextField.getText());
                } catch (Exception e1) {

                    JOptionPane.showMessageDialog(null, "Введите положительное число");
                    return;
                }
                if(n<0)
                    JOptionPane.showMessageDialog(null,"Нельзя использовать отрицательные числа");
                container[0] = n;
                tableModel.setRowCount(0);
                for (int i = 0; i < n; i++) {
                    tableModel.insertRow(idx + 1, container);
                    container[0] = (Integer) container[0] - 1;
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"У таблицы нет ни одной строчки");
                    return;
                }
                if(addTextField.getText().length()==0){
                    JOptionPane.showMessageDialog(null,"Нельзя вводить пустую строку");
                    return;
                }
                String str;
                int key=0;
                try {
                    str = addTextField.getText();
                    for (int i = 0; i < str.length(); i++) {
                        key += Character.getNumericValue(str.charAt(i));
                        if (Character.getNumericValue(str.charAt(i))<0){
                            JOptionPane.showMessageDialog(null,"Используйте только цифры и английские буквы");
                            return;
                        }
                        System.out.println("Символ " + str.charAt(i) + " преобразуется в число " + Character.getNumericValue(str.charAt(i)));
                    }
                    key = key % table.getRowCount();
                    System.out.println("key = " + key);
                    ArrayList str1;
                    str1 = new ArrayList((ArrayList) table.getValueAt(key, 1));
                    str1.add(addTextField.getText());
                    table.setValueAt(str1, key, 1);
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null,"Error");
                    return;
                }

            }
        });
        JTextField findTextField = new JTextField();
        findTextField.setBounds(750,350,200,50);
        add(findTextField);
        JTextField keyTextField = new JTextField();
        keyTextField.setBounds(750,500,200,50);
        add(keyTextField);
        JTextField nameTextField = new JTextField();
        nameTextField.setBounds(750,575,200,50);
        add(nameTextField);
        JLabel keyLabel = new JLabel("Номер искомого элемента");
        keyLabel.setBounds(725,475,200,25);
        add(keyLabel);
        JLabel nameLabel = new JLabel("Цепочка переполнения");
        nameLabel.setBounds(725,550,200,25);
        add(nameLabel);
        JButton findButton = new JButton();
        findButton.setText("Find");
        findButton.setBounds(750,425,200,50);
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"У таблицы нет ни одной строчки");
                    return;
                }
                if(findTextField.getText().length()==0){
                    JOptionPane.showMessageDialog(null,"Нельзя вводить пустую строку");
                    return;
                }
                String str;
                int key=0;
                try {
                    str = findTextField.getText();
                    for (int i = 0; i < str.length(); i++) {
                        key += Character.getNumericValue(str.charAt(i));
                        if (Character.getNumericValue(str.charAt(i))<0){
                            JOptionPane.showMessageDialog(null,"Используйте только цифры и английские буквы");
                            return;
                        }
                        System.out.println("Символ " + str.charAt(i) + " преобразуется в число " + Character.getNumericValue(str.charAt(i)));
                    }
                    key = key % table.getRowCount();
                    System.out.println("key = " + key);
                    ArrayList str1;
                    str1 = new ArrayList((ArrayList) table.getValueAt(key, 1));
                    int parametr = 0;
                    for (int i = 0;i<str1.size();i++){
                        System.out.println(str1.get(i)+" = "+findTextField.getText());

                        if (str1.get(i).equals(findTextField.getText())){
                            parametr++;
                        }

                    }
                    if(parametr==0){
                        JOptionPane.showMessageDialog(null,"Not found");
                        return;

                    }


                    keyTextField.setText(table.getValueAt(key,0).toString());
                    nameTextField.setText(table.getValueAt(key, 1).toString());



                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null,"Error");
                    return;
                }

            }
        });
        add(findButton);

        setTitle("ASD_lab_4");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


}