/**
 * 
 */
package com.janote.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

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
                                                 "Naissance", "Sexe",
                                                 "Redoublant", "Email" }; // column
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

    public void changeSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public boolean addStudent(Student stu) {
        if (stu.getId() == null)
            this.selectedGroup.addStudent(stu);
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

    public Object[][] getStudentList(int groupID) {
        // System.out.println("MainController.getStudentList");
        // return null; //studentDAO.getAllData(groupID,
        // this.groupColTitlesModel);
        Set<Student> students = studentDAO.findAll(groupID); // g.getStudents();
        if (students == null)
            return null;
        Object[][] result = new Object[students.size()][groupColTitlesView.length];
        int i = 0;
        for (Student s : students) {
            Object[] row = { s.getId(), s.getName(), s.getSurname(),
                            s.getBirthdayAsString(), s.getGender(),
                            s.isRepeating(), s.getEmail(), 0, 0 };
            result[i] = row;
            i++;
        }
        return result;
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

        Set<Exam> exams;
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

    public ArrayList<Group> getGroupList() {
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

    public boolean delGroup(Group g) {
        return groupDAO.delete(g);
    }

    public void getTeacher() {
        this.teacher = this.teacherDAO.find(null); // in the current
                                                   // implementation, only one
                                                   // teacher is allowed and is
                                                   // returned by the DAO

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

        teacher = teacherDAO.find(null); // in the current
                                         // implementation, only one
                                         // teacher is allowed and is
                                         // returned by the DAO
        allGroups = teacher.getGroups();
    }
}
