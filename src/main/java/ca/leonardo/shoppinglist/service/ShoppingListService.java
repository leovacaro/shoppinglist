package ca.leonardo.shoppinglist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.leonardo.shoppinglist.beans.ShoppingItem;
import ca.leonardo.shoppinglist.beans.ShoppingList;
import ca.leonardo.shoppinglist.dao.ShoppingItemDao;
import ca.leonardo.shoppinglist.dao.ShoppingListDao;

@Service
public class ShoppingListService {
	
	@Autowired
	private ShoppingListDao shoppingListDao;
	
	@Autowired
	private ShoppingItemDao shoppingItemDao;

	public List<ShoppingList> findListByUsername(String username) {
		return shoppingListDao.findByUsername(username);
	}
	
	public void addList(ShoppingList list, String username) {
		shoppingListDao.add(list, username);
	}
	
	public ShoppingList findListById(int listId) {
		return shoppingListDao.findById(listId);
	}
	
	public void updateList(ShoppingList list) {
		shoppingListDao.update(list);
	}
	
	public void deleteListById(int listId) {
		shoppingListDao.deleteById(listId);
	}
	
	public List<ShoppingItem> findItemByListId(int listId) {
		return shoppingItemDao.findByListId(listId);
	}
	
	public void addItem(ShoppingItem item) {
		shoppingItemDao.add(item);
	}
	
	public ShoppingItem findItemById(int id) {
		return shoppingItemDao.findById(id);
	}
	
	public void deleteItemById(int ItemId) {
		shoppingItemDao.deleteById(ItemId);
	}
	
	public void updateItem(ShoppingItem item) {
		shoppingItemDao.update(item);
	}
}
