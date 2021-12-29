package ca.leonardo.shoppinglist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.leonardo.shoppinglist.beans.User;
import ca.leonardo.shoppinglist.dao.ShoppingItemDao;
import ca.leonardo.shoppinglist.dao.ShoppingListDao;
import ca.leonardo.shoppinglist.dao.UserDao;
import ca.leonardo.shoppinglist.service.ShoppingListService;
import ca.leonardo.shoppinglist.service.UserService;
import ca.leonardo.shoppinglist.beans.ShoppingItem;
import ca.leonardo.shoppinglist.beans.ShoppingList;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingListService shoppingListService;
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/login")
	public String securityLogin() {
		return "/login.html";
	}
	
	@GetMapping("/createUser")
	public String createUserForm(Model model) {
		model.addAttribute("user", new User());
		return "/createUser.html";
	}
	
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute User user, Model model) {
		userService.add(user);
		return "/test/home.html";
	}
	
	//Dictionary for send values to the template view
	@GetMapping("/test/home")
	public String databaseList(Model model) {
		//Get the authenticated username
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		List<ShoppingList> lists = shoppingListService.findListByUsername(currentPrincipalName);
		model.addAttribute("lists", lists);
		return "/test/home.html";
	}
	
	@GetMapping("/createList")
	public String createListForm(Model model) {
		model.addAttribute("list", new ShoppingList());
		return "/test/createList.html";
	}
	
	@PostMapping("/createList")
	public String createList(@ModelAttribute ShoppingList list, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		shoppingListService.addList(list, currentPrincipalName);
		return databaseList(model);
	}
	
	@GetMapping("/editList/{id}")
	public String updateListForm(Model model, @PathVariable("id") int id) {
		ShoppingList list = shoppingListService.findListById(id);
		model.addAttribute("list", list);
		return "/test/editList.html";
	}
	
	@PostMapping("/editList")
	public String updateList(@ModelAttribute ShoppingList list, Model model) {
		shoppingListService.updateList(list);
		return databaseList(model);
	}
	
	@GetMapping("/deleteList/{id}")
	public String deleteListById(Model model, @PathVariable("id") int id) {
		shoppingListService.deleteListById(id);
		return databaseList(model);
	}
	
	@GetMapping("/productsList/{listId}")
	public String itensList(Model model, @PathVariable("listId") int listId) {
		List<ShoppingItem> itens = shoppingListService.findItemByListId(listId);
		model.addAttribute("itens", itens);
		model.addAttribute("listId", listId);
		return "/test/productsList.html";
	}
	
	@GetMapping("/newItem/{listId}")
	public String newItemForm(Model model, @PathVariable("listId") int listId) {
		ShoppingItem item = new ShoppingItem();
		item.setListId(listId);
		model.addAttribute("item", item);
		return "/test/newItem.html";
	}
	
	@PostMapping("/newItem")
	public String newItem(@ModelAttribute ShoppingItem item, Model model) {
		shoppingListService.addItem(item);
		return itensList(model, item.getListId());
	}
	
	@GetMapping("/deleteItem/{itemId}")
	public String deleteItemById(Model model, @PathVariable("itemId") int itemId) {
		ShoppingItem item = shoppingListService.findItemById(itemId);
		shoppingListService.deleteItemById(itemId);
		return itensList(model, item.getListId());
	}
	
	@GetMapping("/editItem/{itemId}")
	public String updateItemForm(Model model, @PathVariable("itemId") int itemId) {
		ShoppingItem item = shoppingListService.findItemById(itemId);
		model.addAttribute("item", item);
		return "/test/editItem.html";
	}
	
	@PostMapping("/editItem")
	public String updateItem(@ModelAttribute ShoppingItem item, Model model) {
		shoppingListService.updateItem(item);
		return itensList(model, item.getListId());
	}	
}
