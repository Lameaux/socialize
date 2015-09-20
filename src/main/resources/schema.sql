CREATE DATABASE socialize CHARACTER SET utf8 COLLATE utf8_general_ci;

-- DROP TABLE IF EXISTS user_account;
-- DROP TABLE IF EXISTS mail_new;
-- DROP TABLE IF EXISTS mail_sent;

CREATE TABLE IF NOT EXISTS user_account (
	id INT auto_increment NOT NULL PRIMARY KEY,
	email VARCHAR(255),
	display_name VARCHAR(255),
	password_hash VARCHAR(255),
	uuid VARCHAR(36),
	created BIGINT DEFAULT 0,
	updated BIGINT DEFAULT 0,
	last_login BIGINT DEFAULT 0,
	active TINYINT DEFAULT 0
) ENGINE=InnoDB;
CREATE INDEX user_account_email ON user_account(email);
CREATE UNIQUE INDEX user_account_uuid ON user_account(uuid);


CREATE TABLE IF NOT EXISTS mail_new (
	id INT auto_increment NOT NULL PRIMARY KEY,
	recipient VARCHAR(255),
	subject VARCHAR(255),
	content TEXT,
	created BIGINT DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS mail_sent (
	id INT NOT NULL PRIMARY KEY,
	recipient VARCHAR(255),
	subject VARCHAR(255),
	content TEXT,
	sent BIGINT DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS password_reset_request (
	user_account_id INT NOT NULL PRIMARY KEY,
	uuid VARCHAR(36),
	created BIGINT DEFAULT 0
) ENGINE=InnoDB;
CREATE UNIQUE INDEX password_reset_request_uuid ON password_reset_request(uuid);

CREATE TABLE IF NOT EXISTS website (
	id INT auto_increment NOT NULL PRIMARY KEY,
	user_account_id INT NOT NULL,
	domain VARCHAR(255),
	site_name VARCHAR(255),
	callback_url VARCHAR(255),
	uuid VARCHAR(255)
) ENGINE=InnoDB;
CREATE UNIQUE INDEX website_domain ON website(domain);
CREATE UNIQUE INDEX website_uuid ON website(uuid);
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS auth_user (
	id INT auto_increment NOT NULL PRIMARY KEY,
	website_id INT NOT NULL,
	provider VARCHAR(255),
	provider_id VARCHAR(255),
	login VARCHAR(255),
	email VARCHAR(255),	
	name VARCHAR(255)
) ENGINE=InnoDB;


