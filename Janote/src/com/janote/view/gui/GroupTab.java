package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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


@SuppressWarnings("serial")
public class GroupTab extends JPanel //implements Observer
{
	protected MainWindow parent = null;
	protected JTable tabData;
	protected GroupTableModel model;
	protected JPanel groupSelection;
	protected JPanel groupActions;
	
	private Group[] groups;
	protected int groupID=0; // 
	protected Object data[][];
	private	String  titles[];  // column titles
	private Gender[] comboData = {Gender.BOY, Gender.GIRL}; // student sex

	
//	protected MainController cont = new MainController();

	//********************
	public GroupTab(int pGroupID, String[] titles, MainWindow Pparent) {
		this.titles = titles;
		this.data = null;
		this.groupID = pGroupID;
		this.parent = Pparent;
	}
	
	public void init() {
		//System.out.println("GroupTabs.init");

		model = new GroupTableModel(this.data, this.titles);		
		tabData = new JTable(model);	
		
		//this.updateStudentList(null); // should update the data table
		//this.updateGroupList(); // should update the group list
				
		groupSelection = new GroupSelector(parent, this.parent.getController().getGroupList(), true);
		groupSelection.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(GroupSelector.COMBO_CHANGED)) {
		            Group g = (Group) evt.getNewValue();
		            if (g.getId() == null)
		            	return;
		            parent.getController().changeSelectedGroup(g);
		            int group_id = g.getId();
		            groupID = group_id;
					updateStudentList(group_id);
		         }
			}			
		});
		
		groupActions = new JPanel();
		JButton btnNewStudent = new JButton("Ajouter un étudiant");
		btnNewStudent.setBackground(Color.ORANGE);
		btnNewStudent.addActionListener(new MoreListener());
		groupActions.add(btnNewStudent);
		JButton btnDelStudent = new JButton("Supprimer l'étudiant sélectionné");
		btnDelStudent.setBackground(Color.RED);
		btnDelStudent.addActionListener(new DelStudentListener());
		groupActions.add(btnDelStudent);
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        groupActions.add(separator);		
		groupActions.setLayout(new BoxLayout(groupActions, BoxLayout.LINE_AXIS));
		JButton btnModGroup = new JButton("Modifier le nom/la description");
		groupActions.add(btnModGroup);
		JButton btnExportGroup = new JButton("Exporter le groupe");
		groupActions.add(btnExportGroup);
		JButton btnDelGroup = new JButton("Supprimer le groupe");
		btnDelGroup.setBackground(Color.RED);
		groupActions.add(btnDelGroup);



		//****************************
		// TODO Disabled for the time being, need to consider the id instead of the row number for the other operations,
//		TableRowSorter sorter = new TableRowSorter<GroupTableModel>(this.model);
//		tabData.setRowSorter(sorter);
//		sorter.setSortsOnUpdates(true);
		tabData.getTableHeader().setReorderingAllowed(false); // do not allow to reorder the columns for the time being
		
		tabData.setRowHeight(40);
		
		TableCellRenderer renderer = new TabRowRenderer();
		tabData.setDefaultRenderer(Object.class, renderer); // row colors 
		
		JComboBox<Gender> comboSex = new JComboBox<Gender>(comboData);
		tabData.getColumn("Sexe").setCellEditor(new DefaultCellEditor(comboSex));
		
		JTableHeader header = tabData.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(tabData));

		
		//**************************** // Detect a double click on a row
		tabData.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int numberOfClicks = e.getClickCount();
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();
				//System.out.println("GroupTab clicked -> numberOfClicks " + numberOfClicks + ", row " + row + ",col " + column);	    		   
				int stud_id = (int) target.getModel().getValueAt(row, GroupTableModel.COL_ID);
				//System.out.println("GroupTab -> mouseListener -> " + stud_id);
				Student stu = parent.getController().getStudent(stud_id);
				//System.out.println(stu);
				if (numberOfClicks == 2 && !target.isCellEditable(row, column)) { // double clic and cell not editable !
					DialogStudent new_student = new DialogStudent(stu, parent.getController());
					DialogStatus st = new_student.showDialog();
					if (st == DialogStatus.OBJECT_UPDATED) {
						updateStudentList(groupID);
					}
				}
			}
		});

	
		JScrollPane scroll = new JScrollPane(tabData);
		this.setLayout(new BorderLayout());
		this.add(groupSelection, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);      
		this.add(groupActions, BorderLayout.SOUTH);

		/*
		int rowModel = tabData.convertRowIndexToModel(3);
		int colModel = tabData.convertColumnIndexToModel(3);
		int rowView = tabData.convertRowIndexToView(3);
		int colView = tabData.convertColumnIndexToView(3);
		 */

	}	
	

	//*****************************************
	//*****************************************
	class MoreListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			DialogStudent new_student = new DialogStudent(null, parent.getController());
			DialogStatus st = new_student.showDialog();
			if (st == DialogStatus.OBJECT_UPDATED) {
				updateStudentList(groupID);
			}
		}
	}

	class DelStudentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTable target = tabData; //(JTable)e.getSource();
			int row = target.getSelectedRow();
			int column = target.getSelectedColumn();
			//System.out.println("GroupTab clicked -> numberOfClicks " + numberOfClicks + ", row " + row + ",col " + column);	    		   
			int stud_id = (int) target.getModel().getValueAt(row, GroupTableModel.COL_ID);
			//System.out.println("GroupTab -> mouseListener -> " + stud_id);
			Student stu = parent.getController().getStudent(stud_id);
			
			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet élément ? \nCette action est irréversible.", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				if (parent.getController().delStudent(stu)) {
					JOptionPane.showMessageDialog(null, "Element supprimé", "Attention", JOptionPane.WARNING_MESSAGE);
					updateStudentList(groupID);
				}
				else
					JOptionPane.showMessageDialog(null, "Un erreur est survenue. \n Element non supprimé.", "Attention", JOptionPane.ERROR_MESSAGE);
			}
			else { // clicked No or Close button
				JOptionPane.showMessageDialog(null, "Action annulée", "Information", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}
	
	//*****************************************
	//@Override 
	public void updateStudentList(Integer groupID) {
		//System.out.println("GroupTab.updateStudentList --> " + this.groupID);

		Object[][] studentData = parent.getController().getStudentList(groupID);
		int nbStudents = studentData.length;
		//System.out.println("Number of students " + nbStudents);

		Object[][] newData = new Object[nbStudents][this.titles.length];
		for (int i=0; i<nbStudents; i++) {
			for (int j=0; j<this.titles.length; j++) {
				//System.out.println("GroupTable.update -> In loop " + i + ", " + j);
				newData[i][j] = studentData[i][j];
			}
			//newData[i][this.titles.length-2] = "Modifier";
			//newData[i][this.titles.length-1] = "Supprimer";
		}
		this.data = new Object[newData.length][titles.length];
		this.data = newData;
//		model = new GroupTableModel(this.data, this.titles);
		model.changeData(newData); // = new GroupTableModel(this.data, this.titles);
//		tabData.setModel(model);
	}

	
	//*****************************************
	//@Override
	public void updateGroupList() {
		this.groups =  parent.getController().getGroupList();
		((GroupSelector) groupSelection).setItems(this.groups, this.groups.length - 1);
	}

	
	
	
	//*****************************************
	/**
	 * This is table header renderer class to change table header appearance.
	 * @author Estelle Scifo
	 *
	 */
	// TODO temporary location of this class ! 
	private static class HeaderRenderer implements TableCellRenderer {

	    DefaultTableCellRenderer renderer;

	    public HeaderRenderer(JTable table) {
	        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
	        renderer.setHorizontalAlignment(JLabel.CENTER);
	        renderer.setBackground(Color.orange);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
	        return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	    }
	}
}