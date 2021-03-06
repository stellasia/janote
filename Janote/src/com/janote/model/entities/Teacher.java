/**
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
package com.janote.model.entities;

import java.util.ArrayList;

/**
 * @author estelle
 * 
 */
public class Teacher extends AbsEntity {

    protected String name;
    protected String surname;

    protected ArrayList<Group> groups = new ArrayList<Group>();

    public Teacher() {
    }

    public Teacher(Integer id, String name, String surname,
            ArrayList<Group> groups) {
        this.id = id;
        this.name = name;
        this.surname = surname;

        this.groups = groups;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
        this.observableUpdated(this);
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname
     *            the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
        this.observableUpdated(this);
    }

    /**
     * @return the groups
     */
    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
        this.observableUpdated(this);
    }

    public void addGroup(Group g) {
        if (this.groups == null)
            this.groups = new ArrayList<Group>();
        g.setTeacher_id(this.getId());
        this.groups.add(g);
        this.observableAdded(g);
    }

    public void updateGroup(Group gr) {
        for (int i = 0; i < groups.size(); i++) {
            Group g = groups.get(i);
            if (g.getId() == gr.getId()) {
                groups.set(i, gr);
                break;
            }
        }
        this.observableUpdated(gr);
    }

    public void removeGroup(Group gr) {
        for (Group g : groups) {
            if (g.getId() == gr.getId()) {
                groups.remove(g);
                break;
            }
        }
        this.observableDeleted(gr);
    }

    public void addStudent(Student s, Group g) {
        g.addStudent(s);
        this.observableAdded(s);
    }

    public void addExam(Exam e, Group g) {
        g.addExam(e);
        this.observableAdded(e);
    }

    public void updateStudent(Student stu) {
        Student s = null;
        for (Group g : groups) {
            if (g.getId() == stu.getGroup_id()) {
                ArrayList<Student> students = g.getStudents();
                for (int i = 0; i < students.size(); i++) {
                    s = students.get(i);
                    if (s.getId() == stu.getId()) {
                        s.update(stu);
                        break;
                    }
                }
            }
        }
        this.observableUpdated(s);
    }

    public void removeStudent(Student stu) {
        for (Group g : groups) {
            if (g.getId() == stu.getGroup_id()) {
                g.removeStudent(stu);
                break;
            }
        }
        this.observableDeleted(stu);
    }

    @Override
    public String toString() {
        String res = "Teacher:: " + this.name + " [ ";
        for (Group g : this.groups)
            res += g.toString();
        return res + " ]";
    }
}
