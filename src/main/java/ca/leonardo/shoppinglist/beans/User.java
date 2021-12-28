package ca.leonardo.shoppinglist.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String email;
	private String username;
	private String password;
	private int enabled;
}
