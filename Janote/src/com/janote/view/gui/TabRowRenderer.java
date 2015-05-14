/**
 * 
 */
package com.janote.view.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author estelle
 *
 */
public class TabRowRenderer  extends DefaultTableCellRenderer { //implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);

        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        setHorizontalAlignment( JLabel.CENTER );
        setVerticalAlignment( JLabel.CENTER );

        //setBorder(noFocusBorder); // remove the default selected cell border
        if (hasFocus) {
        	setBorder(new LineBorder(Color.black));
        }
        
        boolean isEditable = table.getModel().isCellEditable(row, column);
        if (isEditable) {
            cellComponent.setBackground(new Color(255, 100, 0));
        }
        else if(isSelected) {
        	cellComponent.setBackground(Color.darkGray);
        }
        else {
        	if (row % 2 == 0)
        		cellComponent.setBackground(Color.WHITE);
        	else 
        		cellComponent.setBackground(Color.lightGray);
        }
		return cellComponent;
	}
	

}
