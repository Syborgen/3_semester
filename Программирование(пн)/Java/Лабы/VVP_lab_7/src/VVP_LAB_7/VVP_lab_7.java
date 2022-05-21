package VVP_LAB_7;

public class VVP_lab_7 {
    public static void main(String[] args) {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 7");
        System.out.println("Задание: по заданной длине окружности L найти площадь круга S");
        Circle circle = new Circle();
        new Thread(new Vvod(circle)).start();
        new Thread(new Area(circle)).start();
    }
}
