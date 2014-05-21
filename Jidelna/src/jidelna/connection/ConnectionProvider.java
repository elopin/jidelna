package jidelna.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Třída pro vytvoření spojení s databází.
 * 
 * @author Lukáš Janáček
 */
public class ConnectionProvider {
    private final String dbUrl = "jdbc:mysql://project.iivos.cz:9906/iivos3Dalfa?characterEncoding=utf8";
    
    private Connection connection;
    
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
    
    /**
     * Vrací spojení do databáze.
     * @return 
     */
    public Connection getConnection() {
        return connection;
    }
}
