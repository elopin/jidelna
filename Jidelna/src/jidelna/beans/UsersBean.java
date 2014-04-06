package jidelna.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersBean {
	
	private Map<String, UserBean> users = new HashMap<String, UserBean>();

	public Map<String, UserBean> getUsers() {
		if(users.isEmpty()) {
			UserBean elopin = new UserBean();
			elopin.setEmail("elopin@seznam.cz");
			elopin.setPassword("jidelna");
			elopin.setAdmin(true);
			users.put(elopin.getEmail(), elopin);
		}
		return users;
	}

	public void setUsers(Map<String, UserBean> users) {
		this.users = users;
	}

}
