CREATE DATABASE LoanManagementDB;
USE LoanManagementDB;

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    credit_score INT
);

SELECT * FROM customers;

CREATE TABLE loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    principal_amount DOUBLE,
    interest_rate DOUBLE,
    loan_term INT,
    loan_type ENUM('Home', 'Car'),
    loan_status ENUM('Pending', 'Approved', 'Rejected'),
    property_address VARCHAR(200),
    property_value INT,
    car_model VARCHAR(100),
    car_value INT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

SELECT * FROM loans;
