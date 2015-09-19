package com.euromoby.socialize.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordResetRequestFormDto {
	@NotNull
	private String uuid;
	@NotNull
	@Size(min = 6)
	private String password;
	@NotNull
	@Size(min = 6)
	private String password2;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
