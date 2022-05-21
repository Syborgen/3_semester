import javax.swing.*;
import java.awt.*;
import java.lang.Exception;
public class GraphicsPanel extends JPanel {
    private ASD_lab_3.Tree tree;
    public int p,find;
    public GraphicsPanel(ASD_lab_3.Tree tree){
        this.tree = tree;
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        if (tree.getDeep()>0){
            try {
                drawTree(g,tree.head, (int) (startPosition(tree.getDeep())*30),0,1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            if(tree.getDeep()==0) {
                g.drawOval(0, 0, 40, 40);
                g.drawString(Integer.toString(tree.head.data),20,25);
            }//if
        }//else
        setPreferredSize(new Dimension((int) (70*Math.pow(2,tree.getDeep())),100*tree.getDeep()));
        setSize((int) (70*Math.pow(2,tree.getDeep())),100*tree.getDeep());
    }//paintComponent
    public double startPosition(int x){
        int res=0;
        for(int i=0;i<x;i++){
            res+=Math.pow(2,i);
        }//for
        return res;
    }//startPosition
    public void drawTree(Graphics g,ASD_lab_3.Node cur,int x,int y,int level) throws Exception {
        if(cur.leftChild!=null){
            drawTree(g,cur.leftChild, (int) (x-(60*Math.pow(2,(tree.getDeep()-level-1)))),y+20*3,level+1);
            g.drawLine(x,y+20,(int)(x-(60*Math.pow(2,(tree.getDeep()-level-1))))+20,y+20*3);
        }
        try {
            g.drawString(Integer.toString(cur.data), x + 20, y + 20);
        }catch (NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "Ключ имеет не числовое целое значение");
            return;
        }
        g.drawOval(x,y,40,40);
        if (p==1){
            if(cur.leftChild!=null){
                if(find == cur.leftChild.data){
                    g.fillOval(x,y,20,20);
                }
            }
            if(cur.rightChild!=null){
                if(find == cur.rightChild.data){
                    g.fillOval(x,y,20,20);
                }
            }
        }
        if(cur.rightChild!=null){
            drawTree(g,cur.rightChild,(int) (x+(60*Math.pow(2,(tree.getDeep()-level-1)))),y+20*3,level+1);
            g.drawLine(x+40,y+20,(int)(x+(60*Math.pow(2,(tree.getDeep()-level-1))))+20,y+20*3);
        }
    }//drawTree
}
