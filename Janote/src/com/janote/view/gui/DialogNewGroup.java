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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DialogNewGroup extends JDialog {
	private boolean sendData;
	//private JLabel nomLabel, descLabel;
	private JTextField name;
	private JTextArea desc;

	public DialogNewGroup(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when the close cross is clicked, just close the dialog window
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
		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {        
				sendData = true;
				setVisible(false);
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
	
	
	public String getName() {
		return name.getText();
	}
	
	
	public String getDescription() {
		return desc.getText();
	}
	
}