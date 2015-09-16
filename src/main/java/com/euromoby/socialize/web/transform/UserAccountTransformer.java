package com.euromoby.socialize.web.transform;

import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.utils.PasswordUtils;
import com.euromoby.socialize.web.dto.RegisterUserDto;

@Component
public class UserAccountTransformer {
	public UserAccount newUserAccount(RegisterUserDto registerUser) {
		UserAccount userAccount = new UserAccount();
		userAccount.setEmail(registerUser.getEmail());
		userAccount.setLogin(registerUser.getLogin());
		userAccount.setPasswordHash(PasswordUtils.generatePasswordHash(registerUser.getPassword()));
		return userAccount;
	}
	
}
