package com.revature.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
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
		u.setAccount(new Account("gattietime","Open","nlang",1000.00,null));
	}
	@Test
	public void testGetUserInfo() {
		System.out.println("Testing get user info");
		assert(UserManager.getUserInfo(u).contains("Timothy Gattie"));
		System.out.println("getUserInfo successful");		
	}

	@Test
	public void testGetPrompt() {
		System.out.println("Testing get user info");
		assert(UserManager.getPrompt(u).contains("deposit"));
		System.out.println("getUserInfo successful");	
	}

	@Test
	public void testUserExists() {
		//This user does not exist so userExists should return false
		//this is actually checking against the database but it is a UserManager method
		System.out.println("Testing userExists");
		assertFalse(UserManager.userExists(u.getUserName()));
		System.out.println("userExists successful");	
	}

}
