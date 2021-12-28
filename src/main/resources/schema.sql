CREATE TABLE users (
	username VARCHAR(50) NOT NULL,
	name VARCHAR(30) NOT NULL,
	email VARCHAR(30) UNIQUE NOT NULL,
	password VARCHAR(100) NOT NULL,
	enabled TINYINT NOT NULL DEFAULT 1,
	PRIMARY KEY (username)
);

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);
  
CREATE TABLE list (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE item (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	listId INT NOT NULL,
	name VARCHAR(30) NOT NULL,
	quantity INT NOT NULL,
	price DOUBLE NOT NULL,
	FOREIGN KEY (listId) REFERENCES list(id)
);

CREATE TABLE userList (
	username VARCHAR(50) NOT NULL,
	FOREIGN KEY (username) REFERENCES users(username),
	listId INT NOT NULL,
	FOREIGN KEY (listId) REFERENCES list(id)
);