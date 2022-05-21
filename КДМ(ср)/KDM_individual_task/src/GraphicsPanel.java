import javax.swing.*;
import java.awt.*;
import java.lang.Exception;
public class GraphicsPanel extends JPanel {
    public static int n = Main.n;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        int i = 1;
        int a = 0;
        int[] indOfEl;
        int o;
        if (n > 0) {
            try {
                while (i <= n && Main.razmerComp[i] != 0) {
                    o = 0;
                    indOfEl = new int[Main.razmerComp[i]];
                    for (int y = 1; y < n + 1; y++) {
                        if (Main.mark[y] == i & o <= indOfEl.length) {
                            indOfEl[o] = y;
                            o++;
                        }
                    }
                    addGraph(g2, Main.razmerComp[i], 30, 30, a, indOfEl);
                    a += Main.razmerComp[i];
                    i++;
                    repaint();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Введенный граф не имеет вершин");
            return;
        }//if
        setPreferredSize(new Dimension(40 * (n + 3), 40 * (n + 3)));
        setSize(40 * (n + 3), 40 * (n + 3));
    }

    //paintComponent
    public static void drawGraph(Graphics2D g, int n, double xhl, double yhl, int[] indOfEl) throws Exception {//рисование графа
        for (int i = 0; i < n; i++) {
            g.fillOval((int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * i)))) - 5, (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * i)))) - 5, 10, 10);
            g.drawString(Integer.toString(indOfEl[i]), (int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * i))) - 5), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * i)))) - 10);
            for (int j = 0; j < n; j++) {
                if (Main.graf[indOfEl[i]][indOfEl[j]] == 1 & indOfEl[i] != indOfEl[j]) {
                    if(Main.orientirovannost1==0)
                    g.drawLine((int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * i)))), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * i)))), (int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * j)))), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * j)))));
                    else{
                       drawArrowLine(g,(int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * i)))), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * i)))), (int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * j)))), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * j)))),10,3);
                        // drawArrow(g,(int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * i)))), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * i)))), (int) ((xhl + n * 30 / 2) + (n * 30 / 2) * (Math.cos(Math.toRadians((360 / n) * j)))), (int) ((yhl + n * 30 / 2) + (n * 30 / 2) * (Math.sin(Math.toRadians((360 / n) * j)))));
                    }
                }
            }
        }
    }//drawGraph

    public static void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {//рисование стрелки
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

    public static void addGraph(Graphics2D g, int n, double xhl, double yhl, int usedCount, int[] indOfEl) throws Exception {//добавление графа
        drawGraph(g, n, xhl + usedCount * 40, yhl, indOfEl);//рисование графа
    }
}
