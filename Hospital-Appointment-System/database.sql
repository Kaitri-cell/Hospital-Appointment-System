CREATE DATABASE hospital_db;

USE hospital_db;

CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    doctor VARCHAR(50),
    appointment_date VARCHAR(20),
    first_visit BOOLEAN
);
