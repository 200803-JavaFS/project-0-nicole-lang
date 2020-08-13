package com.revature.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionUtility;
public interface DatabaseManager {
	/*
	 * try(Connection conn = ConnectionUtility.getConnection()){
	 * 
	 * }catch(SQLException e) { e.printStackTrace() }
	 */

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
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "Select * From Users Where UserName = ? And Password = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, userName);
			statement.setString(2, password);
			
			//executeQuery is used when we are retrieving a result set; execute is used otherwise
			ResultSet result = statement.executeQuery();
			if (result.next())
			{//store results in user object
				u.setUserName(result.getString("user_name"));
				u.setUserType(result.getString("user_type"));
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
				u.setPassword(result.getString("user_password"));
				return u;
			}
		}catch(SQLException e) { 
			e.printStackTrace(); 
		}

		return u;
	}
	public static Account getAccount(String userName) {
		Account a = new Account();

		//if user has a bank account return the account object; if not return empty account
		if(a.getUserName().equals(userName))
			return a;
		else
			return new Account();
	}
	public static User getUser(String userName)
	{
		//similar to login but doesn't check password; used by employees and admins to view other users' inf
		User u = new User();
		try(Connection conn = ConnectionUtility.getConnection()){
			//prepare SQL query
			String sql = "Select * from users where user_name = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userName);

			//get results
			ResultSet result = statement.executeQuery();
			if (result.next())
			{//store results in user object
				u.setUserName(result.getString("user_name"));
				u.setUserType(result.getString("user_type"));
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
				u.setPassword(result.getString("user_password"));
				return u;
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

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
		String sql = "Select * from accounts where account_status = 'Pending';";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			while(result.next()) {

			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		String requests = "";
		//Run SQL stored procedure for retrieving a list of inactive accounts
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
		//run query to set an account's status to closed
	}

}
