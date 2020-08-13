package com.revature.models;
import java.io.Serializable;

public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	//The Account class represents a single record stored in the Accounts table.
	
	private String userName;
	private String status;
	private String linkedEmployee;
	private double balance;
	
	public Account() {
		userName = "";
		status = "";
		linkedEmployee = "";
		balance = 0;
	}
	public Account(String userName, String status, String linkedEmployee, double balance)
	{//Overloaded constructor for loading existing account record
		this.userName = userName;
		this.status = status;
		this.linkedEmployee = linkedEmployee;
		this.balance = balance;
	}
	
	//declare getters/setters
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLinkedEmployee() {
		return linkedEmployee;
	}

	public void setLinkedEmployee(String linkedEmployee) {
		this.linkedEmployee = linkedEmployee;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
