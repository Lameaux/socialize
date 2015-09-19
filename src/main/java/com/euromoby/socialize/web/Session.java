package com.euromoby.socialize.web;

import java.io.Serializable;

import com.euromoby.socialize.core.model.AuthUser;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer websiteId;

	private AuthUser authUser;

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

}
