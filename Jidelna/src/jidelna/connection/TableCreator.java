/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jidelna.connection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jidelna.beans.UserBean;

/**
 *
 * @author elopin
 */
public class TableCreator {

    
    private final DataRepository repository;
    private final UserBean admin;
    
    private final String USERS_TABLE = "CREATE TABLE janacek_User (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, email VARCHAR(30) NOT NULL UNIQUE, name VARCHAR(30) , surname VARCHAR(30), admin BOOLEAN NOT NULL DEFAULT 0, password BLOB NOT NULL, credit DOUBLE NOT NULL DEFAULT 0) CHARACTER SET utf8";
    private final String DAY_MENU_TABLE = "CREATE TABLE janacek_Day_Menu (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, date DATE NOT NULL UNIQUE, menu1 VARCHAR(30) , price1 INT, menu2 VARCHAR(30), price2 INT ) CHARACTER SET utf8";
    private final String USER_MENU = "CREATE TABLE janacek_User_Menu (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, id_day_menu INT NOT NULL, id_user INT NOT NULL, selection INT NOT NULL DEFAULT 0 ) CHARACTER SET utf8";
    
    
    public TableCreator() {
        repository = new DataRepositoryImpl();
        admin = new UserBean();
        admin.setEmail("admin");
        admin.setName("admin");
        admin.setSurname("admin");
        admin.setPassword("administrator");
        admin.setAdmin(true);
    }

    public void createTables() {
	
        try {
	    repository.getConnection().setAutoCommit(false);
            Statement tableStatement = repository.getConnection().createStatement();
	    
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
	    
	    repository.getConnection().commit();
	    tableStatement.close();
	    repository.getConnection().close();

        } catch (SQLException ex) {
            Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    public void addAdminUser() {
	repository.addUser(admin);
    }

    private boolean checkTable(String tableName) {
	try {
	    return repository.getConnection().getMetaData().getTables(null, null, tableName, null).next();
	} catch (SQLException ex) {
	    Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }

}
