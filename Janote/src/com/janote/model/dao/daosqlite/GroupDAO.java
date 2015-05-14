/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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
		Set<Student> students = obj.getStudents();
		if ( students != null) {
			StudentDAO studentDAO = new StudentDAO(this.connect);
			studentDAO.add(students, groupID); // TODO : check for duplicate Student ? 
		}
		
		Set<Exam> exams = obj.getExams();
		if ( exams != null) {
			ExamDAO examDAO = new ExamDAO(this.connect);
			examDAO.add(exams, groupID); // TODO : check for duplicate Student ? 
		}
		return true;
	}
	

	@Override
	public boolean add(Set<Group> objs, Integer to_id) {
		// TODO Auto-generated method stub
		return false;
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
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		// TODO add protections against errors here
		if (obj.getStudents() != null) {
			StudentDAO sDAO = new StudentDAO(this.connect);
			for (Student s : obj.getStudents() ) {
				if (s.getId() == null)
					sDAO.add(s);
				else
					sDAO.update(s);
			}
		}
		
		if (obj.getExams() != null) {
			ExamDAO eDAO = new ExamDAO(this.connect);
			for (Exam e : obj.getExams() ) {
				if (e.getId() == null)
					eDAO.add(e);
				else
					eDAO.update(e);
			}
		}
		return true;
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
			String query = "SELECT G.* FROM Groups G " +
							"WHERE G.group_id = ?";
			PreparedStatement statement = this.connect.prepareStatement(query); //, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				StudentDAO sdao = new StudentDAO(this.connect);
				ExamDAO edao = new ExamDAO(this.connect);
				gr = map(resultSet); 
				Set<Student> students = sdao.findAll(gr.getId());
				gr.setStudents(students);
				Set<Exam> exams = edao.findAll(gr.getId());
				gr.setExams(exams);
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
	public Set<Group> findAll() {
		Set<Group> grs = new HashSet<Group>();
		
		Group gr = null;
		try {
			String query = "SELECT G.* FROM Groups G ;";
			PreparedStatement statement = this.connect.prepareStatement(query); //, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery();
			StudentDAO sdao = new StudentDAO(this.connect);
			ExamDAO edao = new ExamDAO(this.connect);
			while (resultSet.next()) {
				gr = map(resultSet);
				Set<Student> students = sdao.findAll(gr.getId());
				gr.setStudents(students);
				Set<Exam> exams = edao.findAll(gr.getId());
				gr.setExams(exams);
				grs.add( gr );
			}
		} catch (SQLException e) {
            e.printStackTrace();
		}
		return grs;
	}

    /**
     * Map the current row of the ResultSet to a Group object.
	 *
     * @param resultSet The ResultSet of which the current row is to be mapped to a Group.
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

