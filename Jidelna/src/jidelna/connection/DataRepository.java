/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jidelna.connection;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import jidelna.beans.DayMenuBean;
import jidelna.beans.UserBean;
import jidelna.beans.UserMenuBean;

/**
 *
 * @author elopin
 */
public interface DataRepository {
    
    public void addUser(UserBean user);

    public byte[] getPasswordHashByEmail(String email);
    
    public List<UserBean> getUsers();
    
    public UserBean updateUserCredit(UserBean user, double addition);
    
    public Connection getConnection();
    
    public UserBean getUserByEmail(String email);
    
    public UserBean getUserById(int id);

    public List<DayMenuBean> getMenuDays();
    
    public DayMenuBean getDailyMenu(int day, int month, int year);
    
    public void addMenuDay(DayMenuBean menuDay);
    
    public UserMenuBean getUserMenu(UserBean user, DayMenuBean dayMenu);
    
    public void addUserMenu(UserMenuBean userMenu);
}
