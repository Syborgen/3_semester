package VVP_lab_2;

import java.util.Scanner;
import VVP_lab_6.exceptions;

public class VVP_lab_2 {
    public static void main(String[] args) throws exceptions {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 2");
        System.out.println("Задание: отсортировать нечетные столбцы матрицы по возрастанию, четные-по убыванию");
        int[][] matrix = vvod();
        System.out.println("Исходная матрица: ");
        print(matrix);
        matrix = variant(matrix);
        System.out.println("Конечная матрица: ");
        print(matrix);
    }//main

    public static int[][] vvod() throws exceptions {
        System.out.println("Будете вводить вручную?(2-да,1-нет): ");
        Scanner scr = new Scanner(System.in);
        String answer = scr.nextLine();
        while (true) {
            try {
                if (!answer.equalsIgnoreCase("1"))
                    if (!answer.equalsIgnoreCase("2")) {
                        throw new exceptions("Введите 1 или 2!");
                    } else return handGeneration();
                else return autoGeneration();
            } catch (exceptions e) {
                System.err.println(e.text);
                answer = scr.nextLine();
            }
        }
    }//vvod

    public static int[] sort1(int[] arr, int p) {
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int min_i = i;
            for (int j = i + 1; j < arr.length; j++) {

                if (arr[j] < min) {
                    min = arr[j];
                    min_i = j;
                }
            }
            if (i != min_i) {
                int tmp = arr[i];
                arr[i] = arr[min_i];
                arr[min_i] = tmp;
            }
        }
        if (p == 0)
            reverse(arr);
        return arr;
    }

    public static int[] reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {  // свопинг
            array[i] += array[array.length - 1 - i];
            array[array.length - 1 - i] = array[i] - array[array.length - 1 - i];
            array[i] -= array[array.length - 1 - i];
        }
        return array;
    }

    public static int[][] variant(int[][] matrics) {
        for (int i = 0; i < matrics.length; i++) {
            if (i % 2 == 0) {
                System.out.println("Сортировка строки " + (i + 1));
                matrics[i] = sort1(matrics[i], 1);
            } else {
                System.out.println("Сортировка строки " + (i + 1));
                matrics[i] = sort1(matrics[i], 0);
            }
        }
        return matrics;
    }

    public static int[][] autoGeneration() {
        int n = 5;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = (int) (Math.random() * 20);
        return matrix;
    }

    public static int[][] handGeneration() {
        System.out.println("Введите размерность матрицы: ");
        int n = 0;
        while (n == 0) {
            n = proverka();
            if (n == 0) {
                System.err.println("Значение не может быть 0");
            }
        }
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Элемент с номером [" + (i + 1) + "][" + (j + 1) + "] = ");
                matrix[i][j] = proverka();
            }
        }
        return matrix;
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

    public static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }
}//end
