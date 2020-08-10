package com.revature.service;

import com.revature.models.Customer;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DriverTests {

	public static Customer c;
	
	
	@BeforeClass
	public static void setUp()
	{
		c = new Customer();
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
	public void printInfo()
	{
		System.out.println(c.printAccountInfo());
	}
	@Test
	public void testDeposit() {
		System.out.println("Testing valid deposit");
		Driver.deposit(50.00);
		System.out.println("Testing invalid (negative) deposit");
		Driver.deposit(-50.00);
	}
	@Test
	public void testWithdrawal() {
		System.out.println("Testing valid withdrawal");
		Driver.withdraw(50.00);
		System.out.println("Testing invalid (negative) withdrawal");
		Driver.withdraw(-50.00);
		System.out.println("Testing invalid (greater than balance) withdrawal");
		Driver.withdraw(150.00);
	}
	

}
