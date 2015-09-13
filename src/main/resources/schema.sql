CREATE DATABASE socialize CHARACTER SET utf8 COLLATE utf8_general_ci;

-- DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
	id INT auto_increment NOT NULL PRIMARY KEY,
	email VARCHAR(255),
	password VARCHAR(255)
) ENGINE=InnoDB;



