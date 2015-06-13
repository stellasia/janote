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
        if (this.teacher.getGroups().indexOf(selectedGroup) > -1) {
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

    public boolean updateStudent(Student stu) {
        if (stu.getId() != null) {
            // System.out.println("MainController.addStudent");
            // System.out.println(stu.toString());
            studentDAO.update(stu);

            this.teacher.updateStudent(stu);
        }
        else
            throw new IllegalArgumentException(
                    "Can not update a student that does not already have an id.");
        return true;
    }

    public boolean addOrUpdateGroup(Group gr) {
        // System.out.println("MainController.addOrUpdateGroup -> " + gr);
        if (gr.getId() == null) {
            gr.setTeacher_id(this.teacher.getId());
            boolean status = groupDAO.add(gr);
            if (status) {
                this.teacher.addGroup(gr);
                this.changeSelectedGroup(gr);
            }
            return status;
        }
        else {
            boolean status = groupDAO.update(gr);
            if (status) {
                this.teacher.updateGroup(gr);
            }
            return status;
        }
    }

    public boolean setSelectedGroup(Group g) {
        if (this.teacher.getGroups().contains(g)) {
            this.selectedGroup = g;
            return true;
        }
        return false;
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
            // System.out.println(exams.toString());
            for (Exam e : exams) {
                names.add(e.getName());
            }
        }
        catch (NullPointerException e) {
            System.err
                    .println("MainController.getExamColTitlesView: Could not update the exam column titles");
        }
        // System.out.println(names.toString());
        return names;
    }

    public Teacher getTeacher() {
        Teacher teacher = new Teacher();
        // try {
        teacher = this.findTeacher();
        // }
        // catch (Exception e) {
        // System.err.println("Could not find a teacher in the file");
        // System.err.println(e.getStackTrace().toString());
        // }
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

    public boolean delStudent(Student stu) {
        this.teacher.removeStudent(stu);
        return studentDAO.delete(stu);
    }

    public boolean delGroup(Group g) {
        // System.out.println(g);
        this.teacher.removeGroup(g);
        return groupDAO.delete(g);
    }

    public Teacher findTeacher() {
        Teacher teacher = this.teacherDAO.find(null);
        if (teacher == null) {
            teacher = new Teacher();
            teacherDAO.add(teacher);
        }
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

        Teacher t = teacherDAO.find(null);
        if (t != null) {
            this.teacher.setGroups(t.getGroups());
            this.teacher.setName(t.getName());
            this.teacher.setSurname(t.getSurname());
            this.teacher.setId(t.getId());
        }
    }
}
