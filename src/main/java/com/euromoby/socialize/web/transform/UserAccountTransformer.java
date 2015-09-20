package com.euromoby.socialize.web.transform;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.utils.PasswordUtils;
import com.euromoby.socialize.web.dto.SignupDto;

@Component
public class UserAccountTransformer {
	public UserAccount newUserAccount(SignupDto dto) {
		UserAccount userAccount = new UserAccount();
		userAccount.setEmail(dto.getEmail());
		userAccount.setDisplayName(dto.getDisplayName());
		userAccount.setPasswordHash(PasswordUtils.generatePasswordHash(dto.getPassword()));
		userAccount.setUuid(UUID.randomUUID().toString());
		userAccount.setCreated(System.currentTimeMillis());
		userAccount.setUpdated(0);
		userAccount.setLastLogin(0);
		userAccount.setActive(false);
		return userAccount;
	}
	
}
