package jidelna.connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import jidelna.beans.DayMenuBean;
import jidelna.beans.UserBean;
import jidelna.beans.UserMenuBean;
import jidelna.security.SecurityService;

/**
 * Třída představující implemntaci rozhraní DataRepository. Třída obsahuje 
 * implementaci metod pro získání dat z databáze.
 * 
 * @author Lukáš Janáček
 */
public class DataRepositoryImpl implements DataRepository {

    private ConnectionProvider connection;

    private PreparedStatement addUser;
    private PreparedStatement deleteUser;
    private PreparedStatement updateUser;
    private PreparedStatement getPasswordHash;
    private PreparedStatement updateUserCredit;
    private PreparedStatement addMenuDay;
    private PreparedStatement deleteDailyMenu;
    private PreparedStatement getUserMenuByDayMenu;
    private PreparedStatement addUserMenu;
    private PreparedStatement deleteUserMenu;
    private PreparedStatement deleteUserMenuByUserId;
    private PreparedStatement getUserMenusByUser;
    private PreparedStatement getDayMenuById;
    private Statement statement;

    public DataRepositoryImpl() {
        try {
            this.connection = new ConnectionProvider();
	    
	    
            statement = connection.getConnection().createStatement();
            addUser = connection.getConnection().prepareStatement("INSERT INTO janacek_User(email, name, surname, admin, password) VALUES(?, ?, ?, ?, ?)");
            updateUser = connection.getConnection().prepareStatement("UPDATE janacek_User SET email = ?, name = ?, surname = ?, admin = ?, password = ? WHERE id = ?");
            deleteUser = connection.getConnection().prepareStatement("DELETE FROM janacek_User WHERE id = ?");
            getPasswordHash = connection.getConnection().prepareStatement("SELECT password FROM janacek_User WHERE email = ?");
            updateUserCredit = connection.getConnection().prepareStatement("UPDATE janacek_User SET credit = ? WHERE id = ?");
            addMenuDay = connection.getConnection().prepareStatement("INSERT INTO janacek_Day_Menu(date, menu1, price1, menu2, price2) VALUES(?, ?, ?, ?, ?)");
            deleteDailyMenu = connection.getConnection().prepareStatement("DELETE FROM janacek_Day_Menu WHERE id = ?");
            getUserMenuByDayMenu = connection.getConnection().prepareStatement("SELECT id, id_user, id_day_menu, selection FROM janacek_User_Menu WHERE id_user = ? AND id_day_menu = ?");
            addUserMenu = connection.getConnection().prepareStatement("INSERT INTO janacek_User_Menu(id_user, id_day_menu, selection) VALUES(?, ?, ?)");
            deleteUserMenu = connection.getConnection().prepareStatement("DELETE FROM janacek_User_Menu WHERE id = ?");
            deleteUserMenuByUserId = connection.getConnection().prepareStatement("DELETE FROM janacek_User_Menu WHERE id_user = ?");
            getUserMenusByUser = connection.getConnection().prepareStatement("SELECT id, id_user, id_day_menu, selection FROM janacek_User_Menu WHERE id_user = ?");
            getDayMenuById = connection.getConnection().prepareStatement("SELECT id, date, menu1, price1, menu2, price2 FROM janacek_Day_Menu WHERE id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Přidá uživatele do databáze.
     * @param user 
     */
    @Override
    public void addUser(UserBean user) {
        try {
            if(user.getId() > 0) {
                UserBean userBean = getUserById(user.getId());
                if(userBean != null) {
                    updateUser(user);
                } 
            } else {
            
            SecurityService security = new SecurityService();
            addUser.setString(1, user.getEmail());
            addUser.setString(2, user.getName());
            addUser.setString(3, user.getSurname());
            addUser.setBoolean(4, user.isAdmin());
            addUser.setBytes(5, security.getEncryptedPassword(user.getPassword()));
            addUser.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Odstraní uživatele z databáze.
     * @param user 
     */
    @Override
    public void deleteUser(UserBean user) {
        if(user.getId() > 0) {
            UserBean userBean = getUserById(user.getId());
            if(userBean != null) {
                try {
                    connection.getConnection().setAutoCommit(false);
                    
                    deleteUserMenuByUserId.setInt(1, userBean.getId());
                    deleteUserMenuByUserId.executeUpdate();
                    deleteUser.setInt(1, userBean.getId());
                    deleteUser.executeUpdate();
                    
                    connection.getConnection().commit();
                    connection.getConnection().setAutoCommit(true);
                } catch (SQLException ex) {
                    if(connection.getConnection() != null) {
                        try {
                            connection.getConnection().rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }
    
    /**
     * Aktualizuje data uživatele.
     * @param user 
     */
    @Override
    public void updateUser(UserBean user) {
        try {
            UserBean oldUser = getUserById(user.getId());
            
            updateUser.setString(1, user.getEmail());
            updateUser.setString(2, user.getName());
            updateUser.setString(3, user.getSurname());
            updateUser.setBoolean(4, user.isAdmin());
            
            if(user.getPassword() != null) {
                SecurityService security = new SecurityService();
                updateUser.setBytes(5, security.getEncryptedPassword(user.getPassword()));
            } else {
                updateUser.setBytes(5, getPasswordHashByEmail(oldUser.getEmail()));
            }
            
            updateUser.setInt(6, user.getId());
            updateUser.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Vrací hash hesla uživatele pro autentizaci.
     * @param email
     * @return 
     */
    @Override
    public byte[] getPasswordHashByEmail(String email) {

        byte[] hash = null;
        try {
            getPasswordHash.setString(1, email);
            ResultSet result = getPasswordHash.executeQuery();
            if(result.next()) {
                 hash = result.getBytes("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }

    /**
     * Vrací všechny uživatele z databáze.
     * @return 
     */
    @Override
    public List<UserBean> getUsers() {
        ResultSet result;
        List<UserBean> users = null;
        try {

            result = statement.executeQuery("SELECT id, email, name, surname, admin, credit FROM janacek_User");

            if (result != null) {

                users = new ArrayList<UserBean>();
                while (result.next()) {

                    UserBean userBean = new UserBean();
                    userBean.setId(result.getInt("id"));
                    userBean.setEmail(result.getString("email"));
                    userBean.setName(result.getString("name"));
                    userBean.setSurname(result.getString("surname"));
                    userBean.setAdmin(result.getBoolean("admin"));
                    userBean.setCredit(result.getFloat("credit"));
                    userBean.setValid(true);
                    users.add(userBean);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    /**
     * Aktualizuje kredit uživatele.
     * @param user
     * @param addition
     * @return 
     */
    @Override
    public UserBean updateUserCredit(UserBean user, double addition) {
        try {
            updateUserCredit.setDouble(1, user.getCredit() + (addition)); 
            updateUserCredit.setInt(2, user.getId());
            updateUserCredit.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserBean userBean = getUserById(user.getId());
        return userBean;
    }

    /**
     * Vrací uživatele podle e-mailu.
     * @param email
     * @return 
     */
    @Override
    public UserBean getUserByEmail(String email) {
        UserBean user = null;
        List<UserBean> users = getUsers();
        if (users != null) {
            for (UserBean u : users) {

                if (u.getEmail().equals(email)) {
                    user = u;
                }
            }
        }
        return user;
    }

    /**
     * Vrací uživatele podle jeho id.
     * @param id
     * @return 
     */
    @Override
    public UserBean getUserById(int id) {
        UserBean user = null;
        List<UserBean> users = getUsers();
        if (users != null) {
            for (UserBean u : users) {
                if (u.getId() == id) {
                    user = u;
                }
            }
        }
        return user;
    }

    /**
     * Vrací všechny denní menu.
     * @return 
     */
    @Override
    public List<DayMenuBean> getMenuDays() {
        List<DayMenuBean> menus = null;
        ResultSet result;

        try {
            result = statement.executeQuery("SELECT id, date, menu1, price1, menu2, price2 FROM janacek_Day_Menu");

            if (result != null) {
                menus = new ArrayList<DayMenuBean>();
                while (result.next()) {

                    DayMenuBean menuBean = new DayMenuBean();
                    menuBean.setId(result.getInt("id"));

                    Date date = result.getDate("date");
                    Calendar calendar = Calendar.getInstance(new Locale("cs", "CZ"));
                    calendar.setTime(date);

                    menuBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                    menuBean.setMonth(calendar.get(Calendar.MONTH));
                    menuBean.setYear(calendar.get(Calendar.YEAR));

                    menuBean.setMenu1(result.getString("menu1"));
                    menuBean.setPrice1(result.getInt("price1"));
                    menuBean.setMenu2(result.getString("menu2"));
                    menuBean.setPrice2(result.getInt("price2"));

                    menus.add(menuBean);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return menus;
    }

    /**
     * Vrací denní menu podle dne.
     * @param day
     * @param month
     * @param year
     * @return 
     */
    @Override
    public DayMenuBean getDailyMenu(int day, int month, int year) {
        DayMenuBean dailyMenu = null;
        List<DayMenuBean> menus = getMenuDays();
        if (menus != null) {
            for (DayMenuBean d : menus) {
                if (d.getDay() == day && d.getMonth() == month && d.getYear() == year) {
                    dailyMenu = d;
                }
            }
        }
        return dailyMenu;
    }

    /**
     * Přidá denní menu.
     * @param menuDay 
     */
    @Override
    public void addMenuDay(DayMenuBean menuDay) {
        DayMenuBean dayMenu = getDailyMenu(menuDay.getDay(), menuDay.getMonth(), menuDay.getYear());
        
        try {
            
            if(dayMenu != null) {
                deleteDailyMenu.setInt(1, dayMenu.getId());
                deleteDailyMenu.executeUpdate();
            }
            
            Calendar calendar = Calendar.getInstance(new Locale("cs", "CZ"));
            calendar.set(Calendar.DAY_OF_MONTH, menuDay.getDay());
            calendar.set(Calendar.MONTH, menuDay.getMonth());
            calendar.set(Calendar.YEAR, menuDay.getYear());
            
            addMenuDay.setDate(1, new Date(calendar.getTime().getTime()));
            addMenuDay.setString(2, menuDay.getMenu1());
            addMenuDay.setInt(3, menuDay.getPrice1());
            addMenuDay.setString(4, menuDay.getMenu2());
            addMenuDay.setInt(5, menuDay.getPrice2());

            addMenuDay.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Vrací denní menu uživatele.
     * @param user
     * @param dayMenu
     * @return 
     */
    @Override
    public UserMenuBean getUserMenu(UserBean user, DayMenuBean dayMenu) {
        UserMenuBean userMenu = null;
        ResultSet result = null;
        
        try {
            getUserMenuByDayMenu.setInt(1, user.getId());
            getUserMenuByDayMenu.setInt(2, dayMenu.getId());
            result = getUserMenuByDayMenu.executeQuery();
            
            if(result.next()) {
                userMenu = new UserMenuBean();
                userMenu.setIdUserMenu(result.getInt("id"));
                userMenu.setUser(user);
                userMenu.setDayMenu(dayMenu);
                userMenu.setSelection(result.getInt("selection"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userMenu;
    }

    /**
     * Přidá denní menu uživatele.
     * @param userMenu 
     */
    @Override
    public void addUserMenu(UserMenuBean userMenu) {
        UserMenuBean userMenuBean = getUserMenu(userMenu.getUser(), userMenu.getDayMenu());
        try {
            if(userMenuBean != null) {
                
                double price = getPriceFromSelection(userMenuBean);
                updateUserCredit(userMenuBean.getUser(), price);
                deleteUserMenu.setInt(1, userMenuBean.getIdUserMenu());
                deleteUserMenu.executeUpdate();
                userMenu.getUser().setData(getUserById(userMenu.getUser().getId()));
            }
        
            if(userMenu.getSelection() != 0) {
                
                addUserMenu.setInt(1, userMenu.getUser().getId());
                addUserMenu.setInt(2, userMenu.getDayMenu().getId());
                addUserMenu.setInt(3, userMenu.getSelection());
                addUserMenu.executeUpdate();
                double price = getPriceFromSelection(userMenu);
                updateUserCredit(userMenu.getUser(), -price);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Vrací cenu vybraného oběda uživatele.
     * @param userMenuBean
     * @return 
     */
    private double getPriceFromSelection(UserMenuBean userMenuBean) {
        double price = 0;
        if(userMenuBean.getSelection() == 1) {
            price = userMenuBean.getDayMenu().getPrice1();
        } else if(userMenuBean.getSelection() == 2) {
            price = userMenuBean.getDayMenu().getPrice2();
        }
        return price;
    }

    /**
     * Vrací všechna denní menu uživatele.
     * @param user
     * @return 
     */
    @Override
    public List<UserMenuBean> getUserMenusByUser(UserBean user) {
        List<UserMenuBean> userMenus = null; 
        
        if(user != null) {
            if(user.getId() > 0) {
                try {
                    getUserMenusByUser.setInt(1, user.getId());
                    ResultSet result = getUserMenusByUser.executeQuery();
                    while(result.next()) {
                     
                        DayMenuBean dayMenu = getDayMenuById(result.getInt("id_day_menu"));
                        if(dayMenu != null) {
                            UserMenuBean menuBean = new UserMenuBean();
                            menuBean.setDayMenu(dayMenu);
                            menuBean.setIdUserMenu(result.getInt("id"));
                            menuBean.setUser(user);
                            menuBean.setSelection(result.getInt("selection"));
                            
                            if(userMenus == null) {
                                userMenus = new ArrayList<UserMenuBean>();
                            }
                            
                            userMenus.add(menuBean);
                        }
                    }   
                } catch (SQLException ex) {
                    Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return userMenus;
    }
    
    /**
     * Vrací denní menu podle id.
     * @param idDayMenu
     * @return 
     */
    private DayMenuBean getDayMenuById(int idDayMenu) {
        DayMenuBean menuBean = null;
        if(idDayMenu > 0) {
            try {
                getDayMenuById.setInt(1, idDayMenu);
                ResultSet result = getDayMenuById.executeQuery();
                if(result.next()) {
                    menuBean = new DayMenuBean();
                    menuBean.setId(result.getInt("id"));

                    Date date = result.getDate("date");
                    Calendar calendar = Calendar.getInstance(new Locale("cs", "CZ"));
                    calendar.setTime(date);

                    menuBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                    menuBean.setMonth(calendar.get(Calendar.MONTH));
                    menuBean.setYear(calendar.get(Calendar.YEAR));

                    menuBean.setMenu1(result.getString("menu1"));
                    menuBean.setPrice1(result.getInt("price1"));
                    menuBean.setMenu2(result.getString("menu2"));
                    menuBean.setPrice2(result.getInt("price2"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return menuBean;
    }
    
    /**
     * Zkontroluje přítomnost tabulek a účtu administrátora v databázi.
     * Pokud je potřeba zavolá metody pro obnovení tabulek.
     */
    @Override
    public void checkDatabase() {
	try {
	    if(connection.getConnection().getMetaData().getTables(null, null, "janacek_User", null).next() == false ||
	       connection.getConnection().getMetaData().getTables(null, null, "janacek_Day_Menu", null).next() == false || 
	       connection.getConnection().getMetaData().getTables(null, null, "janacek_User_Menu", null).next() == false) {
	        new TableCreator().createTables();
	    } 
	    if(connection.getConnection().createStatement().executeQuery("SELECT name FROM janacek_User WHERE name = 'admin'").next() == false) {
		new TableCreator().addAdminUser();
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DataRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
	}    
    }
}
