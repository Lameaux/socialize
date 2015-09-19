package com.euromoby.socialize.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "password_reset_request")
public class PasswordResetRequest {

	public static final long TIME_TO_LIVE = 2 * 24 * 60 * 60 * 1000L;	
	
	@Id
	@Column(name = "user_account_id")
	private Integer userAccountId;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "created")
	private long created;

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

}
