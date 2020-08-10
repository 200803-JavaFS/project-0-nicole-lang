package com.revature.service;

import java.util.*;

import com.revature.models.User;

public class Driver {
	//static variables accessible by all driver methods
	static String currentUser;
	static String input;

	static User u;
	static String userType;
	public static void main(String[] args) throws InterruptedException {
		String userN;
		String fullN;
		String passW;
		u = new User();
		
		userType = "";
		boolean done = false;
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Delta Savings!");

		do {
			if(userType == "")
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
					//todo: find database user that matches input username and get their usertype if found
					System.out.println("Enter your password:");
					passW = scan.nextLine();
					//todo: check if password matches the username's associated password
					login(userN);
					break;
				}
				case "1":{
					//create new user account
					System.out.println("Enter your full name:");
					fullN = scan.nextLine();
					System.out.println("Enter a username:");
					userN = scan.nextLine();
					//todo: check availability of user name
					System.out.println("Username " + u.getUserName() + " is available. Enter a password:");
					passW = scan.nextLine();
					createAccount(fullN, userN, passW);
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
				
				switch(userType)
				{
				case "customer":
					
					break;
				case "employee":
					break;
				case "admin":
					break;
				}
				
				u.printAccountInfo();
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
		u.setAccountType("customer");
		//todo: send account open request (add new user to Users table)
		System.out.println("Account requested. Please wait for access");
	}

	private static String login(String user) {
		//do login
		
		//verify password
		//		if(u.getPassword.equals(input))
		//		{
		//			//retrieve user info from database and save user type
		//		}
		//		else
		//			System.out.println("Incorrect password");
		//
		userType = "customer";
		return("In login method");
		
		
	}
	
	public static String withdraw(double amt)
	{
		if(amt > 0 && amt <= u.getBalance())
		{
			u.setBalance(u.getBalance() - amt);
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
			targetUser.setBalance(u.getBalance() + amt);
			return("Transfer successful");
		}
		else if(amt < 0) 
			return "You cannot transfer a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
	}

}