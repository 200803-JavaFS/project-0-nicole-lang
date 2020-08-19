CREATE DATABASE DeltaSavings;

CREATE TABLE Users (
	User_Name VARCHAR(45) NOT NULL PRIMARY KEY,
	--If User_Type is User then the user is not active
	User_Type VARCHAR(8) DEFAULT 'Customer',
	User_First_Name VARCHAR(30),
	User_Last_Name VARCHAR(30),
    User_Password VARCHAR(45),
);
CREATE TABLE Accounts (
    User_Name VARCHAR(45) NOT NULL REFERENCES Users (User_Name),
    Account_Status VARCHAR(7) DEFAULT 'Pending',
	Linked_Employee VARCHAR(45) References Users (User_Name),
    Account_Balance DECIMAL(10,2)
);


-- Create initial users
INSERT INTO users (user_name, user_type, user_first_name, user_last_name, user_password)
VALUES ('nlang', 'Admin', 'Nicole', 'Lang', 82798),
('jdoe', 'Employee', 'John', 'Doe', 12345),
('hkendrick', 'Employee', 'Haley','Kendrick', 09876);
('ffun1', 'Customer', 'Andrew', 'Flynn', 12345)

-- Create corresponding Accounts entry for Andrew Flynn; required to create link
Insert into accounts values ('ffun1', 'Pending', 'nlang', 0.00);

--Create Users/accounts for AccountManager service unit tests
insert into users 
values ('gattietime','Customer','Timothy','Gattie','200803'),
('gattie_time','Customer','Timothy','Gattie','200803');
insert into accounts
values ('gattietime','Open','nlang',1000.00, get_current_time()),
('gattie_time','Open','nlang',0.00, get_current_time());

--Example queries
-- Statement for retrieving a list of pending customer applications
SELECT * FROM Accounts 
WHERE Account_Status = 'Pending';

-- Statement for retrieving a specific employee's customer list to determine whose info they can view
SELECT * FROM Customers 
WHERE Linked_Employee = nlang;

--Stored procedure that returns a timestamp to mark when accounts are approved
Create or replace Function get_current_time() 
returns timestamp with time zone
as $$
select transaction_timestamp() ;
$$Language sql;

--Some of the more complicated statements from my DAO:

--Approve account
Update accounts set account_status = 'Open', linked_employee = ?, "
				+ "open_timestamp = ? where user_name = ?;
				
--Deny account request
Update accounts set account_status = 'Denied', linked_employee = ? "
						+ "where user_name = ?;

--Close account
Update accounts set account_status = 'Closed' where user_name = ?;

