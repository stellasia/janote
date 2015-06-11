/**
 * 
 */
package com.janote.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Estelle Scifo
 * @version 1.0
 */
public class MySQLConnection {
	
	private static Connection connect;

	public static Connection getInstance() {
		if(connect == null){ // instantiate the class if and only if it was not already done
			try { /*chargement du driver*/
				Class.forName("com.mysql.jdbc.Driver").newInstance ( ) ;
				}
				catch (Exception e){System.out .println("Erreur driver: "+e.getMessage ( ) ) ;}

				/**Connexion à la base*/
				try {connect = DriverManager.getConnection ("jdbc:mysql://localhost/Janote","root","") ;
				}
				catch (Exception ez ){System.out.println("Erreur de connexion "+ ez.getMessage ( ));}
		}      
		return connect;
	}   
}
