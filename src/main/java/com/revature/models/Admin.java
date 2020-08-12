package com.revature.models;

public abstract class Admin{

//	An admin can carry out all the same methods as a Customer or Employee, on all accounts
//	They also have the ability to cancel (delete) registered accounts, except for their own.
	
//	Admin users have View, Update, and Delete permissions for all accounts.
	public static final String actions = "Select operation:\n(1) approve an account request"
			+ "\n(2) deny an account request" + "\n(3) delete an account"  +"\n(x) log out";

	
	//todo:move methods to DAO
	public static String listUsers()
	{
		//retrieve list of all users in the database
		//Select * From Users Order by UserName
		return null;
	}
	
	public static void deleteUser(String userName)
	{
		//run query to delete user with the specified username
	}
}
