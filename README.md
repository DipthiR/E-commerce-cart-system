# E-commerce-cart-system

## E-Commerce System (Java)

An E-Commerce System developed in Java that allows users to browse products, add them to a cart, and place orders.
The project also includes an admin panel for managing products and orders.

## Features
 User Side

 User registration & login (Authentication system)

 Browse products by category

 Search and filter products

 Add/remove products from shopping cart

 Checkout and place orders

 View past orders

 Admin Side

 Add / update / delete products

 Manage customer orders

 View sales reports

## Tech Stack

Language: Java (JDK 8 or higher)

Framework:

Java Servlets & JSP (if web-based)

or Spring Boot (for RESTful APIs)

Database: MySQL / PostgreSQL / SQLite

Build Tool: Maven / Gradle

Frontend: JSP / HTML / CSS / JavaScript

## How to Run

Clone this repository:

git clone https://github.com/your-username/java-ecommerce-system.git
cd java-ecommerce-system


Import the project into your IDE (IntelliJ / Eclipse / NetBeans).

Configure your database (MySQL example):

Create a database named ecommerce_db

Import the provided SQL script from /database/ecommerce.sql

Update database settings in application.properties (Spring Boot) or dbconfig.java (JDBC version).

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=yourpassword


Build and run the project:

mvn spring-boot:run


or deploy to Tomcat (for JSP/Servlet version).

Open browser and visit:

http://localhost:8080/
