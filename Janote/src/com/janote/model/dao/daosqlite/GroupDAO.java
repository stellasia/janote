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
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;

/**
 * Group sqlite DAO
 * NB : relations with other tables are handled in the Managers. 
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
			throw new IllegalArgumentException("Object id is not null ("+obj.getId().toString()+"); group may already exist");
		
		String query = "INSERT INTO Groups (group_name, group_description) VALUES(?, ?)";
		PreparedStatement prepare;
		try {
			prepare = this.connect.prepareStatement(query);
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getDescription());
			prepare.executeUpdate();
			//return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		// get the ID of the newly created group
		Integer groupID = null;
		try  {
			ResultSet generatedKeys = prepare.getGeneratedKeys() ;
			if (generatedKeys.next()) {
				groupID = generatedKeys.getInt(1);
			}
			else {
				throw new SQLException("Creating user failed, no ID obtained.");
	       }
	    }
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		 
		// insert students into DB
		ArrayList<Student> students = obj.getStudents();
		if ( students != null) {
			StudentDAO studentDAO = new StudentDAO(this.connect);
			for (Student s : students) {
				s.setGroup_id(groupID); // set the correct groupID for the students
				studentDAO.add(s); // TODO : check for duplicate Student ? 
			}	
		}

		return true;
	}

	@Override
	public boolean delete(Group obj) {
		String query = "DELETE FROM Groups WHERE group_id = ?";
		try {
			PreparedStatement prepare = this.connect.prepareStatement(query);
			prepare.setInt( 1, obj.getId());
			return prepare.execute();
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
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public Group find(Integer id) {
		Group gr = null;
		try {
			String query = "SELECT G.*, S.* FROM Groups G" +
					"JOIN Students S on S.student_group_id = G.group_id" +
					"WHERE G.group_id = ?";
			PreparedStatement statement = this.connect.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				gr = map(resultSet);
			}
		} catch (SQLException e) {
		}
		return gr;

		/*
		 sqlite> select G.id , S.name from Groups G join Students S on S.groupID = G.id;
			id          name      
			----------  ----------
			1           Ford      
			1           Thomson   
			1           Smith     
			2           Alabama   
			2           Ingals    
		 */
	}


	@Override
	public ArrayList<Group> findAll() {
		return null;
	}

    /**
     * Map the current row of the ResultSet to an Student object.
	 *
     * @param resultSet The ResultSet of which the current row is to be mapped to an User.
     * @return The instantiated Student from the current row parameter values of the passed ResultSet
     * @throws SQLException if operation fails.
     */
    private static Group map(ResultSet resultSet) throws SQLException {
        Group gr = new Group();
        gr.setId(resultSet.getInt("group_id"));
        gr.setName(resultSet.getString("group_name"));
        gr.setDescription(resultSet.getString("group_description"));
        return gr;
    }
	
}

