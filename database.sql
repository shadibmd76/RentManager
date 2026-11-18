CREATE DATABASE IF NOT EXISTS utility_db;
USE utility_db;

CREATE TABLE IF NOT EXISTS tenants (
    flat_no VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    nid VARCHAR(30),
    rent DOUBLE
);

CREATE TABLE IF NOT EXISTS rent_bills (
    flat_no VARCHAR(10),
    bill_year INT,
    bill_month INT,
    paid_amount DOUBLE DEFAULT 0,
    PRIMARY KEY (flat_no, bill_year, bill_month)
);
