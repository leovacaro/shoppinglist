package ca.leonardo.shoppinglist.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingList {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
}
