package jidelna.beans;

import java.util.Map.Entry;

public class UserBean {
	
	private String email;
	private String password;
	private double credit;
	private boolean isValid;
	private boolean isLoggedIn;
	private boolean admin;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public boolean checkCredentials(UsersBean users) {
		for (Entry<String, UserBean> validUser : users.getUsers().entrySet()) {
			if (validUser.getKey().equals(getEmail())) {
				if (validUser.getValue().getPassword().equals(getPassword())) {
					setAdmin(validUser.getValue().isAdmin());
					return true;
				}
			}
		}
		return false;
	}

}
