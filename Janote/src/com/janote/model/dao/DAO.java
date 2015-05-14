package com.janote.model.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * DAO (Data Access Object) generic class 
 * (handles objects creation from DB)
 * 
 * (partly inspired from http://balusc.blogspot.co.at/2008/07/dao-tutorial-data-layer.html)
 * 
 * @author Estelle Scifo
 * @version 1.0 
 * @param <T> (an entity)
 */
public abstract class DAO<T> {
	protected Connection connect = null;
	  
	/**
	 * Generic DAO constructor 
	 * 
	 * @param conn 
	 * 		the database connection
	 * @see DAOFactorySQLite
	 * @see DAOFactoryXML
	 */
	public DAO(Connection conn){
		this.connect = conn;
	}

	/**
	 * Create a new object
	 * @param obj
	 * @return boolean (True if successful)
	 */
	public abstract boolean add(T obj);

	/**
	 * Add a bunch of new objects.
	 * Can eventually specify a `to`argument to add obj to another one with an ID.
	 * 
	 * @param objs
	 * @param to_id 
	 * @return
	 */
	public abstract boolean add(Set<T> objs, Integer to_id);
	
	/**
	 * Delete an object 
	 * @param obj
	 * @return boolean (True if successful) Especially, if the object does not exist in the DB, returns false.
	 */
	public abstract boolean delete(T obj);

	/**
	 * Update an existing entry.
	 * @param obj
	 * @return boolean (True if successful) Especially, if the object does not exist in the DB, returns false.
	 */
	public abstract boolean update(T obj);

	/**
	 * Select an object in the DB.
	 * @param id (int)
	 * @return T (the object)
	 */
	public abstract T find(Integer id);

	/**
	 * Get all rows.
	 * @return a table of T.
	 */
	public abstract Set<T> findAll();
	
	
	/**
	 * Convert String object into Date. 
	 * 
	 * @param sDate 
	 * 			the string to be converted
	 * @param sFormat
	 * 			the format of the input string date
	 */
	public static Date stringToDate(String sDate, String sFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        return sdf.parse(sDate);	
    } 
	public static String dateToString(Date date, String sFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        return sdf.format(date);
    } 
	

}
