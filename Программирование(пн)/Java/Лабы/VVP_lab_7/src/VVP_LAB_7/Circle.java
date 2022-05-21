package VVP_LAB_7;
public class Circle {
    final double Pi = 3.1415926536;
    public double l = 0D;
    public double area = 0D;

    public void Circle() {
        l = 0D;
        area = 0D;
    }

    public void reboot() {
        l = 0D;
        area = 0D;
    }

    public void setL(double l) {
        this.l = l;
    }

    public synchronized void setArea() throws InterruptedException {
        double r = l / (2 * Pi);
        area = Pi * r * r;
        notify();
    }

    public synchronized double getArea() throws InterruptedException {
        while (area == 0)
            wait();
        notify();
        return area;
    }
}
