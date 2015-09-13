package com.euromoby.socialize.web;

import java.io.Serializable;

import com.euromoby.socialize.core.model.AuthUser;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuthUser authUser;

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

}
