# Simple Hospital Appointment System

## Files

- HospitalAppointment.java
- database.sql

## Concepts from Advanced Java syllabus

- Swing
- JTextField
- JButton
- JComboBox
- JRadioButton
- JCheckBox
- JTable
- Event Handling
- JDBC
- java.sql
- PreparedStatement
- ResultSet

## Features

1. Enter patient name and age.
2. Select gender.
3. Select doctor.
4. Enter appointment date.
5. Select first visit.
6. Click Book.
7. Click Show Appointments to see database records.

## Database setup

1. Start MySQL/XAMPP.
2. Open phpMyAdmin.
3. Run database.sql.
4. Add MySQL Connector/J .jar to the Java project.

Database settings used in code:

- Database: hospital_db
- Username: root
- Password: empty

## Easy viva explanation

"This is a Java Swing hospital appointment system.
Swing is used for the GUI and JDBC is used to connect Java with MySQL.
When the Book button is clicked, ActionListener calls bookAppointment().
PreparedStatement inserts the patient data into the database.
The Show Appointments button runs a SELECT query and displays the records in JTable."
