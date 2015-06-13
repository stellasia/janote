package com.janote.view.gui.table;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.janote.model.entities.Student;

/**
 * @author Estelle Scifo
 * @version 1.0
 */

@SuppressWarnings("serial")
public class GroupTableModel extends DefaultTableModel {

    // *****************************************
    private ArrayList<Student> data;
    private final String[] title;

    // *****************************************
    public GroupTableModel(ArrayList<Student> data, String[] title) {
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
            return this.data.size();
        return 0;
    }

    // *****************************************
    /**
     * @return the value in the cell with indices row, col
     */
    @Override
    public Object getValueAt(int row, int col) {
        Student s;
        try {
            s = this.data.get(row);
        }
        catch (NullPointerException e) {
            return null;
        }
        if (s != null) {
            GroupTableColumn gtc = GroupTableColumn.fromInteger(col);
            switch (gtc) {
                case ID:
                    return s.getId();
                case NAME:
                    return s.getName();
                case SURNAME:
                    return s.getSurname();
                case GENDER:
                    return s.getGender();
                case BIRTHDAY:
                    return s.getBirthdayAsString();
                case EMAIL:
                    return s.getEmail();
                case REPEATING:
                    return s.isRepeating();
            }
        }
        return null;
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
        // System.out.println("GroupTableModel getColumnClass " + col + " / "
        // + this.getColumnCount());
        // System.out.println(GroupTableColumn.values()[col]);
        if (GroupTableColumn.REPEATING == GroupTableColumn.values()[col])
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
    public void removeRow(Student row) {
        this.data.remove(row);

        // Update table
        this.fireTableDataChanged();
    }

    // *****************************************
    public void addRow(Student row) {
        // System.out.println("GroupTableModel.addRow");
        // System.out.println(row.toString());
        // System.out.println(data.toString());
        // this.data.add(row);

        // Update table
        this.fireTableDataChanged();
    }

    public void updateRow(Student row) {
        Integer index = this.getIndexOf(row);
        this.data.set(index, row);
        this.fireTableDataChanged();
    }

    // *****************************************
    public void changeData(ArrayList<Student> newData) {
        if (newData == null) {
            System.out.println("GroupTableModel.changeData :: newData is null");
            return;
        }
        // System.out.println(this.data.length);
        // System.out.println(newData.length);
        this.data = newData;
        this.fireTableDataChanged();
    }

    public Integer getIndexOf(Student student) {
        if (student.getId() == null)
            return null;
        for (int i = 0; i < this.data.size(); i++) {
            Student s = this.data.get(i);
            if (s.getId() == student.getId())
                return i;
        }
        return null;
    }
}
