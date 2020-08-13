package com.revature.models;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;

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
		userType = "Customer";
	}
	
	public User(String userName, String firstName, String lastName, String password, String userType) {
		//Overloaded constructor for loading existing user record
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userType = userType;
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
}
