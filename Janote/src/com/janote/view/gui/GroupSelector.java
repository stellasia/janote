package com.janote.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.janote.model.entities.Group;

@SuppressWarnings("serial")
public class GroupSelector extends JPanel {

	public static final String COMBO_CHANGED = "Combo Changed";
	   
	protected Group selectedGroup;
	protected JComboBox<Group> combo;
	protected MainWindow parent;
	
	public GroupSelector(MainWindow pParent, Group[] groups, boolean allowAddingNew) {
		this.parent = pParent;
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		JLabel chooseGroup = new JLabel("Choisissez un groupe : ");
		this.add(chooseGroup);
		combo = new JComboBox<Group>(groups);
		combo.setPreferredSize(new Dimension(50, 30));
	    combo.addActionListener(new GroupChangeListener());
	    this.setItems(groups, 0);
	    
//		for (Group gr : groups) {
//			combo.addItem((String) gr.getVal("name"));
//		}
/*
		combo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedGroup = (Group) combo.getSelectedItem();
				parent.getController().changeSelectedGroup(selectedGroup);
				//parent.getGroupTab().
			}
        });
  */
		//combo.addActionListener(new ChangeDisplayedGroupListener(combo, parent.getGroupTab()));
		
		this.add(combo);
		if (allowAddingNew) {
			JLabel orNewGroup = new JLabel(" ou ");
			this.add(orNewGroup);
			JButton btnNewGroup = new JButton("Créer nouveau");
			btnNewGroup.addActionListener(new NewGroupListener());
			btnNewGroup.setBackground(Color.GREEN);
			this.add(btnNewGroup);
		}
	}
	
	//*****************************************
	public Group getSelectedGroup() {
		return selectedGroup;	
	}
	
	public void setItems(Group[] groups, int selected_index) {
		combo.removeAllItems();
		for (Group g : groups)
			combo.addItem(g);
		combo.insertItemAt(new Group(null, "------", "", null, null), 0);
		combo.setSelectedIndex(selected_index);
		combo.revalidate();
	}
	
	//*****************************************
	class NewGroupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//JOptionPane.showMessageDialog(null, "Add group action.", "Temporary", JOptionPane.INFORMATION_MESSAGE);
			DialogGroup zd = new DialogGroup(null, parent.getController());
			boolean option = zd.showGroupDialog();
			if (option) {
//				System.out.println("GroupTab.NewGroupListener.actionPerformed --> ok");
//				parent.getController().addOrUpdate();
				parent.getGroupTab().updateGroupList();
			}
		}
	}
	
	 private class GroupChangeListener implements ActionListener {
	      @Override
	      public void actionPerformed(ActionEvent ae) {
	    	  Group newSelectedGroup = (Group) combo.getSelectedItem();
	    	  firePropertyChange(COMBO_CHANGED, selectedGroup, newSelectedGroup);
	          selectedGroup = newSelectedGroup;
	          //System.out.println("GroupSelector.GroupChangeListener -> group changed");
	      }
	   }
	
}
