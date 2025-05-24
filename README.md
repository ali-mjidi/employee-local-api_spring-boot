# Employee API

A simple RESTful API for managing employees using Spring Boot, MySQL, and Spring Security.

## ⚙️ Tech Stack

- Spring Boot  
- Spring Data JPA  
- Spring Security (Basic Auth, JDBC-based)  
- Spring Data REST (optional)  
- MySQL  

## 🔐 Authentication

Uses **JDBC-based authentication**.  
Users and roles are stored in the database (`users` and `authorities` tables).

## 📁 Setup

1. Create a `.env` file:

```env
DB_URL=jdbc:mysql://localhost:3306/employee_directory
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

2. Make sure the MySQL database `employee_directory` exists and has the correct schema.

3. Run the application:

```bash
./mvnw spring-boot:run
```

## 📡 Sample Endpoints

| Method | Endpoint               | Required Role |
|--------|------------------------|----------------|
| GET    | `/api/employees`       | EMPLOYEE       |
| GET    | `/api/employees/{id}`  | EMPLOYEE       |
| POST   | `/api/employees`       | MANAGER        |
| PUT    | `/api/employees`       | MANAGER        |
| PATCH  | `/api/employees/{id}`  | MANAGER        |
| DELETE | `/api/employees/{id}`  | ADMIN          |

## 📌 Note

This project is intended for educational purposes only and is not designed for production use.


