package ca.leonardo.shoppinglist.dao;

import java.util.List;

import ca.leonardo.shoppinglist.beans.ShoppingList;

public interface ShoppingListDao {
	
	public List<ShoppingList> findByUsername(String username);
	
	public ShoppingList findById(int listId);
	
	public void add(ShoppingList list, String username);
	
	public void deleteById(int listId);
	
	public void update(ShoppingList list);
}
