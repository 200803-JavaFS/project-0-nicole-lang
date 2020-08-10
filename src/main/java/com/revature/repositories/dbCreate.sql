Create database DeltaSavings;
Create table Users(
	UserName VarChar Not Null,
	--UserType should be User, Customer, Employee, or Admin
	UserType VarChar Default 'User',
	UserRealName VarChar,
	UserPassword VarChar,
	Primary key (UserName)
);
Create table Customers(
	UserName VarChar Not Null,
	LinkedEmployee VarChar,
	AccountBalance double,
	Foreign key (UserName) references Users(UserName)
);
--Create Admin and Employees
Insert into Users
Values ("nlang", "Admin", "Nicole Lang", 082798);
Insert into Users
Values ("jdoe", "Employee", "John Doe", 12345);
Insert into Users
Values ("hkendrick", "Employee", "Haley Kendrick", 09876);


--Procedure to retrieve a list of pending customer applications
Create Procedure ListInactiveUsers As
Select * From Users Where UserType = "User"
Go;

--Procedure for retrieving a specific employee's customer list to determine whose info they can view
Create Procedure ListEmployeeCustomers As
Select * From Customers Where LinkedEmployee = @EmpName
Go;

--Procedure to list all active users, including the extra information of customers; for use by Admins
Create Procedure ListCurrentUsers As
Select * From Users Left Join Customers 
Where UserType != "User"
Go;
