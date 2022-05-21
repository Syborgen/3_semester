import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MT implements Runnable{
    public Character[] alphabet = new Character[]{'a', 'b', 'c'};
    final public static Character[] RABOCHIE_PEREMENNYE = {'x', 'y', 'z'};
    final public Character LYAMBDA = 'Y';
    public Integer[] sostoyaniya = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10};
    public static ChtoDelatOdnolenochnaya[][] matritsaMT;
    public static Character[] symbols;
    public static int karetka = 1, indeksSimvola,indeksSimvola1, curSostoyanie = 0,karetka1=1,curSostoyanie1=0;
    public static String vyvodString;


    public MT() throws IOException {
        symbols = new Character[RABOCHIE_PEREMENNYE.length + alphabet.length + 2];
        System.arraycopy(alphabet, 0, symbols, 1, alphabet.length);
        System.arraycopy(RABOCHIE_PEREMENNYE, 0, symbols, alphabet.length + 1, RABOCHIE_PEREMENNYE.length);
        symbols[symbols.length - 1] = LYAMBDA;
        matritsaMT = new ChtoDelatOdnolenochnaya[sostoyaniya.length][symbols.length];
        zapolnenieMatritsy();


        // for (int i = 1; i < sostoyaniya.length; i++) {//вывод матрицы
        //   for (int j = 1; j < symbols.length; j++) {
        //     print(matritsaMT[i][j]);
        //   System.out.print(" ");
        // }//for
        // System.out.println();
        //}//for


    }//MT constuctor


    public void run()  {
        for (int i = 0; i <11 ; i++) {
            if(i!=9) {
                for (int j = 1; j < 8; j++) {
                    print(MT.matritsaMT[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }

        }
        System.out.println("MT: "+Thread.currentThread().getName());

        JFrame frame = new JFrame();
        frame.setTitle("Одноленточная МТ");
        JTextField inTextField = new JTextField(10);
        frame.setSize(400, 450);
        frame.setLocation(50,50);
        frame.setLayout(null);

        inTextField.setBounds(10, 10, 200, 25);
        inTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if ((c != 'a' & c != 'b' & c != 'c') && (c != KeyEvent.VK_BACK_SPACE))
                    e.consume();
            }
        });

        JButton goButton = new JButton();
        goButton.setBounds(250, 10, 100, 25);
        goButton.setText("GO");
        JTextArea textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(10, 50, 350, 350);
        textArea.setEditable(false);
        frame.add(scroll);
        frame.add(goButton);
        frame.add(inTextField);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        goButton.addActionListener(new ActionListener() {
            @Override
            synchronized public void actionPerformed(ActionEvent e) {
                File ofile = new File("Odnolentochnaya.txt");
                FileWriter ofilew = null;
                try {
                    ofilew = new FileWriter(ofile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                BufferedWriter ofbuf = new BufferedWriter(ofilew);
                if (!inTextField.getText().equals("")) {
                    if (inTextField.getText().charAt(0) != 'Y') {
                        curSostoyanie = 0;
                        karetka = 1;
                    }else {
                        JOptionPane.showMessageDialog(null, "Нельзя использовать еще раз");
                        return;
                    }
                }else{
                    curSostoyanie = 0;
                    karetka = 1;

                }


                if (!inTextField.getText().equals(""))
                    if (inTextField.getText().charAt(inTextField.getText().length() - 1) == '+' || inTextField.getText().charAt(inTextField.getText().length() - 1) == '-') {
                        JOptionPane.showMessageDialog(null, "Нельзя использовать уже обработанное слово");
                        return;

                    }
                //Timer timer = null;
                vyvodString = "";

                try {
                    //timer = new Timer(0, new ActionListener() {


                        String workString = "Y" + inTextField.getText() + "Y";
                        char[] workStringChars = workString.toCharArray();
                        String curString;


                        int o = 1;

                        //@Override
                        //public void actionPerformed(ActionEvent e) {
                    while (true) {
                        inTextField.setText(workStringChars.toString());//

                        curString = "";
                        System.out.println("Шаг " + o + "=========================");
                        vyvodString += "Шаг " + o + "=========================\n";

                        o++;
                        for (int i = 0; i < workStringChars.length; i++) {
                            if (i == karetka) {

                                curString += curSostoyanie;
                                System.out.print(curSostoyanie);
                                vyvodString += "" + curSostoyanie;
                                try {
                                    ofbuf.write("" + curSostoyanie);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            curString += workStringChars[i];
                            vyvodString += workStringChars[i];
                            System.out.print(workStringChars[i]);
                            try {
                                ofbuf.write(workStringChars[i]);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }


                        }

                        inTextField.setText(curString);//
                        textArea.setText(vyvodString);
                        try {
                            ofbuf.newLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        vyvodString += '\n';
                        System.out.println();


                        switch (workStringChars[karetka]) {
                            case 'a':
                                indeksSimvola = 1;
                                break;
                            case 'b':
                                indeksSimvola = 2;
                                break;
                            case 'c':
                                indeksSimvola = 3;
                                break;
                            case 'x':
                                indeksSimvola = 4;
                                break;
                            case 'y':
                                indeksSimvola = 5;
                                break;
                            case 'z':
                                indeksSimvola = 6;
                                break;
                            case 'Y':
                                indeksSimvola = 7;
                                break;
                        }//switch

                        int curcursost = curSostoyanie;
                        curSostoyanie = matritsaMT[curcursost][indeksSimvola].toSostoyanie;
                        workStringChars[karetka] = matritsaMT[curcursost][indeksSimvola].toCharacter;
                        switch (matritsaMT[curcursost][indeksSimvola].toNapravlenie) {
                            case 'r':
                                karetka++;
                                break;
                            case 'l':
                                karetka--;
                                break;
                            case 'e':
                                break;
                        }//switch
                        if (curSostoyanie == 9) {
                            System.out.println("Результат: ");
                            System.out.println(workStringChars);

                            String outString = new String(workStringChars);
                            vyvodString += "Результат: \n" + outString;
                            try {
                                ofbuf.write("" + outString);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            //Timer timer = (Timer) e.getSource();
                            //timer.stop();
                            inTextField.setText(outString);
                            textArea.setText(vyvodString);
                            try {
                                ofbuf.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            return;

                        }

                    }//while

                       // }
                    //});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (inTextField.getText().equals("")){
                    System.out.println("Пустое множество");
                }
                //    timer.start();
                else if (inTextField.getText().charAt(0) != 'Y') {
                //    timer.start();
                }
            }
        });



    }//run


    public static void zapolnenieMatritsy() {
        //q0
        matritsaMT[0][1] = new ChtoDelatOdnolenochnaya(1, 'x', 'r');
        matritsaMT[0][2] = new ChtoDelatOdnolenochnaya(8, 'b', 'r');
        matritsaMT[0][3] = new ChtoDelatOdnolenochnaya(8, 'c', 'r');
        matritsaMT[0][4] = new ChtoDelatOdnolenochnaya(10, 'x', 'r');
        matritsaMT[0][5] = new ChtoDelatOdnolenochnaya(10, 'y', 'r');
        matritsaMT[0][6] = new ChtoDelatOdnolenochnaya(10, 'z', 'r');
        matritsaMT[0][7] = new ChtoDelatOdnolenochnaya(9, '-', 'e');
        //q1//
        matritsaMT[1][1] = new ChtoDelatOdnolenochnaya(1, 'a', 'r');
        matritsaMT[1][2] = new ChtoDelatOdnolenochnaya(2, 'y', 'r');
        matritsaMT[1][3] = new ChtoDelatOdnolenochnaya(8, 'c', 'r');
        matritsaMT[1][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[1][5] = new ChtoDelatOdnolenochnaya(1, 'y', 'r');
        matritsaMT[1][6] = new ChtoDelatOdnolenochnaya(8, 'z', 'r');
        matritsaMT[1][7] = new ChtoDelatOdnolenochnaya(8, 'Y', 'e');
        //q2//
        matritsaMT[2][1] = new ChtoDelatOdnolenochnaya(8, 'a', 'r');
        matritsaMT[2][2] = new ChtoDelatOdnolenochnaya(3, 'y', 'r');
        matritsaMT[2][3] = new ChtoDelatOdnolenochnaya(8, 'c', 'r');
        matritsaMT[2][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[2][5] = new ChtoDelatOdnolenochnaya(8, 'y', 'r');
        matritsaMT[2][6] = new ChtoDelatOdnolenochnaya(8, 'z', 'r');
        matritsaMT[2][7] = new ChtoDelatOdnolenochnaya(8, 'Y', 'e');
        //q3//
        matritsaMT[3][1] = new ChtoDelatOdnolenochnaya(8, 'a', 'r');
        matritsaMT[3][2] = new ChtoDelatOdnolenochnaya(3, 'b', 'r');
        matritsaMT[3][3] = new ChtoDelatOdnolenochnaya(4, 'z', 'r');
        matritsaMT[3][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[3][5] = new ChtoDelatOdnolenochnaya(8, 'y', 'r');
        matritsaMT[3][6] = new ChtoDelatOdnolenochnaya(3, 'z', 'r');
        matritsaMT[3][7] = new ChtoDelatOdnolenochnaya(8, 'Y', 'e');
        //q4//
        matritsaMT[4][1] = new ChtoDelatOdnolenochnaya(8, 'a', 'r');
        matritsaMT[4][2] = new ChtoDelatOdnolenochnaya(8, 'b', 'r');
        matritsaMT[4][3] = new ChtoDelatOdnolenochnaya(5, 'z', 'r');
        matritsaMT[4][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[4][5] = new ChtoDelatOdnolenochnaya(8, 'y', 'r');
        matritsaMT[4][6] = new ChtoDelatOdnolenochnaya(8, 'z', 'r');
        matritsaMT[4][7] = new ChtoDelatOdnolenochnaya(8, 'Y', 'e');
        //q5//
        matritsaMT[5][1] = new ChtoDelatOdnolenochnaya(8, 'a', 'r');
        matritsaMT[5][2] = new ChtoDelatOdnolenochnaya(8, 'b', 'r');
        matritsaMT[5][3] = new ChtoDelatOdnolenochnaya(6, 'z', 'r');
        matritsaMT[5][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[5][5] = new ChtoDelatOdnolenochnaya(8, 'y', 'r');
        matritsaMT[5][6] = new ChtoDelatOdnolenochnaya(8, 'z', 'r');
        matritsaMT[5][7] = new ChtoDelatOdnolenochnaya(8, 'Y', 'e');
        //q6//
        matritsaMT[6][1] = new ChtoDelatOdnolenochnaya(8, 'a', 'r');
        matritsaMT[6][2] = new ChtoDelatOdnolenochnaya(8, 'b', 'r');
        matritsaMT[6][3] = new ChtoDelatOdnolenochnaya(7, 'z', 'l');
        matritsaMT[6][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[6][5] = new ChtoDelatOdnolenochnaya(8, 'y', 'r');
        matritsaMT[6][6] = new ChtoDelatOdnolenochnaya(8, 'z', 'r');
        matritsaMT[6][7] = new ChtoDelatOdnolenochnaya(8, 'Y', 'e');
        //q7//
        matritsaMT[7][1] = new ChtoDelatOdnolenochnaya(7, 'a', 'l');
        matritsaMT[7][2] = new ChtoDelatOdnolenochnaya(7, 'b', 'l');
        matritsaMT[7][3] = new ChtoDelatOdnolenochnaya(7, 'c', 'l');
        matritsaMT[7][4] = new ChtoDelatOdnolenochnaya(10, 'x', 'r');
        matritsaMT[7][5] = new ChtoDelatOdnolenochnaya(7, 'y', 'l');
        matritsaMT[7][6] = new ChtoDelatOdnolenochnaya(7, 'z', 'l');
        matritsaMT[7][7] = new ChtoDelatOdnolenochnaya(10, 'Y', 'r');
        //q8//
        matritsaMT[8][1] = new ChtoDelatOdnolenochnaya(8, 'a', 'r');
        matritsaMT[8][2] = new ChtoDelatOdnolenochnaya(8, 'b', 'r');
        matritsaMT[8][3] = new ChtoDelatOdnolenochnaya(8, 'c', 'r');
        matritsaMT[8][4] = new ChtoDelatOdnolenochnaya(8, 'x', 'r');
        matritsaMT[8][5] = new ChtoDelatOdnolenochnaya(8, 'y', 'r');
        matritsaMT[8][6] = new ChtoDelatOdnolenochnaya(8, 'z', 'r');
        matritsaMT[8][7] = new ChtoDelatOdnolenochnaya(9, '-', 'e');
        //q10//
        matritsaMT[10][1] = new ChtoDelatOdnolenochnaya(1, 'x', 'r');
        matritsaMT[10][2] = new ChtoDelatOdnolenochnaya(8, 'b', 'r');
        matritsaMT[10][3] = new ChtoDelatOdnolenochnaya(8, 'c', 'r');
        matritsaMT[10][4] = new ChtoDelatOdnolenochnaya(10, 'x', 'r');
        matritsaMT[10][5] = new ChtoDelatOdnolenochnaya(10, 'y', 'r');
        matritsaMT[10][6] = new ChtoDelatOdnolenochnaya(10, 'z', 'r');
        matritsaMT[10][7] = new ChtoDelatOdnolenochnaya(9, '+', 'e');

    }

    synchronized public static int countOfMoves(int n) {
        int count = 0;
        karetka1=1;
        char[] charMas = new char[n];
        for (int o = 0; o < charMas.length; o++) {
            charMas[o] = 'a';
        }
        for (int e=0;e<(Math.pow(3,n));e++) {
            //System.out.println(String.valueOf(charMas));
            String workString="Y"+String.valueOf(charMas)+"Y";
            char[] workStringChars = workString.toCharArray();
            int p = 0;
            while (true) {
                p++;
                switch (workStringChars[karetka1]) {
                    case 'a':
                        indeksSimvola1 = 1;
                        break;
                    case 'b':
                        indeksSimvola1 = 2;
                        break;
                    case 'c':
                        indeksSimvola1 = 3;
                        break;
                    case 'x':
                        indeksSimvola1 = 4;
                        break;
                    case 'y':
                        indeksSimvola1 = 5;
                        break;
                    case 'z':
                        indeksSimvola1 = 6;
                        break;
                    case 'Y':
                        indeksSimvola1 = 7;
                        break;
                }//switch
                int curcursost = curSostoyanie1;
                curSostoyanie1 = matritsaMT[curcursost][indeksSimvola1].toSostoyanie;
                workStringChars[karetka1] = matritsaMT[curcursost][indeksSimvola1].toCharacter;
                switch (matritsaMT[curcursost][indeksSimvola1].toNapravlenie) {
                    case 'r':
                        karetka1++;
                        break;
                    case 'l':
                        karetka1--;
                        break;
                    case 'e':
                        break;
                }//switch
                if (curSostoyanie1 == 9) {
                   // System.out.println(p);
                    if (count<p) count=p;
                    break;
                }
            }//while


            Kursovaya.insert(charMas);
            curSostoyanie1=0;
            karetka1=1;
        }//for
        return count;
    }//countOfMoves;

    public static void print(ChtoDelatOdnolenochnaya a) {
        System.out.print(a.toSostoyanie + "" + a.toCharacter + "" + a.toNapravlenie);
    }
}//MT

