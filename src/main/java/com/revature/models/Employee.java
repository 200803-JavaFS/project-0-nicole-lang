package com.revature.models;

public abstract class Employee{

//This abstract class holds Strings that describe Employee permissions.
	/*	Select operation:
	 * 	(1) list pending account creation requests
	 * 	(2) list your active customers
	 * 	(x) log out
	 * */
	public static final String actions = "Select operation:\n(1) approve an account request"
			+ "\n(2) delete an account request" + "\n(x) log out";
	public static final String accountActions = "Type a username to view customer information.";
	
	
}
