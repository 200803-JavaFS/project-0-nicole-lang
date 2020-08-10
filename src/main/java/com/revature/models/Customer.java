package com.revature.models;

public class Customer extends User {
	
	//A Customer IS A User
	
	//A customer's available actions are deposit, withdrawal, and transfer

	//Only customer accounts have a balance.
	boolean activeUser;
	double balance;
	
	//Customers have View and Update permissions but only for their own accounts.
	public Customer() {
		//initialize variables
		this.realName = "";
		this.userName = "";
		this.password = "";
		this.balance = 0.00;
	}

	public String printAccountInfo() {
		String output = this.realName + "\n Balance: " + this.balance;
		return output;
	}
	public boolean getActive() {
		return activeUser;
	}

	public void setActive() {
		this.activeUser = !activeUser;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
