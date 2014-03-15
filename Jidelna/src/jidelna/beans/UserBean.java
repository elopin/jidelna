package jidelna.beans;

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
		for (UserBean validUser : users.getUsers()) {
			if (validUser.getEmail().equals(getEmail())) {
				if (validUser.getPassword().equals(getPassword())) {
					setAdmin(validUser.isAdmin());
					return true;
				}
			}
		}
		return false;
	}

}
