package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

	public static Connection getConnection() throws SQLException{
		String url = "jdbc:postgresql://javafs200803.czwrnxk0mgv6.us-east-2.rds.amazonaws.com:5432/deltasavings";
		String userName = "postgres";
		String password = "password";
		return DriverManager.getConnection(url, userName, password);
	}
	/*
	 * public static void main(String[] args) { //try w/ resources block; takes a
	 * method that creates a resource and automatically closes it at the //end
	 * without the need for a finally block try(Connection conn =
	 * ConnectionUtility.getConnection()) {
	 * System.out.println("Connection successful"); } catch (SQLException e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
}
