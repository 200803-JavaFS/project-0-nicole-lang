package com.revature.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.User;

public class DriverTests {

	public static User c;
	
	@BeforeClass
	public static void setUp()
	{
		c = new User();
	}
	@Before
	public void setValues()
	{
		//create test customer
		c.setRealName("Nicole Lang");
		c.setUserName("nlang");
		c.setPassword("082798");
		c.setBalance(100.00);
	}
	@Test
	public void testDeposit() {
		System.out.println("Testing valid deposit");
		Driver.deposit(50.00);
		assertTrue(c.getBalance() == 150.00);
		
		System.out.println("Testing invalid (negative) deposit");
		Driver.deposit(-50.00);
		assertTrue(c.getBalance() == 150.00);
		//reset balance
		c.setBalance(100.00);
	}
	@Test
	public void testWithdrawal() {
		System.out.println("Testing valid withdrawal");
		Driver.withdraw(50.00);
		assertTrue(c.getBalance() == 50.00);
		
		System.out.println("Testing invalid (negative) withdrawal");
		Driver.withdraw(-50.00);
		assertTrue(c.getBalance() == 50.00);
		
		System.out.println("Testing invalid (greater than balance) withdrawal");
		Driver.withdraw(75.00);
		assertTrue(c.getBalance() == 50.00);
	}
	

}
