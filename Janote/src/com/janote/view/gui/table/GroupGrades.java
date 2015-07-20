package com.janote.view.gui.table;

import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.janote.model.entities.Student;

public class GroupGrades extends JPanel {

    protected JDialog parent = null;
    protected JTable tabData;

    // protected Group gr;
    // protected Exam exam;
    protected Object[][] data;

    public GroupGrades(JDialog Pparent, Object[][] data) {
        this.parent = Pparent;
        this.data = data;
        this.init();
    }

    public void init() {
        String[] titles = { "Nom Prénom", "Note" };
        tabData = new JTable(this.data, titles);
        tabData.setRowHeight(30);

        this.add(new JScrollPane(tabData));
    }

    public HashMap<Student, Float> getGrades() {
        HashMap<Student, Float> result = new HashMap<Student, Float>();
        // for (Student s : gr.getStudents()) {
        // result.put(s, Float.parseFloat((String) tabData.getValueAt(1, 1)));
        // }
        return result;
    }
}
