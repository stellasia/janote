package com.janote.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import com.janote.model.entities.Group;

public class ChangeDisplayedGroupListener implements ActionListener {
	
	JComboBox combo;
	GroupTab gtab;
	
	public ChangeDisplayedGroupListener(JComboBox combo, GroupTab gtab) {
		this.combo = combo;
		this.gtab = gtab;
	}
	
	@Override
	public void actionPerformed (ActionEvent arg0) {
		 //GroupSelector combo = (GroupSelector) arg0.getSource();
         //String currentQuantity = (String)combo.getSelectedItem();
		if (combo.getSelectedIndex() > 0) {
			Group currentGroup = (Group) combo.getSelectedItem();
			int newGroupID = currentGroup.getId();
			gtab.updateStudentList(newGroupID);
		}
	}
}

