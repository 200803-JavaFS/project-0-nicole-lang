package com.revature;

import java.util.*;

public class Driver {

	public static void main(String[] args) {
		boolean done = false;
		int userType;
		Scanner scan = new Scanner(System.in);
		String input;
		System.out.println("Welcome to Delta Savings!");
		
		do {
			System.out.println("Enter '0' to log in or '1' to create an account.");
			input = scan.nextLine();
			if(input.equals("0"))
			{
				//do login
				System.out.println("Enter your username:");
				input = scan.nextLine();
				//todo: find database user that matches input username and get their usertype if found
				System.out.println("Enter your password:");
				input = scan.nextLine();
				//verify password
				/*if(u.getPassword.equals(input))
				 * {
				 * 		//display user's basic info and prompt with available actions
				 * }
				 * else
				 * 		System.out.println("Incorrect password");
				*/
			}
			else if(input.equals("1"))
			{
				//do customer account open request
				Customer c = new Customer();
				System.out.println("Enter your full name:");
				c.setRealName(scan.nextLine());
				System.out.println("Enter your username:");
				//todo: add check if username is taken and ask for a different one if it is
				c.setUsername(scan.nextLine());
				System.out.println("Enter your password:");
				c.setPassword(scan.nextLine());
				//todo: send account open request
				System.out.println("Account requested. Please wait for access");
				
			}
			else
				System.out.println("Invalid input");
		}while (!done);
		System.out.println("Thank you for using Delta Savings, have a nice day!");
		scan.close();
	}

}