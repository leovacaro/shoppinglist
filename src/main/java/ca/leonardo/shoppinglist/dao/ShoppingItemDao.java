package ca.leonardo.shoppinglist.dao;

import java.util.List;

import ca.leonardo.shoppinglist.beans.ShoppingItem;

public interface ShoppingItemDao {
	
	public List<ShoppingItem> findByListId(int listId);
	
	public void add(ShoppingItem item);
	
	public ShoppingItem findById(int id);
	
	public void deleteById(int ItemId);
	
	public void update(ShoppingItem item);
}
