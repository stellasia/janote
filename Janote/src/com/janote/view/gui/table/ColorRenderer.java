package com.janote.view.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
		AbstractTableModel model = (AbstractTableModel) table.getModel();
		boolean isCellEditable = model.isCellEditable(row, column);
		System.out.println("change color ");
		if (isCellEditable) {
			setOpaque(true);
			setBackground(Color.BLUE);
		} else {
			setOpaque(false);
			setBackground(Color.WHITE);
		}
		return this;
	}
}