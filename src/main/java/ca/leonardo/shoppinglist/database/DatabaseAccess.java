package ca.leonardo.shoppinglist.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.leonardo.shoppinglist.beans.ShoppingItem;
import ca.leonardo.shoppinglist.beans.ShoppingList;
import ca.leonardo.shoppinglist.beans.User;

@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void addUser(User user) {
		String insertUser = "INSERT INTO users (username, name, email, password) "
				+ " VALUES (:username, :name, :email, :password)";
		MapSqlParameterSource paramsUser = new MapSqlParameterSource();
		paramsUser.addValue("username", user.getUsername())
		.addValue("name", user.getName()).addValue("email", user.getEmail())
		.addValue("password", user.getPassword());
		jdbc.update(insertUser, paramsUser);
	}
	
	public void addAuthority(String username) {
		String insertAuth = "INSERT INTO authorities (username, authority) "
				+ " VALUES (:username, :authority)";
		MapSqlParameterSource paramsAuth = new MapSqlParameterSource();
		paramsAuth.addValue("username", username)
		.addValue("authority", "ROLE_USER");
		jdbc.update(insertAuth, paramsAuth);
	}
	
	public User findUserByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String userByUsername = "SELECT * FROM users WHERE username = :username";
		params.addValue("username", username);
		/*The class BeanPropertyRowMapper maps rows from the user table to the
		  java user class*/
		return jdbc.query(userByUsername, params, 
				new BeanPropertyRowMapper<User>(User.class)).get(0);
	}
	
	public List<ShoppingList> findListsByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String listByUserUsername = "SELECT list.id, list.name FROM users INNER JOIN " 
				+ "userlist ON users.username = userlist.username "
				+ "INNER JOIN list ON list.id = userlist.listId "
				+ "WHERE users.username = :username";
		params.addValue("username", username);
		return jdbc.query(listByUserUsername, params, 
				new BeanPropertyRowMapper<ShoppingList>(ShoppingList.class));
	}
	
	public ShoppingList findListById(int listId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String listById = "SELECT * FROM list WHERE id = :listId";
		params.addValue("listId", listId);
		List<ShoppingList> results = jdbc.query(listById, params, 
				new BeanPropertyRowMapper<ShoppingList>(ShoppingList.class));
		return results.get(0);
	}
	
	public void addList(ShoppingList list, String username) {
		//Inserting new list
		String insertList = "INSERT INTO list (name)" 
				+ " VALUES (:name);";
		MapSqlParameterSource paramsList = new MapSqlParameterSource();
		paramsList.addValue("name", list.getName());
		jdbc.update(insertList, paramsList);
		
		//Read id from the created list
		String readIdList = "SELECT * FROM list WHERE name = :name";
		MapSqlParameterSource paramsListId = new MapSqlParameterSource();
		paramsListId.addValue("name", list.getName());
		ShoppingList shoppingList = jdbc.query(readIdList, paramsListId, 
				new BeanPropertyRowMapper<ShoppingList>(ShoppingList.class)).get(0);
		
		String insertUserList = "INSERT INTO userList (username, listId)"
				+ " VALUES (:username, :listId)";
		MapSqlParameterSource paramsUserList = new MapSqlParameterSource();
		paramsUserList.addValue("username", username);
		paramsUserList.addValue("listId", shoppingList.getId());
		jdbc.update(insertUserList, paramsUserList);
	}
	
	public void deleteListById(int listId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", listId);
		//Delete foreign key first to be able to delete the primary key
		String deleteFromUserList = "DELETE FROM userlist WHERE listID = :id";
		String deleteFromList = "DELETE FROM list WHERE id = :id";
		jdbc.update(deleteFromUserList, params);
		jdbc.update(deleteFromList, params);
	}
	
	public void updateList(ShoppingList list) {
		String updateList = "UPDATE list SET name = :name WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", list.getName()).addValue("id", list.getId());
		jdbc.update(updateList, params);
	}
	
	public List<ShoppingItem> findItensByListId(int listId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String itensByListId = "SELECT * FROM item WHERE listId = :listId";
		params.addValue("listId", listId);
		List<ShoppingItem> results = jdbc.query(itensByListId, params, 
				new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		return results;
	}
	
	public void addItem(ShoppingItem item) {
		String insertItem = "INSERT INTO item (listId, name, quantity, price) "
				+ " VALUES (:listId, :name, :quantity, :price)";
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		paramsItem.addValue("listId", item.getListId())
		.addValue("name", item.getName()).addValue("quantity", item.getQuantity())
		.addValue("price", item.getPrice());
		jdbc.update(insertItem, paramsItem);
	}
	
	public ShoppingItem findItemById(int id) {
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		String itemById = "SELECT * FROM item WHERE id = :id";
		paramsItem.addValue("id", id);
		List<ShoppingItem> results = jdbc.query(itemById, paramsItem, 
				new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		return results.get(0);
	}
	
	public void deleteItemById(int ItemId) {
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		paramsItem.addValue("itemId", ItemId);
		String deleteFromItemList = "DELETE FROM item WHERE id = :itemId";
		jdbc.update(deleteFromItemList, paramsItem);
	}
	
	public void updateItem(ShoppingItem item) {
		String updateItem = "UPDATE item SET name = :name, "
				+ "quantity = :quantity, price = :price WHERE id = :id";
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		paramsItem.addValue("id", item.getId()).addValue("name", item.getName())
		.addValue("quantity", item.getQuantity()).addValue("price", item.getPrice());
		jdbc.update(updateItem, paramsItem);
	}
}