package com.revature.service;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;

public interface AccountManager {

	//Bank account service interface
	public static String withdraw(Account a, double amt)
	{
		if(amt > 0 && amt <= a.getBalance())
		{
			a.setBalance(a.getBalance() - amt);
			DatabaseManager.updateBalance(a.getUserName(), a.getBalance());
			return("Withdrawal successful. New balance: " + a.getBalance());
		}
		else if(amt < 0) 
			return "You cannot withdraw a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}
	
	
	public static String deposit(Account a, double amt)
	{
		//deposit funds if amount provided is positive
		if(amt > 0)
		{
			a.setBalance(a.getBalance() + amt);
			DatabaseManager.updateBalance(a.getUserName(), a.getBalance());
			return("Deposit successful. New balance: " + a.getBalance());
		}
		else
			return("You cannot deposit a negative amount of money; please use withdraw to remove funds");
	}
	
	
	public static String transfer(Account currentAccount, double amt, Account targetAccount)
	{
		if(amt > 0 && amt <= currentAccount.getBalance())
		{
			currentAccount.setBalance(currentAccount.getBalance() - amt);
			targetAccount.setBalance(targetAccount.getBalance() + amt);
			DatabaseManager.updateBalance(currentAccount.getUserName(), currentAccount.getBalance());
			DatabaseManager.updateBalance(targetAccount.getUserName(), targetAccount.getBalance());
			return("Transfer successful");
		}
		else if(amt < 0) 
			return "You cannot transfer a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}

}
