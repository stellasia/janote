/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.janote.model.dao.DAO;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Gender;
import com.janote.model.entities.Student;

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
		if (obj.getId() != null)
			throw new IllegalArgumentException("Object id is not null ("+obj.getId().toString()+"); student may already exist");
		String query = "INSERT INTO Students (student_name, student_surname, student_email, student_birthday, student_repeating, student_gender, student_group_id) VALUES(?, ?, ?, ?, ?,?,?)";
		try {
			PreparedStatement prepare = this.connect.prepareStatement(query);
			//prepare.setInt(1, obj.getID()); // no, do not set the ID, will be set automatically
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getSurname());
			prepare.setString(3, obj.getEmail());
			prepare.setString(4, obj.getBirthdayAsString());
			prepare.setBoolean(5, obj.isRepeating());
			prepare.setString(6, obj.getGender().getValue());
			prepare.setInt(7, obj.getGroup_id());
			if (prepare.executeUpdate() == 0)
				return false;
		} 
		catch (SQLException e) {
			e.printStackTrace(System.out);
	        System.err.println(e.getMessage());
			return false;
		}
		
		if (obj.getExamsGrades() != null) {
			ExamDAO eDAO = new ExamDAO(this.connect);
			Iterator it = obj.getExamsGrades().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				Exam exam = (Exam) pair.getKey();
				Float grade = (float) pair.getValue();
				String query2 = "INSERT INTO Grades (grade_student_id, grade_exam_id, grade_value) VALUES(?, ?, ?)";
				try {
					PreparedStatement prepare = this.connect.prepareStatement(query);
					//prepare.setInt(1, obj.getID()); // no, do not set the ID, will be set automatically
					prepare.setInt(1, obj.getId());
					prepare.setInt(2, exam.getId());
					prepare.setFloat(3, grade);
					if (prepare.executeUpdate() == 0)
						return false;
				} 
				catch (SQLException e) {
					e.printStackTrace(System.out);
			        System.err.println(e.getMessage());
					return false;
				}
			}
		}
		
		return true;
	}

	
	@Override
	public boolean add(Set<Student> objs, Integer to_id) { // TODO : avoid multiple requests if possible (use multiple inserts) ?
		for (Student s : objs) {
			s.setGroup_id(to_id);
			this.add(s);
		}
		return true;
	}
    
	
	@Override
	public boolean delete(Student obj) {
		try {
			String query = "DELETE FROM Students WHERE student_id = ? ";
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
		if (obj.getId() == null)
			throw new IllegalArgumentException("Object id is null; student ("+obj.toString()+") may already exist");
		try {
			String query = "UPDATE Students SET student_name=?, student_surname=?, student_email=? , student_birthday=?, student_gender=?, student_repeating=?, student_group_id=? WHERE student_id = ? ";
			PreparedStatement prepare = this.connect.prepareStatement(query);
			prepare.setString(1, obj.getName());
			prepare.setString(2, obj.getSurname());
			prepare.setString(3, obj.getEmail());
			prepare.setString(4, obj.getBirthdayAsString());
			prepare.setString(5, obj.getGender().getValue());
			prepare.setBoolean(6, obj.isRepeating());
			prepare.setInt(7, obj.getGroup_id());
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
			ResultSet result = this.connect.createStatement().executeQuery(" SELECT S.* FROM Students S WHERE student_id = " + id + ";");
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
	public Set<Student> findAll() {
		Set<Student> listOfStudents = new HashSet<Student>();
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Students"); 
			while (result.next() ) {
				Student student = map(result);
				listOfStudents.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfStudents;
	}

	
	public Set<Student> findAll(int groupID) {
		Set<Student> listOfStudents = new HashSet<Student>();
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Students WHERE student_group_id="+groupID); 
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
     * Map the current row of the ResultSet to a Student object.
	 *
     * @param resultSet The ResultSet of which the current row is to be mapped to a Student.
     * @return The instantiated Student from the current row parameter values of the passed ResultSet
     * @throws SQLException if operation fails.
     */
    public static Student map(ResultSet resultSet) throws SQLException {
        Student stu = new Student();
        stu.setId(resultSet.getInt("student_id"));
        stu.setName(resultSet.getString("student_name"));
        stu.setSurname(resultSet.getString("student_surname"));
        stu.setGender(Gender.fromString(resultSet.getString("student_gender")));
        stu.setEmail(resultSet.getString("student_email"));
        stu.setBirthday(resultSet.getString("student_birthday"));
        stu.setRepeating(resultSet.getBoolean("student_repeating"));
        stu.setGroup_id(resultSet.getInt("student_group_id"));
        return stu;
    }

}
