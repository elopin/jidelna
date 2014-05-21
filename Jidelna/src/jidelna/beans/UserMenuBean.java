package jidelna.beans;

/**
 * Bean představující vybrané denní menu uživatele.
 * 
 * @author Lukáš Janáček
 */
public class UserMenuBean {
    
    private int idUserMenu;
    private UserBean user;
    private DayMenuBean dayMenu;
    
    private int selection;
    
    public int getIdUserMenu() {
        return idUserMenu;
    }

    public void setIdUserMenu(int idUserMenu) {
        this.idUserMenu = idUserMenu;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public DayMenuBean getDayMenu() {
        return dayMenu;
    }

    public void setDayMenu(DayMenuBean dayMenu) {
        this.dayMenu = dayMenu;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
    
    /**
     * Naplní bean daty.
     * @param bean 
     */
    public void setData(UserMenuBean bean) {
        setIdUserMenu(bean.getIdUserMenu());
        setUser(bean.getUser());
        setDayMenu(bean.getDayMenu());
        setSelection(bean.getSelection());
    }
}
