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
        admin.setEmail("elopin@seznam.cz");
        admin.setName("admin");
        admin.setSurname("admin");
        admin.setPassword("admin");
        admin.setAdmin(true);
    }

    public void createTables() {
        try {
            Statement tableStatement = repository.getConnection().createStatement();
            tableStatement.executeUpdate(USERS_TABLE);
            repository.addUser(admin);
            tableStatement.executeUpdate(DAY_MENU_TABLE);
            tableStatement.executeUpdate(USER_MENU);

        } catch (SQLException ex) {
            Logger.getLogger(TableCreator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        new TableCreator().createTables();
    }

}
