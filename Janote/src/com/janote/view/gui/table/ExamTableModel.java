package com.janote.view.gui.table;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.janote.model.entities.Exam;
import com.janote.model.entities.Student;

/**
 * @author Estelle Scifo
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ExamTableModel extends DefaultTableModel {

    // *****************************************
    private ArrayList<Student> data;
    private ArrayList<String> title;

    // *****************************************
    public ExamTableModel(ArrayList<Student> data, ArrayList<String> title) {
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
        if (this.title != null)
            return this.title.size();
        return 0;
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
            switch (col) {
                case 0:
                    return s.getId();
                case 1:
                    return s.getName();
                case 2:
                    return s.getSurname();
                default:
                    return getGrade(row, col);
            }
        }
        return null;
    }

    public Float getGrade(int row, int col) {
        // get the exam corresponding to col
        int stud_id = (int) this.getValueAt(row, 0);
        // get the student corresponding to row
        String exam_name = this.getColumnName(col);
        // System.out.println("ExamTableModel.getGrade");
        // System.out.println(stud_id);
        // System.out.println(exam_name);
        // get the grade of student for exam
        for (Student s : this.data) {
            if (s.getId() == stud_id) {
                for (Exam e : s.getExams()) {
                    if (e.getName().equals(exam_name)) {
                        return s.getGrade(e);
                    }
                }
            }
        }
        return new Float(-100);
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
        return this.title.get(col);
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
        if (this.data == null)
            this.data = new ArrayList<Student>();
        this.data.add(row);

        // Update table
        this.fireTableDataChanged();
    }

    public void updateRow(Student row) {
        Integer index = this.getIndexOf(row);
        this.data.set(index, row);
        this.fireTableDataChanged();
    }

    // *****************************************
    public void changeData(ArrayList<Exam> newExams, ArrayList<Student> newData) {
        if (newData == null) {
            System.out.println("GroupTableModel.changeData :: newData is null");
            // return;
        }
        // else {
        // System.out.println("GroupTableModel.changeData");
        // if (this.data != null)
        // System.out.println(this.data.size());
        // System.out.println(newData.size());
        // }
        this.title = new ArrayList<String>();
        this.title.add("id");
        this.title.add("Nom");
        this.title.add("Prénom");
        for (Exam e : newExams) {
            this.title.add(e.getName());
        }
        this.data = newData;
        this.fireTableStructureChanged();
        // this.fireTableDataChanged();
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
