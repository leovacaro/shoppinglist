package ca.leonardo.shoppinglist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.leonardo.shoppinglist.beans.User;
import ca.leonardo.shoppinglist.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void add(User user) {
		userDao.add(user);
		userDao.addAuthority(user.getUsername());
	} 
}
