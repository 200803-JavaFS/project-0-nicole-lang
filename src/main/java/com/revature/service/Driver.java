package com.revature.service;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.models.User;

public class Driver implements UserManager, AccountManager, DatabaseManager{
	//static variables accessible by all driver methods
	private static String logMessage;
	private static String input;
	private static double result;
	private static double amt;
	private static User u;
	private static String userType;
	private static Account targetAccount;
	private static final String selectionError = "Invalid Selection";
	private static final Logger log = LogManager.getLogger();

	//These arrays are used to save memory by declaring several constants for user types and statuses
	//The employee user type is not specifically used in any checks so it is excluded
	private static final String[] types = {"Customer", "Admin"};
	private static final String[] statuses = {"Pending", "Denied", "Open", "Closed"};

	//main method
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
					System.out.println("Enter your username:");
					userN = scan.nextLine();
					System.out.println("Enter your password:");
					passW = scan.nextLine();
					if(doLogin(userN, passW))
					{
						logMessage = "User " + u.getUserName() + " logged in";
						log.info(logMessage);
						System.out.println("Login successful");
						loginDone = true;
					}
					break;
				}

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
								System.out.println("Username " + u.getUserName() + " is available. "
										+ "Enter a password:");
								passW = scan.nextLine();
								if(passW.isEmpty())
									//skip account creation if no password is provided
									System.out.println("Password cannot be blank. Try again");
								else {
									createAccount(fullN, userN, passW);
									logMessage = "New account " + userN + " has been created and is "
											+ "pending activation";
									log.info(logMessage);
								}
								done = true;
							}
							else				
							{//prevent duplicate usernames
								logMessage = "User failed to create account under taken username " + userN;
								log.error(logMessage);
								System.out.println("That username is taken, try another "
										+ "(or press enter to cancel)");
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
					System.out.println(selectionError);
					break;
				}
			}
			else
			{
				//If there is a user logged in, display their basic info and prompt with available actions
				System.out.println(UserManager.getUserInfo(u));
				System.out.println(UserManager.getPrompt(userType));

				input = scan.nextLine();
				if(userType.equals(types[0]))
					doBanking(u.getAccount(), input, scan);
				else if (!userType.isEmpty()){
					switch(input)
					{
					case "1":
						//option 1 for employee/admin is to approve a pending account
						System.out.println("Enter the username of the account you want to approve:");
						input = scan.nextLine();
						targetAccount = DatabaseManager.getAccount(input);
						//check if target user account is pending activation
						if(targetAccount.getStatus().equals(statuses[0]))//Pending
						{
							DatabaseManager.approveAccount(input, u.getUserName());
							logMessage = "User account " + targetAccount.getUserName() + 
									" was approved by super-user " + u.getUserName();
							log.info(logMessage);
						}
						else if (targetAccount.getUserName().equals(""))
						{
							System.out.println("No bank account with that username");
							logMessage = "Super-user " + u.getUserName() + "attempted to "
									+ "approve a nonexistent bank account under Username " + input;
							log.error(logMessage);
						}

						break;
					case "2":
					{//option 2 for employee/admin is deny a pending account
						System.out.println("Enter the username of the account you want to deny:");
						input = scan.nextLine();
						targetAccount = DatabaseManager.getAccount(input);
						//check if target user account is pending activation
						if(targetAccount.getStatus().equals(statuses[0]))//Pending
						{
							DatabaseManager.denyAccountRequest(input, u.getUserName(), statuses[1]);
							logMessage = "Account request for username " + targetAccount.getUserName() 
							+ " was denied by super-user " + u.getUserName();
							log.info(logMessage);
						}
						else if (targetAccount.getUserName().equals(""))
						{
							System.out.println("No account request with that username");
							logMessage = "Super-user " + u.getUserName() + "attempted to deny a "
									+ "nonexistent account request under Username " + input;
							log.error(logMessage);
						}else

							System.out.println("Only accounts with status 'Pending' can be denied.");
					}
					break;
					case "3":						
						if (userType.equals(types[1]))//Admin
						{//Option 3 for admin is close an account
							System.out.println("Enter the username of the account to close:");
							input = scan.nextLine();
							if((DatabaseManager.getAccount(input)).getStatus().equals(statuses[2]))//Open 
							{
								targetAccount = DatabaseManager.getAccount(input);
								DatabaseManager.closeAccount(input);
								log.info("User account " + targetAccount.getUserName() + 
										" was closed by admin " + u.getUserName());
							}
							else if (targetAccount.getUserName().equals(""))
							{
								System.out.println("No bank account with that username");
								logMessage = "Admin " + u.getUserName() + "attempted to close a "
										+ "nonexistent bank account under Username " + input;
								log.info(logMessage);
							}else
								System.out.println("Only accounts with status 'Open' can be closed.");
						}else
							System.out.println("Invalid selection");
						break;
					case "accs":
						//display a given user's account info if the current user is not a customer
						if(!userType.equals(types[0]))
						{
							System.out.println("Enter the username of the account you want to access:");
							input = scan.nextLine();
							if(UserManager.userExists(input))
							{//print the requested user info
								System.out.println(DatabaseManager.getUser(input).toString());

								if(userType.equals(types[1]))//Admin
								{//allow banking actions
									if(UserManager.userExists(input) && DatabaseManager.getAccount(input)
											.getOpenTime().equals(null))
									{//get the requested customer account and perform banking actions
										targetAccount = DatabaseManager.getAccount(input);
										System.out.println(UserManager.getPrompt(types[0]));//Customer
										while(!input.isEmpty())
										{
											input = scan.nextLine();
											doBanking(targetAccount, input, scan);
										}
									}	
								}
							}
						}

						break;
					case "x":
						userType = "";
						logMessage = "User " + u.getUserName() + " logged out";
						log.info(logMessage);
						System.out.println("Logged out of " + u.getUserName());

						//reset user object and loop control variable
						u = new User();
						loginDone = false;
						break;

					default:
						System.out.println(selectionError);
						break;
					}
				}else
					System.out.println("Error: invalid user type");

			}

		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	public static boolean doLogin(String userN, String passW) {
		//login with existing username and password
		boolean success = false;

		if(UserManager.userExists(userN))
		{//Retrieve user record from the database
			u = DatabaseManager.login(userN, passW);
			userType = u.getUserType();
			//check if password matched saved password
			if(userType.equals(""))
			{
				System.out.println("Incorrect password");
				logMessage = "Failed login attempt to user " + userN;
				log.info(logMessage);
			}
			else 
			{//if the user is a customer, get their linked account
				if(userType.equals(types[0]))
				{//print customer login result
					u.setAccount(DatabaseManager.getAccount(userN));
					printLoginResult(u.getAccount().getStatus());
					
					if(u.getAccount().getStatus().equals("Open"))
					{//only log in if the account is open
						success = true;
					}			
				}else if(!userType.equals(""))
					success = true;							
			}
			
		}else
		{
			System.out.println("User " + userN + " does not exist");
		}

		return success;
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
	public static void doBanking(Account a, String selection, Scanner scan)
	{
		String currentUser = u.getUserName();
		targetAccount = new Account();
		switch(selection)
		{
		case "1":
			System.out.print("Enter amount to deposit: $");
			amt = scan.nextDouble();							
			result = AccountManager.deposit(a, amt, currentUser);
			a.setBalance(result);
			break;
		case "2":
			System.out.print("Enter amount to withdraw: $");
			amt = scan.nextDouble();							
			result = AccountManager.withdraw(a, amt, currentUser);
			a.setBalance(result);
			break;
		case "3":
			System.out.println("Enter the username of the account you want to transfer to:");
			input = scan.nextLine();
			if(!DatabaseManager.getAccount(input).getStatus().equals(""))
			{//only allow transfer if receiving account exists and is open
				targetAccount = DatabaseManager.getAccount(input);
				if(targetAccount.getStatus().equals(statuses[2]))//Open
				{
					System.out.println("Enter amount to transfer: $");
					amt = scan.nextDouble();
					AccountManager.transfer(a, amt, targetAccount, currentUser);
				}else
				{
					logMessage = "Transfer to account" + input + " from account " + a.getUserName() 
					+ " failed. Target account is not open and cannot receive funds. Initiating user: "
					+ currentUser;
					log.error(logMessage);
					System.out.println("Target account is not open. Transfer cancelled");
				}
			}
			break;
		default:
			System.out.println(selectionError);
			break;
		}

	}

}