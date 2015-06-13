/**
 * 
 */
package com.janote.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.sqlite.SQLiteConfig;

/**
 * @author Estelle Scifo
 * @version 1.0
 */
public class SQLiteConnection {

    private static Connection connect = null;
    private static String path_to_db = null;

    /**
     * Connection to an SQLite object using the singleton design pattern
     * 
     * @param DBPath
     *            (String) the path to the current database file on the disk (if
     *            DBPath="", changed to default value "data/test.sql")
     * @return the connection
     */
    public static Connection getInstance(String DBPath) {
        // System.out.println("SQLiteConnection.getInstance");

        /*
         * if (DBPath != path_to_db) { connect = null; }
         */

        path_to_db = DBPath;

        if (connect == null) { // instantiate the class if and only if it was
                               // not already done
            // System.out.println("SQLiteConnection.getInstance null connection");
            try {
                Class.forName("org.sqlite.JDBC").newInstance();
            }
            catch (Exception e) {
                System.err.println("Error driver: " + e.getMessage());
            }

            try {
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);
                connect = DriverManager.getConnection("jdbc:sqlite:"
                        + path_to_db, config.toProperties());
            }
            catch (Exception ez) {
                System.err.println("Erreur de connexion " + ez.getMessage());
            }
        }
        return connect;
    }
}