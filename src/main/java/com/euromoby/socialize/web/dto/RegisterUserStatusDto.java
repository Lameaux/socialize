package com.euromoby.socialize.web.dto;


public class RegisterUserStatusDto {

	private RegisterUserDto user;

	private Integer id;

	private boolean error;

	public RegisterUserDto getUser() {
		return user;
	}

	public void setUser(RegisterUserDto user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}
