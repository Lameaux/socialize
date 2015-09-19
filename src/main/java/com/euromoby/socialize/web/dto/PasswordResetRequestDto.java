package com.euromoby.socialize.web.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class PasswordResetRequestDto {
	@NotNull
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
