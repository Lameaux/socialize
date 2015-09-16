package com.euromoby.socialize.web.dto;

public class CheckEmailStatusDto {

	private String email;

	private boolean exists;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

}
