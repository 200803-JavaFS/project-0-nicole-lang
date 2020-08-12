package com.revature.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTests {

	public static User c;
	public int result;
	
	@BeforeClass
	public static void setUp()
	{
		c = new User();
	}
	@Before
	public void setValues()
	{
		//create test customer
		result = 0;
		c.setAccountType("Customer");
		c.setRealName("Nicole Lang");
		c.setUserName("nlang");
		c.setPassword("082798");
		c.setBalance(100.00);
		c.setActive(false);
	}

	@Test
	public void testPrintAccountInfo() {
		System.out.println("Testing printing account info");
		assertFalse(c.printAccountInfo().isEmpty());
		System.out.println("printAccountInfo successful");
	}

}
