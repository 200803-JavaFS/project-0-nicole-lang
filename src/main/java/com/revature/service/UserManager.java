package com.revature.service;

import com.revature.dao.DatabaseManager;
import com.revature.models.User;

public interface UserManager {

	public static String getUserInfo(User u) {
		String output = "Welcome, " + u.getFirstName() + " " + u.getLastName();
		switch(u.getUserType())
		{//print the appropriate account information / available lists for the type of user
		case "customer":
			//print account balance
			break;
		case "employee":
			output += "--Pending Account Requests--\n" + DatabaseManager.listRequests()
			+ "\n\n--Your Active Customers--" + DatabaseManager.listEmpCustomers(u.getUserName());
			break;
		case "admin":
			output += "--Pending Account Requests--\n" + DatabaseManager.listRequests()
			+ "--User List--\n" + DatabaseManager.listUsers();
			break;
		default:
			output = "Error: Invalid user type";
			break;
		}	
		return output;
	}
	public static String getPrompt(User u)
	{
		String actions;
		switch(u.getUserType())
		{
		case "Customer":
			actions = "Select operation:\n(1) deposit\n(2) withdraw\n(3) transfer"
					+ "\n(x) log out";
			break;
		case "Employee":
			actions = "Select operation:\n(1) approve an account request"
					+ "\n(2) delete an account request" + "\n(x) log out";
			break;
		case "Admin":
			actions = "Select operation:\n(1) approve an account request"
					+ "\n(2) deny an account request" + "\n(3) delete an account"  +"\n(x) log out";
			break;
		default:
			actions = "Invalid user type!";
			break;
		}
		return actions;
	}
	public static boolean userExists(String userName) {
		//check if a user with the specified userName exists
		return (DatabaseManager.getUser(userName).getUserName().equals(userName));
	}

}
