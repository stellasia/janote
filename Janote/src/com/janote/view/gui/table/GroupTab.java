package com.janote.view.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.janote.model.entities.Gender;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;
import com.janote.view.gui.GroupSelector;
import com.janote.view.gui.MainWindow;
import com.janote.view.gui.dialog.DialogGroup;
import com.janote.view.gui.dialog.DialogStatus;
import com.janote.view.gui.dialog.DialogStudent;

@SuppressWarnings("serial")
public class GroupTab extends JPanel // implements Observer
{
    protected MainWindow parent = null;
    protected JTable tabData;
    protected GroupTableModel model;
    protected GroupSelector groupSelector;
    protected JPanel groupActions;

    private ArrayList<Group> groups;
    protected int groupID = 0; //
    protected ArrayList<Student> data;
    private final String titles[]; // column titles
    private final Gender[] comboData = { Gender.BOY, Gender.GIRL }; // student
                                                                    // sex

    // ********************
    public GroupTab(String[] titles, MainWindow Pparent) {
        this.titles = titles;
        this.data = null;
        this.groupID = 0;
        this.parent = Pparent;
        // this.groupSelector = parent.getGroupSelector();
    }

    public void init() {
        // System.out.println("GroupTabs.init");

        model = new GroupTableModel(this.data, this.titles);
        tabData = new JTable(model);

        // this.updateStudentList(null); // should update the data table
        // this.updateGroupList(); // should update the group list
        ArrayList<Group> grset = this.parent.getController().getGroupList();
        this.groups = grset;
        groupSelector = new GroupSelector(parent);
        groupSelector.setItems(groups, 0);
        groupSelector.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(GroupSelector.COMBO_CHANGED)) {
                    Group g = (Group) evt.getNewValue();
                    if (g == null)
                        return;
                    if (g.getId() == null)
                        return;
                    // parent.changeSelectedGroup(g);
                }
            }
        });

        groupActions = new JPanel();
        groupActions
                .setLayout(new BoxLayout(groupActions, BoxLayout.LINE_AXIS));

        JButton btnNewStudent = new JButton("Ajouter un étudiant");
        btnNewStudent.setBackground(Color.GREEN);
        btnNewStudent.addActionListener(new MoreListener());
        groupActions.add(btnNewStudent);
        JButton btnDelStudent = new JButton("Supprimer l'étudiant sélectionné");
        btnDelStudent.setBackground(Color.RED);
        btnDelStudent.addActionListener(new DelStudentListener());
        groupActions.add(btnDelStudent);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        groupActions.add(separator);
        JButton btnModGroup = new JButton("Modifier le nom/la description");
        btnModGroup.setBackground(Color.ORANGE);
        btnModGroup.addActionListener(new ModGroupListener());
        groupActions.add(btnModGroup);
        JButton btnDelGroup = new JButton("Supprimer le groupe");
        btnDelGroup.setBackground(Color.RED);
        btnDelGroup.addActionListener(new DelGroupListener());
        groupActions.add(btnDelGroup);

        // ****************************

        tabData.setAutoCreateRowSorter(true); // a generic sorter
        tabData.getRowSorter().toggleSortOrder(1);

        tabData.setRowHeight(40);

        TableCellRenderer renderer = new TabRowRenderer();
        tabData.setDefaultRenderer(Object.class, renderer); // row colors

        JComboBox<Gender> comboSex = new JComboBox<Gender>(comboData);
        tabData.getColumn("Sexe")
                .setCellEditor(new DefaultCellEditor(comboSex));

        JTableHeader header = tabData.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(tabData));

        // **************************** // Detect a double click on a row
        tabData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numberOfClicks = e.getClickCount();
                JTable target = (JTable) e.getSource();
                int row, column;
                try {
                    row = target.convertRowIndexToModel(target.getSelectedRow());
                    column = target.convertColumnIndexToModel(target
                            .getSelectedColumn());
                }
                catch (ArrayIndexOutOfBoundsException exception) {
                    System.err
                            .println("GroupTab.addMonseListener:: Could not find a matching row in the table.");
                    return;
                }
                // System.out.println("GroupTab clicked -> numberOfClicks " +
                // numberOfClicks + ", row " + row + ",col " + column);
                int stud_id = (int) target.getModel().getValueAt(row,
                        GroupTableColumn.ID.value());
                // System.out.println("GroupTab -> mouseListener -> " +
                // stud_id);
                Student stu = parent.getController().getStudent(stud_id);
                // System.out.println(stu);
                if (numberOfClicks == 2 && !target.isCellEditable(row, column)) {
                    DialogStudent new_student = new DialogStudent(stu, parent
                            .getController());
                    DialogStatus st = new_student.showDialog();
                    if (st == DialogStatus.OBJECT_UPDATED) {
                        // updateStudentList(groupID);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabData);
        this.setLayout(new BorderLayout());
        this.add(groupSelector, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(groupActions, BorderLayout.SOUTH);

        /*
         * int rowModel = tabData.convertRowIndexToModel(3); int colModel =
         * tabData.convertColumnIndexToModel(3); int rowView =
         * tabData.convertRowIndexToView(3); int colView =
         * tabData.convertColumnIndexToView(3);
         */

    }

    // *****************************************
    // *****************************************
    class MoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            DialogStudent new_student = new DialogStudent(null,
                    parent.getController());
            DialogStatus st = new_student.showDialog();
            if (st == DialogStatus.OBJECT_UPDATED) {
                // updateStudentList(groupID);
            }
        }
    }

    // *****************************************
    class DelStudentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable target = tabData; // (JTable)e.getSource();
            int row, column;
            try {
                row = target.convertRowIndexToModel(target.getSelectedRow());
                column = target.convertColumnIndexToModel(target
                        .getSelectedColumn());
            }
            catch (ArrayIndexOutOfBoundsException exception) {
                System.err
                        .println("GroupTab.DelStudentListener:: Could not find a matching row in the table.");
                return;
            }
            // System.out.println("GroupTab clicked -> numberOfClicks " +
            // numberOfClicks + ", row " + row + ",col " + column);
            int stud_id = (int) target.getModel().getValueAt(row,
                    GroupTableColumn.ID.value());
            // System.out.println("GroupTab -> mouseListener -> " + stud_id);
            Student stu = parent.getController().getStudent(stud_id);

            int option = JOptionPane
                    .showConfirmDialog(
                            null,
                            "Voulez-vous vraiment supprimer cet élément ? \nCette action est irréversible.",
                            "Suppression", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                if (parent.getController().delStudent(stu)) {
                    JOptionPane.showMessageDialog(null, "Element supprimé",
                            "Attention", JOptionPane.WARNING_MESSAGE);
                    // updateStudentList(groupID);
                }
                else
                    JOptionPane.showMessageDialog(null,
                            "Un erreur est survenue. \n Element non supprimé.",
                            "Attention", JOptionPane.ERROR_MESSAGE);
            }
            else { // clicked No or Close button
                JOptionPane.showMessageDialog(null, "Action annulée",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    // *****************************************
    class DelGroupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int group_id = groupID;
            Group gr = parent.getController().getGroup(group_id);

            int option = JOptionPane
                    .showConfirmDialog(
                            null,
                            "Voulez-vous vraiment supprimer cet élément ? \nCette action est irréversible.",
                            "Suppression", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                if (parent.getController().delGroup(gr)) {
                    JOptionPane.showMessageDialog(null, "Element supprimé",
                            "Attention", JOptionPane.WARNING_MESSAGE);
                    // updateGroupList();
                }
                else
                    JOptionPane.showMessageDialog(null,
                            "Un erreur est survenue. \n Element non supprimé.",
                            "Attention", JOptionPane.ERROR_MESSAGE);
            }
            else { // clicked No or Close button
                JOptionPane.showMessageDialog(null, "Action annulée",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // *****************************************
    class ModGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Group current_group = parent.getController().getGroup(groupID);
            System.out.println(current_group);
            DialogGroup dgroup = new DialogGroup(current_group,
                    parent.getController());
            boolean option = dgroup.showDialog();
            if (option) {
                // updateGroupList();
            }
        }
    }

    // *****************************************
    public GroupTableModel getModel() {
        return this.model;
    }

    // *****************************************
    // @Override
    public void updateStudentList(Group g, ArrayList<Student> data) {
        // System.out.println("GroupTab.updateStudentList");
        // System.out.println(data);
        model.changeData(data);
        groupSelector.setSelectedGroup(g);
    }

    // *****************************************
    // @Override
    public void updateGroupList(ArrayList<Group> grset, Group selectedGroup) {
        this.groups = grset;
        // System.out.println(Arrays.toString(this.groups));
        groupSelector.setItems(this.groups, selectedGroup.getId());
    }

    // *****************************************
    /**
     * This is table header renderer class to change table header appearance.
     * 
     * @author Estelle Scifo
     * 
     */
    // TODO temporary location of this class !
    private static class HeaderRenderer implements TableCellRenderer {

        DefaultTableCellRenderer renderer;

        public HeaderRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer) table.getTableHeader()
                    .getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            renderer.setBackground(Color.orange);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int col) {
            return renderer.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, col);
        }
    }
}