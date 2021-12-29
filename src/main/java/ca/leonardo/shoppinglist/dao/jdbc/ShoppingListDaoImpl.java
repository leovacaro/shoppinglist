package ca.leonardo.shoppinglist.dao.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.leonardo.shoppinglist.beans.ShoppingList;
import ca.leonardo.shoppinglist.dao.ShoppingListDao;

@Repository
public class ShoppingListDaoImpl implements ShoppingListDao {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	@Override
	public List<ShoppingList> findByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String listByUserUsername = "SELECT list.id, list.name FROM users INNER JOIN " 
				+ "userlist ON users.username = userlist.username "
				+ "INNER JOIN list ON list.id = userlist.listId "
				+ "WHERE users.username = :username";
		params.addValue("username", username);
		return jdbc.query(listByUserUsername, params, 
				new BeanPropertyRowMapper<ShoppingList>(ShoppingList.class));
	}

	@Override
	public ShoppingList findById(int listId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String listById = "SELECT * FROM list WHERE id = :listId";
		params.addValue("listId", listId);
		List<ShoppingList> results = jdbc.query(listById, params, 
				new BeanPropertyRowMapper<ShoppingList>(ShoppingList.class));
		return results.get(0);
	}

	@Override
	public void add(ShoppingList list, String username) {
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

	@Override
	public void deleteById(int listId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", listId);
		//Delete foreign key first to be able to delete the primary key
		String deleteFromUserList = "DELETE FROM userlist WHERE listID = :id";
		String deleteFromList = "DELETE FROM list WHERE id = :id";
		jdbc.update(deleteFromUserList, params);
		jdbc.update(deleteFromList, params);
	}

	@Override
	public void update(ShoppingList list) {
		String updateList = "UPDATE list SET name = :name WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", list.getName()).addValue("id", list.getId());
		jdbc.update(updateList, params);		
	}
}
