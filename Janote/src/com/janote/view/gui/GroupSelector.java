package com.janote.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.janote.model.entities.Group;
import com.janote.view.gui.dialog.DialogGroup;

@SuppressWarnings("serial")
public class GroupSelector extends JPanel {

    public static final String COMBO_CHANGED = "Combo Changed";

    protected ArrayList<Group> groups;
    protected Group selectedGroup;
    protected JComboBox<Group> combo;
    protected MainWindow parent;

    public GroupSelector(MainWindow parent) {

        this.parent = parent;

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JLabel chooseGroup = new JLabel("Choisissez un groupe : ");
        this.add(chooseGroup);
        combo = new JComboBox<Group>();
        combo.setPreferredSize(new Dimension(50, 30));
        combo.addActionListener(new GroupChangeListener());
        this.setItems(groups, null);
        this.add(combo);

        JLabel orNewGroup = new JLabel(" ou ");
        this.add(orNewGroup);
        JButton btnNewGroup = new JButton("Créer nouveau");
        btnNewGroup.addActionListener(new NewGroupListener());
        btnNewGroup.setBackground(Color.GREEN);
        this.add(btnNewGroup);
    }

    // *****************************************
    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setItems(ArrayList<Group> groups, Group selected_group) {
        combo.removeAllItems();
        combo.insertItemAt(new Group(null, "------", "", null, null), 0);
        // System.out.println(groups);
        if (groups != null)
            for (Group g : groups)
                combo.addItem(g);
        if (selected_group != null)
            combo.setSelectedItem(selected_group);
        else
            combo.setSelectedIndex(0);
        combo.revalidate();
    }

    public void setSelectedGroup(Group gr) {
        if (gr != null)
            combo.setSelectedItem(gr);
        else
            combo.setSelectedIndex(0);
    }

    // *****************************************
    class NewGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            // JOptionPane.showMessageDialog(null, "Add group action.",
            // "Temporary", JOptionPane.INFORMATION_MESSAGE);
            DialogGroup zd = new DialogGroup(null, parent);
            boolean option = zd.showDialog();
            if (option) {
                // parent.getGroupTab().updateGroupList();
            }
        }
    }

    private class GroupChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Group newSelectedGroup = (Group) combo.getSelectedItem();
            firePropertyChange(COMBO_CHANGED, selectedGroup, newSelectedGroup);
            selectedGroup = newSelectedGroup;
            // System.out.println("GroupSelector.GroupChangeListener -> group changed");
        }
    }

    public Integer findGroup(Group group) {
        ListIterator<Group> iter = this.groups.listIterator();
        while (iter.hasNext()) {
            if (iter.next() == group) {
                return iter.nextIndex();
            }
        }
        return null;
    }

}
