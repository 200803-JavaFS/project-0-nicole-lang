package com.revature.models;

//import the more specific user models to gain access to their methods
import com.revature.models.Employee;
import com.revature.models.Admin;

public class User {

	//Declare class-level variables
	String realName;
	String userName;
	String password;
	String accountType;
	boolean activeUser;
	double balance;
	
	public User() {
		//initialize variables
		this.realName = "";
		this.userName = "";
		this.password = "";
		this.balance = 0.00;
		this.activeUser = false;
	}

	public String printAccountInfo() {
		String output="";
		switch(accountType)
		{//print the appropriate account information for the type of user
			case "customer":
				output = this.realName + "\n Balance: " + this.balance;
				break;
			case "employee":
				output = "Welcome, "+ userName;
				break;
			case "admin":
				output = "\nUsername: " + userName + "\n" + 
						realName +  "\nPassword: " + password + "\nBalance: " + balance;
				break;
		}
		
		return output;
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
