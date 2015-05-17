/**
 * 
 */
package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.janote.model.entities.Student;

/**
 * @author estelle
 *
 */
@SuppressWarnings("serial")
public class DialogStudentInfo extends JDialog {

	private Student student;
	
	public DialogStudentInfo(Student stu){
		super();
		this.student = stu;
	    this.setTitle("Informations sur l'étudiant  " + student.getId());

		this.setSize(new Dimension(500, 400));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the close cross is clicked, just close the dialog window
		
		this.initComponent();
	}

	public boolean showDialog(){
		this.setVisible(true);      
//		return this.sendData;      
		return true;
	}


	private void initComponent(){
		//====================================
		JPanel panGeneral = new JPanel();
		panGeneral.setPreferredSize(new Dimension(500, 200));
		panGeneral.setBorder(BorderFactory.createTitledBorder("Résumé"));

		JLabel picture = new JLabel();
		Icon icon;
//		System.out.println("DialogStudentInfo -> initComponent -> student sex = " + student.getVal("sex"));
/*
		if (student.getVal("sex").equals("F"))
			icon = new ImageIcon(getClass().getResource("/graduate_female.png"));  
		else
			icon = new ImageIcon(getClass().getResource("/graduate_male.png"));  
		picture.setIcon(icon);
*/		
	    JLabel nomLabel = new JLabel(student.getSurname() + " " + student.getName());
	    nomLabel.setFont(new Font(nomLabel.getFont().getName(), Font.BOLD, 25));
		
	    System.out.println(student);
	    
	    String displayed_grade = "ND";
	    if (student.getAverage_grade() != null) 
	    	displayed_grade = student.getAverage_grade().toString();
	    JLabel meanLabel = new JLabel(displayed_grade);
	    meanLabel.setForeground(Color.RED);
	    meanLabel.setFont(new Font(meanLabel.getFont().getName(), Font.BOLD, 25));

	    JPanel b2 = new JPanel(); // intermediate panel for element positioning
	    b2.setLayout(new BoxLayout(b2, BoxLayout.PAGE_AXIS));
	    b2.add(nomLabel);
	    b2.add(meanLabel);
	    
	    panGeneral.setLayout(new BoxLayout(panGeneral, BoxLayout.LINE_AXIS));
	    panGeneral.add(picture);
	    panGeneral.add(b2);
	    
		this.getContentPane().add(panGeneral, BorderLayout.NORTH);
		
		//====================================
		JPanel panPersonalDetails = new JPanel();
		panPersonalDetails.setPreferredSize(new Dimension(250, 200));
		panPersonalDetails.setBorder(BorderFactory.createTitledBorder("Détails personnels"));
	    panPersonalDetails.setLayout(new BoxLayout(panPersonalDetails, BoxLayout.PAGE_AXIS));
	    JLabel birthLabel = new JLabel("Anniversaire : " + student.getBirthdayAsString());
	    JLabel emailLabel = new JLabel("Email        : " + student.getEmail());
	    JLabel repeatingLabel = new JLabel("Redoublant    : " + student.isRepeating());

	    panPersonalDetails.add(birthLabel);
	    panPersonalDetails.add(repeatingLabel);
	    panPersonalDetails.add(emailLabel);

		this.getContentPane().add(panPersonalDetails, BorderLayout.WEST);

		//====================================
		JPanel panGradesDetails = new JPanel();
		panGradesDetails.setPreferredSize(new Dimension(250, 200));
		panGradesDetails.setBorder(BorderFactory.createTitledBorder("Notes"));

		this.getContentPane().add(panGradesDetails, BorderLayout.EAST);
	}  
}

