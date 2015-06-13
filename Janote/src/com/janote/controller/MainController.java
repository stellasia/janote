/**
 * 
 */
package com.janote.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.janote.model.dao.AbsDAOFactory;
import com.janote.model.dao.daosqlite.ExamDAO;
import com.janote.model.dao.daosqlite.GroupDAO;
import com.janote.model.dao.daosqlite.StudentDAO;
import com.janote.model.dao.daosqlite.TeacherDAO;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;
import com.janote.model.entities.Teacher;

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
    protected TeacherDAO teacherDAO;
    protected StudentDAO studentDAO;
    protected GroupDAO groupDAO;
    protected ExamDAO examDAO;
    protected Teacher teacher;
    protected ArrayList<Group> allGroups;

    /**
     * Information about the columns in the group tab
     */
    private final String groupColTitlesView[] = { "id", "Nom", "Prénom",
                                                 "Sexe", "Naissance", "Email",
                                                 "Redoublant" }; // column
                                                                 // titles

    /**
     * The currently displayed group.
     */
    private Group selectedGroup;

    /**
     * Default constructor, does nothing
     */
    public MainController(Teacher t) {
        teacher = t;
    }

    public Group getSelectedGroup() {
        return this.selectedGroup;
    }

    public boolean changeSelectedGroup(Group selectedGroup) {
        if (allGroups.indexOf(selectedGroup) > -1) {
            this.selectedGroup = selectedGroup;
            return true;
        }
        return false;
    }

    public boolean addStudent(Student stu) {
        if (stu.getId() == null) {
            // System.out.println("MainController.addStudent");
            // System.out.println(stu.toString());
            stu.setGroup_id(selectedGroup.getId());
            studentDAO.add(stu);

            this.teacher.addStudent(stu, selectedGroup);
        }
        else
            throw new IllegalArgumentException(
                    "Can not add a student that already have a non null id.");
        return true;
    }

    public boolean addOrUpdateGroup(Group gr) {
        // System.out.println("MainController.addOrUpdateGroup -> " + gr);
        if (gr.getId() == null)
            return groupDAO.add(gr);
        else
            return groupDAO.update(gr);
    }

    public boolean setSelectedGroup(Group g) {
        this.selectedGroup = g;
        return true;
    }

    public String[] getGroupColTitlesView() {
        return this.groupColTitlesView;
    }

    public ArrayList<String> getExamColTitlesView() {
        /*
         * String[] names = new String[3]; names[0] = "id"; names[1] = "Nom";
         * names[2] = "Prénom"; return names;
         */
        ArrayList<String> names = new ArrayList<String>();
        names.add("id");
        names.add("Nom");
        names.add("Prénom");

        ArrayList<Exam> exams;
        try {
            exams = this.examDAO.findAll(1); // this.selectedGroup.getId());
            System.out.println(exams.toString());
            for (Exam e : exams) {
                names.add(e.getName());
            }
        }
        catch (NullPointerException e) {

        }
        System.out.println(names.toString());
        return names;
    }

    public Teacher getTeacher() {
        Teacher teacher = new Teacher();
        try {
            teacher = this.findTeacher();
        }
        catch (Exception e) {
            System.err.println("Could not find a teacher in the file");
        }
        return teacher;
    }

    public ArrayList<Group> getGroupList() {
        return teacher.getGroups();
    }

    public ArrayList<Student> getStudentList() {
        // System.out.println("MainController");
        // System.out.println(this.selectedGroup.toString());
        // System.out.println(this.selectedGroup.getStudents());
        return this.selectedGroup.getStudents();
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

    public boolean delGroup(Group g) {
        return groupDAO.delete(g);
    }

    public Teacher findTeacher() {
        Teacher teacher = this.teacherDAO.find(null); // in the current
                                                      // implementation, only
                                                      // one
                                                      // teacher is allowed and
                                                      // is
                                                      // returned by the DAO,
                                                      // whatever parameter is
                                                      // passed to the find
                                                      // method
        return teacher;
    }

    public void start(String name) throws SQLException {
        // System.out.println("MainController.start");
        // first call, first call to the DB to initialize the view
        adf = AbsDAOFactory.getFactory(AbsDAOFactory.SQLITE_DAO_FACTORY, name);
        // now, initialize the models
        teacherDAO = (TeacherDAO) adf.getTeacherDAO();
        groupDAO = (GroupDAO) adf.getGroupDAO();
        examDAO = (ExamDAO) adf.getExamDAO();
        studentDAO = (StudentDAO) adf.getStudentDAO();

        teacher.setGroups(this.getTeacher().getGroups());
        allGroups = this.getGroupList();
    }
}
