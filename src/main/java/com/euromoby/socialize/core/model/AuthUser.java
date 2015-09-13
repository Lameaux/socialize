package com.euromoby.socialize.core.model;

public class AuthUser {

	private Integer id;
	private Integer websiteId;
	private String provider;
	private String providerId;
	private String login;
	private String email;
	private String name;
	private String image;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "AuthUser [id=" + id + ", websiteId=" + websiteId + ", provider=" + provider + ", providerId=" + providerId + ", login=" + login + ", email="
				+ email + ", name=" + name + ", image=" + image + "]";
	}

}
