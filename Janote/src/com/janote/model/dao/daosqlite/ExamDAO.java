/**
 * 
 */
package com.janote.model.dao.daosqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.janote.model.dao.DAO;
import com.janote.model.entities_new.Exam;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Exam obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Exam obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Exam find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Exam> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


}

