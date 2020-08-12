package com.revature.service;

import java.util.*;
import com.revature.dao.DatabaseManager;
import com.revature.models.User;

public class Driver {
	//static variables accessible by all driver methods
	static String currentUser;
	static String input;

	static User u;
	static String userType;
	public static void main(String[] args){
		String userN;
		String fullN;
		String passW;
		boolean loginDone = false;
		u = new User();
		
		userType = "";
		boolean done = false;
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Delta Savings!");

		do {
			if(!loginDone)
			{
				System.out.println("Enter '0' to log in or '1' to create an account. Enter 'exit' to quit.");
				input = scan.nextLine();
				switch(input)
				{
				case "0":
				{
					//login with existing username and password
					System.out.println("Enter your username:");
					userN = scan.nextLine();
					System.out.println("Enter your password:");
					passW = scan.nextLine();

					userType = DatabaseManager.getUserType(userN);
					
					u = DatabaseManager.login(userN, passW);
					if(u.getActive())
						loginDone = true;
					else if(u.getRealName().equals(""))
						System.out.println("User "+ userN + " does not exist.");
					else
						System.out.println("Incorrect password");
					break;
				}
				case "1":{
					//create new user account
					System.out.println("Enter your full name:");
					fullN = scan.nextLine();
					//temporarily using execution control to control username selection loop
					while(!done)
					{
						System.out.println("Enter a username:");
						userN = scan.nextLine();
						if(DatabaseManager.getUser(userN).getRealName().equals(""))
						{
							System.out.println("Username " + u.getUserName() + " is available. Enter a password:");
							passW = scan.nextLine();
							createAccount(fullN, userN, passW);
							done = true;
						}
						else
							System.out.println("That username is taken, try another");
					}
					System.out.println("Account request submitted. Please wait for access.");
					//reset boolean done so execution will continue
					done = false;
					break;
				}
				case "exit":
				{
					done = true;
					break;
				}
				default:
					System.out.println("Invalid selection");
					break;
				}
			}
			else
			{
				//If there is a user logged in, display their basic info
				u.printAccountInfo();
				input = scan.nextLine();
				
			}
			//second check for if user is logged in, outside of initial info display
			//prevents repeat display of lists in every loop iteration
			if(loginDone)
			{
				//prompt with available actions
				System.out.println(u.getPrompt());
			}
			
		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void createAccount(String real, String user, String pwd) {
		//create customer account open request
		u.setRealName(real);
		u.setUserName(user);
		u.setPassword(pwd);
		u.setAccountType("Customer");
		DatabaseManager.createUser(u);
		System.out.println("Account requested. Please wait for access");
	}
	
	//The following methods should also call dao methods to
	//update the database record(s) affected and log the transaction.
	public static String withdraw(double amt)
	{
		if(amt > 0 && amt <= u.getBalance())
		{
			u.setBalance(u.getBalance() - amt);
			DatabaseManager.updateBalance(u.getUserName(), u.getBalance());
			return("Withdrawal successful. New balance: " + u.getBalance());
		}
		else if(amt < 0) 
			return "You cannot withdraw a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}
	
	
	public static String deposit(double amt)
	{
		//deposit funds if amount provided is positive
		if(amt > 0)
		{
			u.setBalance(u.getBalance() + amt);
			DatabaseManager.updateBalance(u.getUserName(), u.getBalance());
			return("Deposit successful. New balance: " + u.getBalance());
		}
		else
			return("You cannot deposit a negative amount of money; please use withdraw to remove funds");
	}
	
	
	public static String transfer(double amt, User targetUser)
	{
		if(amt > 0 && amt <= u.getBalance())
		{
			u.setBalance(u.getBalance() - amt);
			targetUser.setBalance(targetUser.getBalance() + amt);
			DatabaseManager.updateBalance(u.getUserName(), u.getBalance());
			DatabaseManager.updateBalance(targetUser.getUserName(), targetUser.getBalance());
			return("Transfer successful");
		}
		else if(amt < 0) 
			return "You cannot transfer a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}

}