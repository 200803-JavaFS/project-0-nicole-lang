package com.revature.models;

public abstract class Admin{

	//This abstract class holds strings that desribe Admin permissions.
	/*	Select operation:
	 * 	(1) approve an account request
	 * 	(2) deny an account request
	 * 	(x) log out
	 * */
//	Admin users have View, Update, and Delete permissions for all accounts.
	public static final String actions = "Select operation:\n(1) approve an account request"
			+ "\n(2) deny an account request" + "\n(3) delete an account"  +"\n(x) log out";
}
