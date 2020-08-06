package com.models;

import java.util.List;

public class Employee extends Customer{

	public List<Customer> Customers;
	public Customer c;
	//An Employee IS A User; 1 Employee HAS Many Customers
	
	//An employee can view the information of any customer but cannot edit it
	//They also can approve/deny account creation requests.
	
	//Permissions for Employee users: View, Update(only to change default user into customer), 
	//Delete(only if deleting an unregistered User)
	public Employee() {
		super();
		//loadCustomers
	}
	
	//Overloaded printAccountInfo, to select a user to view the info of
	public String printAccountInfo(String user) {
		for(Customer s : Customers) {
			if(s.userName == user)
			{
				c = s;
				break;
			}
		}
		if(c != null)
		{
			String output = "\nUsername: " + this.userName + "\n" + 
				this.realName +  "\nPassword: " + this.password + "\nBalance: " + this.balance;
			return output;
		}
		else
			return "User not found.";
	}

}
