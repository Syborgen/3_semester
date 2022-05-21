import javax.swing.*;
import java.awt.*;
import java.lang.Exception;
public class GraphicsPanel extends JPanel {
    public int n;
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
        try {
            drawTriangle(g, n, 10, 0, 10, 500, 510, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//paintComponent

    public void drawTriangle(Graphics g, int n, int x1, int y1, int x2, int y2, int x3, int y3) throws Exception {
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x3, y3, x2, y2);
        g.drawLine(x1, y1, x3, y3);
        if (n > 0) {
            n--;
            drawTriangle(g, n, ((x1 + x2) / 2), ((y1 + y2) / 2), ((x3 + x2) / 2), ((y3 + y2) / 2), x2, y2);
            drawTriangle(g, n, ((x1 + x2) / 2), ((y1 + y2) / 2), x1, y1, ((x1 + x3) / 2), ((y1 + y3) / 2));
            drawTriangle(g, n, x3, y3, ((x3 + x2) / 2), ((y3 + y2) / 2), ((x1 + x3) / 2), ((y1 + y3) / 2));
        }
    }
}
