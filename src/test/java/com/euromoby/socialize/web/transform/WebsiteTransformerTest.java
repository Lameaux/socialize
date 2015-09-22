package com.euromoby.socialize.web.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.model.Website;
import com.euromoby.socialize.web.dto.WebsiteDto;

public class WebsiteTransformerTest {

	public static final String DOMAIN = "domain";
	public static final Integer USER_ID = 123;	
	public static final String SITE_NAME = "sitename";
	public static final String UUID = "uuid";	
	
	@Test
	public void newWebsite() {

		WebsiteDto dto = new WebsiteDto();
		dto.setDomain(DOMAIN);
		dto.setSiteName(SITE_NAME);
		UserAccount userAccount = new UserAccount();
		userAccount.setId(USER_ID);
		
		WebsiteTransformer websiteTransformer = new WebsiteTransformer();
		
		Website website = websiteTransformer.newWebsite(dto, userAccount);
		assertEquals(DOMAIN, website.getDomain());
		assertEquals(USER_ID, website.getUserAccountId());
		assertEquals(SITE_NAME, website.getSiteName());
		assertNotNull(website.getUuid());	
		assertTrue(website.getCreated() > 0);
		assertEquals(0, website.getUpdated());		
	}

}
