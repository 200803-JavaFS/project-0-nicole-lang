package com.revature.service;

import java.util.*;

import com.revature.models.Customer;

public class Driver {
	//static variables accessible by all driver methods
	static int userType;
	static String currentUser;
	static String input;

	static Customer c = new Customer();
	
	public static void main(String[] args) throws InterruptedException {

		boolean done = false;
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Delta Savings!");

		do {
			if(currentUser.isEmpty())
			{
				System.out.println("Enter '0' to log in or '1' to create an account. Enter 'exit' to quit.");
				input = scan.nextLine();
				switch(input)
				{
				case "0":
				{
					currentUser = login(scan);
					break;
				}
				case "1":{
					createAccount(scan);
					
					break;
				}
				case "exit":
				{
					done = true;
					break;
				}
				default:
					System.out.println("Invalid input");
					break;
				}
			}
			else
			{
				//If there is a user logged in, display their basic info and prompt with available actions
				c.printAccountInfo();
			}
			
		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void createAccount(Scanner s) {
		//do customer account open request
		System.out.println("Enter your full name:");
		c.setRealName(s.nextLine());
		System.out.println("Enter a username:");
		//todo: add check if username is taken and ask for a different one if it is
		c.setUserName(s.nextLine());
		System.out.println("Username " + c.getUserName() + " is available. Enter a password:");
		c.setPassword(s.nextLine());
		//todo: send account open request (add new user to Users table)
		System.out.println("Account requested. Please wait for access");
	}

	private static String login(Scanner s) {
		String userName;
		//do login
		System.out.println("Enter your username:");
		userName = s.nextLine();
		//todo: find database user that matches input username and get their usertype if found
		System.out.println("Enter your password:");
		input = s.nextLine();
		//verify password
		//				if(u.getPassword.equals(input))
		//				  {
		//				  		//retrieve user info from database and save user type
		//				  }
		//				  else
		//				  		System.out.println("Incorrect password");
		//
		
		return userName;
		
	}
	
	public static String withdraw(double amt)
	{
		if(amt > 0 && amt <= c.getBalance())
		{
			c.setBalance(c.getBalance() - amt);
			return("Withdrawal successful. New balance: " + c.getBalance());
		}
		else if(amt < 0) 
			return "You cannot withdraw a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}
	
	
	public static String deposit(double amt)
	{
		if(amt > 0)
		{
			c.setBalance(c.getBalance() + amt);
			return("Deposit successful. New balance: " + c.getBalance());
		}
		else
			return("You cannot deposit a negative amount of money; please use withdraw to remove funds");
	}
	
	
	public static String transfer(double amt, Customer targetUser)
	{
		if(amt > 0 && amt <= c.getBalance())
		{
			c.setBalance(c.getBalance() - amt);
			targetUser.setBalance(c.getBalance() + amt);
			return("Transfer successful");
		}
		else if(amt < 0) 
			return "You cannot transfer a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}

}