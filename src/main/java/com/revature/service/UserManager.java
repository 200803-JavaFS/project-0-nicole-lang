package com.revature.service;

import com.revature.dao.DatabaseManager;
import com.revature.models.User;

public interface UserManager {
		
	//User info service interface
	public static String getUserInfo(User u) {
		
		String output = "Welcome, " + u.getFirstName() + " " + u.getLastName() + "\n";
		switch(u.getUserType())
		{//print the appropriate account information / available lists for the type of user
		case "Customer":
			//print account balance
			output += "Balance: " + u.getAccount().getBalance();
			break;
			
			//for employees and admins, a list of available customer information is printed once upon login
		case "Employee":
			output += "--Pending Account Requests--\n" + DatabaseManager.listRequests()
			+ "\n\n--Your Active Customers--" + DatabaseManager.listEmpCustomers(u.getUserName());
			break;
		case "Admin":
			output += "--Pending Account Requests--\n" + DatabaseManager.listRequests()
			+ "--User List--\n" + DatabaseManager.listUsers();
			break;
		default:
			//The default cases in these methods shouldn't be reached 
			//because user type is validated in the driver
			output = "Error: Invalid user type";
			break;
		}	
		return output;
	}
	public static String getPrompt(String type)
	{//returns a user actions prompt specific to the current user type
		final String logoutPrompt = "\n(x) log out";
		String actions;
		switch(type)
		{
		case "Customer":
			actions = "Select operation:\n(1) deposit\n(2) withdraw\n(3) transfer"
					+ logoutPrompt;
			break;
		case "Employee":
			actions = "Select operation:\n(1) approve an account request"
					+ "\n(2) delete an account request" + logoutPrompt;
			break;
		case "Admin":
			actions = "Select operation:\n(1) approve an account request"
					+ "\n(2) deny an account request" + "\n(3) close an account\n(4) access a customer account" + logoutPrompt;
			break;
		default:
			actions = "No prompt found";
			break;
		}
		return actions;
	}
	public static boolean userExists(String userName) {
		//check if a user with the specified userName exists
		return (DatabaseManager.getUser(userName).getUserName().equals(userName));
	}

}
