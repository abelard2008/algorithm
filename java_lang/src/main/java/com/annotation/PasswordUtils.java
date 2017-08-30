package com.annotation;

import java.util.*;

public class PasswordUtils {
	@UseCase(id = 50, description = "Passwords must contain at least one numeric")
	public boolean validatePassword(String password) {
		return (password.matches("\\w*\\d\\w*"));
	}

	@UseCase(id = 49)
	public String encryptPassword(String password) {
		return new StringBuilder(password).reverse().toString();
	}

	@UseCase(id = 48, description = "New passwords can’t equal previously used ones")
	public boolean checkForNewPassword(List<String> prevPasswords,
			String password) {
		return !prevPasswords.contains(password);
	}
} // /:~
