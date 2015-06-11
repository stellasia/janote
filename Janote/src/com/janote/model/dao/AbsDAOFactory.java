/**
 * 
 */
package com.janote.model.dao;

import java.sql.SQLException;

import com.janote.model.entities.Exam;
import com.janote.model.entities.Group;
import com.janote.model.entities.Student;
import com.janote.model.entities.Teacher;

/**
 * DAOFactory generic class Instantiate a unique connection object (SQLite or
 * MySQL)
 * 
 * @see DAO
 * @author Estelle Scifo
 * @version 1.0
 */
public abstract class AbsDAOFactory {
    public static final int SQLITE_DAO_FACTORY = 1;
    public static final int XML_DAO_FACTORY = 2;

    /**
     * 
     * @return an object of type DAO<Group>
     */
    public abstract DAO<Group> getGroupDAO();

    /**
     * 
     * @return an object of type DAO<Student>
     */
    public abstract DAO<Student> getStudentDAO();

    /**
     * 
     * @return an object of type DAO<Exam>
     */
    public abstract DAO<Exam> getExamDAO();

    /**
     * 
     * @return an object of type DAO<Exam>
     */
    public abstract DAO<Teacher> getTeacherDAO();

    /**
     * 
     * @param type
     *            (int) one of the above defined (public static final int)
     *            parameters *_DAO_FACTORY
     * @return a DAOFactory object
     * @throws Exception
     */
    public static AbsDAOFactory getFactory(int type, String name)
            throws SQLException {
        switch (type) {
            case SQLITE_DAO_FACTORY:
                return new DAOFactorySQLite(name);
            case XML_DAO_FACTORY:
                return new DAOFactoryXML();
            default:
                return null;
        }
    }

    public void setDBName(String name) {
        // TODO recharge a DB if the source file is changed
    }
}