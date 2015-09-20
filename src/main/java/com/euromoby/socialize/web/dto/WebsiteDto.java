package com.euromoby.socialize.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WebsiteDto {
	@NotNull
	@Size(min = 3)
	private String siteName;

	@NotNull
	@Size(min = 4)
	private String domain;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
