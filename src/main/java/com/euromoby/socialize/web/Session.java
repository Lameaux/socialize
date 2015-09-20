package com.euromoby.socialize.web;

import java.io.Serializable;

import com.euromoby.socialize.core.model.UserAccount;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer websiteId;

	private UserAccount userAccount = new UserAccount(); // TODO

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

}
