package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
	/*This utility class creates a Connection object and is used in the DAO methods to
	 * transmit data to and from the DeltaSavings database. The DriverManager class is
	 * used to establish the connection.
	 */
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:postgresql://javafs200803.czwrnxk0mgv6.us-east-2.rds.amazonaws.com:5432/deltasavings";
		String userName = "postgres";
		String password = "password";
		return DriverManager.getConnection(url, userName, password);
	}
}
