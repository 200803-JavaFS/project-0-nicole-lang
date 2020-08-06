package com.models;

public class Admin extends Employee{

	//An Admin IS AN Employee
	
	//An admin can carry out all the same methods as a Customer or Employee, on all accounts
	//They also have the ability to cancel (delete) registered accounts, except for their own.
	
	//Admin users have View, Update, and Delete permissions for all accounts.
	public Admin() {
		super();
	}
}
