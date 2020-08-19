package com.revature.service;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.models.User;

public class Driver implements UserManager, AccountManager, DatabaseManager{
	/* The Driver class handles user input and implements the DAO and service layer interfaces.
	 * The currently logged-in user and their user type are stored at class level.
	 * JUnit testing is implemented for the doLogin method. (DriverLoginTest.java)
	 */
	//static variables accessible by all driver methods
	private static String logMessage;
	private static String input;
	private static User u;
	private static String userType;
	private static Account targetAccount;
	private static boolean loginDone = false;
	private static Scanner scan;
	private static final String SELECTION_ERROR = "Invalid Selection";
	private static final Logger log = LogManager.getLogger();

	//These arrays are used to save memory by declaring several constants for user types and statuses
	private static final String[] types = {"Customer", "Admin", "Employee"};
	private static final String[] statuses = {"Pending", "Denied", "Open", "Closed"};

	//main method
	public static void main(String[] args){

		//initialize variables
		String userN;
		String fullN;
		String passW;
		boolean loginInfoDisplayed = false;
		int loginAttempts = 0;
		userType = "";
		boolean done = false;
		scan = new Scanner(System.in);
		System.out.println("Welcome to Delta Savings!");

		do {
			if(!loginDone)
			{
				u = new User();
				System.out.println("Enter '0' to log in or '1' to create an account. Enter 'x' to quit.");
				input = scan.nextLine();
				switch(input)
				{
				case "0":
				{
					System.out.println("Enter your username:");
					userN = scan.nextLine();
					if(UserManager.userExists(userN))
					{//check if user exists before asking for password
						System.out.println("Enter your password:");
						do
						{//allow 3 login attempts before force quit
							passW = scan.nextLine();
							if(doLogin(userN, passW, loginAttempts))
							{
								logMessage = "User " + userN + " logged in";
								log.info(logMessage);
								System.out.println("Login successful\n");
								loginInfoDisplayed = false;
								loginDone = true;
							}else if(DatabaseManager.getUser(userN).getUserType().equals(types[0])
									&& !DatabaseManager.getAccount(userN).getStatus().equals(statuses[2]))
								//terminate application if customer account being logged into is not open
								//a user whose account is inactive shouldn't be able to continue their session
								done = true;
							if(!loginDone)
								loginAttempts++;
							
						}while(loginAttempts < 3 && !loginDone && !done);
						if(loginAttempts == 3)
						{//kick user out if they input incorrect password 3 times
							System.out.println("Sorry, you have exceeded your login attempts for this session.");
							done = true;
						}
					}else
						System.out.println("User not found");
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
								System.out.println("Username " + userN + " is available. "
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
							System.out.println("Login cancelled\n");
							done = true;
						}
					}
					//reset boolean done so execution will continue
					done = false;
					//reset user object
					u = new User();
					break;
				}
				case "x":
				{
					done = true;
					break;
				}
				default:
					System.out.println(SELECTION_ERROR);
					break;
				}
			}
			else
			{
				//If there is a user logged in, display their basic info and prompt with available actions
				if(!loginInfoDisplayed)
				{//only display initial login message and lists once
					System.out.println(UserManager.getUserInfo(u));
					loginInfoDisplayed = true;
				}
				System.out.println(UserManager.getPrompt(userType));

				input = scan.nextLine();
				if(userType.equals(types[0]))
					doBanking(u.getAccount(), input);

				else if (!userType.isEmpty()){
					switch(input)
					{
					case "1":
						//option 1 for employee/admin is to approve a pending account
						if(!DatabaseManager.listRequests().isEmpty())
						{
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
						}else
							System.out.println("There are no pending account requests.\n");
						break;
					case "2":
					{//option 2 for employee/admin is deny a pending account
						if(!DatabaseManager.listRequests().isEmpty())
						{
							System.out.println("Enter the username of the account you want to deny:");
							input = scan.nextLine();
							targetAccount = DatabaseManager.getAccount(input);
							//check if target user account is pending activation
							if(targetAccount.getStatus().equals(statuses[0]))//Pending
							{//success
								DatabaseManager.denyAccountRequest(input, u.getUserName());
								logMessage = "Account request for username " + targetAccount.getUserName() 
								+ " was denied by super-user " + u.getUserName();
								log.info(logMessage);
							}
							else if (targetAccount.getUserName().equals(""))
							{//failure
								System.out.println("No account request with that username");
								logMessage = "Super-user " + u.getUserName() + "attempted to deny a "
										+ "nonexistent account request under Username " + input;
								log.error(logMessage);
							}else

								System.out.println("Only accounts with status 'Pending' can be denied.\n");
						}else
							System.out.println("There are no pending account requests.\n");					
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
								System.out.println("No bank account with that username\n");
								logMessage = "Admin " + u.getUserName() + "attempted to close a "
										+ "nonexistent bank account under Username " + input;
								log.info(logMessage);
							}else
								System.out.println("Only accounts with status 'Open' can be closed.\n");
						}else
							System.out.println(SELECTION_ERROR + "\n");
						break;
					case "accs":
						doAccountManagement();
						break;
					case "x":
						doLogout(false);
						loginInfoDisplayed = false;
						break;

					default:
						System.out.println(SELECTION_ERROR);
						break;
					}
				}else
					System.out.println("Error: invalid user type");

			}

		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

	private static void doAccountManagement() {
		//account management handling for admins and employees
		userType = u.getUserType();
		User targetUser = new User();
		if(getEmpCustomers(u.getUserName()).isEmpty())
			System.out.println("You don't have any viewable customer accounts.");

		//display a given user's account info if the current user is not a customer
		else if(!userType.equals(types[0]))
		{
			System.out.println("Enter the username of the account you want to access:");
			input = scan.nextLine();
			if(UserManager.userExists(input))
			{
				targetUser = DatabaseManager.getUser(input);
			}
			if(checkPrivileges(u, targetUser))
			{//get account info if it exists, then print available info
				if(targetUser.getUserType().equals(types[0]))
				{//if selected user is a customer, determine whether to allow banking access
					targetUser.setAccount(DatabaseManager.getAccount(input));
					if(userType.equals(types[1]))//Admin
					{//print the info and allow banking access
						System.out.println(targetUser.toString());
						while(!input.equals("x")){
							System.out.println(UserManager.getPrompt(types[0]));
							input = scan.nextLine();
							doBanking(targetUser.getAccount(), input);
						}				
					}else
						System.out.println(targetUser.toString());
				}else
					System.out.println(targetUser.toString());
				
			}else
				System.out.println("You don't have access to this account.\n");
		}else
			System.out.println("You don't have access to this command.\n");
	}

	private static boolean checkPrivileges(User currentUser, User target) {
		boolean result = false;
		switch(userType)
		{
			case "Employee":
				//check employee customer list
				for(Account a: getEmpCustomers(currentUser.getUserName()))
				{
					if(a.getUserName().equals(target.getUserName()))
						return true;
				}
					System.out.println("You don't have access to this account\n");
				break;
			case "Admin":
				result = true;
				break;
		}
		return result;
	}

	private static void doLogout(boolean adminBanking) {
		if(!adminBanking) 
		{//log out if account being accessed is the same as the one logged in
			userType = "";
			logMessage = "User " + u.getUserName() + " logged out";
			log.info(logMessage);
			System.out.println("Logged out of " + u.getUserName() + "\n");

			//reset user object and loop control variable
			u = new User();
			loginDone = false;	
		}
		
	}

	private static List<Account> getEmpCustomers(String currentUser) {
		return DatabaseManager.listEmpCustomers(currentUser);

	}

	public static boolean doLogin(String userN, String passW, int attempts) {
		//login with existing username and password
		boolean success = false;

		//Retrieve user record from the database
		u = new User();
		u = DatabaseManager.login(userN, passW);
		userType = u.getUserType();
		//check if password matched saved password
		if(userType.equals(""))
		{
			System.out.println("Incorrect password. Try again (Attempts remaining: " + (3 - attempts) + ")");
			if((3 - attempts) > 0)
				logMessage = "Failed login attempt to user " + userN;
			else
				logMessage = "User " + userN + " exceeded their login attempts and was kicked out";
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

		return success;
	}

	private static void printLoginResult(String status) {
		//Print message instead of logging in if customer account isn't open
		//storing this switch statement in its own method so main method isn't as crowded
		switch(status)
		{
		case "Pending":
			log.warn("User " + u.getUserName() + " attempted to log in to their account but it has not "
					+ "been activated yet.");
			System.out.println("This account hasn't been activated yet, try again later\n");
			break;
		case "Denied":
			log.warn("User " + u.getUserName() + " attempted to log in to their account but their"
					+ "account creation request was denied by " + u.getAccount().getLinkedEmployee());
			System.out.println("Sorry, your account request has been denied. Contact an admin for assistance.\n");
			break;
		case "Closed":
			log.warn("User " + u.getUserName() + " attempted to log in to their closed account");
			System.out.println("This account has been closed. If you believe this is an error, please "
					+ "contact an admin for assistance.\n");
			break;
		default:
			//if status is invalid it means there is no account under the given username. This is okay
			//because there is validation to prevent nonexistent account info from displaying.
			break;
		}

	}

	public static void createAccount(String real, String user, String pwd) {
		//prepare data for submission
		u.setUserType(types[0]);
		u.setFirstName(real.substring(0, real.indexOf(" ")));
		u.setLastName(real.substring(real.indexOf(" ") + 1));
		u.setUserName(user);
		u.setPassword(pwd);
		//A new account's linked employee and open timestamp are null as these will be set upon approval
		u.setAccount(new Account(user, "Pending", null, 0.00, null));
		//create records for new user and their bank account
		DatabaseManager.createUser(u);
		DatabaseManager.createAccount(u.getAccount());
		System.out.println("Account request submitted. Please wait for access\n");
	}

	public static void doBanking(Account a, String selection)
	{//perform customer bank account actions
		double result;
		double amt;
		String currentUser = u.getUserName();
		targetAccount = new Account();
		switch(selection)
		{
		case "1":
			System.out.print("Enter amount to deposit: $");
			amt = scan.nextDouble();						
			scan.nextLine();
			result = AccountManager.deposit(a, amt, currentUser);
			a.setBalance(result);
			break;
		case "2":
			System.out.print("Enter amount to withdraw: $");
			amt = scan.nextDouble();		
			scan.nextLine();
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
					scan.nextLine();
					AccountManager.transfer(a, amt, targetAccount, currentUser);
				}else
				{
					logMessage = "Transfer to account" + input + " from account " + a.getUserName() 
					+ " failed. Target account is not open and cannot receive funds. Initiating user: "
					+ currentUser;
					log.error(logMessage);
					System.out.println("Target account is not open. Transfer cancelled\n");
				}
			}
			break;
		case "x":
			if(a.getUserName().equals(u.getUserName()))
				doLogout(false);
			else
				doLogout(true);
			break;
		default:
			System.out.println(SELECTION_ERROR);
			break;

		}
	}
}
