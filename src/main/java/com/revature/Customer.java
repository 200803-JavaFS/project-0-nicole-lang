package com.revature;

public class Customer extends User {
	//A Customer IS A User
	
	//A customer's available actions are deposit, withdrawal, and transfer
	//Only customer accounts have a balance.
	double balance;
	
	//Customers have View and Update permissions but only for their own accounts.
	public Customer() {
		super();
	}
}
