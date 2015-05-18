/**
 * 
 */
package com.janote.controller;

import java.util.Set;

import com.janote.model.dao.AbsDAOFactory;
import com.janote.model.dao.daosqlite.ExamDAO;
import com.janote.model.dao.daosqlite.GroupDAO;
import com.janote.model.dao.daosqlite.StudentDAO;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;

/**
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class MainController {
	/**
	 * DAO for groups, students and exams
	 */
	protected AbsDAOFactory adf;
	protected StudentDAO studentDAO;
	protected GroupDAO groupDAO;
	protected ExamDAO examDAO;
	protected Set<Group> allGroups;
	
	/**
	 * Information about the columns in the group tab
	 */
	private	String groupColTitlesView[] = {"id", "Nom", "Prénom", "Naissance", "Sexe", "Redoublant", "Email"};       // column titles

	/**
	 * The currently displayed group.
	 */
	private Group selectedGroup;
	
	/**
	 * Default constructor, does nothing
	 */
	public MainController() {}
	
	
	public Group getSelectedGroup() {
		return this.selectedGroup;
	}
	
	public void changeSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	public boolean addOrUpdateStudent(Student stu) {
		//this.selectedGroup.addStudent(stu);
		if (stu.getId() == null)
			return studentDAO.add(stu);
		else
			return studentDAO.update(stu);
	}
	
	public boolean addOrUpdateGroup(Group gr) {
		//System.out.println("MainController.addOrUpdateGroup -> " + gr);
		if (gr.getId() == null)
			return groupDAO.add(gr);
		else
			return groupDAO.update(gr);
	}
	
	public Object[][] getStudentList(int groupID) {
//		System.out.println("MainController.getStudentList");
//		return null; //studentDAO.getAllData(groupID, this.groupColTitlesModel);
		Set<Student> students = studentDAO.findAll(groupID); //g.getStudents();
		if (students == null) 
			return null;
		Object[][] result = new Object[students.size()][groupColTitlesView.length];
		int i = 0;
		for (Student s : students) {
			Object[] row = {s.getId(), s.getName(), s.getSurname(), s.getBirthdayAsString(), s.getGender(), s.isRepeating(), s.getEmail(), 0, 0};
			result[i] = row;
			i++;
		}
		return result;
	}
	
	public String[] getGroupColTitlesView() {
		return this.groupColTitlesView;
	}
	
	public Set<Group> getGroupList() {
		return groupDAO.findAll();
	}
	
	public Student getStudent(int id) {
		return studentDAO.find(id);
	}
	
	public Group getGroup(int id) {
		return groupDAO.find(id);
	}
	
	public boolean delStudent(Student s) {
		return studentDAO.delete(s);
	}

	public boolean delGroup (Group g) {
		return groupDAO.delete(g);
	}

	
	public void start(String name) throws Exception {
		//System.out.println("MainController.start");
		// first call, first call to the DB to initialize the view
		adf = AbsDAOFactory.getFactory(AbsDAOFactory.SQLITE_DAO_FACTORY, name);
		// now, initialize the models
		groupDAO = (GroupDAO) adf.getGroupDAO();
		studentDAO = (StudentDAO) adf.getStudentDAO();
		allGroups = groupDAO.findAll();
	}
}
