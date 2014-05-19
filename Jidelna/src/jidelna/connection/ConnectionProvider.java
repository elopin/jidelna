/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jidelna.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elopin
 */
public class ConnectionProvider {
    private static String dbUrl = "jdbc:mysql://project.iivos.cz:9906/iivos3Dalfa?characterEncoding=utf8";
    
    private static Connection connection;
    
    public ConnectionProvider() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl,"janacek", "Lukas.Janacek");
	    
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
        } 
                
    }
    
    public Connection getConnection() {
        return connection;
    }
}
