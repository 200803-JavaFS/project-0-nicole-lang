package com.revature.service;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.utils.ConnectionUtility;

public class AccountManagerTests {

	static Account a;
	static Account adminA;
	static Account transferA;
	static double balance;
	@BeforeClass
	public static void setUpAccount(){
		a = DatabaseManager.getAccount("gattietime");
		adminA = DatabaseManager.getAccount("nlang");
		transferA = DatabaseManager.getAccount("gattie_time");
	}
	@Before
	public void setBalance()
	{
		a.setBalance(1000.00);
		balance = a.getBalance();
		DatabaseManager.updateBalance(a.getUserName(), balance);
	}
	@Test
	public void testWithdraw() {
		//Withdraw $200 from account should leave $800 as new balance
		System.out.println("Testing bank withdrawal");
		assertTrue(AccountManager.withdraw(a, 200.00, "gattietime") == 800.00);
		System.out.println("Withdraw successful");
	}
	@Test
	public void testAdminWithdraw()
	{
		//Withdraw $200 from account should leave $800 as new balance
				System.out.println("Testing bank withdrawal");
				assertTrue(AccountManager.withdraw(a, 200.00, "nlang") == 800.00);
				System.out.println("Withdraw successful");
	}

	@Test
	public void testDeposit() {
		//Deposit $200 into account should leave $1000 as new balance
		System.out.println("Testing bank deposit");
		assertTrue(AccountManager.deposit(a, 200.00, "nlang") == 1200.00);	
		System.out.println("Deposit successful");
	}

	@Test
	public void testTransfer() {
		//Transfer $1000 into other account should leave $0 in account 1 and $1000 in account 2
		System.out.println("Testing bank withdrawal");
		double[] newBals = AccountManager.transfer(a, 1000.00, transferA, "nlang");
		assertTrue(newBals[0] == 0.00);
		System.out.println("Transfer step 1 (withdraw) successful");
		assertTrue(newBals[1] == 1000.00);
		System.out.println("Transfer step 2 (deposit) successful");
	}
	@AfterClass
	public static void resetAccounts()
	{
		DatabaseManager.updateBalance(a.getUserName(), 1000.00);
		DatabaseManager.updateBalance(transferA.getUserName(), 0.00);
	}

}
