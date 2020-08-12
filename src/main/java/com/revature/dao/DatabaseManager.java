package com.revature.dao;
import com.revature.models.User;

public interface DatabaseManager {
	//DAO interface to provide methods which run queries on the DeltaSavings database and return the results

	//There should be a separate User manager and Account manager interface, for each table
	
	public static void updateBalance(String currentUser, double newBalance)
	{
		//Update Users Set Balance = @newBalance Where UserName = @currentUser 
	}
	public static void createUser(User newUser)
	{
		//Insert Into Users Values (@userName, @activeUser, @userType, @realName, @password)
	}
	public static User login(String userName, String password)
	{
		User u = new User();
		//Select * From Users Where UserName = @userName And Password = @password
		//populate the current user's fields if a matching record is found
		return u;
	}
	public static User getUser(String userName)
	{
		//similar to login but doesn't check password; used by employees and admins to view other users' inf
		User u = new User();
		//Select * From Users Where UserName = @userName
		return u;
	}
	public static String getUserType(String userName)
	{
		String type = "Customer"; //default type
		//Return only the user type of a selected user, to check permissions/what information to display
		//Select UserType from Users Where UserName = @userName
		return type;
	}
	public static String listEmpCustomers(String empUserName)
	{
		String empCustomers = "";
		//Run SQL stored procedure for retrieving a list of customers whose linked employee == empUserName
		//return the retrieved list in String format
		return empCustomers;
	}
	public static String listRequests()
	{
		String requests = "";
		//Run SQL stored procedure for retrieving a list of inactive users
		//return the retrieved list in String format
		return requests;
	}
	public static String listUsers()
	{
		String allUsers = "";
		//retrieve list of all users in the database
		//Select * From Users Order by UserName
		return allUsers;
	}
	public static void deleteUser(String userName)
	{
		//run query to delete the database record of a user from the request list
	}

}
