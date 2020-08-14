package com.revature.service;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.models.User;

public class Driver {
	//static variables accessible by all driver methods
	public static String input;
	public static String result;
	public static double amt;
	public static User u;
	public static String userType;
	public static Account targetAccount;
	public static final Logger log = LogManager.getLogger();
	public static void main(String[] args){

		//initialize variables
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

					if(UserManager.userExists(userN))
					{//User exists
						u = DatabaseManager.login(userN, passW);
						userType = u.getUserType();

						if(userType.equals("Customer"))
						{
							u.setAccount(DatabaseManager.getAccount(userN));
							printLoginResult(u.getAccount().getStatus());
							
						}else if (userType.equals("Employee") || userType.equals("Admin"))
						{
							loginDone = true;
							log.info("User " + u.getUserName() + " logged in");
							System.out.println("Login successful");
						}

					}else
						System.out.println("User " + userN + " does not exist");
				}
				break;

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
							log.info("New account " + userN + " has been created and is pending activation");
							done = true;
						}
						else
							//prevent duplicate usernames
							log.info("User failed to create account under taken username " + userN);
						System.out.println("That username is taken, try another");
					}
					System.out.println("Account request submitted. Please wait for access.");
					//reset boolean done so execution will continue
					done = false;
					//reset user object
					u = new User();
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
				//If there is a user logged in, display their basic info and prompt with available actions
				System.out.println(UserManager.getUserInfo(u));
				System.out.println(UserManager.getPrompt(u));
				
				input = scan.nextLine();
				switch(input)
				{
				case "1":
					if(userType.equals("Customer"))
						{
							System.out.print("Enter amount to deposit: $");
							amt = scan.nextDouble();							
							result = AccountManager.deposit(u.getAccount(), amt);
							System.out.println(result);
						}
					else
					{//option 1 for employee/admin is to approve a pending account
						System.out.println("Enter the username of the account you want to approve:");
						input = scan.nextLine();
						targetAccount = DatabaseManager.getAccount(input);
						//check if target user account is pending activation
						if(targetAccount.getStatus().equals("Pending"))
						{
							DatabaseManager.approveAccount(input, u.getUserName());
							log.info("User account " + targetAccount.getUserName() + 
									" was approved by super-user " + u.getUserName());
						}
						else if (targetAccount.getUserName().equals(""))
						{
							System.out.println("No bank account with that username");
							log.info("Super-user " + u.getUserName() + "attempted to approve a nonexistent bank account "
									+ "under Username " + input);
						}
					}
				}
				userType = "";
				u = new User();
				log.info("User " + u.getUserName() + " logged out");
				loginDone = false;
			}
			//second check for if user is logged in, outside of initial info display
			//prevents repeat display of lists in every loop iteration
			if(loginDone)
			{
				//prompt with available actions
				System.out.println(UserManager.getPrompt(u));
				input = scan.nextLine();
			}

		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void printLoginResult(String status) {
		//storing this switch statement in its own method so main method isn't as crowded
		switch(status)
		{		
		case "Open":
			log.info("User " + u.getUserName() + " logged in");
			System.out.println("Login successful");
			break;
		case "Pending":
			log.warn("User " + u.getUserName() + " attempted to log to their account but it has not been activated yet.");
			System.out.println("This account hasn't been activated yet, try again later");
			break;
		case "Closed":
			log.warn("User " + u.getUserName() + " attempted to log in to their closed account");
			System.out.println("This account has been closed. Contact admin for assistance.");
			break;
		default:
			log.error("Login method failed to prevent invalid user");
			System.out.println("Error: invalid account status");
			break;
		}
		
	}

	public static void createAccount(String real, String user, String pwd) {
		//prepare data for submission
		u.setFirstName(real.substring(0, real.indexOf(" ")));
		u.setLastName(real.substring(real.indexOf(" ")));
		u.setUserName(user);
		u.setPassword(pwd);

		DatabaseManager.createUser(u);
		System.out.println("Account requested. Please wait for access");
	}

}