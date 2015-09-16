package com.euromoby.socialize.core.utils;

public class PasswordUtils {

	public static String generatePasswordHash(String originalPassword) {
		return BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
	}

	public static boolean passwordMatches(String originalPassword, String passwordHash) {
		return BCrypt.checkpw(originalPassword, passwordHash);
	}
}
