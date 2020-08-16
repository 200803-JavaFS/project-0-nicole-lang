package com.revature.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.models.Account;
import com.revature.models.User;

public class UserManagerTests {

	static User u;
	@Before
	public void setUpUser()
	{//initialize user fields
		u = new User();
		u.setUserName("gattietime");
		u.setFirstName("Timothy");
		u.setLastName("Gattie");
		u.setPassword("200803");
		u.setUserType("Customer");
		u.setAccount(new Account("gattietime","Open","nlang",1000.00,null));
	}
	@Test
	public void testGetUserInfo() {
		System.out.println("Testing get user info");
		assertTrue(UserManager.getUserInfo(u).contains("Timothy Gattie"));
		System.out.println("getUserInfo successful");		
	}

	@Test
	public void testGetPrompt() {
		System.out.println("Testing get user info");
		assertTrue(UserManager.getPrompt(u.getUserType()).contains("deposit"));
		System.out.println("getUserInfo successful");
	}

	@Test
	public void testUserExists() {
		System.out.println("Testing userExists");
		assertTrue(UserManager.userExists(u.getUserName()));
		System.out.println("userExists successful");	
	}

}
