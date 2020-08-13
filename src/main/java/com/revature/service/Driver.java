package com.revature.service;

import java.util.*;
import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.models.User;

public class Driver {
	//static variables accessible by all driver methods
	static String input;

	static User u;
	static Account a;
	static String userType;
	public static void main(String[] args){
		
		//initialize variables
		String userN;
		String fullN;
		String passW;
		boolean loginDone = false;
		u = new User();
		a = new Account();
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
					if(u.getUserName().equals(userN))
					{
						//user exists
					}
					if(userType.equals("Customer"))
					{
						switch(a.getStatus())
						{
						case "Open":

							loginDone = true;
							break;
						case "Pending":
							System.out.println("This account hasn't been activated yet, try again later");
							break;
						case "Closed":
							System.out.println("This account has been closed. Contact admin for assistance.");
							break;
						}
					}else if (u.getUserType().equals("Employee") || u.getUserType().equals("Admin"))
						loginDone = true;
					else
						System.out.println();
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
						if(!UserManager.userExists(userN))
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
				System.out.println(UserManager.getUserInfo(u));
				input = scan.nextLine();
				
			}
			//second check for if user is logged in, outside of initial info display
			//prevents repeat display of lists in every loop iteration
			if(loginDone)
			{
				//prompt with available actions
				System.out.println(UserManager.getPrompt(u));
			}
			
		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void createAccount(String real, String user, String pwd) {
		//create customer account open request
		u.setFirstName(real.substring(0, real.indexOf(" ")));
		u.setLastName(real.substring(real.indexOf(" ")));
		u.setUserName(user);
		u.setPassword(pwd);
		DatabaseManager.createUser(u);
		System.out.println("Account requested. Please wait for access");
	}
	
	//The following methods should be in an AccountManager service class
	

}