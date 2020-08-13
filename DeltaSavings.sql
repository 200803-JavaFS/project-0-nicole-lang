CREATE DATABASE DeltaSavings;

CREATE TABLE Users (
	User_Name VARCHAR(45) NOT NULL PRIMARY KEY,
	--If User_Type is User then the user is not active
	User_Type VARCHAR(8) DEFAULT 'User',
	User_First_Name VARCHAR(30),
	User_Last_Name VARCHAR(30),
    User_Password VARCHAR(45),
);
CREATE TABLE Accounts (
    User_Name VARCHAR(45) NOT NULL REFERENCES Users (User_Name),
    Account_Status VARCHAR(7) DEFAULT 'Pending',
	Linked_Employee VARCHAR(45) References Users (User_Name),
    Account_Balance DOUBLE PRECISION
);


-- Create Admin and Employees
INSERT INTO users (user_name, user_type, user_first_name, user_last_name, user_password)
VALUES ('nlang', 'Admin', 'Nicole', 'Lang', 082798),
('jdoe', 'Employee', 'John', 'Doe', 12345),
('hkendrick', 'Employee', 'Haley','Kendrick', 09876);
('ffun1', 'Customer', 'Andrew', 'Flynn', 12345)

-- Create corresponding Accounts entry for Andrew Flynn; required to create link
Insert into accounts values ('ffun1', 'Pending', 'nlang', 0.00);

--Example queries
-- Statement for retrieving a list of pending customer applications
SELECT * FROM Accounts 
WHERE Linked_Employee = null;

SELECT 

-- Statement for retrieving a specific employee's customer list to determine whose info they can view
SELECT * FROM Customers 
WHERE Linked_Employee = nlang;

-- List all active users; for use by Admins
SELECT * FROM Users Where User_Type != 'User';