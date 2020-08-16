package com.revature.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionUtility;
public interface DatabaseManager {
	
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
	{//add a new user to the Users table
		try(Connection conn = ConnectionUtility.getConnection()){

			String sql = "Insert Into Users Values (?, ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);

			int i = 0;
			statement.setString(++i, newUser.getUserName());
			statement.setString(++i, newUser.getUserType());
			statement.setString(++i, newUser.getFirstName());
			statement.setString(++i, newUser.getLastName());
			statement.setString(++i, newUser.getPassword());

			statement.executeUpdate();

		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
	}
	public static void createAccount(Account newAccount)
	{//add a new account to the Accounts table
		try(Connection conn = ConnectionUtility.getConnection()){

			String sql = "Insert Into Accounts Values (?, ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);

			int i = 0;
			statement.setString(++i, newAccount.getUserName());
			statement.setString(++i, newAccount.getStatus());
			statement.setString(++i, newAccount.getLinkedEmployee());
			statement.setDouble(++i, newAccount.getBalance());
			statement.setTimestamp(++i, saveDateTime());
			statement.executeUpdate();

		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
	}
	public static User login(String userName, String password)
	{//retrieve the personal information of a logged-in user
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
		//retrieve the account information of a given customer
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
				a.setOpenTime(result.getTimestamp("open_timestamp"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	public static User getUser(String userName)
	{//retrieve the personal information of a given user
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
	{//retrieve only the user type of a given user
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
	{//retrieve a list of accounts with the current user as their linked employee
		Account a = new Account();
		String empCustomers = "";
		String sql = "Select * from accounts where linked_employee = ?;";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, empUserName);
			
			ResultSet result = statement.executeQuery();

			//get all matching rows
			while(result.next())
			{
				a.setUserName(result.getString("user_name"));
				a.setStatus(result.getString("account_status"));
				a.setLinkedEmployee(result.getString("linked_employee"));
				a.setOpenTime(result.getTimestamp("open_timestamp"));
				//Account.toString() doesn't include the username because it's usually accessed alongside User.toString()
				empCustomers += a.getUserName() + "\t" + a.toString() + "\n";
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(empCustomers.isEmpty())
			return "none\n";
		else
			return empCustomers;
	}
	public static String listRequests()
	{//retrieve a list of pending account requests; formatted using overridden toString()
		Account a = new Account();
		String requests = "";
		String sql = "Select * from accounts where account_status = 'Pending';";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			//get all matching rows
			while(result.next())
			{
				a.setUserName(result.getString("user_name"));
				a.setStatus(result.getString("account_status"));
				a.setLinkedEmployee(result.getString("linked_employee"));
				a.setOpenTime(result.getTimestamp("open_timestamp"));
				//Account.toString() doesn't include the username because it's usually accessed alongside User.toString()
				requests += a.getUserName() + "\t" + a.toString() + "\n";
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(requests.isEmpty())
			return "none\n";
		else
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
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
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
		String sql = "Update accounts set account_status = 'Open', linked_employee = ?, open_timestamp = ? where user_name = ?";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, empName);
			statement.setTimestamp(2, saveDateTime());
			statement.setString(3, userName);
			
			statement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void denyAccountRequest(String userName, String linkedEmployee, String status) {
		//set a given customer's account status to Closed
				String sql = "Update accounts set account_status = 'Denied', linked_employee = ? where user_name = ?";
				try(Connection conn = ConnectionUtility.getConnection()){

					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setString(1, status);
					statement.setString(2, linkedEmployee);
					statement.setString(3, userName);
					statement.executeUpdate();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
		
	}
	public static void closeAccount(String userName)
	{
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
	public static Timestamp saveDateTime()
	{//access stored function to get a timestamp 
		String sql = "Select get_current_time();";
		try(Connection conn = ConnectionUtility.getConnection()){

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeQuery();
			ResultSet result = statement.getResultSet();
			return result.getTimestamp(1);
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
