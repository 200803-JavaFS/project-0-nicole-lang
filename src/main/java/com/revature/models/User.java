package com.revature.models;

public class User {

	//Declare class-level variables
	final String defaultActions = "Select operation:\n(1) deposit\n(2) withdraw\n(3) transfer"
			+ "\n(x) log out";
	
	String realName;
	String userName;
	String password;
	String accountType;
	boolean activeUser;
	double balance;
	
	public User() {
		//initialize variables
		realName = "";
		userName = "";
		password = "";
		balance = 0.00;
		activeUser = false;
	}

	public String printAccountInfo() {
		String output = "Welcome, " + realName;
		switch(accountType)
		{//print the appropriate account information / available lists for the type of user
			case "customer":
				output += "Balance: " + balance;
				break;
			case "employee":
				output += "--Pending Account Requests--\n" + Employee.listRequests()
					+ "\n\n--Your Active Customers--" + Employee.listCustomers(userName);
				break;
			case "admin":
				output += "--Pending Account Requests--\n" + Employee.listRequests()
					+ "--User List--\n" + Admin.listUsers();
				break;
		}	
		return output;
	}
	public String getPrompt()
	{
		String actions;
		switch(accountType)
		{
		case "Customer":
			actions = defaultActions;
			break;
		case "Employee":
			actions = Employee.actions;
			break;
		case "Admin":
			actions = Admin.actions;
			break;
		default:
			actions = "Invalid user type!";
			break;
		}
		return actions;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getActive() {
		return activeUser;
	}

	public void setActive(boolean val) {
		this.activeUser = val;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getAccountType() {
		return accountType;
		
	}

	public void setAccountType(String type) {
		accountType = type;
	}

}
