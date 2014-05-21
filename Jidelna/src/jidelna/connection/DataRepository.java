package jidelna.connection;

import java.sql.Connection;
import java.util.List;
import jidelna.beans.DayMenuBean;
import jidelna.beans.UserBean;
import jidelna.beans.UserMenuBean;

/**
 * Rozhraní pro získání dat z databáze.
 * 
 * @author Lukáš Janáček
 */
public interface DataRepository {
    
    public void addUser(UserBean user);
    
    public void updateUser(UserBean user);

    public byte[] getPasswordHashByEmail(String email);
    
    public List<UserBean> getUsers();
    
    public UserBean updateUserCredit(UserBean user, double addition);
    
    public UserBean getUserByEmail(String email);
    
    public UserBean getUserById(int id);

    public List<DayMenuBean> getMenuDays();
    
    public DayMenuBean getDailyMenu(int day, int month, int year);
    
    public void addMenuDay(DayMenuBean menuDay);
    
    public UserMenuBean getUserMenu(UserBean user, DayMenuBean dayMenu);
    
    public void addUserMenu(UserMenuBean userMenu);
    
    public void deleteUser(UserBean user);

    public List<UserMenuBean> getUserMenusByUser(UserBean user);
    
    public void checkDatabase();
}
