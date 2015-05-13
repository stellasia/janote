/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.janote.model.dao.DAO;
import com.janote.model.entities.Exam;
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

	
	/**
	 * Check that a resultset contains some students information. 
	 * 
	 * @param rs a ResultSet instance
	 * @return boolean
	 * @throws SQLException
	 */
	@Deprecated
	private boolean hasStudent(ResultSet rs) throws SQLException {
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();

		// get the column names; column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {
		    String columnName = rsMetaData.getColumnName(i);
		    // Get the name of the column's table name
		    if ("student_id".equals(columnName) && rs.getInt(i) > 0 )  {
		        return true;
		    }
		}
		return false;
	}

	@Override
	public Group find(Integer id) {
		Group gr = null;
		try {
			String query = "SELECT G.*, S.* FROM Groups G " +
							"LEFT JOIN Students S ON S.student_group_id = G.group_id " +
							"WHERE G.group_id = ?";
			PreparedStatement statement = this.connect.prepareStatement(query); //, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				gr = map(resultSet);
				ArrayList <Student> listStudents = new ArrayList<Student> (); 
				ArrayList <Exam> listExams = new ArrayList<Exam> (); 
				do {
					if (resultSet.getInt("student_id") > 0)
						listStudents.add(StudentDAO.map(resultSet));		
					/*
					if (resultSet.getInt("exam_id") > 0)
						listExams.add(ExamDAO.map(resultSet));
					*/
				} while(resultSet.next());
				if (!listStudents.isEmpty())
					gr.setStudents(listStudents);
				if (!listExams.isEmpty())
					gr.setExams(listExams);
				//}	
			}
		} catch (SQLException e) {
            e.printStackTrace();
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
		ArrayList<Group> grs = new ArrayList<Group>();
		
		Group gr = null;
		try {
			String query = "SELECT G.*, S.* FROM Groups G " +
							"LEFT JOIN Students S ON S.student_group_id = G.group_id" ;
			PreparedStatement statement = this.connect.prepareStatement(query); //, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery();
			ArrayList <Student> listStudents = null; //new ArrayList<Student> (); 
			ArrayList <Exam> listExams = null; //new ArrayList<Exam> (); 
			//do 
			while (resultSet.next()) {
				if (gr == null || resultSet.getInt("group_id") != gr.getId()) {
					if (listStudents != null && !listStudents.isEmpty())
						gr.setStudents(listStudents);
					if (listExams != null && !listExams.isEmpty())
						gr.setExams(listExams);
					if (gr != null)
						grs.add(gr);
					gr = map(resultSet);
					listStudents = new ArrayList<Student> (); 
					listExams = new ArrayList<Exam> ();
				}
				if (resultSet.getInt("student_id") > 0)
					listStudents.add(StudentDAO.map(resultSet));		
					/*
					if (resultSet.getInt("exam_id") > 0)
						listExams.add(ExamDAO.map(resultSet));
					*/
			}
		} catch (SQLException e) {
            e.printStackTrace();
		}
		return grs;
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

