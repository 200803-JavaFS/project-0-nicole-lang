package com.revature.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DriverLoginTest {


	@Test
	public void testValidLogin() {
		System.out.println("Testing login method for valid user");
		assertTrue(Driver.doLogin("nlang", "82798"));
		System.out.println("Valid user login test successful");
	}	
	@Test
	public void testInvalidLogin() {
		System.out.println("Testing login method for invalid user");
		assertFalse(Driver.doLogin("qwerty", "zxcvb"));
		System.out.println("Invalid user login test successful");
	}
	@Test
	public void testInactiveLogin() {
		System.out.println("Testing login method for inactive user");
		assertFalse(Driver.doLogin("ffun1", "12345"));
		System.out.println("Inactive user login test successful");
	}
	@Test
	public void testWrongPasswordLogin() {
		System.out.println("Testing login method for incorrect password");
		assertFalse(Driver.doLogin("nlang", "a"));
		System.out.println("Incorrect password test successful");
	}

}
