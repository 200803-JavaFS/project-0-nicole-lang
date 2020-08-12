package com.revature.models;

public abstract class Employee{

//	An employee can view but not edit the information of any of their own customers.
//	They also can approve/deny account creation requests.

//	Permissions for Employee users: View, Update(only to change default user into customer), 
//	Delete(only if deleting an unregistered User)
	
	/*	Select operation:
	 * 	(1) list pending account creation requests
	 * 	(2) list your active customers
	 * 	(x) log out
	 * */
	public static final String actions = "Select operation:\n(1) approve an account request"
			+ "\n(2) delete an account request" + "\n(x) log out";
	public static final String accountActions = "Type a username to view customer information.";
	
	//todo: move methods to DAO
	public static String listCustomers(String empUserName)
	{
		String empCustomers = "";
		//Run SQL stored procedure for retrieving a list of customers whose linked employee == empUserName
		//return the retrieved list in String format
		return empCustomers.toString();
	}
	public static String listRequests()
	{
		String requests = "";
		//Run SQL stored procedure for retrieving a list of inactive users
		//return the retrieved list in String format
		return requests.toString();
	}
	public static void deleteUser(String userName)
	{
		//run query to delete the database record of a user from the request list
	}
}
