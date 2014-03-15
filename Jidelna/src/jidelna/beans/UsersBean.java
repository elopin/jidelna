package jidelna.beans;

import java.util.ArrayList;
import java.util.List;

public class UsersBean {
	
	private List<UserBean> users = new ArrayList<UserBean>();

	public List<UserBean> getUsers() {
		if(users.isEmpty()) {
			UserBean elopin = new UserBean();
			elopin.setEmail("elopin@seznam.cz");
			elopin.setPassword("jidelna");
			elopin.setAdmin(true);
			users.add(elopin);
		}
		return users;
	}

	public void setUsers(List<UserBean> users) {
		this.users = users;
	}

}
