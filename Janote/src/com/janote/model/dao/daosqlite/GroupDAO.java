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
import com.janote.model.entities_new.Group;

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
		String query = "INSERT INTO Groups (name, description) VALUES(?, ?)";
		try {
			PreparedStatement prepare = this.connect.prepareStatement(query);
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getDescription());
			prepare.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Group obj) {
		String query = "DELETE FROM Groups WHERE id = ?";
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
		String query = "UPDATE Groups SET name=?, description=? WHERE id = ?";
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
			String query = "SELECT id, name, description FROM Groups WHERE id = ?";
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
     * Map the current row of the	 ResultSet to an Student object.
	 *
     * @param resultSet The ResultSet of which the current row is to be mapped to an User.
     * @return The instantiated Student from the current row parameter values of the passed ResultSet
     * @throws SQLException if operation fails.
     */
    private static Group map(ResultSet resultSet) throws SQLException {
        Group gr = new Group();
        gr.setId(resultSet.getInt("id"));
        gr.setName(resultSet.getString("name"));
        gr.setDescription(resultSet.getString("description"));
        return gr;
    }
	
}

