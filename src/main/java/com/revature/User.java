package com.revature;

public class User {
	//User will be an interface in next build
	boolean isAdmin;
	String realName;
	String username;
	String password;
	//balance is customer-only; will be removed
	double balance;
	
	//create user object to retrieve/save info
	public User() {
		super();
		isAdmin = false;
	}

	//return this user's account information
	public String getAccountInfo()
	{
		String output = this.realName + "\nUsername: " + 
				this.username + "\nPassword: " + this.password + "\n Balance: " + this.balance;
		return output;
	}

	//getters/setters
	public String getRealName() {
		return realName;
	}
	public void setRealName(String name) {
		this.realName = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
