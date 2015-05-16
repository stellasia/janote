package com.janote.view.gui;


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

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
		InputStream path = this.getClass().getClassLoader().getResourceAsStream("help.css");
		String text = new Scanner(path, "UTF-8").useDelimiter("\\A").next();
		styleSheet.addRule(text);

		// create some html as a string
		String htmlString = "";
		path = this.getClass().getClassLoader().getResourceAsStream("help.html");
		htmlString = new Scanner(path, "UTF-8").useDelimiter("\\A").next();
		
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
