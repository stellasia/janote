package com.janote.view.gui;

import javax.swing.table.DefaultTableModel;

/**
 * @author Estelle Scifo
 * @version 1.0
 */

@SuppressWarnings("serial")
public class GroupTableModel extends DefaultTableModel {

	// *****************************************
	public static int COL_ID = 0;

	private Object[][] data;
	private String[] title;

	// *****************************************
	public GroupTableModel(Object[][] data, String[] title) {
		// System.out.println("GroupTableModel");
		this.data = data;
		this.title = title;
	}

	// *****************************************
	/**
	 * @return the number of columns in the table
	 */
	@Override
	public int getColumnCount() {
		return this.title.length;
	}

	// *****************************************
	/**
	 * @return the number of rows in the table
	 */
	@Override
	public int getRowCount() {
		if (this.data != null)
			return this.data.length;
		return 0;
	}

	// *****************************************
	/**
	 * @return the value in the cell with indices row, col
	 */
	@Override
	public Object getValueAt(int row, int col) {
		try {
			return this.data[row][col];
		} catch (NullPointerException e) {
			return null;
		}
	}

	// *****************************************
	/**
	 * Returns the columns title at index col
	 * 
	 * @param col
	 *            integer representing the column index (starting at 0)
	 * @return the title/name of the column with index col.
	 */
	@Override
	public String getColumnName(int col) {
		return this.title[col];
	}

	// *****************************************
	/**
	 * @param col
	 *            : Integer representing a column index
	 * @return the Class type of the Object contained in the column.
	 */
	@Override
	public Class getColumnClass(int col) {
		// System.out.println("GroupTableModel getColumnClass " + col + " / " +
		// this.getColumnCount());
		// System.out.println(this.data[0][col].getClass());
		/*
		 * try { return this.getValueAt(1, col).getClass(); // code never
		 * executed... } catch (Exception e) { return Object.class; }
		 */
		if (col == 5)
			return Boolean.class;
		return super.getColumnClass(col);
	}

	// *****************************************
	/**
	 * @param row
	 * @param col
	 * @return the boolean true it is possible to edit the cell, false
	 *         otherwise.
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	// *****************************************
	/**
	 * Change the editable status of the cell.
	 * 
	 * @param row
	 * @param col
	 */
	public void setCellEditable(boolean editableStatus, int row, int col) {
		// System.out.println("GroupTableModel.setCellEditable row:"+row+", col:"+col);
		if (row < this.getRowCount() && col < this.getColumnCount()) {
			// editableCellState[row][col] = editableStatus;
			fireTableCellUpdated(row, col); // fire signal for the
											// TabRowRenderer to do its job and
											// colour the cell with the right
											// color (depending on its editable
											// and selection status)
		}
	}

	// *****************************************
	/**
	 * Change the editable status of a whole row.
	 * 
	 * @param row
	 */
	public void setRowEditable(boolean editable, int row) {
		if (row >= this.getRowCount())
			return;
		int nbColumns = this.getColumnCount();
		for (int col = 1; col < nbColumns; col++) {
			this.setCellEditable(editable, row, col);
		}
	}

	// *****************************************
	/**
	 * Change the editable status of a whole column.
	 * 
	 * @param col
	 */
	public void setColEditable(boolean editable, int col) {
		int nbRows = this.getRowCount();
		for (int row = 0; row < nbRows; row++) {
			this.setCellEditable(editable, row, col);
		}
	}

	// *****************************************
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// System.out.println("setValueAt " + aValue);
		try {
			// if (this.isCellEditable(rowIndex, columnIndex)) {
			if (this.isCellEditable(rowIndex, columnIndex)) {
				data[rowIndex][columnIndex] = aValue;
				fireTableCellUpdated(rowIndex, columnIndex); // TODO : is this
																// necessary ?
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// *****************************************
	@Override
	public void removeRow(int row) {
	}

	// *****************************************
	@Override
	public void addRow(Object[] data) {
		int indice = 0, nbRow = this.getRowCount(), nbCol = this
				.getColumnCount();
		// System.out.println("GroupTableModel.addRow --> nbRows: " + nbRow +
		// ", nbCol: " + nbCol);

		// add a row in the data table
		Object temp[][] = this.data;
		this.data = new Object[nbRow + 1][nbCol];
		for (Object[] value : temp) {
			this.data[indice++] = value;
		}
		this.data[indice] = data;
		temp = null;

		// Update table
		this.fireTableDataChanged();
	}

	// *****************************************
	public void changeData(Object[][] newData) {
		// System.out.println("GroupTableModel.changeData");
		if (newData == null) {
			System.out.println("newData is null");
		}
		// System.out.println(this.data.length);
		// System.out.println(newData.length);
		this.data = newData;
		this.fireTableDataChanged();
	}

	@Override
	public void addColumn(Object newColName) {
		int indice = 0, nbRow = this.getRowCount(), nbCol = this
				.getColumnCount();

		// add a row in the data table
		String temp[] = this.title;
		this.title = new String[nbCol + 1];
		for (String value : temp) {
			this.title[indice++] = value;
		}
		this.title[nbCol] = (String) newColName;
		temp = null;

		// Update table
		this.fireTableStructureChanged();
	}
}
