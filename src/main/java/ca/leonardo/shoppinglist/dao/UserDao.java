package ca.leonardo.shoppinglist.dao;

import ca.leonardo.shoppinglist.beans.User;

public interface UserDao {
	
	public void add(User user);
	
	public void addAuthority(String username);
	
	public User findByUsername(String username);
}
