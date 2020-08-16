package com.revature.service;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.models.User;

public class Driver implements UserManager, AccountManager, DatabaseManager{
	//static variables accessible by all driver methods
	private static String input;
	private static double result;
	private static double amt;
	private static User u;
	private static String userType;
	private static Account targetAccount;
	private static final Logger log = LogManager.getLogger();
	
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
					{//Retrieve user record from the database
						u = DatabaseManager.login(userN, passW);
						userType = u.getUserType();

						if(userType.equals("Customer"))
						{//if the user is a customer, get their linked account
							u.setAccount(DatabaseManager.getAccount(userN));
							printLoginResult(u.getAccount().getStatus());
						}
						//finish login procedure
						loginDone = true;
						log.info("User " + u.getUserName() + " logged in");
						System.out.println("Login successful");
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
					{//
						if(!fullN.isEmpty())
						{
							System.out.println("Enter a username:");
							userN = scan.nextLine();
							if(!UserManager.userExists(userN))
							{
								System.out.println("Username " + u.getUserName() + " is available. Enter a password:");
								passW = scan.nextLine();
								if(passW.isEmpty())
								{//exit loop if no password is provided
									System.out.println("Password cannot be blank. Try again");
									done = true;
								}
								createAccount(fullN, userN, passW);
								log.info("New account " + userN + " has been created and is pending activation");
								done = true;
							}
							else				
							{//prevent duplicate usernames
								log.info("User failed to create account under taken username " + userN);
								System.out.println("That username is taken, try another (or press enter to cancel)");
							}
						}else
						{
							System.out.println("Login cancelled");
							done = true;
						}
					}
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
				System.out.println(UserManager.getPrompt(u.getUserType()));

				input = scan.nextLine();
				switch(input)
				{
				case "1":
					if(userType.equals("Customer"))
					{//option 1 for customers is deposit
						System.out.print("Enter amount to deposit: $");
						amt = scan.nextDouble();							
						result = AccountManager.deposit(u.getAccount(), amt);
						u.getAccount().setBalance(result);
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
					break;
				case "2":
					if(userType.equals("Customer"))
					{//option 2 for customer is withdraw
						System.out.print("Enter amount to withdraw: $");
						amt = scan.nextDouble();							
						result = AccountManager.withdraw(u.getAccount(), amt);
						u.getAccount().setBalance(result);
					}else
					{//option 2 for employee/admin is deny a pending account
						System.out.println("Enter the username of the account you want to deny:");
						input = scan.nextLine();
						targetAccount = DatabaseManager.getAccount(input);
						//check if target user account is pending activation
						if(targetAccount.getStatus().equals("Pending"))
						{
							DatabaseManager.denyAccountRequest(input, u.getUserName(), "Denied");
							log.info("Account request for username " + targetAccount.getUserName() + 
									" was denied by super-user " + u.getUserName());
						}
						else if (targetAccount.getUserName().equals(""))
						{
							System.out.println("No account request with that username");
							log.info("Super-user " + u.getUserName() + "attempted to deny a nonexistent account request "
									+ "under Username " + input);
						}else

							System.out.println("Only accounts with status 'Pending' can be denied.");
					}
					break;
				case "3":
					if(userType.equals("Customer"))
					{//Option 3 for customers is transfer
						System.out.println("Enter the username of the account you want to transfer to:");
						input = scan.nextLine();
						if((DatabaseManager.getAccount(input)).getStatus().equals("Open"))
						{
							//get target account
							targetAccount = DatabaseManager.getAccount(input);
							System.out.println("Enter amount to transfer: $");
							amt = scan.nextDouble();
							AccountManager.transfer(u.getAccount(), amt, targetAccount);
						}
					}
					else if (userType.equals("Admin"))
					{//Option 3 for admin is close an account
						System.out.println("Enter the username of the account to close:");
						input = scan.nextLine();
						if((DatabaseManager.getAccount(input)).getStatus().equals("Open")) {
							targetAccount = DatabaseManager.getAccount(input);
							DatabaseManager.closeAccount(input);
							log.info("User account " + targetAccount.getUserName() + 
									" was closed by admin " + u.getUserName());
						}
						else if (targetAccount.getUserName().equals(""))
						{
							System.out.println("No bank account with that username");
							log.info("Admin " + u.getUserName() + "attempted to close a nonexistent bank "
									+ "account under Username " + input);
						}else
							System.out.println("Only accounts with status 'Open' can be closed.");
					}else
						System.out.println("Invalid selection");
					break;
				case "4":
					if(userType.equals("Admin"))
					{
						//todo: implement allowing admin users to access any customer's account info
					}else
						System.out.println("Invalid selection");
					break;
				case "x":
					userType = "";
					log.info("User " + u.getUserName() + " logged out");
					System.out.println("Logged out of " + u.getUserName());
					u = new User();
					loginDone = false;
					break;

				default:
					System.out.println("Invalid selection");
					break;
				}
			}

		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void printLoginResult(String status) {
		//Print message based on status of customer account
		//storing this switch statement in its own method so main method isn't as crowded
		switch(status)
		{		
		case "Open":
			log.info("User " + u.getUserName() + " logged in");
			System.out.println("Login successful");
			break;
		case "Pending":
			log.warn("User " + u.getUserName() + " attempted to log in to their account but it has not "
					+ "been activated yet.");
			System.out.println("This account hasn't been activated yet, try again later");
			break;
		case "Denied":
			log.warn("User " + u.getUserName() + " attempted to log in to their account but their"
					+ "account creation request was denied by " + u.getAccount().getLinkedEmployee());
			System.out.println("Sorry, your account request has been denied. Contact an admin for assistance.");
			break;
		case "Closed":
			log.warn("User " + u.getUserName() + " attempted to log in to their closed account");
			System.out.println("This account has been closed. If you believe this is an error, please"
					+ "contact an admin for assistance.");
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
		//A new account's linked employee and open timestamp are null as these will be set upon approval
		u.setAccount(new Account(user, "Pending", null, 0.00, null));
		//create records for new user and their bank account
		DatabaseManager.createUser(u);
		DatabaseManager.createAccount(u.getAccount());
		System.out.println("Account request submitted. Please wait for access");
	}

}