/**
 * 
 */
package com.janote.model.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

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

	public DAOFactorySQLite(String path) throws Exception {
		conn = SQLiteConnection.getInstance(path);
		if (this.createTables(this.getClass().getResourceAsStream(
				"/table_schema_v1.sql")) == false)
			throw new Exception("Ici");
	}

	@Override
	public DAO<Group> getGroupDAO() {
		return new GroupDAO(conn);
	}

	@Override
	public DAO<Student> getStudentDAO() {
		return new StudentDAO(conn);
	}

	@Override
	public DAO<Exam> getExamDAO() {
		return new ExamDAO(conn);
	}

	public boolean createTables(InputStream inputStream) {
		try {
			Statement st = conn.createStatement();
			String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\A")
					.next();

			/*
			 * BufferedReader in = new BufferedReader(new
			 * FileReader(inputStream));
			 * 
			 * String str; StringBuffer sb = new StringBuffer();
			 * 
			 * 
			 * while ((str = in.readLine()) != null) { if (str.startsWith("#"))
			 * { continue; } sb.append(str + "\n "); } in.close();
			 */
			// System.out.println(text);
			st.executeUpdate(text);
			return true;
		} catch (Exception e) {
			System.err.println("Failed to Execute" + inputStream
					+ ". The error is" + e.getMessage());
			return false;
		}
	}
}
