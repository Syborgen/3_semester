package VVP_lab_3;

import VVP_lab_6.exceptions;

import java.util.ArrayList;
import java.util.Scanner;

public class VVP_lab_3 {
    ArrayList<Integer> list = new ArrayList<Integer>();

    public VVP_lab_3() {

    }

    public int[] handGeneration() {
        System.out.println("Введите размерность матрицы: ");
        int n = 0;
        while (n == 0) {
            n = proverka();
            if (n == 0) {
                System.err.println("Значение не может быть 0");
            }
        }
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Элемент с номером [" + (i + 1) + "] = ");
            list.add(proverka());
        }
        return array;
    }

    public static ArrayList<Integer> variant(ArrayList<Integer> arr){
        int j=arr.size();
        for (int i = 0; i<j;i++){
            if (arr.get(i)%2==0){
                arr.add(i,arr.get(i));
                arr.add(i,arr.get(i));
                i+=2;
                j+=2;
            }
        }
    return arr;
    }



    public static int proverka() {
        Scanner scr = new Scanner(System.in);
        int temp;
        while (true) {
            try {
                if (!scr.hasNextInt())
                    throw new exceptions("Введите число: ");
                else {
                    temp = scr.nextInt();
                    return temp;
                }
            } catch (exceptions e) {
                System.err.println(e.text);
                scr.next();
            }
        }
    }

}//end
