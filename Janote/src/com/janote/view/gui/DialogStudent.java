/**
 * 
 */
package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.janote.controller.MainController;
import com.janote.model.entities.Gender;
import com.janote.model.entities.Student;

/**
 * @author estelle
 *
 */
@SuppressWarnings("serial")
public class DialogStudent extends JDialog {

	public static final int LABEL_WIDTH = 300;
	public static final int LABEL_HEIGHT = 30;
	
	private Student student;
	private MainController cont;
	
	private JTextField name, surname, birth, email;
	private JComboBox<Gender> gender;
	
	private DialogStatus status;
	
	public DialogStudent(Student stu, MainController cont) {
		super();
		
		this.setModal(true); // VERY IMPORTANT ! Allow to block any other input, ie allow to get the relevant return value in showDialog
		
		if (stu == null) {
			this.student = new Student();
			this.setTitle("Enregistrer un nouvel étudiant");
		}
		else {
			this.student = stu;
			this.setTitle("Informations sur l'étudiant  " + student.getId());
		}
		this.cont = cont;
		
	    this.setSize(500, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the close cross is clicked, just close the dialog window
		
		this.status = DialogStatus.NOTHING_CHANGE; // default is everything OK status
		this.initComponent();
	}

	public DialogStatus showDialog(){
		this.setVisible(true); 
		//System.out.println("In DialogStudent.showDialog --> " + this.status);
		return this.status;
	}


	private void initComponent() {
	    JPanel panGen = new JPanel();
	    //panGen.setBackground(Color.white);
	    panGen.setBorder(BorderFactory.createTitledBorder("Général"));
	    
	    GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
	    JPanel outerPanNom = new JPanel();
	    outerPanNom.setLayout( new GridBagLayout() );
	    name = new JTextField();
	    name.setSize( new Dimension(DialogStudent.LABEL_WIDTH, DialogStudent.LABEL_HEIGHT));
	    name.setText(this.student.getName());
	    JLabel nomLabel = new JLabel("Nom : ");
	    Font font = nomLabel.getFont();
	    Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize()+2);
	    nomLabel.setFont(boldFont);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; 
        outerPanNom.add(nomLabel, c);
        c.gridx = 1; c.gridy = 0; c.gridwidth = 2; 
        outerPanNom.add(name, c);

	    surname = new JTextField();
	    surname.setSize( new Dimension(DialogStudent.LABEL_WIDTH, DialogStudent.LABEL_HEIGHT));
	    surname.setText(this.student.getSurname());
	    JLabel prenomLabel = new JLabel("Prénom : ");
	    prenomLabel.setFont(boldFont);
        c.gridx = 0; c.gridy = 1; c.gridwidth = 1; 
        outerPanNom.add(prenomLabel, c);
        c.gridx = 1; c.gridy = 1; c.gridwidth = 2; 
        outerPanNom.add(surname, c);
        
	    gender = new JComboBox<Gender>();
	    gender.addItem(Gender.BOY);
	    gender.addItem(Gender.GIRL);
	    JLabel genderLabel = new JLabel("Sexe : ");
	    genderLabel.setFont(boldFont);
        c.gridx = 0; c.gridy = 2; c.gridwidth = 1; 
        outerPanNom.add(genderLabel, c);
        c.gridx = 1; c.gridy = 2; c.gridwidth = 2; 
        outerPanNom.add(gender, c);
	    panGen.add(outerPanNom);
	    
	    birth = new JTextField();
	    birth.setPreferredSize( new Dimension(DialogStudent.LABEL_WIDTH, DialogStudent.LABEL_HEIGHT));
	    birth.setText(this.student.getBirthdayAsString());
	    JLabel birthLabel = new JLabel("Date de naissance : ");
	    birthLabel.setFont(boldFont);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 1;
        outerPanNom.add(birthLabel, c);
        c.gridx = 1; c.gridy = 3; c.gridwidth = 2;
        outerPanNom.add(birth, c);
	    
	    email = new JTextField();
	    email.setPreferredSize( new Dimension(DialogStudent.LABEL_WIDTH, DialogStudent.LABEL_HEIGHT));
	    email.setText(this.student.getEmail());
	    JLabel emailLabel = new JLabel("Adresse e-mail : ");
	    emailLabel.setFont(boldFont);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 1;
        outerPanNom.add(emailLabel, c);
        c.gridx = 1; c.gridy = 4; c.gridwidth = 2;
        outerPanNom.add(email, c);
       
        JButton saveButton = new JButton("Enregistrer");
	    saveButton.setPreferredSize( new Dimension(DialogStudent.LABEL_WIDTH / 2, DialogStudent.LABEL_HEIGHT));
        JButton cancelButton = new JButton("Annuler");
	    cancelButton.setPreferredSize( new Dimension(DialogStudent.LABEL_WIDTH / 2, DialogStudent.LABEL_HEIGHT));
        c.gridx = 1; c.gridy = 5; c.gridwidth = 1;
        outerPanNom.add(saveButton, c);
        c.gridx = 2; c.gridy = 5; c.gridwidth = 1;
        outerPanNom.add(cancelButton, c);
        
        panGen.add(outerPanNom);
        
	    this.getContentPane().add(panGen, BorderLayout.CENTER);
	    
	    
	    cancelButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//setVisible(false);
				dispose();
			}
	    });
	    
	    saveButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Student stu = new Student(student.getId(),
										 name.getText(),
										 surname.getText(),
										 (Gender) gender.getSelectedItem(),
										 email.getText(),
										 birth.getText(),
										 student.isRepeating(),
										 cont.getSelectedGroup().getId());

				JOptionPane mess = new JOptionPane();
				if (cont.addOrUpdateStudent(stu)) {
					status = DialogStatus.OBJECT_UPDATED;
					mess.showMessageDialog(null, "L'étudiant a bien été ajouté.", "Succès", JOptionPane.INFORMATION_MESSAGE);
					//System.out.println("In DialogStudent.saveButtonListener --> " + status);
					setVisible(false);
					dispose();
				}
				else {
					status = DialogStatus.ERROR; // the Dialog status is no more "everything is ok" 
					mess.showMessageDialog(null, "L'ajout de l'étudiant a échoué... ", "Erreur", JOptionPane.ERROR_MESSAGE);
					System.err.println("The student " + stu + " couldn't be added....");
				}
			}
	    	
	    });
		
	}  
}

