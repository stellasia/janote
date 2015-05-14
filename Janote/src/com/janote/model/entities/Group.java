package com.janote.model.entities;

import java.util.Set;

//import com.janote.model.managers.GroupManager;


/**
 * Class to describe a group of students
 * characterized by:
 * 			- an id (integer)
 * 			- a name (String)
 *  		- a description (String), optional
 *  
 * @author Estelle Scifo
 * @version 1.0
 */
public class Group {

	protected Integer id;
	protected String name;
	protected String description;
	
	protected Set<Student> students;
	//protected HashMap<Exam, Float> exams;
	protected Set<Exam> exams;
	
//	protected static GroupManager objects = new GroupManager();

	
	public Group() {} // necessary for dev purpose
	
	/**
	 * @param id
	 * @param name
	 * @param description : a short description
	 * @param students : list of Student in the group
	 */
	public Group(Integer id, String name, String description,
			Set<Student> students, Set<Exam> exams) {
		super();
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setStudents(students);
		this.setExams(exams);
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the list of students in the group
	 */
	public Set<Student> getStudents() {
		return students;
	}
	/**
	 * @return the list of exams taken by the group
	 */
	//public HashMap<Exam, Float> getExams() {
	public Set<Exam> getExams() {
		return exams;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param students the students to set
	 */
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	/**
	 * @param exams the exams to set
	 */
	//public void setExams(HashMap<Exam, Float> exams) {
	public void setExams(Set<Exam> exams) {
		this.exams = exams;
		for (Student s : this.getStudents()) {
			s.setExams(exams);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/*
	@Override
	public String toString() {
		final int maxLen = 10;
		return "Group [id=" + id + ", name=" + name + ", description="
				+ description + ", students="
				+ (students != null ? toString(students, maxLen) : null)
				+ ", exams="
//				+ (exams != null ? toString(exams.entrySet(), maxLen) : null)
				+ (exams != null ? toString(exams, maxLen) : null)
				+ "] \n";
	}
	
	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n ");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", \n ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	*/
	
	@Override
	public String toString() {
		return this.name + " (" + this.description.substring(0, 50) + "... )";
	}	
	
}
