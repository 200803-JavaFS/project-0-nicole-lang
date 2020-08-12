package com.revature.dao;
import com.revature.models.User;
public class DatabaseManager {

	public static String currentUser;
	public DatabaseManager() {
		// TODO Auto-generated constructor stub
	}
	public static void updateBalance(double newBalance)
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

}
