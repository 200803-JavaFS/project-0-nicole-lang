package com.revature.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;

public interface AccountManager {

	Logger log = LogManager.getLogger();
	//Bank account service interface
	public static Double withdraw(Account a, double amt, String currentUser)
	{
		String logMessage;
		double initialBalance = a.getBalance();
		if(amt > 0 && amt <= initialBalance)
		{
			a.setBalance(initialBalance - amt);
			DatabaseManager.updateBalance(a.getUserName(), a.getBalance());

			if(currentUser.equals(a.getUserName()))
			{
				logMessage = "Transaction: User " + a.getUserName() + " withdrew " + amt 
						+ " from their account. Balance is now $" + a.getBalance();
				log.info(logMessage);	
			}
			else
			{
				logMessage = "Transaction: $" + amt + " removed from account " + a.getUserName()
				+ " by " + currentUser + ". Balance is now $" + a.getBalance();
				log.info(logMessage);
			}
			System.out.println("Withdrawal successful. New balance: " + a.getBalance());
			
		}
		else if(amt < 0) 
		{
			System.out.println("You cannot withdraw a negative/zero amount of money\n");
		}	
		else
			System.out.println("Insufficient funds");
		
		return a.getBalance();
	}
	
	
	public static double deposit(Account a, double amt, String currentUser)
	{
		String logMessage;
		String userN;
		//deposit funds if amount provided is positive
		if(amt > 0)
		{
			userN = a.getUserName();

			//update balance in account object
			a.setBalance(a.getBalance() + amt);			
			//send result to database
			DatabaseManager.updateBalance(userN, a.getBalance());

			if(currentUser.equals(userN))
			{
				logMessage = "Transaction: User " + userN + " deposited $" + amt 
						+ " into their account. Balance is now $" + a.getBalance();
				log.info(logMessage);	
			}
			else
			{
				logMessage = "Transaction: $" + amt + " deposited into account " + userN
						+ " by " + currentUser + ". Balance is now $" + a.getBalance();
				log.info(logMessage);
			}
			
			System.out.println("Deposit successful. New balance: " + a.getBalance() + "\n");
		}
		else
			System.out.println("You cannot deposit a negative/zero amount of money\n");
		return a.getBalance();
	}
	
	
	public static double[] transfer(Account currentAccount, double amt, Account targetAccount, String currentUser)
	{
		String logMessage;
		String userName1;
		String userName2;
		double newBals[];
		newBals = new double[2];
		if(amt > 0 && amt <= currentAccount.getBalance())
		{//update the balances of the account objects
			userName1 = currentAccount.getUserName();
			userName2 = targetAccount.getUserName();
			currentAccount.setBalance(currentAccount.getBalance() - amt);
			targetAccount.setBalance(targetAccount.getBalance() + amt);
			
			//send result to database
			DatabaseManager.updateBalance(userName1, currentAccount.getBalance());
			DatabaseManager.updateBalance(userName2, targetAccount.getBalance());
			
			if(currentUser.equals(userName1))
			{
				logMessage = "Transaction: " + userName1 + " transferred $" + amt + " into account " 
						+ userName2;
				log.info(logMessage);
			}
			else
			{
				logMessage = "Transaction: " + currentUser + " transferred $" + amt + " into account " 
						+ userName2 + " from " + userName1;
				log.info(logMessage);
			}
			logMessage = "User " + userName1 + " balance = " + currentAccount.getBalance() 
			+"\nUser " + userName2 + " balance = " + targetAccount.getBalance();
			log.info(logMessage);
			
			//prepare return array (used in testing)
			newBals[0] = currentAccount.getBalance();
			newBals[1] = targetAccount.getBalance();
			
			if(currentUser.equals(currentAccount.getUserName()))
				System.out.println("Transfer successful. New balance is " + currentAccount.getBalance() + "\n");
			else
				System.out.println("Transfer successful. See log file for details\n");
		}
		else if(amt < 0) 
			System.out.println("You cannot transfer a negative/zero amount of money\n");
		else
			System.out.println("Insufficient funds\n");
		
		return(newBals);
	}
	public static List<Account> getCustomers(String currentUser)
	{//retrieve a list of customers linked to the given Employee
		return DatabaseManager.listEmpCustomers(currentUser);
	}
	public static List<Account> getPendingAccounts()
	{
		return DatabaseManager.listRequests();
	}
	

}
