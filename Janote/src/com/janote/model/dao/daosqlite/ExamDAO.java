/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.janote.model.dao.DAO;
import com.janote.model.entities.Exam;

/**
 * @author Estelle Scifo
 * @version 1.0
 */
public class ExamDAO  extends DAO<Exam> {

	public ExamDAO (Connection conn) {
		super(conn);
	}

	@Override
	public boolean add(Exam obj) {
		if (obj.getId() != null)
			throw new IllegalArgumentException("Object id is not null ("+obj.getId().toString()+"); exam may already exist");
		String query = "INSERT INTO Exams (exam_name, exam_description, exam_coefficient, exam_group_id) VALUES(?, ?, ?, ?)";
		try {
			PreparedStatement prepare = this.connect.prepareStatement(query);
			//prepare.setInt(1, obj.getId()); // no, do not set the ID, will be set automatically
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getDescription());
			prepare.setFloat (3, obj.getCoefficient());
			prepare.setInt   (4, obj.getGroup_id());
			if (prepare.executeUpdate() == 0)
				return false;
		} 
		catch (SQLException e) {
			e.printStackTrace(System.out);
	        System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean add(Set<Exam> objs, Integer to_id) { // TODO : avoid multiple requests if possible (use multiple inserts) ?
		for (Exam e : objs) {
			e.setGroup_id(to_id);
			this.add(e);
		}
		return true;
	}
	
	@Override
	public boolean delete(Exam obj) {
		String query = "DELETE FROM Exams WHERE exam_id = ?";
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
	public boolean update(Exam obj) {
		if (obj.getId() == null)
			throw new IllegalArgumentException("Object id is null; student ("+obj.toString()+") may already exist");
		try {
			String query = "UPDATE Exams SET exam_name=?, exam_description=?, exam_coefficient=?, exam_group_id=? WHERE exam_id = ? ";
			PreparedStatement prepare = this.connect.prepareStatement(query);
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getDescription());
			prepare.setFloat (3, obj.getCoefficient());
			prepare.setInt (4, obj.getGroup_id());
			prepare.setInt(5, obj.getId());
			if (prepare.executeUpdate() == 1)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Exam find(Integer id) {
		Exam exam = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Exams WHERE id = " + id); 
			while ( result.next() ) {
				exam = map(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exam;
	}

	@Override
	public Set<Exam> findAll() {
		Set<Exam> listOfExams = new HashSet<Exam>();
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Exams"); 
			while (result.next() ) {
				Exam exam = map(result);
				listOfExams.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfExams;
	}

	public Set<Exam> findAll(Integer group_id) {
		Set<Exam> listOfExams = new HashSet<Exam>();
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Exams WHERE exam_group_id = "+ group_id); 
			while (result.next() ) {
				Exam exam = map(result);
				listOfExams.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfExams;
	}
	
    /**
     * Map the current row of the ResultSet to an Exam object.
	 *
     * @param resultSet The ResultSet of which the current row is to be mapped to an Exam.
     * @return The instantiated Student from the current row parameter values of the passed ResultSet
     * @throws SQLException if operation fails.
     */
    public static Exam map(ResultSet resultSet) throws SQLException {
        Exam exam = new Exam();
        exam.setId(resultSet.getInt("exam_id"));
        exam.setName(resultSet.getString("exam_name"));
        exam.setDescription(resultSet.getString("exam_description"));
        exam.setCoefficient(resultSet.getFloat("exam_coefficient"));
        exam.setGroup_id(resultSet.getInt("exam_group_id"));
        return exam;
    }


}

