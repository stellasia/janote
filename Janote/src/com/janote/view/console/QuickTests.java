package com.janote.view.console;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.janote.model.dao.AbsDAOFactory;
import com.janote.model.dao.DAO;
import com.janote.model.dao.DAOFactorySQLite;
import com.janote.model.dao.daosqlite.GroupDAO;
import com.janote.model.dao.daosqlite.StudentDAO;
import com.janote.model.entities_new.Gender;
import com.janote.model.entities_new.Group;
import com.janote.model.entities_new.Student;
import com.janote.model.managers.GroupManager;

/**
 * This class is used as a basic test for the models and DAO methods. 
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class QuickTests {

	private Scanner sc;
	
	public QuickTests () { 
		sc = new Scanner(System.in);
	}
	
	/**
	 * Helper for sqlite file creation.
	 * 
	 * @param fileName
	 * @return the fileName extension if any (empty String otherwise)
	 */
	private static String getFileExtension(String fileName) {
	    if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	    	return fileName.substring(fileName.lastIndexOf(".")+1);
	    else return "";
	}
	  
	/**
	 * A function to get the a filename from the standard input. 
	 * A '.sql' extension is added automatically if not entered by the user. 
	 * 
	 * @return the fileName with '.sql' extension
	 */
	public String ChooseFile() {
	
		System.out.println("Please enter file name : ");
        //String entry = this.sc.nextLine();
        String entry = "test.sql";
		System.out.print("You juste entered ");
        System.out.println(entry);
        
        String ext = getFileExtension(entry);
        if ( ! ext.equals("sql")) {
        	entry = entry + ".sql";
        	System.out.println("WARNING: will write to " + entry);
        }
        
        File f = new File(entry);
        if ( (f.exists() && !f.isDirectory()) || !f.exists()) {
        	return entry;
        }
	
        return null;
	}
	
	/**
	 * Create a connection to an SQLite database. 
	 * 
	 * @return a connection to an SQLite database
	 */
	public AbsDAOFactory get_connection() {
		String filename = this.ChooseFile();
		if (filename != null) {
			DAOFactorySQLite adf = (DAOFactorySQLite) AbsDAOFactory.getFactory(AbsDAOFactory.SQLITE_DAO_FACTORY, filename);
			return adf;
		}		
		return null;
	}
	
	/**
	 * Test the Student model and StudentDAO
	 * 
	 * @param dao
	 */
	public void test_students(StudentDAO dao) {
		System.out.println("Adding students");
		Student s0 = new Student();
		System.out.println(s0);
		//dao.add(s0);
		
		Student s1 = null;
		s1 = new Student(1,  "Ford", "Peter", Gender.BOY, "pford@somewhere.com", "11-01-1999", false, null);
		System.out.println(s1);
		dao.add(s1);
		
		//System.out.println(dao.find(1));
	}
	
	/**
	 * Test the Group model and GroupDAO
	 * 
	 * @param dao
	 */
	public void test_groups(GroupDAO dao) {
		Student s1  = new Student(2,  "Ford", "Shanon", Gender.GIRL, "sford@somewhere.com", "11-01-1999", false, null);
		Student s2  = new Student(3,  "Thomson", "John", Gender.BOY, "j.thomson@elsewhere.com", "14-11-1998", false, null);
		Student s3  = new Student(4,  "Smith", "Albert", Gender.BOY, "albert.smith@usa.com", "21-06-1997", true, null);
		Student s4  = new Student(5,  "Alabama", "Anna", Gender.GIRL, "aaaa@lala.com", "07-02-1999", true, null);
		Student s5  = new Student(6,  "Ingals", "Mary", Gender.GIRL, "ming@farm.com", "31-08-1999", false, null);

		ArrayList<Student> list1 = new ArrayList<Student>();
		list1.add(s1); list1.add(s2); list1.add(s3);
		Group g1 = new Group(1, "G1", "Ceci est la description du groupe 1. Par exemple : '1èreS 1'", list1 );
		System.out.println(g1);
		dao.add(g1);
		
		/*
		for (Student s:list1) {
			if (dao.addStudent(s, g1))
				System.out.println("Student successfully added");
			else 
				System.out.println("Failed to add student to db");
		}
		*/
		
		ArrayList<Student> list2 = new ArrayList<Student>();
		list2.add(s4); list2.add(s5);
		Group g2 = new Group(2, "G2", "Ceci est la description du groupe 2. Par exemple : 'TS 13'", list2 );
		System.out.println(g2);
		dao.add(g2);
		/*
		for (Student s:list2) {
			if (dao.addStudent(s, g2))
				System.out.println("Student successfully added");
			else 
				System.out.println("Failed to add student to db");
		}
		*/
		
	}
	
	/**
	 * Main method of the class
	 */
	public void run() {
		AbsDAOFactory adf = this.get_connection();
		if (adf == null) {
			System.out.println("Failed to create connection.");
			return ;
		}
		System.out.println("Connection create successfully. Continue... ");

		System.out.println("Test ongoing... ");
		
		GroupManager gm = new GroupManager();
		
//		StudentDAO sdao = (StudentDAO) adf.getStudentDAO();
//		this.test_students(sdao);
		
//		GroupDAO gdao = (GroupDAO) adf.getGroupDAO();
//		this.test_groups(gdao);
		
		System.out.println("Exit.");
	}
	
}