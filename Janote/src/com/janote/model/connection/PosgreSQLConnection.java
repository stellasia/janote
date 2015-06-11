package com.janote.model.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * PosgreSQLConnection class
 * Test the connection and reading the data of a local postgresql database.
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class PosgreSQLConnection {
	/**
	 * PosgreSQLConnection default constructor
	 * Initialize the connection and the statements.
	 * 
	 */
	public PosgreSQLConnection() {      
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver O.K.");

			String url = "jdbc:postgresql://localhost:5432/Janote";
			String user = "postgres";
			String passwd = "watowace";

			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");         
			
			//Création d'un objet Statement
//			Statement state = conn.createStatement();
			PreparedStatement state = conn.prepareStatement("SELECT * FROM notes");
			ResultSet result = state.executeQuery();
			//L'objet ResultSet contient le résultat de la requête SQL
//			ResultSet result = state.executeQuery("SELECT * FROM Students");
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			
			System.out.println("\n**********************************");
			//On affiche le nom des colonnes
			for(int i = 1; i <= resultMeta.getColumnCount(); i++)
				System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
			
			System.out.println("\n**********************************");
		         
			while(result.next()){         
				for(int i = 1; i <= resultMeta.getColumnCount(); i++)
					System.out.print("\t" + result.getObject(i).toString() + "\t |");
		            
				System.out.println("\n---------------------------------");
				
			}	
			
			result.close();
			state.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}   	   
	}
}
