package com.euromoby.socialize.core.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordUtilsTest {

	public static final String PASSWORD = "pwd";	
	
	@Test
	public void passwordMatches() {
		String hash = PasswordUtils.generatePasswordHash(PASSWORD);
		assertTrue(PasswordUtils.passwordMatches(PASSWORD, hash));
	}

}
