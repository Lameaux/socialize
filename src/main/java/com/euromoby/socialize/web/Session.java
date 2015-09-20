package com.euromoby.socialize.web;

import java.io.Serializable;

import com.euromoby.socialize.core.model.UserAccount;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private String website;

	private UserAccount userAccount; // = new UserAccount(3); // TODO

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
