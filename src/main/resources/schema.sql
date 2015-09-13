CREATE DATABASE socialize CHARACTER SET utf8 COLLATE utf8_general_ci;

-- DROP TABLE IF EXISTS account;
-- DROP TABLE IF EXISTS website;
-- DROP TABLE IF EXISTS auth_user;

CREATE TABLE IF NOT EXISTS account (
	id INT auto_increment NOT NULL PRIMARY KEY,
	email VARCHAR(255),
	password_hash VARCHAR(255),
	salt VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS website (
	id INT auto_increment NOT NULL PRIMARY KEY,
	account_id INT NOT NULL,
	domain VARCHAR(255),
	token VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS auth_user (
	id INT auto_increment NOT NULL PRIMARY KEY,
	website_id INT NOT NULL,
	provider VARCHAR(255),
	provider_id VARCHAR(255),
	login VARCHAR(255),
	email VARCHAR(255),	
	name VARCHAR(255)
) ENGINE=InnoDB;


