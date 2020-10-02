package colorTabla;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
@SuppressWarnings("serial")
public class MiRenderer extends DefaultTableCellRenderer {
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus,int row,int column) {
 
        int numero = (Integer) table.getValueAt(row, column);
 
        if ((numero >0) && !(column==0)) {
        	if ((row%2)==0) {
            	setBackground(Color.GREEN);
            	setForeground(Color.BLACK);
        	}else {
        		setBackground(Color.ORANGE);
            	setForeground(Color.BLACK);
        	}
        } else if (column==0){
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.BLACK);
        }else{
            setBackground(Color.GRAY);
            setForeground(Color.BLACK);
        }
 
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
 
}