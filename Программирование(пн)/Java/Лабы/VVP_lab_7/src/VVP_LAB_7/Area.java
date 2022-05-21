package VVP_LAB_7;
public class Area implements Runnable {
    private Circle circle;

    public Area(Circle circle) {
        this.circle = circle;
    }

    public void run() {
        while (true) {
            try {

                circle.setArea();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
