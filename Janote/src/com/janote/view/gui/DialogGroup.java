/**
 * 
 */
package com.janote.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.janote.controller.MainController;
import com.janote.model.entities.Group;

@SuppressWarnings("serial")
public class DialogGroup extends JDialog {
	private boolean sendData;
	//private JLabel nomLabel, descLabel;
	private JTextField name;
	private JTextArea desc;

	private MainController cont;
	private Group group;
	
	public DialogGroup(Group g, MainController cont){
		super();
		
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the close cross is clicked, just close the dialog window
		
		this.setModal(true);
		
		if (group == null) {
			this.group = new Group();
			this.setTitle("Enregistrer un nouveau groupe");
		}
		else {
			this.group = g;
			this.setTitle("Informations sur le groupe " + group.getId());
		}
		this.cont = cont;
		
		this.initComponent();
	}

	public boolean showGroupDialog(){
		this.setVisible(true);      
		return this.sendData;      
	}


	private void initComponent(){

		// group name
		JPanel panName = new JPanel();
		//    panNom.setBackground(Color.white);
		panName.setPreferredSize(new Dimension(400, 70));
		name = new JTextField();
		name.setPreferredSize(new Dimension(350, 25));
		panName.setBorder(BorderFactory.createTitledBorder("Nom du nouveau groupe"));
		//    nomLabel = new JLabel("Saisir un nom :");
		//    panNom.add(nomLabel);
		panName.add(name);

		// groups description
		desc = new JTextArea();
		desc.setPreferredSize(new Dimension(350, 200));
		JScrollPane panDesc = new JScrollPane(desc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panDesc.setPreferredSize(new Dimension(400, 400));
		panDesc.setBorder(BorderFactory.createTitledBorder("Description (optionel)"));
		// panDesc.add(desc);


		JPanel content = new JPanel();
		//    content.setBackground(Color.white);
		content.add(panName);
		content.add(panDesc);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");
		okBouton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Group gr = new Group(group.getId(),
									name.getText(),
									desc.getText(),
									null,
									null
									);

				JOptionPane mess = new JOptionPane();
				if (cont.addOrUpdateGroup(gr)) {
					mess.showMessageDialog(null, "Le groupe a bien été ajouté.", "Succès", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				}
				else {
					mess.showMessageDialog(null, "L'ajout du groupe a échoué... ", "Erreur", JOptionPane.ERROR_MESSAGE);
					System.err.println("The group " + gr + " couldn't be added....");
				}
			}
	    	
	    });


		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sendData = false;
				setVisible(false);
			}      
		});

		control.add(okBouton);
		control.add(cancelBouton);

		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}  
	
	
	
}