package VVP_LAB_7;
import java.util.Scanner ;
public class Vvod implements Runnable {
    Circle circle;

    public Vvod(Circle circle) {
        this.circle = circle;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите длину окружности");
            double a;
            a = proverkaDouble(sc);
            circle.setL(a);
            try {
                System.out.println("Площадь круга " + circle.getArea());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }//run

    public static double proverkaDouble(Scanner sc) {
        double r;
        while (!sc.hasNextDouble()) {
            sc.nextLine();
        }
        r = sc.nextDouble();
        while (!(r > 0)) {
            while (!sc.hasNextDouble()) {
                sc.nextLine();
            }
            r = sc.nextDouble();
        }
        return r;
    }
}