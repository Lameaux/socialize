package com.euromoby.socialize.web.dto;

public class WebsiteStatusDto {

	private String redirectUrl;

	private boolean error;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}
