package com.revature;

public class Employee extends User{

	//An Employee IS A User; 1 Employee HAS Many Customers
	
	//An employee can view the information of any customer but cannot edit it
	//They also can approve/deny account creation requests.
	
	//Permissions for Employee users: View, Update(only to change default user into customer), 
	//Delete(only if deleting an unregistered User)
	public Employee() {
		super();
	}

}
