package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.janote.model.dao.DAO;
import com.janote.model.entities.Group;
import com.janote.model.entities.Teacher;

public class TeacherDAO extends DAO<Teacher> {

    public TeacherDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean add(Teacher obj) {
        if (obj.getId() != null)
            throw new IllegalArgumentException("Object id is not null ("
                    + obj.getId().toString() + "); student may already exist");
        String query = "INSERT INTO Teachers (teacher_name, teacher_surname) VALUES(?, ?)";
        try {
            PreparedStatement prepare = this.connect.prepareStatement(query);
            // prepare.setInt(1, obj.getID()); // no, do not set the ID, will be
            // set automatically
            prepare.setString(1, obj.getName());
            prepare.setString(2, obj.getSurname());
            if (prepare.executeUpdate() == 0)
                return false;
            obj.setId(prepare.getGeneratedKeys().getInt(1));
        }
        catch (SQLException e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean add(ArrayList<Teacher> objs, Integer to_id) {
        return false;
    }

    @Override
    public boolean delete(Teacher obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(Teacher obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Teacher find(Integer id) {
        Teacher teach = null;
        try {
            ResultSet result = this.connect.createStatement().executeQuery(
                    "SELECT * FROM Teachers");
            if (result.next()) {
                teach = map(result);
            }
            else {
                teach = createDefault();
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if (teach != null) {
            GroupDAO gdao = new GroupDAO(this.connect);
            ArrayList<Group> groups = gdao.findAll();
            if (groups != null)
                teach.setGroups(groups);
        }
        return teach;
    }

    @Override
    public ArrayList<Teacher> findAll() {
        return null;
    }

    /**
     * Map the current row of the ResultSet to an Teacher object.
     * 
     * @param resultSet
     *            The ResultSet of which the current row is to be mapped to an
     *            Teacher.
     * @return The instantiated Student from the current row parameter values of
     *         the passed ResultSet
     * @throws SQLException
     *             if operation fails.
     */
    public static Teacher map(ResultSet resultSet) throws SQLException {
        Teacher exam = new Teacher();
        exam.setId(resultSet.getInt("teacher_id"));
        exam.setName(resultSet.getString("teacher_name"));
        exam.setSurname(resultSet.getString("teacher_surname"));
        return exam;
    }

    public Teacher createDefault() {
        Teacher new_teacher = new Teacher(null, "", "", null);
        this.add(new_teacher);
        return new_teacher;
    }
}
