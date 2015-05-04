/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.janote.model.dao.DAO;
import com.janote.model.entities_new.Gender;
import com.janote.model.entities_new.Student;

/**
 * The student DAO. 
 * Manage the Students table and link to the Student java objects.
 * 
 * NB: the average grade is not a column of the Students table, it is computed *on the fly*. 
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class StudentDAO extends DAO<Student> {

	public static final int REPEATING_Y = 0;
	public static final int REPEATING_N = 1;

	public StudentDAO(Connection conn) {
		super(conn);
	}

	@Override
	/**
	 * Add a new row into the Student table.
	 * 
	 * @param obj
	 * 			an instance of Student. Note that the id of that Student is not used, even if it is set.
	 * @see Student
	 */
	public boolean add(Student obj) {
		String query = "INSERT INTO Students (name, surname, email, birthday, repeating, gender, groupID) VALUES(?, ?, ?, ?, ?,?,?)";
		try {
			PreparedStatement prepare = this.connect.prepareStatement(query);
			//prepare.setInt(1, obj.getID()); // no, do not set the ID, will be set automatically
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getSurname());
			prepare.setString(3, obj.getEmail());
			prepare.setString(4, obj.getBirthdayAsString());
			prepare.setBoolean(5, obj.isRepeating());
			prepare.setInt(6, obj.getGender().getValue());
			prepare.setInt(7, obj.getGroup_id());
			prepare.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace(System.out);
	        System.err.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean delete(Student obj) {
		try {
			String query = "DELETE FROM Students WHERE id = ? ";
			PreparedStatement prepare = this.connect.prepareStatement(query);
			prepare.setInt(1, obj.getId());
			if (prepare.executeUpdate() != 1)
				return false;
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Student obj) {
		try {
			String query = "UPDATE Students SET name=?, surname=?, email=? , birthday=?, gender=?, repeating=?, groupID=? WHERE id = ? ";
			PreparedStatement prepare = this.connect.prepareStatement(query);
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getSurname());
			prepare.setString(3, obj.getEmail());
			prepare.setString(4, obj.getBirthdayAsString());
			prepare.setInt(5, obj.getGender().getValue());
			prepare.setBoolean(6, obj.isRepeating());
			prepare.setInt(8, obj.getId());
			if (prepare.executeUpdate() == 1)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Student find(Integer id) {
		Student student = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Students WHERE id = " + id); 
			while ( result.next() ) {		
				student = map(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	/**
	 * 
	 */
	public ArrayList<Student> findAll() {
		ArrayList<Student> listOfStudents = new ArrayList<Student>();
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Students"); 
//			ResultSet result = this.connect.createStatement().executeQuery("SELECT s.*, (SELECT sum(g.grade * e.coefficient) / sum(e.coefficient) FROM Exams as e, Grades as g WHERE g.studentID=s.id) as average FROM Students AS s"); 
			while (result.next() ) {
				Student student = map(result);
				listOfStudents.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfStudents;
	}

	
	public ArrayList<Student> findAll(int groupID, String[] wantedColumns) {
		ArrayList<Student> listOfStudents = new ArrayList<Student>();
		try {
//			ResultSet result = this.connect.createStatement().executeQuery("SELECT s.*, (SELECT sum(g.grade * e.coefficient) / sum(e.coefficient) FROM Exams as e, Grades as g WHERE g.studentID=s.id) as average FROM Students AS s WHERE s.groupID="+groupID); 
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Students WHERE groupID="+groupID); 
			while (result.next() ) {
				Student student = map(result);
				listOfStudents.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfStudents;
	}
	
	
    /**
     * Map the current row of the ResultSet to an Student object.
	 *
     * @param resultSet The ResultSet of which the current row is to be mapped to an User.
     * @return The instantiated Student from the current row parameter values of the passed ResultSet
     * @throws SQLException if operation fails.
     */
    private static Student map(ResultSet resultSet) throws SQLException {
        Student stu = new Student();
        stu.setId(resultSet.getInt("id"));
        stu.setName(resultSet.getString("name"));
        stu.setSurname(resultSet.getString("surname"));
        stu.setGender(Gender.values()[resultSet.getInt("gender")]);
        stu.setEmail(resultSet.getString("email"));
        stu.setBirthday(resultSet.getString("birthday"));
        stu.setRepeating(resultSet.getBoolean("repeating"));
        return stu;
    }
    
}
