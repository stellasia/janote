package com.janote.view.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 * 
 * @author Estelle Scifo
 * @version 1.0
 */

@SuppressWarnings("serial")
public class TabColumnButtonEditor extends DefaultCellEditor {

	//*****************************************
	protected JButton button;
	private ButtonListener bListener = new ButtonListener();

	
	//*****************************************
	//Constructeur avec une CheckBox
	public TabColumnButtonEditor(JCheckBox checkBox) { //, ActionListener bListener) {
		//Par défaut, ce type d'objet travaille avec un JCheckBox
		super(checkBox);
		//On crée à nouveau un bouton
		button = new JButton();
		button.setOpaque(true);
		//On lui attribue un listener
		button.addActionListener(bListener);
	}

	
	//*****************************************
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 
		bListener.setRow(row);
		bListener.setColumn(column);
		bListener.setTable(table);

		/**
		 * @see TabColumnButtonRenderer
		 */
		if (value instanceof String) 
			button.setText((value == null) ? "" : value.toString() );
//		if (column == GroupTableModel.COL_EDIT) {
//			if ((table.getModel()).getValueAt(row, column).equals("Modifier"))
//				button.setText("Enregistrer");
//			else
//				button.setText("Modifer");
//		}
		return button;
	}

	
	//*****************************************
	/**
	 * Button listener
	 * 
	 * @author Estelle Scifo
	 * @version 1.0
	 *
	 */
	class ButtonListener implements ActionListener {        
		private int column, row, id;
		private JTable table;

		public void setColumn(int col){this.column = col;}
		public void setRow(int row){this.row = row;}
		public void setId(int id){this.id = id;}

		public void setTable(JTable table){this.table = table;}

		public void actionPerformed(ActionEvent event) {
			//table.setValueAt("New Value " + (++nbre), this.row, (this.column -1));
			if (column == GroupTableModel.COL_EDIT)
				((GroupTableModel) table.getModel()).editRow(row);
			else if (column == GroupTableModel.COL_DELETE)
				((GroupTableModel) table.getModel()).removeRow(row);
		}
	}     
}