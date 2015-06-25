/**
 * 
 */
package com.janote.view.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;
import com.janote.view.gui.GroupSelector;
import com.janote.view.gui.MainWindow;
import com.janote.view.gui.dialog.DialogNewExam;
import com.janote.view.gui.dialog.DialogStatus;

/**
 * @author estelle
 * 
 *         NB: lot of duplicated code with GroupTab...
 */
public class ExamTab extends JPanel {
    protected MainWindow parent = null;
    protected JTable tabData;
    protected ExamTableModel model;
    protected GroupSelector groupSelector;

    private ArrayList<Group> groups;
    protected int groupID = 1; //
    protected ArrayList<Student> data;
    protected ArrayList<String> titles; // column titles

    public ExamTab(MainWindow Pparent) {
        // this.titles = titles;
        this.data = null;
        this.groupID = 0;
        this.parent = Pparent;
    }

    public void init() {
        // System.out.println("ExamTab.init");

        // Object[][] data = { { 1, 2, 3 }, { 10, 20, 30 } };

        model = new ExamTableModel(this.data, this.titles);
        tabData = new JTable(model);

        // in this table, selection is made by columns not by row as usual
        tabData.setColumnSelectionAllowed(true);
        tabData.setRowSelectionAllowed(false);

        ArrayList<Group> grset = this.parent.getController().getGroupList();
        this.groups = grset;
        groupSelector = new GroupSelector(parent);
        groupSelector.setItems(groups, null);
        groupSelector.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(GroupSelector.COMBO_CHANGED)) {
                    Group g = (Group) evt.getNewValue();
                    if (g == null)
                        return;
                    if (g.getId() == null)
                        return;
                    parent.changeSelectedGroup(g);
                }
            }
        });

        tabData.setAutoCreateRowSorter(true); // a generic sorter
        // tabData.getRowSorter().toggleSortOrder(1);
        tabData.setRowHeight(40);
        TableCellRenderer renderer = new TabRowRenderer();
        tabData.setDefaultRenderer(Object.class, renderer); // row colors
        tabData.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabData);
        this.setLayout(new BorderLayout());
        this.add(groupSelector, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);

        JButton btnNewExam = new JButton("Ajouter un exam");
        btnNewExam.setBackground(Color.GREEN);
        btnNewExam.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Exam exam = new Exam();
                DialogNewExam new_exam = new DialogNewExam(exam, parent
                        .getController());
                DialogStatus st = new_exam.showDialog();
                if (st == DialogStatus.OBJECT_UPDATED) {
                    // updateStudentList(groupID);
                }
            }

        });
        this.add(btnNewExam, BorderLayout.SOUTH);

    }

    // *****************************************
    // @Override
    public void updateStudentList(Group g, ArrayList<Student> data,
            ArrayList<Exam> title) {
        // System.out.println("GroupTab.updateStudentList");
        // System.out.println(data);
        // System.out.println(title);
        model.changeData(title, data);
        groupSelector.setSelectedGroup(g);
    }
}
