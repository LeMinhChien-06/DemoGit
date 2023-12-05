package com.MMT_Shop.swing;

import com.MMT_Shop.model.StatusType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.ObjectInputFilter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table_SP extends JTable {

    public Table_SP() {
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setGridColor(new Color(230,230,230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object o, boolean bnl, boolean bnl1, int row, int column) {
                TableHeader header = new TableHeader(o + "");
                if (column == 4) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int row, int column) {
                if (column != 4) {
                    Component com = super.getTableCellRendererComponent(jtable,o, selected, bln1, row, column);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if(selected){
                        //com.setForeground(new Color(15,89,140));
                        com.setForeground(Color.RED);
                        
                    }else{
                        com.setForeground(new Color(102,102,102));
                    }
                    return com;
                }else{
                    StatusType type = (StatusType)o;
                    CellStatus cell = new CellStatus(type);
                    return cell;
                }
               
            }

        });
    }
    public void addRow(Object[] row){
        DefaultTableModel model = (DefaultTableModel) getModel();
        //model.setRowCount(0);
        model.addRow(row);
    }


}
