package ca.leonardo.shoppinglist.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.leonardo.shoppinglist.beans.User;
import ca.leonardo.shoppinglist.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void add(User user) {
		String insertUser = "INSERT INTO users (username, name, email, password) "
				+ " VALUES (:username, :name, :email, :password)";
		MapSqlParameterSource paramsUser = new MapSqlParameterSource();
		paramsUser.addValue("username", user.getUsername())
		.addValue("name", user.getName()).addValue("email", user.getEmail())
		.addValue("password", user.getPassword());
		jdbc.update(insertUser, paramsUser);
	}
	
	@Override
	public User findByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String userByUsername = "SELECT * FROM users WHERE username = :username";
		params.addValue("username", username);
		/*The class BeanPropertyRowMapper maps rows from the user table to the
		  java user class*/
		return jdbc.query(userByUsername, params, 
				new BeanPropertyRowMapper<User>(User.class)).get(0);
	}
	
	@Override
	public void addAuthority(String username) {
		String insertAuth = "INSERT INTO authorities (username, authority) "
				+ " VALUES (:username, :authority)";
		MapSqlParameterSource paramsAuth = new MapSqlParameterSource();
		paramsAuth.addValue("username", username)
		.addValue("authority", "ROLE_USER");
		jdbc.update(insertAuth, paramsAuth);
	}

}
