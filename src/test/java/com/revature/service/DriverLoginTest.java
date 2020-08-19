package com.revature.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DriverLoginTest {
	//JUnit test class for Driver.java

	@Test
	public void testValidLogin() {
		System.out.println("Testing login method for valid user");
		assertTrue(Driver.doLogin("nlang", "82798", 0));
		System.out.println("Valid user login test successful");
	}	
	@Test
	public void testInvalidLogin() {
		System.out.println("Testing login method for invalid user");
		assertFalse(Driver.doLogin("qwerty", "zxcvb", 0));
		System.out.println("Invalid user login test successful");
	}
	@Test
	public void testInactiveLogin() {
		System.out.println("Testing login method for inactive user");
		assertFalse(Driver.doLogin("ffun1", "12345", 0));
		System.out.println("Inactive user login test successful");
	}
	@Test
	public void testWrongPasswordLogin() {
		System.out.println("Testing login method for incorrect password");
		assertFalse(Driver.doLogin("nlang", "a", 0));
		System.out.println("Incorrect password test successful");
	}

}
