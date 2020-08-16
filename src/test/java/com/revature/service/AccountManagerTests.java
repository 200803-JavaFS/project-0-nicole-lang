package com.revature.service;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.utils.ConnectionUtility;

public class AccountManagerTests {

	static Account a;
	static Account transferA;
	static final double amt = 200;
	@Before
	public void setUpAccount() throws Exception {
		a = DatabaseManager.getAccount("gattietime");
		transferA = DatabaseManager.getAccount("gattie_time");
	}

	@Test
	public void testWithdraw() {
		//Withdraw $200 from account should leave $800 as new balance
		System.out.println("Testing bank withdrawal");
		assertTrue(AccountManager.withdraw(a, 200.00) == 800.00);	
		System.out.println("Withdraw successful");
	}

	@Test
	public void testDeposit() {
		//Deposit $200 into account should leave $1000 as new balance
		System.out.println("Testing bank deposit");
		assertTrue(AccountManager.deposit(a, 200.00) == 1000.00);	
		System.out.println("Deposit successful");
	}

	@Test
	public void testTransfer() {
		//Transfer $1000 into other account should leave $0 in account 1 and $1000 in account 2
		System.out.println("Testing bank withdrawal");
		double[] newBals = AccountManager.transfer(a, 1000.00, transferA);
		assert(newBals[0] == 0.00);
		System.out.println("Transfer step 1 (withdraw) successful");
		assertTrue(newBals[1] == 1000.00);
		System.out.println("Transfer step 2 (deposit) successful");
	}
	@AfterClass
	public static void resetAccount()
	{
		//reset the test accounts' balance
		String sql = "Update Accounts set account_balance = 1000 where user_name = 'gattietime';"
				+ "Update Accounts set account_balance = 0 where user_name = 'gattie_time'";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
