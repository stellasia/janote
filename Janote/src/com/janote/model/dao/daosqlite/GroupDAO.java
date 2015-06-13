/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.janote.model.dao.DAO;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;

/**
 * Group sqlite DAO NB : relations with other tables are handled in the
 * Managers.
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class GroupDAO extends DAO<Group> {

    public GroupDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean add(Group obj) {
        if (obj.getId() != null)
            throw new IllegalArgumentException("Object id is not null ("
                    + obj.getId().toString() + "); group may already exist");

        String query = "INSERT INTO Groups (group_name, group_description) VALUES(?, ?)";
        PreparedStatement prepare;
        try {
            prepare = this.connect.prepareStatement(query);
            prepare.setString(1, obj.getName());
            prepare.setString(2, obj.getDescription());
            prepare.executeUpdate();
            // return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // get the ID of the newly created group
        Integer groupID = null;
        try {
            ResultSet generatedKeys = prepare.getGeneratedKeys();
            if (generatedKeys.next()) {
                groupID = generatedKeys.getInt(1);
                obj.setId(groupID);
            }
            else {
                throw new SQLException("Creating group failed, no ID obtained.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // insert students into DB
        ArrayList<Student> students = obj.getStudents();
        if (students != null) {
            StudentDAO studentDAO = new StudentDAO(this.connect);
            studentDAO.add(students, groupID); // TODO : check for duplicate
                                               // Student ?
        }

        ArrayList<Exam> exams = obj.getExams();
        if (exams != null) {
            ExamDAO examDAO = new ExamDAO(this.connect);
            examDAO.add(exams, groupID); // TODO : check for duplicate Exams ?
        }
        return true;
    }

    @Override
    public boolean add(ArrayList<Group> objs, Integer to_id) {
        throw new UnsupportedOperationException(
                "Can not add a group to another object.");
    }

    @Override
    public boolean delete(Group obj) {
        String query = "DELETE FROM Groups WHERE group_id = ?";
        try {
            PreparedStatement prepare = this.connect.prepareStatement(query);
            prepare.setInt(1, obj.getId());
            if (prepare.executeUpdate() != 1)
                return false;
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Group obj) {
        if (obj.getId() == null)
            return false;
        String query = "UPDATE Groups SET group_name=?, group_description=? WHERE group_id = ?";
        try {
            PreparedStatement prepare = this.connect.prepareStatement(query);
            prepare.setString(1, obj.getName());
            prepare.setString(2, obj.getDescription());
            prepare.setInt(3, obj.getId());
            prepare.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // TODO add protections against errors here
        if (obj.getStudents() != null) {
            StudentDAO sDAO = new StudentDAO(this.connect);
            for (Student s : obj.getStudents()) {
                if (s.getId() == null)
                    sDAO.add(s);
                else
                    sDAO.update(s);
            }
        }

        if (obj.getExams() != null) {
            ExamDAO eDAO = new ExamDAO(this.connect);
            for (Exam e : obj.getExams()) {
                if (e.getId() == null)
                    eDAO.add(e);
                else
                    eDAO.update(e);
            }
        }
        return true;
    }

    @Override
    public Group find(Integer id) {
        Group gr = null;
        try {
            String query = "SELECT G.* FROM Groups G " + "WHERE G.group_id = ?";
            PreparedStatement statement = this.connect.prepareStatement(query); // ,
                                                                                // ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                                                // ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                StudentDAO sdao = new StudentDAO(this.connect);
                ExamDAO edao = new ExamDAO(this.connect);
                gr = map(resultSet);
                ArrayList<Student> students = sdao.findAll(gr.getId());
                gr.setStudents(students);
                ArrayList<Exam> exams = edao.findAll(gr.getId());
                gr.setExams(exams);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return gr;
    }

    @Override
    public ArrayList<Group> findAll() {
        ArrayList<Group> grs = new ArrayList<Group>();

        Group gr = null;
        try {
            String query = "SELECT G.* FROM Groups G ;";
            PreparedStatement statement = this.connect.prepareStatement(query); // ,
                                                                                // ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                                                // ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();
            StudentDAO sdao = new StudentDAO(this.connect);
            ExamDAO edao = new ExamDAO(this.connect);
            while (resultSet.next()) {
                gr = map(resultSet);
                ArrayList<Student> students = sdao.findAll(gr.getId());
                gr.setStudents(students);
                ArrayList<Exam> exams = edao.findAll(gr.getId());
                gr.setExams(exams);
                grs.add(gr);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return grs;
    }

    /**
     * Map the current row of the ResultSet to a Group object.
     * 
     * @param resultSet
     *            The ResultSet of which the current row is to be mapped to a
     *            Group.
     * @return The instantiated Student from the current row parameter values of
     *         the passed ResultSet
     * @throws SQLException
     *             if operation fails.
     */
    private static Group map(ResultSet resultSet) throws SQLException {
        Group gr = new Group();
        gr.setId(resultSet.getInt("group_id"));
        gr.setName(resultSet.getString("group_name"));
        gr.setDescription(resultSet.getString("group_description"));
        gr.setTeacher_id(resultSet.getInt("group_teacher_id"));
        return gr;
    }

}
