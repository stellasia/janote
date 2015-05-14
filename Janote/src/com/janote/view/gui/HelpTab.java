package com.janote.view.gui;


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * A complete Java class that demonstrates how to create an HTML viewer with styles,
 * using the JEditorPane, HTMLEditorKit, StyleSheet, and JFrame.
 * 
 * @author alvin alexander, devdaily.com.
 *
 */

@SuppressWarnings("serial")
public class HelpTab extends JPanel
{

	public HelpTab() {

		// add an html editor kit
		HTMLEditorKit kit = new HTMLEditorKit();
		//jEditorPane.setEditorKit(kit);

		// add some styles to the html
		StyleSheet styleSheet = kit.getStyleSheet();
		// styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
		String pathname = "data/help.css"; 
		BufferedReader reader;
		StringBuilder feuilleCSS = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(pathname));
			String tmp;
			while((tmp=reader.readLine())!=null){
				feuilleCSS.append(tmp);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		styleSheet.addRule(feuilleCSS.toString());

		// create some html as a string
		String htmlString = "";
		try {
			File file = new File(getClass().getResource("/help.html").getFile());
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int)file.length()];
			fis.read(data);
			fis.close();
			htmlString = new String(data, "UTF-8");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("I could not find the file to be displayed in the help tab "+e.getMessage());
		}	

		JEditorPane jEditorPane = new JEditorPane("text/html", htmlString);
		jEditorPane.setEditable(false);
		//jEditorPane.setOpaque(false);
		jEditorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == EventType.ACTIVATED) {
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (URISyntaxException ex) {
					} catch (IOException ex) {
					}
				}
			}        	
		});

		JScrollPane scrollPane = new JScrollPane(jEditorPane);

		this.setLayout(new BorderLayout()); // the use of layout is necessary for the scroll pane size to be well fitted to the window
		this.add(scrollPane, BorderLayout.CENTER);

	}
}
