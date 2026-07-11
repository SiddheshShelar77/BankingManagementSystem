-- Create Database
CREATE DATABASE IF NOT EXISTS banking_system;

USE banking_system;

-- ==========================================
-- Customers Table
-- ==========================================

CREATE TABLE customers (

    customer_id INT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(100) NOT NULL UNIQUE,

    password VARCHAR(100) NOT NULL,

    phone VARCHAR(15) NOT NULL

);

-- ==========================================
-- Accounts Table
-- ==========================================

CREATE TABLE accounts (

    account_no BIGINT PRIMARY KEY,

    customer_id INT NOT NULL,

    balance DOUBLE NOT NULL DEFAULT 0,

    account_type VARCHAR(20) NOT NULL,

    FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
        ON DELETE CASCADE

);

-- ==========================================
-- Transactions Table
-- ==========================================

CREATE TABLE transactions (

    transaction_id INT AUTO_INCREMENT PRIMARY KEY,

    from_account BIGINT NULL,

    to_account BIGINT NULL,

    amount DOUBLE NOT NULL,

    transaction_type VARCHAR(20) NOT NULL,

    transaction_date DATETIME NOT NULL,

    FOREIGN KEY (from_account)
        REFERENCES accounts(account_no)
        ON DELETE SET NULL,

    FOREIGN KEY (to_account)
        REFERENCES accounts(account_no)
        ON DELETE SET NULL

);

-- ==========================================
-- Verify Tables
-- ==========================================

SHOW TABLES;

DESC customers;
DESC accounts;
DESC transactions;