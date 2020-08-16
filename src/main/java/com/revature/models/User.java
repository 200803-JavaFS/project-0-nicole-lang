package com.revature.models;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	//The User class represents a single record in the Users table.
	
	private String userName;	
	private String firstName;
	private String lastName;
	private String password;
	private String userType;
	private Account bankAccount;
	
	public User() {
		//initialize variables
		userName = "";
		firstName = "";
		lastName = "";
		password = "";
		userType = "";
		bankAccount = new Account();
	}
	
	public User(String userName, String firstName, String lastName, String password, String userType) {
		//Overloaded constructor for loading existing user record
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userType = userType;
		bankAccount = new Account();
	}
	@Override
	public String toString()
	{//usertype		Username = username		Name = firstname lastname
		return (userType + "\tUsername = "+ userName + "\tName = "+ firstName + " " + lastName 
				+ "\t" + bankAccount.toString());
	}
	//declare getters and setters

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserType() {
		return userType;	
	}

	public void setUserType(String type) {
		userType = type;
	}

	public void setAccount(Account account) {
		//Assign a bank account to a customer user
		bankAccount = account;
		
	}

	public Account getAccount() {
		return bankAccount;
	}

}
