package com.euromoby.socialize.web.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.utils.PasswordUtils;
import com.euromoby.socialize.web.dto.SignupDto;

public class UserAccountTransformerTest {

	public static final String EMAIL = "email";
	public static final String DISPLAY_NAME = "displayName";
	public static final String PASSWORD = "pwd";
	public static final String PASSWORD2 = "pwd2";	
	
	@Test
	public void newUserAccount() {
		SignupDto signupDto = new SignupDto();
		signupDto.setEmail(EMAIL);
		signupDto.setDisplayName(DISPLAY_NAME);
		signupDto.setPassword(PASSWORD);
		signupDto.setPassword2(PASSWORD2);
		
		UserAccountTransformer userAccountTransformer = new UserAccountTransformer();
		
		UserAccount userAccount = userAccountTransformer.newUserAccount(signupDto);
		assertEquals(EMAIL, userAccount.getEmail());
		assertEquals(DISPLAY_NAME, userAccount.getDisplayName());
		assertTrue(PasswordUtils.passwordMatches(PASSWORD, userAccount.getPasswordHash()));		
		assertNotNull(userAccount.getUuid());	
		assertTrue(userAccount.getCreated() > 0);
		assertEquals(0, userAccount.getUpdated());
		assertEquals(0, userAccount.getLastLogin());	
		assertFalse(userAccount.isActive());
	}

}
