# Auth System - Backend

A secure authentication backend built with **Spring Boot**, **Spring Security**, **JWT Authentication**, and **PostgreSQL**.

## Features

* User Registration
* User Login
* JWT Authentication
* Spring Security Authorization
* Email Verification
* Forgot Password
* Password Reset via Email
* BCrypt Password Encryption
* Global Exception Handling
* DTO-based API Design
* RESTful APIs
* Docker Support
* PostgreSQL Integration

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT (JJWT)
* Maven
* Docker
* Java Mail Sender
* Lombok

---


## API Endpoints

### Authentication

| Method | Endpoint              | Description        |
| ------ | --------------------- | ------------------ |
| POST   | `/api/users/register` | Register User      |
| POST   | `/api/users/login`    | Login User         |
| GET    | `/api/users/profile`  | Get Logged-in User |

### Email Verification

| Method | Endpoint                 |
| ------ | ------------------------ |
| GET    | `/api/users/verifyEmail` |

### Password Management

| Method | Endpoint                    |
| ------ | --------------------------- |
| POST   | `/api/users/forgotPassword` |
| POST   | `/api/users/resetPassword`  |

---

## Getting Started

### Clone Repository

## Configure Database, Email and JWT

Create a PostgreSQL database.

Update your `application.properties`


## Run Application

Using Maven

```bash
mvn spring-boot:run
```

Or

```bash
mvn clean install
java -jar target/auth.jar
```

---

## Docker

* Build Image
* Run Container

## Security

* Passwords are encrypted using BCrypt.
* JWT is used for authentication.
* Stateless session management.
* Protected APIs require a valid JWT.
* Email verification required before login.
* Password reset links are sent securely via email.

---

## Future Improvements

* Refresh Tokens
* Role-Based Authorization
* OAuth2 Login
* Google Authentication
* User Profile Update
* Rate Limiting
* Audit Logging

---