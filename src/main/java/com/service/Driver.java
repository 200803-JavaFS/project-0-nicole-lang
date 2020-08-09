package com.service;

import java.util.*;

import com.models.*;

public class Driver {
	//static variables accessible by all driver methods
	static int userType;
	static String currentUser;
	static String input;
	
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
				System.out.println();
			}
			
		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void createAccount(Scanner s) {
		//do customer account open request
		Customer c = new Customer();
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


	//public String withdraw(double amt)
	//{
	//	
	//}
	//
	//
	//public String deposit(double amt)
	//{
	//	
	//}
	//
	//
	//public String transfer(double amt)
	//{
	//	
	//}

}