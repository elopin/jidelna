package jidelna.beans;

/**
 * Bean představující uživatele. 
 * 
 * @author Lukáš Janáček
 */
public class UserBean {

    private int id;
    private String email;
    private String name;
    private String surname;
    private String displayName;
    private String password;
    private double credit;
    
    private boolean admin;
    private boolean isValid;
    private boolean isLoggedIn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        if (displayName == null) {
            StringBuilder sb = new StringBuilder();
            if (name != null) {
                sb.append(name);
                sb.append(" ");
            }
            if (surname != null) {
                sb.append(surname);
            }
            if (name == null && surname == null) {
                sb.append(email);
            }
            displayName = sb.toString();
        }
        return displayName;
    }

    /**
     * Naplní bean daty uživatele.
     * @param user 
     */
    public void setData(UserBean user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setName(user.getName());
        setSurname(user.getSurname());
        setAdmin(user.isAdmin());
        setCredit(user.getCredit());
    }
}
