/**
 * 
 */
package com.janote.view.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    protected JButton btnNewExam;

    private ArrayList<Group> groups;
    protected int groupID = 1; //
    protected ArrayList<Exam> exams;
    protected ArrayList<Student> students;
    protected ArrayList<String> titles; // column titles

    public ExamTab(MainWindow Pparent) {
        // this.titles = titles;
        this.exams = null;
        this.students = null;
        this.groupID = 0;
        this.parent = Pparent;
    }

    public void init() {
        // System.out.println("ExamTab.init");

        // Object[][] data = { { 1, 2, 3 }, { 10, 20, 30 } };

        model = new ExamTableModel(this.exams, this.students, this.titles);
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

        tabData.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int numberOfClicks = e.getClickCount();
                JTable target = (JTable) e.getSource();
                int clicked_row = target.getSelectedRow();
                int clicked_column = target.getSelectedColumn();
                int column;
                try {
                    column = target.convertColumnIndexToModel(clicked_column);
                }
                catch (ArrayIndexOutOfBoundsException exception) {
                    System.err
                            .println("ExamTab.addMonseListener:: Could not find a matching column in the table.");
                    return;
                }
                Exam exam = exams.get(column - 3);
                if (numberOfClicks == 2
                        && !target.isCellEditable(clicked_row, clicked_column)) {
                    DialogNewExam new_exam = new DialogNewExam(exam,
                            groupSelector.getSelectedGroup(), parent
                                    .getController());
                    DialogStatus st = new_exam.showDialog();
                    if (st == DialogStatus.OBJECT_UPDATED) { //
                        // updateStudentList(groupID);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabData);
        this.setLayout(new BorderLayout());
        this.add(groupSelector, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);

        btnNewExam = new JButton("Ajouter un exam");
        btnNewExam.setBackground(Color.GREEN);
        btnNewExam.setEnabled(false); // cannot add exam until a group is
                                      // selected

        btnNewExam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Exam exam = new Exam();
                DialogNewExam new_exam = new DialogNewExam(exam, groupSelector
                        .getSelectedGroup(), parent.getController());
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
    public void updateStudentList(Group g) {
        // System.out.println("GroupTab.updateStudentList");
        // System.out.println(g);
        this.exams = g.getExams();
        this.students = g.getStudents();
        model.changeData(this.exams, this.students);
        groupSelector.setSelectedGroup(g);
        if (g != null)
            btnNewExam.setEnabled(true);
    }
}
