package com.euromoby.socialize.web.transform;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.model.Website;
import com.euromoby.socialize.web.dto.WebsiteDto;

@Component
public class WebsiteTransformer {
	public Website newWebsite(WebsiteDto dto, UserAccount userAccount) {
		Website website = new Website();
		website.setDomain(dto.getDomain().toLowerCase());
		website.setUserAccountId(userAccount.getId());
		website.setSiteName(dto.getSiteName());
		website.setUuid(UUID.randomUUID().toString());
		website.setCreated(System.currentTimeMillis());
		website.setUpdated(0);
		return website;
	}

}
