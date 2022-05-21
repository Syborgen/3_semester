import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MnogolentochnayaMT implements Runnable {
    public Character[] alphabet = new Character[]{'a', 'b', 'c'};
    final public Character LYAMBDA = 'Y';
    public Integer[] sostoyaniya = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8,9};
    public static ChtoDelatMnogolenochnaya[][] matritsaMTa,matritsaMTY;
    public static Character[] symbols;
    public static int karetka1=1,karetka2=1,indeksSimvola1,indeksSimvola2,curSostoyanie=0;
    public static int karetka12=1,karetka22=1,indeksSimvola12,indeksSimvola22,curSostoyanie1=0;
    public static String vyvodString;


    public MnogolentochnayaMT() throws Exception {
    symbols = new Character[alphabet.length + 2];
    System.arraycopy(alphabet, 0, symbols, 1, alphabet.length);
    symbols[symbols.length - 1] = LYAMBDA;
    matritsaMTa = new ChtoDelatMnogolenochnaya[sostoyaniya.length][symbols.length];
    matritsaMTY = new ChtoDelatMnogolenochnaya[sostoyaniya.length][symbols.length];
    zapolnenieMatritsy();

}//MT constuctor


    public void run()  {
        System.out.println("MMT: "+Thread.currentThread().getName());
        JFrame frame = new JFrame();
        frame.setTitle("Многоленточная МТ");
        JTextField inTextField1 = new JTextField(10);
        frame.setSize(400, 450);
        frame.setLocation(50,500);
        frame.setLayout(null);
        JTextField inTextField2 = new JTextField(10);
        inTextField1.setBounds(10, 10, 200, 25);
        inTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if ((c != 'a' & c != 'b' & c != 'c') && (c != KeyEvent.VK_BACK_SPACE))
                    e.consume();
            }
        });
        inTextField2.setBounds(10, 50, 200, 25);
        inTextField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if ((c != 'a' & c != 'b' & c != 'c') && (c != KeyEvent.VK_BACK_SPACE))
                    e.consume();
            }
        });
        frame.add(inTextField2);

        JButton goButton = new JButton();
        goButton.setBounds(250, 10, 100, 25);
        goButton.setText("GO");
        JTextArea textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(10, 100, 350, 300);
        textArea.setEditable(false);
        frame.add(scroll);
        inTextField2.setEditable(false);
        frame.add(goButton);
        frame.add(inTextField1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        goButton.addActionListener(new ActionListener() {
            @Override
            synchronized public void actionPerformed(ActionEvent e) {
                File ofile = new File("Mnogolentochnaya.txt");
                FileWriter ofilew = null;
                try {
                    ofilew = new FileWriter(ofile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                BufferedWriter ofbuf = new BufferedWriter(ofilew);
                if (!inTextField1.getText().equals(""))
                    if (inTextField1.getText().charAt(0) != 'Y') {
                        curSostoyanie = 0;
                        karetka1 = 1;
                        karetka2=1;
                    }

                if (!inTextField1.getText().equals(""))
                    if (inTextField1.getText().charAt(inTextField1.getText().length() - 1) == '+' || inTextField1.getText().charAt(inTextField1.getText().length() - 1) == '-') {
                        JOptionPane.showMessageDialog(null, "Нельзя использовать уже обработанное слово");
                        return;

                    }
                vyvodString = "";
                inTextField1.setEditable(true);
                String workStringFirst;
                if(inTextField1.getText().equals("")){
                    workStringFirst="YY";
                }else
                workStringFirst = "Y" + inTextField1.getText() + "Y";
                char[] workStringCharsFirst = workStringFirst.toCharArray();
                String curString1,curString2;
                ArrayList<Character> workStringCharsSecond = new ArrayList<>();
                workStringCharsSecond.add('Y');
                workStringCharsSecond.add('Y');
                int o = 1;
                curSostoyanie = 0;
                karetka1 = 1;
                karetka2=1;
                while (true) {
                    inTextField1.setText(workStringCharsFirst.toString());
                    curString1="";
                    curString2="";
                    System.out.println("Шаг " + o + "===============");
                    vyvodString+="Шаг " + o + "===============\n"+"Первая лента: ";
                    o++;
                    System.out.print("Первая лента: ");
                    for (int i = 0; i < workStringCharsFirst.length; i++) {
                        if (i == karetka1) {
                            curString1+=curSostoyanie;
                            vyvodString+=""+curSostoyanie;
                            System.out.print(curSostoyanie);
                            try {
                                ofbuf.write("" + curSostoyanie);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }

                        curString1 += workStringCharsFirst[i];
                        vyvodString += workStringCharsFirst[i];
                        System.out.print(workStringCharsFirst[i]);
                        try {
                            ofbuf.write(workStringCharsFirst[i]);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println();
                    try {
                        ofbuf.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.print("Вторая лента: ");
                    vyvodString+="\nВторая лента: ";
                    for (int i = 0; i < workStringCharsSecond.size(); i++) {
                        if (i == karetka2) {
                            curString2+=curSostoyanie;
                            vyvodString+=""+curSostoyanie;
                            System.out.print(curSostoyanie);
                            try {
                                ofbuf.write("" + curSostoyanie);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        curString1 += workStringCharsSecond.get(i);
                        vyvodString += workStringCharsSecond.get(i);
                        System.out.print(workStringCharsSecond.get(i));
                        try {
                            ofbuf.write(workStringCharsSecond.get(i));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println();
                    inTextField1.setText(curString1);//
                    inTextField2.setText(curString2);//
                    textArea.setText(vyvodString);
                    try {
                        ofbuf.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    vyvodString += '\n';

                    System.out.println();


                    switch (workStringCharsFirst[karetka1]) {
                        case 'a':
                            indeksSimvola1 = 1;
                            break;
                        case 'b':
                            indeksSimvola1 = 2;
                            break;
                        case 'c':
                            indeksSimvola1 = 3;
                            break;
                        case 'Y':
                            indeksSimvola1 = 4;
                            break;
                    }//switch
                    switch (workStringCharsSecond.get(karetka2)) {
                        case 'a':
                            indeksSimvola2 = 1;
                            break;
                        case 'Y':
                            indeksSimvola2 = 4;
                            break;
                    }//switch

                    if (indeksSimvola2 == 1) {
                        int curcursost = curSostoyanie;
                        curSostoyanie = matritsaMTa[curcursost][indeksSimvola1].toSostoyanie;
                        workStringCharsFirst[karetka1] = matritsaMTa[curcursost][indeksSimvola1].toCharacter1;
                        workStringCharsSecond.set(karetka2, matritsaMTa[curcursost][indeksSimvola1].toCharacter2);
                        switch (matritsaMTa[curcursost][indeksSimvola1].toNapravlenie1) {
                            case 'r':
                                karetka1++;
                                break;
                            case 'l':
                                karetka1--;
                                break;
                            case 'e':
                                break;
                        }//switch
                        switch (matritsaMTa[curcursost][indeksSimvola1].toNapravlenie2) {
                            case 'r':
                                karetka2++;
                                break;
                            case 'l':
                                karetka2--;
                                break;
                            case 'e':
                                break;
                        }//switch
                    } else {
                        int curcursost = curSostoyanie;
                        curSostoyanie = matritsaMTY[curcursost][indeksSimvola1].toSostoyanie;
                        workStringCharsFirst[karetka1] = matritsaMTY[curcursost][indeksSimvola1].toCharacter1;
                        workStringCharsSecond.set(karetka2, matritsaMTY[curcursost][indeksSimvola1].toCharacter2);
                        switch (matritsaMTY[curcursost][indeksSimvola1].toNapravlenie1) {
                            case 'r':
                                karetka1++;
                                break;
                            case 'l':
                                karetka1--;
                                break;
                            case 'e':
                                break;

                        }
                        switch (matritsaMTY[curcursost][indeksSimvola1].toNapravlenie2) {
                            case 'r':
                                karetka2++;
                                break;
                            case 'l':
                                karetka2--;
                                break;
                            case 'e':
                                break;
                        }//switch
                    }
                    if (workStringCharsSecond.get(workStringCharsSecond.size() - 1) != 'Y')
                        workStringCharsSecond.add('Y');
                    if (curSostoyanie == 9) {
                        System.out.println("Результат: ");
                        System.out.print("Первая лента: ");
                        System.out.println(workStringCharsFirst);
                        String outString = new String(workStringCharsFirst);
                        inTextField1.setText(outString);
                        vyvodString+="Результат: \nПервая лента: " + outString+"\n";
                        outString=new String(workStringCharsSecond.toString());

                       // try {
                       //     ofbuf.write("" + outString);
                       // } catch (IOException ex) {
                       //     ex.printStackTrace();
                       // }


                        char[] out = new char[workStringCharsSecond.size()];
                        for (int i = 0; i < workStringCharsSecond.size(); i++) {
                            out[i] = workStringCharsSecond.get(i);
                        }
                        vyvodString+="Вторая лента: "+String.valueOf(out);
                        textArea.setText(vyvodString);
                        inTextField2.setText(""+String.valueOf(out));
                        System.out.print("Вторая лента: ");
                        System.out.println(out);
                        try {
                            ofbuf.write(workStringCharsFirst);
                            ofbuf.newLine();
                            ofbuf.write(out);
                            ofbuf.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        return;
                    }

                }
            }
        });
    }//run





    public static void zapolnenieMatritsy() {
        //q0
        matritsaMTY[0][1]=new ChtoDelatMnogolenochnaya(0,'a','a','r','r');
        matritsaMTY[0][2]=new ChtoDelatMnogolenochnaya(1,'b','Y','e','l');
        matritsaMTY[0][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[0][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');
        //q1
        matritsaMTY[1][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[1][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[1][3]=new ChtoDelatMnogolenochnaya(4,'c','Y','e','r');
        matritsaMTY[1][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');

        matritsaMTa[1][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[1][2]=new ChtoDelatMnogolenochnaya(2,'b','a','r','e');
        matritsaMTa[1][3]=new ChtoDelatMnogolenochnaya(8,'c','a','r','e');
        matritsaMTa[1][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q2
        matritsaMTY[2][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[2][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[2][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[2][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');

        matritsaMTa[2][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[2][2]=new ChtoDelatMnogolenochnaya(1,'b','a','r','l');
        matritsaMTa[2][3]=new ChtoDelatMnogolenochnaya(8,'c','a','r','e');
        matritsaMTa[2][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q3
        matritsaMTY[3][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[3][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[3][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[3][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');

        matritsaMTa[3][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[3][2]=new ChtoDelatMnogolenochnaya(8,'b','a','r','e');
        matritsaMTa[3][3]=new ChtoDelatMnogolenochnaya(8,'c','a','r','e');
        matritsaMTa[3][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q4
        matritsaMTY[4][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[4][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[4][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[4][4]=new ChtoDelatMnogolenochnaya(9,'+','Y','e','l');

        matritsaMTa[4][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[4][2]=new ChtoDelatMnogolenochnaya(8,'b','a','r','e');
        matritsaMTa[4][3]=new ChtoDelatMnogolenochnaya(5,'c','a','r','e');
        matritsaMTa[4][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q5
        matritsaMTY[5][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[5][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[5][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[5][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');

        matritsaMTa[5][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[5][2]=new ChtoDelatMnogolenochnaya(8,'b','a','r','e');
        matritsaMTa[5][3]=new ChtoDelatMnogolenochnaya(6,'c','a','r','e');
        matritsaMTa[5][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q6
        matritsaMTY[6][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[6][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[6][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[6][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');

        matritsaMTa[6][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[6][2]=new ChtoDelatMnogolenochnaya(8,'b','a','r','e');
        matritsaMTa[6][3]=new ChtoDelatMnogolenochnaya(7,'c','a','r','e');
        matritsaMTa[6][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q7
        matritsaMTY[7][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[7][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[7][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[7][4]=new ChtoDelatMnogolenochnaya(8,'Y','Y','e','e');

        matritsaMTa[7][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[7][2]=new ChtoDelatMnogolenochnaya(8,'b','a','r','e');
        matritsaMTa[7][3]=new ChtoDelatMnogolenochnaya(4,'c','a','r','r');
        matritsaMTa[7][4]=new ChtoDelatMnogolenochnaya(8,'Y','a','e','e');
        //q8
        matritsaMTY[8][1]=new ChtoDelatMnogolenochnaya(8,'a','Y','r','e');
        matritsaMTY[8][2]=new ChtoDelatMnogolenochnaya(8,'b','Y','r','e');
        matritsaMTY[8][3]=new ChtoDelatMnogolenochnaya(8,'c','Y','r','e');
        matritsaMTY[8][4]=new ChtoDelatMnogolenochnaya(9,'-','Y','e','e');

        matritsaMTa[8][1]=new ChtoDelatMnogolenochnaya(8,'a','a','r','e');
        matritsaMTa[8][2]=new ChtoDelatMnogolenochnaya(8,'b','a','r','e');
        matritsaMTa[8][3]=new ChtoDelatMnogolenochnaya(8,'c','a','r','e');
        matritsaMTa[8][4]=new ChtoDelatMnogolenochnaya(9,'-','a','e','e');


    }

    synchronized public static int countOfMoves(int n) {
        int count = 0;
        karetka12 = 1;
        karetka22 = 1;
        curSostoyanie1 = 0;
        char[] charMas = new char[n];
        for (int o = 0; o < charMas.length; o++) {
            charMas[o] = 'a';
        }

        for (int e=0;e<(Math.pow(3,n));e++) {
        String workStringFirst;
        if (String.valueOf(charMas).equals("")) {
            workStringFirst = "YY";
        } else
            workStringFirst = "Y" + String.valueOf(charMas) + "Y";
        char[] workStringCharsFirst = workStringFirst.toCharArray();
        ArrayList<Character> workStringCharsSecond = new ArrayList<>();
        workStringCharsSecond.add('Y');
        workStringCharsSecond.add('Y');
        int o = 1;
        while (true) {


            o++;
            switch (workStringCharsFirst[karetka12]) {
                case 'a':
                    indeksSimvola12 = 1;
                    break;
                case 'b':
                    indeksSimvola12 = 2;
                    break;
                case 'c':
                    indeksSimvola12 = 3;
                    break;
                case 'Y':
                    indeksSimvola12 = 4;
                    break;
            }//switch
            switch (workStringCharsSecond.get(karetka22)) {
                case 'a':
                    indeksSimvola22 = 1;
                    break;
                case 'Y':
                    indeksSimvola22 = 4;
                    break;
            }//switch

            if (indeksSimvola22 == 1) {
                int curcursost = curSostoyanie1;
                curSostoyanie1 = matritsaMTa[curcursost][indeksSimvola12].toSostoyanie;
                workStringCharsFirst[karetka12] = matritsaMTa[curcursost][indeksSimvola12].toCharacter1;
                workStringCharsSecond.set(karetka22, matritsaMTa[curcursost][indeksSimvola12].toCharacter2);
                switch (matritsaMTa[curcursost][indeksSimvola12].toNapravlenie1) {
                    case 'r':
                        karetka12++;
                        break;
                    case 'l':
                        karetka12--;
                        break;
                    case 'e':
                        break;
                }//switch
                switch (matritsaMTa[curcursost][indeksSimvola12].toNapravlenie2) {
                    case 'r':
                        karetka22++;
                        break;
                    case 'l':
                        karetka22--;
                        break;
                    case 'e':
                        break;
                }//switch
            } else {
                int curcursost = curSostoyanie1;
                curSostoyanie1 = matritsaMTY[curcursost][indeksSimvola12].toSostoyanie;
                workStringCharsFirst[karetka12] = matritsaMTY[curcursost][indeksSimvola12].toCharacter1;
                workStringCharsSecond.set(karetka22, matritsaMTY[curcursost][indeksSimvola12].toCharacter2);
                switch (matritsaMTY[curcursost][indeksSimvola12].toNapravlenie1) {
                    case 'r':
                        karetka12++;
                        break;
                    case 'l':
                        karetka12--;
                        break;
                    case 'e':
                        break;

                }
                switch (matritsaMTY[curcursost][indeksSimvola12].toNapravlenie2) {
                    case 'r':
                        karetka22++;
                        break;
                    case 'l':
                        karetka22--;
                        break;
                    case 'e':
                        break;
                }//switch
            }
            if (workStringCharsSecond.get(workStringCharsSecond.size() - 1) != 'Y')
                workStringCharsSecond.add('Y');
            if (curSostoyanie1 == 9) {
                if (count < o) count = --o;
                break;

            }

        }//while
        Kursovaya.insert(charMas);
        karetka12 = 1;
        karetka22 = 1;
        curSostoyanie1 = 0;
    }


        return count;
    }//countOfMoves;

    public static void print(ChtoDelatOdnolenochnaya a) {
        System.out.print(a.toSostoyanie + "" + a.toCharacter + "" + a.toNapravlenie);
    }
}
