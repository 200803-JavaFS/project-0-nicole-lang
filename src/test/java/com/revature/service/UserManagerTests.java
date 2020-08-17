package com.revature.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.Account;
import com.revature.models.User;

public class UserManagerTests {

	static User u;
	@BeforeClass
	public static void setUpUser()
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
		//test valid cases
		assertTrue(UserManager.getUserInfo(u).contains("Balance"));
		u.setUserType("Employee");
		assertTrue(UserManager.getUserInfo(u).contains("Your Active Customers"));
		u.setUserType("Admin");
		assertTrue(UserManager.getUserInfo(u).contains("User List"));
		//test default case
		u.setUserType("");
		assertFalse(UserManager.getUserInfo(u).contains("Balance"));
		System.out.println("getUserInfo successful");		
	}

	@Test
	public void testGetPrompt() {
		System.out.println("Testing get prompt");
		assertTrue(UserManager.getPrompt(u.getUserType()).contains("deposit"));
		u.setUserType("Employee");
		assertTrue(UserManager.getPrompt(u.getUserType()).contains("approve"));
		u.setUserType("Admin");
		assertTrue(UserManager.getPrompt(u.getUserType()).contains("close"));
		u.setUserType("");
		assertFalse(UserManager.getPrompt(u.getUserType()).contains("Select operation"));
		System.out.println("getPrompt successful");
	}

	@Test
	public void testUserExists() {
		System.out.println("Testing userExists");
		assertTrue(UserManager.userExists(u.getUserName()));
		System.out.println("userExists successful");	
	}
	
	@After
	public void resetUserType()
	{
		u.setUserType("Customer");
	}

}
