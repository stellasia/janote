package com.janote.view.gui.table;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;

public class GroupGrades extends JPanel {

    protected JDialog parent = null;
    protected JTable tabData;
    protected ArrayList<Student> data;

    protected Group gr;
    protected Exam exam;

    public GroupGrades(JDialog Pparent, Group group, Exam e) {
        this.data = null;
        this.parent = Pparent;
        this.gr = group;
        this.exam = e;
        this.init();
    }

    public void init() {
        String[] titles = { "Nom Prénom", "Note" };
        Object[][] data = new Object[this.gr.getStudents().size()][2];
        for (int i = 0; i < this.gr.getStudents().size(); i++) {
            Student s = this.gr.getStudents().get(i);
            data[i][0] = s.getName() + " " + s.getSurname();
            data[i][1] = s.getGrade(exam);
        }
        tabData = new JTable(data, titles);
        tabData.setRowHeight(30);

        this.add(new JScrollPane(tabData));
    }
}
