package VVP_lab_3;

import VVP_lab_6.exceptions;

public class Main {
    public static void main(String[] args) throws exceptions {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 3");
        System.out.println("Задание: утроить все четные элементы массива");
        VVP_lab_3 a = new VVP_lab_3();
        a.handGeneration();
        System.out.println("Исходный массив "+a.list.toString());
        a.variant(a.list);
        System.out.println("Результирующий массив "+a.list.toString());
        VVP_lab_3Test b = new VVP_lab_3Test();
        b.variant();

    }
}
