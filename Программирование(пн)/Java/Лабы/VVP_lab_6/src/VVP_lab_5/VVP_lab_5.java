package VVP_lab_5;

import VVP_lab_6.exceptions;
import java.util.Scanner;

public class VVP_lab_5 {
    public static void main(String[] args) throws exceptions {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 5");
        System.out.println("Задание: Количество слов в строке, заканчивающейся точкой");
        String str = vvod();
        System.out.println("Количество слов: " + findCount(str));
    }

    public static String vvod() {
        while (true) {
            System.out.println("Введите строку с точкой в конце:");
            Scanner scr = new Scanner(System.in);
            String str = scr.nextLine();
            if ('.' != str.charAt(str.length() - 1)) {
                System.out.println("Вы не завершили строку точкой!");
            } else
                return str;
        }
    }

    public static int findCount(String str) {
        int count = 0;
        int first = -1, second;
        char[] cstr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (cstr[i] == ' ' | cstr[i] == '.' | cstr[i] == '?' | cstr[i] == '!' | cstr[i] == ',' | cstr[i] == '`' | cstr[i] == '+' | cstr[i] == '=') {
                second = i;
                if (second - first > 1) {
                    count++;
                }
                first = second;
            }
        }
        return count;
    }
}
