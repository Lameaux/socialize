package com.euromoby.socialize.web.dto;

public class CheckLoginStatusDto {

	private String login;
	private boolean exists;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

}
