
import java.io.*;

public class VVP_lab_9 {

    public static void main(String[] args) {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 9");
        System.out.println("Задание: переписать первые 5 строк из исходного файла в другой файл");
        try {
            File readFile = new File("readFile.txt");
            File writeFile = new File("writeFile.txt");
            FileWriter fileWriter = new FileWriter(writeFile);
            FileReader fileReader = new FileReader(readFile);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            BufferedReader br = new BufferedReader(fileReader);
            if (!writeFile.exists()) {
                writeFile.createNewFile();
            }//if
            String line;
            int k = 0;
            while (5 > k) {
                if ((line = br.readLine()) != null) {
                    System.out.println(line);
                    bw.write(line);
                    bw.newLine();
                } else {
                    bw.write("Конец исходного файла");
                    bw.newLine();
                }
                k++;
            }
            bw.close();
            br.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }//catch
    }//main
}//end
