package com.janote.view.gui;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * @author Estelle Scifo
 * @version 1.0
 */

@SuppressWarnings("serial")
public class GroupTableModel extends AbstractTableModel {

	//*****************************************
	public static int COL_ID = 0;
	public static int COL_EDIT = 7;
	public static int COL_DELETE = 8;

	private Object[][] data;
	private String[] title;

	//private boolean[][] editableCellState;


	//*****************************************
	public GroupTableModel(Object[][] data, String[] title) {
		//System.out.println("GroupTableModel");
		this.data = data;
		this.title = title;
		//		this.editableCellState = new boolean[this.getRowCount()][this.getColumnCount()];
		//initEditableStates();
	}


	/*
	//*****************************************
	protected void initEditableStates() {
		boolean[][] tmp = new boolean[this.getRowCount()][this.getColumnCount()];
		this.editableCellState = tmp;
		this.setColEditable(true, COL_EDIT);
		this.setColEditable(true, COL_DELETE);
	}
	 */

	//*****************************************
	/**
	 * @return the number of columns in the table 
	 */
	public int getColumnCount() {
		return this.title.length;
	}


	//*****************************************
	/**
	 * @return the number of rows in the table
	 */
	public int getRowCount() {
		return this.data.length;
	}


	//*****************************************
	/**
	 * @return the value in the cell with indices row, col
	 */
	public Object getValueAt(int row, int col) {
		return this.data[row][col];
	}            


	//*****************************************
	/**
	 * Returns the columns title at index col
	 * @param col 
	 * 	integer representing the column index (starting at 0)
	 * @return the title/name of the column with index col.
	 */
	public String getColumnName(int col) {
		return this.title[col];
	}


	//*****************************************
	/**
	 * @param col : Integer representing a column index
	 * @return the Class type of the Object contained in the column.
	 */
	public Class getColumnClass(int col){
		//		System.out.println("GroupTableModel getColumnClass " + col + " / " + this.getColumnCount());
		//		System.out.println(this.data[0][col].getClass());
		try {
			return this.getValueAt(1, col).getClass();
		}
		catch (Exception e) {
			return Object.class;
		}
	}


	//*****************************************
	/**
	 * @param row
	 * @param col
	 * @return the boolean true it is possible to edit the cell, false otherwise.
	 */
	public boolean isCellEditable(int row, int col){
		/*
		try {
			return editableCellState[row][col];
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		 */
		if (col == 0) 
			return false;
		return true;
	}


	//*****************************************
	/**
	 * Change the editable status of the cell. 
	 * 
	 * @param row
	 * @param col
	 */
	public void setCellEditable(boolean editableStatus, int row, int col) {
		//		System.out.println("GroupTableModel.setCellEditable row:"+row+", col:"+col);
		if (row < this.getRowCount() && col < this.getColumnCount()) {
			//editableCellState[row][col] = editableStatus;
			fireTableCellUpdated(row, col); // fire signal for the TabRowRenderer to do its job and colour the cell with the right color (depending on its editable and selection status)
		}
	}


	//*****************************************
	/**
	 * Change the editable status of a whole row.
	 * @param row
	 */
	public void setRowEditable(boolean editable, int row) {
		if (row >= this.getRowCount())
			return;
		int nbColumns = this.getColumnCount();
		for (int col=1; col<nbColumns; col++) {
			this.setCellEditable(editable, row, col);
		}
	}


	//*****************************************
	/**
	 * Change the editable status of a whole column.
	 * @param col
	 */
	public void setColEditable(boolean editable, int col) {
		int nbRows = this.getRowCount();
		for (int row=0; row<nbRows; row++) {
			this.setCellEditable(editable, row, col);
		}
	}


	//*****************************************
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		//		System.out.println("setValueAt " + aValue);
		try {
			//			if (this.isCellEditable(rowIndex, columnIndex)) {
			if(this.isCellEditable(rowIndex, columnIndex)
					//					&& !this.getColumnName(columnIndex).equals("Modifier")
					//					&& !this.getColumnName(columnIndex).equals("Supprimer")
					) {	

				data[rowIndex][columnIndex] = aValue;
				fireTableCellUpdated(rowIndex, columnIndex);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	//*****************************************
	public void removeRow(int row) {
		int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet élément ? \nCette action est irréversible.", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if(option == JOptionPane.OK_OPTION) {
			JOptionPane.showMessageDialog(null, "Element supprimé", "Attention", JOptionPane.WARNING_MESSAGE);
		}
		else { // clicked No or Close button
			//JOptionPane.showMessageDialog(null, "Action annulée", "Information", JOptionPane.INFORMATION_MESSAGE);
			;
		}

	}


	//*****************************************
	public void editRow(int row) {
		/*
		//		System.out.println("GroupTableModel.editRow --> idRow = " + row);	
		if (this.isCellEditable(row, 1)) {
			//			this.button.setText("Editer");
			this.setRowEditable(false, row); 
			this.setCellEditable(true, row, GroupTableModel.COL_EDIT);
			this.setCellEditable(true, row, GroupTableModel.COL_DELETE);
			//			this.setValueAt("Modifier", row, GroupTableModel.COL_EDIT);
		}
		else {
			//			this.button.setText("Enregistrer");
			this.setRowEditable(true, row); 
			//			this.setValueAt("Enregistrer", row, GroupTableModel.COL_EDIT);
		}
		//		this.fireTableCellUpdated(row, 7);
		 */
	}


	//*****************************************
	public void addRow(Object[] data) {
		int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
		//		System.out.println("GroupTableModel.addRow --> nbRows: " + nbRow + ", nbCol: " + nbCol);

		// add a row in the data table
		Object temp[][] = this.data;
		this.data = new Object[nbRow+1][nbCol];      
		for(Object[] value : temp) {
			this.data[indice++] = value;
		}
		this.data[indice] = data;
		temp = null;

		//initEditableStates();
		//this.setRowEditable(true, nbRow);

		//	      System.out.println("Nombre de lignes arpès ajout :" + this.getRowCount());

		// Update table
		this.fireTableDataChanged();
	}


	//*****************************************
	public void changeData(Object[][] newData) {
		//		System.out.println("GroupTableModel.changeData");
		if (newData == null) {
			System.out.println("newData is null");
		}
		this.data = newData;		
		this.fireTableDataChanged();
	}
}
