package ca.leonardo.shoppinglist.dao.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.leonardo.shoppinglist.beans.ShoppingItem;
import ca.leonardo.shoppinglist.dao.ShoppingItemDao;

@Repository
public class ShoppingItemDaoImpl implements ShoppingItemDao {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	@Override
	public List<ShoppingItem> findByListId(int listId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String itensByListId = "SELECT * FROM item WHERE listId = :listId";
		params.addValue("listId", listId);
		List<ShoppingItem> results = jdbc.query(itensByListId, params, 
				new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		return results;
	}

	@Override
	public void add(ShoppingItem item) {
		String insertItem = "INSERT INTO item (listId, name, quantity, price) "
				+ " VALUES (:listId, :name, :quantity, :price)";
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		paramsItem.addValue("listId", item.getListId())
		.addValue("name", item.getName()).addValue("quantity", item.getQuantity())
		.addValue("price", item.getPrice());
		jdbc.update(insertItem, paramsItem);
	}

	@Override
	public ShoppingItem findById(int id) {
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		String itemById = "SELECT * FROM item WHERE id = :id";
		paramsItem.addValue("id", id);
		List<ShoppingItem> results = jdbc.query(itemById, paramsItem, 
				new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		return results.get(0);
	}

	@Override
	public void deleteById(int ItemId) {
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		paramsItem.addValue("itemId", ItemId);
		String deleteFromItemList = "DELETE FROM item WHERE id = :itemId";
		jdbc.update(deleteFromItemList, paramsItem);
	}

	@Override
	public void update(ShoppingItem item) {
		String updateItem = "UPDATE item SET name = :name, "
				+ "quantity = :quantity, price = :price WHERE id = :id";
		MapSqlParameterSource paramsItem = new MapSqlParameterSource();
		paramsItem.addValue("id", item.getId()).addValue("name", item.getName())
		.addValue("quantity", item.getQuantity()).addValue("price", item.getPrice());
		jdbc.update(updateItem, paramsItem);
	}
}
