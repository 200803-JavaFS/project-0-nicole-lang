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
		try(Connection conn = ConnectionUtility.getConnection()){

			String sql = "Update Accounts Set account_balance = ? Where user_name = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(1, newBalance);
			statement.setString(2, currentUser);

			statement.execute();

		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
		//Update Users Set Balance = @newBalance Where UserName = @currentUser 
	}
	public static void createUser(User newUser)
	{
		try(Connection conn = ConnectionUtility.getConnection()){

			String sql = "Insert Into Users Values (?, ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);

			int i = 0;
			statement.setString(++i, newUser.getUserName());
			statement.setString(++i, newUser.getUserType());
			statement.setString(++i, newUser.getFirstName());
			statement.setString(++i, newUser.getLastName());
			statement.setString(++i, newUser.getPassword());

			statement.execute();

		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
	}
	public static User login(String userName, String password)
	{
		User u = new User();
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "Select * From Users Where user_name = ? And user_password = ?";
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
		try(Connection conn = ConnectionUtility.getConnection()){

			String sql = "Select * from Accounts where user_name = ?";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, userName);
			ResultSet result = statement.executeQuery();

			if(result.next())
			{
				a.setUserName(result.getString("user_name"));
				a.setStatus(result.getString("account_status"));
				a.setLinkedEmployee(result.getString("linked_employee"));
				a.setBalance(result.getDouble("account_balance"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return a;
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
		String type = "";
		try(Connection conn = ConnectionUtility.getConnection()){
			//prepare SQL query
			String sql = "Select user_type from users where user_name = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userName);

			//get result
			ResultSet result = statement.executeQuery();
			if (result.next())
			{//store user type
				type = result.getString("user_type");
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
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
		User u = new User();
		String requests = "";
		String sql = "Select * from users join accounts on (users.user_name = accounts.user_name)"
				+ "where account_status = 'Pending';";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			//get all matching rows
			while(result.next())
			{
				u.setUserName(result.getString("user_name"));
				u.setFirstName(result.getString("first_name"));
				u.setLastName(result.getString("last_name"));
				u.setUserType(result.getString("user_type"));
				requests += u.toString() + "\n";
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return requests;

	}
	public static String listUsers()
	{
		String allUsers = "";
		User u = new User();
		String sql = "Select * from users order by user_name;";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			//get all matching rows
			while(result.next())
			{
				u.setUserName(result.getString("user_name"));
				u.setFirstName(result.getString("first_name"));
				u.setLastName(result.getString("last_name"));
				u.setUserType(result.getString("user_type"));
				allUsers += u.toString() + "\n";
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		//retrieve list of all users in the database
		//Select * From Users Order by UserName
		return allUsers;
	}
	public static void approveAccount(String userName, String empName) {
		//set a given customer's account status to Open and their linked employee to whoever approved it
		String sql = "Update accounts set account_status = 'Open', linked_employee = ? where user_name = ?";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, empName);
			statement.setString(2, userName);
			statement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		//set 
		
	}
	public static void closeAccount(String userName) {
		//set a given customer's account status to Closed
		String sql = "Update accounts set account_status = 'Closed' where user_name = ?";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userName);
			statement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
