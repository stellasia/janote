package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import com.janote.model.dao.DAO;
import com.janote.model.entities.Group;
import com.janote.model.entities.Teacher;

public class TeacherDAO extends DAO<Teacher> {

    public TeacherDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean add(Teacher obj) {
        return false;
    }

    @Override
    public boolean add(Set<Teacher> objs, Integer to_id) {
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        GroupDAO gdao = new GroupDAO(this.connect);
        ArrayList<Group> groups = gdao.findAll();
        teach.setGroups(groups);
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
}
