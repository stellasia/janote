/**
 * 
 */
package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.janote.model.entities.Group;

/**
 * @author estelle
 * 
 */
public class ExamTab extends JPanel {
	protected MainWindow parent = null;
	protected JTable tabData;
	protected GroupTableModel model;
	protected GroupSelector groupSelection;

	private Group[] groups;
	protected int groupID = 1; //
	protected Object data[][];
	private final String titles[]; // column titles

	public ExamTab(int pGroupID, String[] titles, MainWindow Pparent) {
		this.titles = titles;
		this.data = null;
		this.groupID = pGroupID;
		this.parent = Pparent;
	}

	public void init() {
		// System.out.println("GroupTabs.init");

		model = new GroupTableModel(this.data, this.titles);
		tabData = new JTable(model);

		Set<Group> grset = this.parent.getController().getGroupList();
		this.groups = grset.toArray(new Group[grset.size()]);
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
		// System.out.println("GroupTab.updateStudentList --> " + this.groupID);

		Object[][] studentData = parent.getController().getStudentList(groupID);
		int nbStudents = studentData.length;
		// System.out.println("Number of students " + nbStudents);

		Object[][] newData = new Object[nbStudents][this.titles.length];
		for (int i = 0; i < nbStudents; i++) {
			for (int j = 0; j < this.titles.length; j++) {
				// System.out.println("GroupTable.update -> In loop " + i + ", "
				// + j);
				newData[i][j] = studentData[i][j];
			}
		}
		this.data = new Object[newData.length][titles.length];
		this.data = newData;
		model.changeData(newData); // = new GroupTableModel(this.data,
									// this.titles);
	}

}
