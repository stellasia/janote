package com.janote.view.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TabColumnButtonRenderer extends JButton implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
		if (value instanceof String)
			setText((value != null) ? value.toString() : "");
		return this;
	}
}