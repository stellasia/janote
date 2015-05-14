/**
 * 
 */
package com.janote.view.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class defining the welcome tab of the application; it allows to select the database file from an existing file or create a new file. 
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
@SuppressWarnings("serial")
public class WelcomeTab extends JPanel {

	//*****************************************
	public static final String prefNameLastSqlFile = "LAST_SQL_FILE";
	protected String fileName = null; 
	protected String previousFileName = null;
	protected MainWindow parent = null;

	
	//*****************************************
	public WelcomeTab(MainWindow pParent) {

		this.parent = pParent; // used to propagate the action to that parent (especially, unlock the tabs once the data file is selected and valid)

		String userName =  System.getProperty("user.name");
		JLabel userHello = new JLabel("Bonjour " + userName + " ! ", JLabel.CENTER);
		userHello.setFont (userHello.getFont ().deriveFont (64.0f));

		this.setLayout(new GridLayout(3, 1));
		this.add(userHello);


		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);        

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.ipady = 20;   
		gbc.gridx = 0;
		gbc.gridy = 0;
		JButton proceed = new JButton("Fichier courant");
		proceed.setHorizontalTextPosition(JButton.CENTER);
		proceed.setVerticalTextPosition(JButton.BOTTOM);
//		Icon icon_proceed = new ImageIcon("images/old_edit_redo.png");
		Icon icon_proceed = new ImageIcon(getClass().getResource("/old_edit_redo.png")); 
		proceed.setIcon(icon_proceed);
		proceed.addActionListener(new LoadFile());
		panel.add(proceed,gbc);

		Preferences prefs = Preferences.userNodeForPackage(getClass());
		previousFileName = prefs.get(prefNameLastSqlFile, null);
		if (previousFileName == null) {
			proceed.setEnabled(false);
		}
		else 
			proceed.setToolTipText(this.previousFileName);

		
		//		gbc.ipady = 20;   
		gbc.gridx = 1;
		gbc.gridy = 0;
		JButton new_file = new JButton("Nouveau fichier");
		new_file.setHorizontalTextPosition(JButton.CENTER);
		new_file.setVerticalTextPosition(JButton.BOTTOM);
		Icon icon_new_file = new ImageIcon(getClass().getResource("/file_new.png")); 
		new_file.setIcon(icon_new_file);
		new_file.addActionListener(new NewFile());
		panel.add(new_file,gbc);

		//		gbc.fill = GridBagConstraints.HORIZONTAL;
		//		gbc.ipady = 20;   
		gbc.gridx = 2;
		gbc.gridy = 0;
		JButton open_file = new JButton("Ouvrir fichier");
		open_file.setHorizontalTextPosition(JButton.CENTER);
		open_file.setVerticalTextPosition(JButton.BOTTOM);
		Icon icon_open_file = new ImageIcon(getClass().getResource("/folder_green_open.png")); 
		open_file.setIcon(icon_open_file);
		open_file.addActionListener(new OpenFile());
		panel.add(open_file,gbc);

		this.add(panel);

		
		JLabel instructions = new JLabel("Choisissez un fichier pour avoir accès aux autres onglets (voir aide)", JLabel.CENTER);
		instructions.setFont (userHello.getFont ().deriveFont (18.0f));
		this.add(instructions);

	}


	//*****************************************
	public class NewFile implements ActionListener {
		// TODO update the connection when a new file is created (not just at the beginning)
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser choix = new JFileChooser();
			int result=choix.showOpenDialog((Component) e.getSource());
			if (result == JFileChooser.APPROVE_OPTION) {
				String name = choix.getSelectedFile().getAbsolutePath();
				if (name != null) {
					setFileName(name);
				}   
			} else if (result == JFileChooser.CANCEL_OPTION) {
				; // action cancelled
			}
		}
	}

	
	//*****************************************
	public class LoadFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setFileName(previousFileName);
//			unlockParentTabs();
		}
	}


	//*****************************************
	public class OpenFile implements ActionListener {
		// TODO update the connection when a new file is opened (not just at the beginning)
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser choix = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL files", "sql", "sqlite"); // I just want SQL files
			choix.setFileFilter(filter);
			//choix.setCurrentDirectory(new java.io.File("data")); // in the 'data' folder
			int retour=choix.showOpenDialog((Component) e.getSource());
			String selected_file = "test.sql"; // default
			if(retour==JFileChooser.APPROVE_OPTION){
				selected_file = choix.getSelectedFile().getAbsolutePath();
				setFileName(selected_file);
			}
			//			System.out.println(selected_file);
		} 
	}


	//*****************************************
	public String getFileName() {
		return this.fileName;
	}

	
	//*****************************************
	public void setFileName(String name) {
		//System.out.println("WelcomeTab.setFileName");
		this.fileName = name;
		this.parent.setDBName(this.fileName);
		this.unlockParentTabs();
	}

	
	//*****************************************
	public void unlockParentTabs() {
		this.parent.unlockTabs();
	}
}
