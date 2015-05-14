/**
 * 
 */
package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.janote.model.entities.Group;
import com.janote.observer.Observer;

/**
 * @author estelle
 *
 */
public class ExamTab extends JPanel implements Observer {
	protected MainWindow parent = null;
	protected JTable tabData;
	protected GroupTableModel model;
	protected GroupSelector groupSelection;
	
	private Group[] groups;
	protected int groupID=1; // 
	protected Object data[][];
	private	String  titles[];  // column titles

	public ExamTab(int pGroupID, String[] titles, MainWindow Pparent) {
		this.titles = titles;
		// TODO IMPORTANT temporary data size !!
		this.data = new Object[100][titles.length+2];
		for (int i=0; i<titles.length+2; i++) {
			this.data[0][i] = "";
		}
		this.groupID = pGroupID;
		this.parent = Pparent;
	}

	public void init() {
		//System.out.println("GroupTabs.init");
		groupSelection = new GroupSelector(parent, parent.getController().getGroupList(), false);
		
		JScrollPane scroll = new JScrollPane(tabData);
		this.setLayout(new BorderLayout());
		this.add(groupSelection, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);      
		
		JButton btnNewStudent = new JButton("Ajouter un exam");
		btnNewStudent.setBackground(Color.GREEN);
		//btnNewStudent.addActionListener(new MoreListener());
		this.add(btnNewStudent, BorderLayout.SOUTH);
	}
	
	@Override
	public void updateStudentList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGroupList() {
		// TODO Auto-generated method stub
		
	}
}
