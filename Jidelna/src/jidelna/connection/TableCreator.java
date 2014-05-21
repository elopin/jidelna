package jidelna.connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jidelna.security.SecurityService;

/**
 * Třída pro kontrolu tabulek v databázi. Pokud je potřeba, vytvoří tabulky
 * a přidá administrátora.
 * 
 * @author Lukáš Janáček
 */
public class TableCreator {

    private ConnectionProvider connection;
    private PreparedStatement addAdmin;
    
    private final String USERS_TABLE = "CREATE TABLE janacek_User (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, email VARCHAR(30) NOT NULL UNIQUE, name VARCHAR(30) , surname VARCHAR(30), admin BOOLEAN NOT NULL DEFAULT 0, password BLOB NOT NULL, credit DOUBLE NOT NULL DEFAULT 0) CHARACTER SET utf8";
    private final String DAY_MENU_TABLE = "CREATE TABLE janacek_Day_Menu (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, date DATE NOT NULL UNIQUE, menu1 VARCHAR(30) , price1 INT, menu2 VARCHAR(30), price2 INT ) CHARACTER SET utf8";
    private final String USER_MENU = "CREATE TABLE janacek_User_Menu (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, id_day_menu INT NOT NULL, id_user INT NOT NULL, selection INT NOT NULL DEFAULT 0 ) CHARACTER SET utf8";
    
    
    public TableCreator() {
        connection = new ConnectionProvider();
	try {
	    addAdmin = connection.getConnection().prepareStatement("INSERT INTO janacek_User(email, name, surname, admin, password) VALUES(?, ?, ?, ?, ?)");
	} catch (SQLException ex) {
	    Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void createTables() {
        try {
	    connection.getConnection().setAutoCommit(false);
            Statement tableStatement = connection.getConnection().createStatement();
	    
	    if(checkTable("janacek_User")) {
	        tableStatement.executeUpdate("DROP TABLE janacek_User");
	    }
	    if(checkTable("janacek_Day_Menu")) {
	        tableStatement.executeUpdate("DROP TABLE janacek_Day_Menu");
	    }
	    if(checkTable("janacek_User_Menu")) {
	        tableStatement.executeUpdate("DROP TABLE janacek_User_Menu");
	    }
	    
            tableStatement.executeUpdate(USERS_TABLE);
            addAdminUser();
            tableStatement.executeUpdate(DAY_MENU_TABLE);
            tableStatement.executeUpdate(USER_MENU);
	    
	    connection.getConnection().commit();
	    tableStatement.close();
	    connection.getConnection().close();

        } catch (SQLException ex) {
            Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void addAdminUser() {
	try {
	    SecurityService security = new SecurityService();
	    addAdmin.setString(1, "admin");
	    addAdmin.setString(2, "admin");
	    addAdmin.setString(3, "admin");
	    addAdmin.setBoolean(4, true);
	    addAdmin.setBytes(5, security.getEncryptedPassword("administrator"));
	    addAdmin.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private boolean checkTable(String tableName) {
	try {
	    return connection.getConnection().getMetaData().getTables(null, null, tableName, null).next();
	} catch (SQLException ex) {
	    Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }
}
