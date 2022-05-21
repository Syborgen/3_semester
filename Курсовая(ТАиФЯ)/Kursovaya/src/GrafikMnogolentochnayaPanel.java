import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GrafikMnogolentochnayaPanel extends JPanel {
    public static int toCount=0;
    public static int[] a={0,0};
    public java.util.List<VershinaMnogolentochnaya> vershiny = new ArrayList<>();

    public void add(VershinaMnogolentochnaya v){
        vershiny.add(v);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        System.out.println("GrafikMnogolentochnayaPanel: " + Thread.currentThread().getName());
        drawArrowLine(g, 25, 370, 25, 20, 10, 5);//начало координат x=25,y=370
        drawArrowLine(g, 25, 370, 725, 370, 10, 5);//width=700 height=350
        //System.out.println(cur);
        if (vershiny.size() != 0){
            for (int i = 0; i < vershiny.size(); i++) {
                int dx;
                if (i == 0) {
                    dx = 0;
                } else {
                    dx = 700 / vershiny.size();
                }
                int dy = 350 / 100;
                System.out.println("Painting component "+i);
                g.fillOval(25 + dx * (i)-5, 370 - dy * vershiny.get(i).getY()-5, 10, 10);

                g.drawString(Integer.toString(vershiny.get(i).getY()), 25 + dx * (i) - 10, 370 - dy * vershiny.get(i).getY() - 10);
                g.drawString(""+i,25+dx*(i),385);

            }
        }else System.out.println("Size = 0");


    }//paintComponent
    public static void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }


}
