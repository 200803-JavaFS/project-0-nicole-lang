package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.DatabaseManager;
import com.revature.models.Account;
import com.revature.models.User;

public interface UserManager {
		
	//User info service interface
	public static String getUserInfo(User u) {
		StringBuilder builder = new StringBuilder();		
		builder.append("Welcome, " + u.getFirstName() + " " + u.getLastName() + "\n");
		
		switch(u.getUserType())
		{//print the appropriate account information / available lists for the type of user
		case "Customer":
			//print account balance
			builder.append("Balance: " + u.getAccount().getBalance() + "");
			break;
			
			//for employees and admins, lists of available customer records are printed once upon login
		case "Admin":			
			builder.append("--User List--\n");
			for(User a : DatabaseManager.listUsers())
			{
				builder.append(a.getUserName()+ "\n");
			}
			
		//execution is meant to fall through here because an admin shares access to the employee list types
		case "Employee":			
			builder.append("--Pending Account Requests--\n");
			
			for(Account a : DatabaseManager.listRequests())
			{
				builder.append(a.getUserName() + "\n");
			}
			builder.append("--Your Active Customers--\n");
			for(Account a : DatabaseManager.listEmpCustomers(u.getUserName()))
			{
				builder.append(a.getUserName() + "\n");
			}
			break;
		default:
			//The default cases in these methods shouldn't be reached 
			//because user type is validated in the driver
			builder.append("Invalid user type");
			break;
		}	
		return builder.toString();
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
					+ "\n(2) delete an account request\n(accs) access a customer account" + logoutPrompt;
			break;
		case "Admin":
			actions = "Select operation:\n(1) approve an account request"
					+ "\n(2) deny an account request" + "\n(3) close an account\n(accs) access a customer account" + logoutPrompt;
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
