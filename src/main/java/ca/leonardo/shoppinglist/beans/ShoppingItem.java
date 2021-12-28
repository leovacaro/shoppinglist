package ca.leonardo.shoppinglist.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingItem {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int listId;
	private String name;
	private int quantity;
	private double price;
}
