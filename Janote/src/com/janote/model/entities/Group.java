package com.janote.model.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to describe a group of students characterized by: - an id (integer) - a
 * name (String) - a description (String), optional
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class Group extends AbsEntity {

    protected String name;
    protected String description;
    protected Integer teacher_id;

    protected Set<Student> students;
    // protected HashMap<Exam, Float> exams;
    protected Set<Exam> exams;

    // protected static GroupManager objects = new GroupManager();

    public Group() {
    } // necessary for dev purpose

    /**
     * @param id
     * @param name
     * @param description
     *            : a short description
     * @param students
     *            : list of Student in the group
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
    // public HashMap<Exam, Float> getExams() {
    public Set<Exam> getExams() {
        return exams;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        if (name != null && name.length() > 0) {
            this.name = name;
        }
        else
            throw new IllegalArgumentException(
                    "Le nom d'un groupe doit être rempli !");
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param students
     *            the students to set
     */
    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    /**
     * @param exams
     *            the exams to set
     */
    // public void setExams(HashMap<Exam, Float> exams) {
    public void setExams(Set<Exam> exams) {
        this.exams = exams;
        if (this.getStudents() != null) {
            for (Student s : this.getStudents()) {
                s.setExams(exams);
            }
        }
    }

    public void addStudent(Student s) {
        if (this.students == null)
            this.students = new HashSet<Student>();
        s.setGroup_id(this.getId());
        this.students.add(s);
        this.observableAdded(s);
    }

    public void updateStudent(Student s) {
        // get the position of s in this.students
        // set the ith element of students to s
    }

    /**
     * @return the teacher_id
     */
    public Integer getTeacher_id() {
        return teacher_id;
    }

    /**
     * @param teacher_id
     *            the teacher_id to set
     */
    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        String short_description = this.description;
        if (this.description.length() > 60) {
            short_description = short_description.substring(0, 57) + "...";
        }
        return this.name + " (" + short_description + ")";
    }

}
