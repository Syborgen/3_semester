import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row,int column){//главная диагональ и столбец 0 и строчка 0 в таблице - нередактируемы
        if(row==0||column==0||row==column) return false;
        else return true;
    }
}
