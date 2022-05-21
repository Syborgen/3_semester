import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public final static double PI = 3.14159265359;
    public static int[][] graf = null;//{{5,1,2,3,4,5},{1,1,0,0,0,0},{2,0,1,0,0,0},{3,0,0,1,0,0},{4,0,0,0,1,0},{5,0,0,0,0,1}};//матрица смежности
    public static int[][] osnovanieGrafa;
    public static int count = 0, n;//колво компонент связности, колво вершин
    public static int[] mark = null;//{0,0,0,0,0,0};
    public static int[] razmerComp;
    public static File file = new File("In.txt");
    public static int orientirovannost1 = 0;

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();//главное окно
        frame.setSize(1000, 700);
        frame.setTitle("Individual task");
        frame.setLayout(null);

        JRadioButton neoRadioButton = new JRadioButton("Неограф", true);
        neoRadioButton.setBounds(400, 300, 200, 25);

        JRadioButton orRadioButton = new JRadioButton("Орграф", false);
        orRadioButton.setBounds(400, 325, 200, 25);

        ButtonGroup orientirovannost = new ButtonGroup();
        orientirovannost.add(neoRadioButton);
        orientirovannost.add(orRadioButton);

        JTextArea listing = new JTextArea();
        JScrollPane listingScroll = new JScrollPane(listing);
        listingScroll.setBounds(600, 300, 380, 350);

        JRadioButton fileRadioButton = new JRadioButton("Ввод с файла", true);
        fileRadioButton.setBounds(400, 350, 200, 25);

        JRadioButton handsRadioButton = new JRadioButton("Ввод вручную", false);
        handsRadioButton.setBounds(400, 400, 200, 25);

        ButtonGroup vvod = new ButtonGroup();
        vvod.add(handsRadioButton);
        vvod.add(fileRadioButton);


        JTextField tableRazmer = new JTextField();
        tableRazmer.setBounds(400, 425, 200, 25);


        JButton chooseFile = new JButton();
        chooseFile.setText("Выбрать файл");
        chooseFile.setBounds(400, 375, 200, 25);
        chooseFile.addActionListener(new ActionListener() {//выбор файла
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовый файл", "txt");//ограничение на текстовые файлы
                fileChooser.setFileFilter(filter);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int ret = fileChooser.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                }
            }
        });

        GraphicsPanel graphicsPanel = new GraphicsPanel();//создание компонента, в котором мы будем строить граф
        final JScrollPane scroll = new JScrollPane(graphicsPanel);
        scroll.setBounds(0, 0, 980, 300);

        MyTableModel tm = new MyTableModel();

        DefaultTableColumnModel cm = new DefaultTableColumnModel();

        JTable table = new JTable(tm);
        table.setColumnModel(cm);
        table.setTableHeader(null);
        table.setAutoResizeMode(0);

        JButton paintButton = new JButton();
        paintButton.setText("Пуск!");
        paintButton.setSize(100, 100);
        paintButton.setBounds(400, 480, 200, 50);
        paintButton.addActionListener(new ActionListener() {//активация алгоритма
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter out = new BufferedWriter(new FileWriter("Listing.txt"))) {//создание потока вывода в файл
                    count = 0;//задаем стартовое количество компонент связности
                    out.write("Количество компонент связности: " + count);
                    if (orRadioButton.isSelected())
                        orientirovannost1 = 1;//если выбран орграф то задаем значение флага 1
                    else
                        orientirovannost1 = 0;//если выбран неограф то задаем значение флага 0
                    if (fileRadioButton.isSelected()) {//выбран вывод из файла
                        out.newLine();
                        out.write("Ввод с файла");
                        out.newLine();


                        try (BufferedReader in = new BufferedReader(new FileReader(file))) {//vvod s fayla
                            Scanner sin = new Scanner(in);
                            n = sin.nextInt();//присваиваем количеству вершин первое число из файла
                            graf = new int[n + 1][n + 1];
                            graf[0][0] = n;
                            mark = new int[n + 1];
                            razmerComp = new int[n + 1];
                            for (int i = 1; i < n + 1; i++)//заполняем массив матрицы смежности
                                graf[0][i] = sin.nextInt();//заполняем массив матрицы смежности
                            for (int i = 1; i < n + 1; i++)//заполняем массив матрицы смежности
                                for (int j = 0; j < n + 1; j++) {//заполняем массив матрицы смежности
                                    graf[i][j] = sin.nextInt();//заполняем массив матрицы смежности
                                }


                        } catch (IOException | NumberFormatException e1) {
                            JOptionPane.showMessageDialog(null, "File Error");
                            out.newLine();
                            out.write("File Error");
                            out.newLine();
                            return;
                        } catch (InputMismatchException e1) {
                            JOptionPane.showMessageDialog(null, "Ошибка в содержании файла");
                            out.newLine();
                            out.write("Ошибка в содержании файла");
                            out.newLine();
                            return;
                        } catch (NoSuchElementException e1) {
                            JOptionPane.showMessageDialog(null, "Недостаточно данных в файле(неполная таблица смежности)");
                            out.newLine();
                            out.write("Недостаточно данных в файле(неполная таблица смежности)");
                            out.newLine();
                            return;
                        }


                    }//if
                    if (handsRadioButton.isSelected()) {//если выбран ввод с клавиатуры
                        out.newLine();
                        out.write("Ввод с клавиатуры");

                        try {
                            n = (Integer) (table.getValueAt(0, 0));//сохраняем количество
                        } catch (ArrayIndexOutOfBoundsException e1) {
                            JOptionPane.showMessageDialog(null, "Сначала создайте и заполните матрицу смежности");
                            out.newLine();
                            out.write("Сначала создайте и заполните матрицу смежности");
                            out.newLine();
                            return;
                        }
                        graf = new int[n + 1][n + 1];
                        mark = new int[n + 1];
                        razmerComp = new int[n + 1];
                        for (int i = 0; i <= n; i++)
                            for (int j = 0; j <= n; j++) {
                                try {
                                    graf[i][j] = Integer.parseInt(table.getValueAt(i, j).toString());//заполнение массива матрицы смежности
                                } catch (NumberFormatException e1) {
                                    JOptionPane.showMessageDialog(null, "Недопустимое значение (строка: " + i + " столбец: " + j + ")");
                                    out.newLine();
                                    out.write("Недопустимое значение (строка: " + i + " столбец: " + j + ")");
                                    out.newLine();
                                    return;
                                }
                                if (i > 0 & j > 0)
                                    if (graf[i][j] != 0 & graf[i][j] != 1) {
                                        JOptionPane.showMessageDialog(null, "Недопустимое значение (строка: " + i + " столбец: " + j + ")");
                                        out.newLine();
                                        out.write("Недопустимое значение (строка: " + i + " столбец: " + j + ")");
                                        out.newLine();
                                        return;
                                    }

                            }
                    }
                    for (int i = 1; i < n + 1; i++) {//zapolnenie massiva komponent
                        mark[i] = 0;//задаем начальные значение массивов
                        razmerComp[i] = 0;//задаем начальные значение массивов
                    }
                    System.out.println("Граф");
                    printMatr(graf);
                    System.out.println("=============================================");
                    System.out.println("Основание графа");
                    osnovanieGrafa = normalize(graf);//поиск основания для графа
                    printMatr(osnovanieGrafa);
                    out.write("Количество вершин: " + n);
                    out.newLine();
                    out.write("Массив принадлежности вершин к компонентам связности: ");
                    for (int i = 0; i < mark.length; i++)
                        out.write(mark[i] + " ");
                    out.newLine();
                    out.write("Массив размера компонент связности: ");
                    for (int i = 0; i < razmerComp.length; i++)
                        out.write(razmerComp[i] + " ");
                    out.newLine();
                    out.write("Матрица смежности:\n");
                    for (int i = 0; i < n + 1; i++) {
                        for (int j = 0; j < n + 1; j++) {
                            out.write(Integer.toString(graf[i][j]) + " ");
                        }
                        out.newLine();
                    }
                    out.newLine();
                    out.write("Основание графа:\n");
                    for (int i = 0; i < n + 1; i++) {
                        for (int j = 0; j < n + 1; j++) {
                            out.write(Integer.toString(osnovanieGrafa[i][j]) + " ");
                        }
                        out.newLine();
                    }
                    out.newLine();
                    if (orientirovannost1 == 0) {//если пользователь выбрал неограф, а исходные данные задают орграф то программа автоматически берет основание данного орграфа
                        int a = 0;//флаг
                        for (int i = 1; i < n + 1; i++)
                            for (int j = 1; j < n + 1; j++) {

                                if (graf[i][j] != graf[j][i])//если матрица смежности несимметрична
                                    a++;//увеличиваем значение флага

                            }
                        if (a != 0) {//если флаг не 0
                            JOptionPane.showMessageDialog(null, "Матрица смежности определяет орграф поэтому для работы программы было взято основание введенного орграфа");
                            out.write("Матрица смежности определяет орграф поэтому для работы программы было взято основание введенного орграфа");
                            out.newLine();
                        }
                    }
                    out.write("!!!!!!!!!!!!!НАЧАЛО РАБОТЫ АЛГОРИТМА!!!!!!!!!!!");
                    for (int i = 1; i < n + 1; i++) {//poisk komponent svyaznosti
                        if (mark[i] == 0) {//если вершина i еще не помечена

                            count++;//увеличиваем число компонент связности
                            out.newLine();
                            out.write("Для компоненты связности №" + count);
                            out.newLine();
                            component(i, count, out);//запускаем функцию работающую с компонентой связности
                            out.write("шаг №" + razmerComp[count]);
                            out.newLine();
                            out.write("Массив принадлежности вершин к компонентам связности: ");
                            for (int o = 0; o < mark.length; o++)
                                out.write(mark[o] + " ");
                            out.newLine();
                            out.write("Массив размера компонент связности: ");
                            for (int o = 0; o < razmerComp.length; o++)
                                out.write(razmerComp[o] + " ");
                            out.newLine();
                        }
                    }
                    out.write("!!!!!!!!!!!!!!!!!!!!!!!КОНЕЦ РАБОТЫ АЛГОРИТМА!!!!!!!!!!!!!!!!!!");
                    out.newLine();
                    out.write("После поиска компонент связности(основное задание) изменились переменные:");
                    out.newLine();
                    out.write("Количество компонент связности: " + count);
                    out.newLine();
                    out.write("Массив принадлежности вершин к компонентам связности: ");
                    for (int i = 0; i < mark.length; i++)
                        out.write(mark[i] + " ");
                    out.newLine();
                    out.write("Массив размера компонент связности: ");
                    for (int i = 0; i < razmerComp.length; i++)
                        out.write(razmerComp[i] + " ");
                    out.newLine();


                    System.out.println(count);
                    String exit = new String();

                    out.write("Результат работы программы=============================\n" + Integer.toString(count));
                    exit += "Компонент связности в графе: " + count + "\nКоличество вершин в компоненте : номера вершин\n";

                    out.newLine();
                    for (int i = 1; i < n + 1; i++) {
                        if (razmerComp[i] != 0) {
                            out.write(Integer.toString(razmerComp[i]) + " ");
                            exit += (razmerComp[i]) + " : ";
                        }
                        for (int j = 1; j < n + 1; j++) {
                            if (mark[j] == i) {
                                out.write(Integer.toString(graf[j][0]) + "; ");
                                exit += (graf[j][0]) + "; ";
                            }
                        }
                        if (razmerComp[i] != 0)
                            exit += "\n";
                        if (razmerComp[i] != 0)
                            out.newLine();
                    }
                    listing.setText(exit);
                    out.write("============================================================");
                    out.flush();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при выводе в файл");
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                graphicsPanel.n = n;
                graphicsPanel.repaint();
            }
        });


        JButton setRazmer = new JButton();
        setRazmer.setText("Задать размер матрицы");
        setRazmer.setBounds(400, 450, 200, 25);
        setRazmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Integer.parseInt(tableRazmer.getText()) < 1 || Integer.parseInt(tableRazmer.getText()) > 101) {
                        JOptionPane.showMessageDialog(null, "Введите число от 1 до 100");
                    } else {
                        tm.setColumnCount(Integer.parseInt(tableRazmer.getText()) + 1);
                        tm.setRowCount(Integer.parseInt(tableRazmer.getText()) + 1);
                        table.setValueAt(Integer.parseInt(tableRazmer.getText()), 0, 0);
                        for (int i = 1; i <= Integer.parseInt(tableRazmer.getText()); i++) {
                            table.setValueAt(i, i, 0);

                        }
                        for (int i = 1; i <= Integer.parseInt(tableRazmer.getText()); i++) {
                            table.setValueAt(i, 0, i);

                        }
                        for (int i = 1; i <= Integer.parseInt(tableRazmer.getText()); i++)
                            for (int j = 1; j <= Integer.parseInt(tableRazmer.getText()); j++)
                                table.setValueAt(0, i, j);
                        for (int i = 0; i < Integer.parseInt(tableRazmer.getText()) + 1; i++) {
                            table.getColumnModel().getColumn(i).setMaxWidth(25);
                        }
                    }
                } catch (Exception e3) {
                    JOptionPane.showMessageDialog(null, "Введите число");
                }
            }
        });


        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBounds(0, 300, 400, 350);
        frame.add(listingScroll);
        frame.add(chooseFile);
        frame.add(setRazmer);
        frame.add(tableRazmer);
        frame.add(fileRadioButton);
        frame.add(handsRadioButton);
        frame.add(neoRadioButton);
        frame.add(orRadioButton);
        frame.add(paintButton);
        frame.add(scroll);
        frame.add(scrollTable);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }//main

    public static void component(int v, int count, BufferedWriter out) throws Exception {//функция поиска составляющих в компоненте связности аргументы:номер вершины, количество компонент связноси, поток вывода в файл
        mark[v] = count;//прсваиваем элементу массива с номером обрабатываемой вершины значение равному номеру обрабатываемой компоненты связности
        razmerComp[count]++;//увеличиваем значение элемента массива с номером обрабатываемой компоненты связности
        for (int i = 1; i < n + 1; i++) {
            if (osnovanieGrafa[i][v] == 1) {//если текущая вершина связана с проверяемой
                if (mark[i] == 0) {//если вершина еще не обработана
                    out.write("шаг №" + razmerComp[count]);
                    out.newLine();
                    out.write("Массив принадлежности вершин к компонентам связности: ");
                    for (int o = 0; o < mark.length; o++)
                        out.write(mark[o] + " ");
                    out.newLine();
                    out.write("Массив размера компонент связности: ");
                    for (int o = 0; o < razmerComp.length; o++)
                        out.write(razmerComp[o] + " ");
                    out.newLine();
                    component(i, count, out);//рекурсивно вызываем функцию обработки
                }
            }
        }
    }//component

    public static void printMatr(int[][] a) {//вывод матрицы в консоль
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }//printmatr

    public static int[][] normalize(int[][] in) {//поиск основания матрицы
        int[][] out = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++)
            out[i] = Arrays.copyOf(graf[i], n + 1);
        for (int i = 1; i < in.length; i++)
            for (int j = 1; j < in.length; j++) {
                if (in[i][j] == 1 | in[j][i] == 1) {
                    out[i][j] = 1;
                    out[j][i] = 1;
                }
            }
        return out;
    }//normalize
}