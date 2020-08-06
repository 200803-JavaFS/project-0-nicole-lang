package com.revature;

public class Customer implements User {
	
	//A Customer IS A User
	
	//A customer's available actions are deposit, withdrawal, and transfer

	String realName;
	String userName;
	String password;
	
	//Only customer accounts have a balance.
	double balance;
	
	//Customers have View and Update permissions but only for their own accounts.
	public Customer() {
		super();
	}

	public String printAccountInfo() {
		String output = this.realName + "\n Balance: " + this.balance;
		return output;
	}

	public String withdraw(double amt) {
		if(amt > 0 && amt < balance)
		{
			this.balance -= amt;
			return "Withdrawal successful. Balance is now $" + balance;
		}
		else if(amt < 0) 
			return "You cannot withdraw a negative amount of money; please use deposit to add funds";
		else
			return "Insufficient funds";
			
	}
	public String deposit(double amt) {
		if(amt > 0)
		{
			this.balance += amt;
			return "Deposit successful. Balance is now $" + balance;
		}
		else
			return("You cannot deposit a negative amount of money; please use withdraw to remove funds");
	}
	public String transfer(double amt, Customer targetUser)
	{
		//validation of target user will be done in the driver
		targetUser.deposit(amt);
		this.balance -= amt;
		return "Transfer successful. Your balance is now $" + this.balance;
	}
	public void transferDeposit(double amt)
	{
		//change balance without returning a result, to hide target user's balance
		balance += amt;
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

}
