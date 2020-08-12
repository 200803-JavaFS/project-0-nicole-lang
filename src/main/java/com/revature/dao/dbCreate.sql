CREATE DATABASE DeltaSavings;
CREATE TABLE Users (
    UserName VARCHAR(45) NOT NULL,
    IsActive BOOLEAN DEFAULT FALSE,
    UserType VARCHAR(8) DEFAULT 'Customer',
    UserRealName VARCHAR(45),
    UserPassword VARCHAR(45),
    PRIMARY KEY (UserName)
);
CREATE TABLE Customers (
    UserName VARCHAR(45) NOT NULL,
    LinkedEmployee VARCHAR(45),
    AccountBalance DOUBLE,
    FOREIGN KEY (UserName)
        REFERENCES Users (UserName)
);
-- Create Admin and Employees
INSERT INTO Users
VALUES ("nlang", "Admin", "Nicole Lang", 082798);
INSERT INTO Users
VALUES ("jdoe", "Employee", "John Doe", 12345);
INSERT INTO Users
VALUES ("hkendrick", "Employee", "Haley Kendrick", 09876);

-- Procedure to retrieve a list of pending customer applications
Delimiter $$
CREATE PROCEDURE ListInactiveUsers() 
BEGIN
SELECT * FROM Users 
WHERE IsActive = FALSE;
END$$
-- Procedure for retrieving a specific employee's customer list to determine whose info they can view
CREATE PROCEDURE ListEmployeeCustomers(EmpName VARCHAR(45))
BEGIN
SELECT * FROM Customers 
WHERE LinkedEmployee = @EmpName;
END$$

-- Procedure to list all active users; for use by Admins
CREATE PROCEDURE ListCurrentUsers()
BEGIN
SELECT * FROM Users LEFT JOIN Customers 
ON Users.UserName = Customers.UserName 
WHERE UserType != "User";
END$$
