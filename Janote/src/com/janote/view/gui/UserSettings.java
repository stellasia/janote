package com.janote.view.gui;

import java.util.prefs.Preferences;

public class UserSettings {

	protected Preferences settings;
	protected String[] prefIds; //= "LAST_SQL_FILE";
	protected static int NB = 1;
	
	public UserSettings() {
		settings = Preferences.userRoot();
//		prefIds = new Array();
//		prefIds[0] = "LAST_SQL_FILE";
		
	}
	
	public void setPreference(String id, Object value) {
		//if (id )
	}
	
	public Object getPreference(String id) {
		return "";
	}
}
