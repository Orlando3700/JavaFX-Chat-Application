# JavaFX Chat Application

A simple chat application built using JavaFX and MySQL. This application allows users to register, log in, send messages, and view chat history. All messages are stored in a MySQL database and displayed in a real-time chat interface.

---

## Features

- User Registration and Login
- Session tracking with `UserSession`
- Chat interface with message list
- Messages saved to and loaded from MySQL
- Logout functionality
- JavaFX GUI with CSS styling

---


---

## Prerequisites

- Java 11+ and JavaFX SDK installed
- MySQL installed and running
- MySQL JDBC Driver
- An IDE like Eclipse or command line to run Java apps

---

## Setup Instructions

### 1. Clone the repository

git clone https://github.com/Orlando3700/JavaFX-Chat-Application.git
cd javafx-chat-application

### 2. Set up the MySQL database

Log in to your MySQL server.
Create a new database chat_app:
CREATE DATABASE chat_app;
Run the schema.sql file to create tables:
mysql -u your_user -p chat_app < schema.sql

### 3. Configure database connection
Open DatabaseConnector.java and update the credentials:

private static final String URL = "jdbc:mysql://localhost:3306/chat_app";
private static final String USER = "your_user";
private static final String PASSWORD = "your_password";

### 4. Run the application

Use your IDE to run Main.java or
Compile and run from the terminal:
javac -cp ".;path/to/mysql-connector.jar;path/to/javafx-sdk/lib/*" src/javafx_chat_application/Index.java
java -cp ".;path/to/mysql-connector.jar;path/to/javafx-sdk/lib/*" javafx_chat_application.Index

SQL Schema (schema.sql)

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);



Orlando Fernand
https://github.com/Orlando3700/JavaFX-Chat-Application
