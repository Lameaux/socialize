CREATE DATABASE socialize CHARACTER SET utf8 COLLATE utf8_general_ci;

-- DROP TABLE IF EXISTS user_account;
-- DROP TABLE IF EXISTS mail_new;
-- DROP TABLE IF EXISTS mail_sent;

CREATE TABLE IF NOT EXISTS user_account (
	id INT auto_increment NOT NULL PRIMARY KEY,
	email VARCHAR(255),
	login VARCHAR(255),
	password_hash VARCHAR(255)
) ENGINE=InnoDB;
CREATE UNIQUE INDEX user_account_email ON user_account(email);
CREATE UNIQUE INDEX user_account_login ON user_account(login);

CREATE TABLE IF NOT EXISTS mail_new (
	id INT auto_increment NOT NULL PRIMARY KEY,
	sender VARCHAR(255),
	recipient VARCHAR(255),
	subject VARCHAR(255),
	content TEXT,
	created BIGINT default 0
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS mail_sent (
	id INT NOT NULL PRIMARY KEY,
	sender VARCHAR(255),
	recipient VARCHAR(255),
	subject VARCHAR(255),
	content TEXT,
	sent BIGINT default 0
) ENGINE=InnoDB;

-- --------------------------------------------------------------

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


