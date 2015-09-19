package com.euromoby.socialize.web.dto;


public class SignupStatusDto {

	private SignupDto user;

	private Integer id;

	private boolean error;

	public SignupDto getUser() {
		return user;
	}

	public void setUser(SignupDto user) {
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
