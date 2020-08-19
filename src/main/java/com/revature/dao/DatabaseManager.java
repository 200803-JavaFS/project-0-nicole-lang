package com.revature.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionUtility;
public interface DatabaseManager {
	
	/* The DatabaseManager interface handles the execution of SQL statements on the DeltaSavings
	 * database. Statements are used for static SQL statements, and PreparedStatements are used when
	 * user input is involved in the statement, to prevent SQL injection.
	 * 
	 * The saveDateTime method accesses the get_current_time() stored procedure.
	 */
	
	public static void updateBalance(String currentUser, double newBalance)
	{//called from deposit, withdraw, and transfer methods to update the account record's balance
		try(Connection conn = ConnectionUtility.getConnection()){

			String sql = "Update Accounts Set account_balance = ? Where user_name = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(1, newBalance);
			statement.setString(2, currentUser);

			statement.execute();

		}catch(SQLException e) { 
			e.printStackTrace(); 
		}
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
			result.next();
			//store user type
			type = result.getString("user_type");

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return type;
	}
	public static List<Account> listEmpCustomers(String empUserName)
	{//retrieve a list of accounts with the current user as their linked employee
		List<Account> customers = new ArrayList<>();
		Account a = new Account();
		String sql = "Select * from accounts where linked_employee = ?";
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
				customers.add(a);
				a = new Account();
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	public static List<Account> listRequests()
	{//retrieve a list of pending account requests
		List<Account> requests = new ArrayList<Account>();
		Account a = new Account();
		String sql = "Select * from accounts where account_status = 'Pending';";
		try(Connection conn = ConnectionUtility.getConnection()){			

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);

			//get all matching rows
			while(result.next())
			{
				a.setUserName(result.getString("user_name"));
				a.setStatus(result.getString("account_status"));
				a.setLinkedEmployee(result.getString("linked_employee"));
				a.setOpenTime(result.getTimestamp("open_timestamp"));
				requests.add(a);
				a = new Account();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
			return requests;

	}
	public static List<User> listUsers()
	{
		//retrieve list of all users in the database
		List<User> allUsers = new ArrayList<>();
		User u = new User();
		String sql = "Select * from users order by user_name;";
		try(Connection conn = ConnectionUtility.getConnection()){

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);

			//get all users
			while(result.next())
			{
				u.setUserName(result.getString("user_name"));
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
				u.setUserType(result.getString("user_type"));
				allUsers.add(u);
				u = new User();
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return allUsers;
	}
	public static void approveAccount(String userName, String empName) {
		//set a given customer's account status to Open and their linked employee to whoever approved it
		String sql = "Update accounts set account_status = 'Open', linked_employee = ?, "
				+ "open_timestamp = ? where user_name = ?;";
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

	public static void denyAccountRequest(String userName, String linkedEmployee) {
		//set a given customer's account status to Closed
				String sql = "Update accounts set account_status = 'Denied', linked_employee = ?, "
						+ "open_time = ? where user_name = ?;";
				try(Connection conn = ConnectionUtility.getConnection()){

					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setString(1, linkedEmployee);
					statement.setTimestamp(2, saveDateTime());
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

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getTimestamp(1);
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
