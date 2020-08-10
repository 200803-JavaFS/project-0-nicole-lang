package com.revature.models;

import com.revature.service.Driver;
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

	@Test
	public void testGetActive() {
		System.out.println("Testing Customer getActive");
		assertFalse(c.getActive());
		System.out.println("getActive successful");
	}

	@Test
	public void testSetActive() {
		System.out.println("Testing Customer setActive");
		c.setActive(true);
		assertTrue(c.getActive());
		System.out.println("setActive successful");
	}

	@Test
	public void testGetBalance() {
		System.out.println("Testing Customer getBalance");
		assertTrue(c.getBalance() == 100.00);
		System.out.println("getBalance successful");
	}

	@Test
	public void testSetBalance() {
		System.out.println("Testing Customer setBalance");
		c.setBalance(200.00);
		assertTrue(c.getBalance() == 200.00);
		System.out.println("setBalance successful");
	}

	@Test
	public void testGetRealName() {
		System.out.println("Testing Customer getRealName");
		assertTrue(c.getRealName() == "Nicole Lang");
		System.out.println("getRealName successful");
	}

	@Test
	public void testSetRealName() {
		System.out.println("Testing Customer setRealName");
		c.setRealName("Jane Doe");
		assertTrue(c.getRealName() == "Jane Doe");
		System.out.println("setRealName successful");
	}

	@Test
	public void testGetUserName() {
		System.out.println("Testing Customer getUserName");
		assertTrue(c.getUserName() == "nlang");
		System.out.println("getUserName successful");
	}

	@Test
	public void testSetUserName() {
		System.out.println("Testing Customer getRealName");
		c.setUserName("jdoe");
		assertTrue(c.getUserName() == "jdoe");
		System.out.println("setUserName successful");
	}

	@Test
	public void testGetPassword() {
		System.out.println("Testing Customer getPassword");
		assertTrue(c.getPassword() == "082798");
		System.out.println("getPassword successful");
	}

	@Test
	public void testSetPassword() {
		System.out.println("Testing Customer getRealName");
		c.setPassword("12345");
		assertTrue(c.getPassword() == "12345");
		System.out.println("getPassword successful");
	}

}
