package com.janote.model.entities;

import java.util.ArrayList;

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

    protected ArrayList<Student> students;
    // protected HashMap<Exam, Float> exams;
    protected ArrayList<Exam> exams;

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
            ArrayList<Student> students, ArrayList<Exam> exams) {
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
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * @return the list of exams taken by the group
     */
    // public HashMap<Exam, Float> getExams() {
    public ArrayList<Exam> getExams() {
        return exams;
    }

    /**
     * 
     * @return a 2D-array listing the student name and grades
     */
    public Object[][] getStudentGrades(Exam e) {
        Object[][] result = new Object[this.students.size()][2];
        for (int i = 0; i < this.students.size(); i++) {
            Student stu = this.students.get(i);
            result[i][0] = String.format("%s %s", stu.getName(),
                    stu.getSurname());
            for (int j = 0; j < this.exams.size(); j++) {
                Exam exam = this.exams.get(j);
                if (exam.getId() == e.getId())
                    result[i][1] = stu.getGrade(exam);
            }
        }
        return result;
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
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    /**
     * @param exams
     *            the exams to set
     */
    // public void setExams(HashMap<Exam, Float> exams) {
    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
        /*
         * if (this.getStudents() != null) { for (Student s :
         * this.getStudents()) { s.setExams(exams); } }
         */
    }

    public void addStudent(Student s) {
        if (this.students == null)
            this.students = new ArrayList<Student>();
        s.setGroup_id(this.getId());
        this.students.add(s);
        this.observableAdded(s);
    }

    public void removeStudent(Student s) {
        this.students.remove(s);
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

    public void addExam(Exam e) {
        this.exams.add(e);
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
