package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
	
	//Enable connection to the PostGreSQL DeltaSavings database hosted on AWS
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:postgresql://javafs200803.czwrnxk0mgv6.us-east-2.rds.amazonaws.com:5432/deltasavings";
		String userName = "postgres";
		String password = "password";
		return DriverManager.getConnection(url, userName, password);
	}
}
