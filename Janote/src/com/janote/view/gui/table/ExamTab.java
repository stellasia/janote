/**
 * 
 */
package com.janote.view.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.janote.model.entities.Group;
import com.janote.view.gui.GroupSelector;
import com.janote.view.gui.MainWindow;

/**
 * @author estelle
 * 
 */
public class ExamTab extends JPanel {
    protected MainWindow parent = null;
    protected JTable tabData;
    protected GroupTableModel model;
    protected GroupSelector groupSelection;

    private ArrayList<Group> groups;
    protected int groupID = 1; //
    protected Object data[][];
    private final ArrayList<String> titles; // column titles

    public ExamTab(ArrayList<String> titles, MainWindow Pparent) {
        this.titles = titles;
        this.data = null;
        this.groupID = 0;
        this.parent = Pparent;
    }

    public void init() {
        // System.out.println("ExamTab.init");

        // Object[][] data = { { 1, 2, 3 }, { 10, 20, 30 } };

        String[] dsf = new String[this.titles.size()];
        this.titles.toArray(dsf);
        model = new GroupTableModel(null, dsf);
        tabData = new JTable(model);

        ArrayList<Group> grset = this.parent.getController().getGroupList();
        this.groups = grset;
        groupSelection = new GroupSelector(parent, this.groups, false);
        groupSelection.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(GroupSelector.COMBO_CHANGED)) {
                    Group g = (Group) evt.getNewValue();
                    if (g == null)
                        return;
                    if (g.getId() == null)
                        return;
                    parent.getController().changeSelectedGroup(g);
                    int group_id = g.getId();
                    groupID = group_id;
                    updateStudentList(group_id);
                }
            }
        });

        ArrayList<String> names = this.parent.getController()
                .getExamColTitlesView();
        for (String n : names)
            model.addColumn(n, new Object[0]);

        JScrollPane scroll = new JScrollPane(tabData);
        this.setLayout(new BorderLayout());
        this.add(groupSelection, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);

        JButton btnNewExam = new JButton("Ajouter un exam");
        btnNewExam.setBackground(Color.GREEN);
        // btnNewStudent.addActionListener(new MoreListener());
        this.add(btnNewExam, BorderLayout.SOUTH);

    }

    // *****************************************
    // @Override
    public void updateStudentList(Integer groupID) {
        return;
    }

}
