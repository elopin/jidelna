/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jidelna.beans;

/**
 *
 * @author elopin
 */
public class UserMenuBean {
    
    private int idUserMenu;
    private UserBean user;
    private DayMenuBean dayMenu;
    
    private int selection;
    
    

    /**
     * @return the idUserMenu
     */
    public int getIdUserMenu() {
        return idUserMenu;
    }

    /**
     * @param idUserMenu the idUserMenu to set
     */
    public void setIdUserMenu(int idUserMenu) {
        this.idUserMenu = idUserMenu;
    }

    /**
     * @return the user
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * @return the dayMenu
     */
    public DayMenuBean getDayMenu() {
        return dayMenu;
    }

    /**
     * @param dayMenu the dayMenu to set
     */
    public void setDayMenu(DayMenuBean dayMenu) {
        this.dayMenu = dayMenu;
    }

    /**
     * @return the selection
     */
    public int getSelection() {
        return selection;
    }

    /**
     * @param selection the selection to set
     */
    public void setSelection(int selection) {
        this.selection = selection;
    }
    
    public void setData(UserMenuBean bean) {
        setIdUserMenu(bean.getIdUserMenu());
        setUser(bean.getUser());
        setDayMenu(bean.getDayMenu());
        setSelection(bean.getSelection());
    }
}
