/**
 * 
 */
package com.janote.model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import com.janote.model.connection.SQLiteConnection;
import com.janote.model.dao.daosqlite.ExamDAO;
import com.janote.model.dao.daosqlite.GroupDAO;
import com.janote.model.dao.daosqlite.StudentDAO;
import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;


/**
 * The DAOFactory for the SQLite databases.
 * 
 * @see AbsDAOFactory
 * @author Estelle Scifo
 * @version 1.0
 */
public class DAOFactorySQLite extends AbsDAOFactory {
	protected final Connection conn;

	public DAOFactorySQLite(String path) {
		 conn = SQLiteConnection.getInstance(path); 
		 //ClassLoader cl = this.getClass().getClassLoader();
		 //this.createTables(cl.getResource("../data/table_schema_v1.sql").toString());
		 this.createTables("data/table_schema_v1.sql");
	}
	
	public DAO<Group> getGroupDAO(){
		return new GroupDAO(conn);
	}

	public DAO<Student> getStudentDAO(){
		return new StudentDAO(conn);
	}

	public DAO<Exam> getExamDAO(){
		return new ExamDAO(conn);
	}

	public boolean createTables(String filepath) {
		try {
			Statement st = conn.createStatement();

			BufferedReader in = new BufferedReader(new FileReader(filepath));
			String str;
			StringBuffer sb = new StringBuffer();

			while ((str = in.readLine()) != null) {
				if (str.startsWith("#")) {
					continue;
				}
				sb.append(str + "\n ");
			}
			in.close();
						
			st.executeUpdate(sb.toString());
			return true;
		} 
		catch (Exception e) {
			System.err.println("Failed to Execute" + filepath +". The error is"+ e.getMessage());
			return false;
		}
	}
}
