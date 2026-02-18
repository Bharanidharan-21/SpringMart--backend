# SpringMart Backend Project

## Role-Based Multi-Vendor E-Commerce System  
Built using **Spring Boot, Spring Security, JWT & MySQL**

---

## Overview

SpringMart is a role-based multi-vendor e-commerce backend system designed to simulate a real-world marketplace platform similar to Amazon or Flipkart.

The system supports:

- Multi-role architecture (Admin, Merchant, Customer)
- Secure JWT-based authentication
- Role-Based Access Control (RBAC)
- Merchant approval workflow
- Transactional order processing
- Review & rating system
- Stock auto-management

The application follows a **Monolithic Layered Architecture** and exposes RESTful APIs.

---

##  Architecture

Layered Monolithic Architecture:

```
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
MySQL Database
```

### Layer Responsibilities

| Layer        | Responsibility |
|-------------|---------------|
| Controller  | REST API handling |
| Service     | Business logic & validation |
| Repository  | Database operations (JPA/Hibernate) |
| Security    | JWT authentication & RBAC |

---

##  Security Implementation

- JWT-based stateless authentication
- BCrypt password encryption
- Role-based authorization using Spring Security
- Protected API endpoints
- CSRF disabled for REST APIs
- Global exception handling

### Role Authorization Example

```java
.requestMatchers("/api/admin/**").hasRole("ADMIN")
.requestMatchers("/api/merchant/**").hasRole("MERCHANT")
.requestMatchers("/api/customer/**").hasRole("CUSTOMER")
```

---

## User Roles & Capabilities

### 🔹 ADMIN
- Approve / Reject merchants
- View all users
- Moderate products
- Hard delete products
- Monitor platform statistics

### 🔹 MERCHANT
- Add / Update products
- Soft delete products
- Manage stock
- View merchant-specific orders
- Requires admin approval before login

### 🔹 CUSTOMER
- Browse active products
- Search & filter products
- Manage cart
- Place orders (COD simulation)
- Submit reviews (only after purchase)
- View order history

---

##  Core Modules

- Authentication Module (JWT)
- Admin Module
- Merchant Module
- Customer Module
- Cart Module
- Order Module (Transactional)
- Review & Rating Module

---

##  Database Design

### Main Entities

- User
- Product
- CartItem
- Order
- OrderItem
- Review
- Role (Enum)

### Key Relationships

- One Merchant → Many Products
- One Customer → Many Orders
- One Order → Many OrderItems
- One Product → Many Reviews
- One Customer → Many CartItems

---

##  Business Rules Implemented

✔ Merchant must be approved before login  
✔ Customer cannot review without purchase  
✔ Stock reduces upon order placement  
✔ Product auto-deactivates when stock = 0  
✔ Soft delete for merchant products  
✔ Hard delete for admin  
✔ JWT required for protected APIs  
✔ Role validation at API level  

---

##  Technologies Used

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Maven
- Jakarta Validation


---

##  How to Run the Project

###  Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/springmart-backend.git
cd springmart-backend
```

### Create MySQL Database

```sql
CREATE DATABASE springmart;
```

###  Configure Application Properties

Create:

```
src/main/resources/application.properties
```

Use `application-example.properties` as reference.

### Run Application

```bash
mvn spring-boot:run
```

Or run via Spring Tool Suite.

Server runs at:

```
http://localhost:8080
```

---

##  Security Notice

The `application.properties` file is excluded from version control for security reasons.

Use the provided `application-example.properties` file to configure your local environment.

---

##  API Testing

You can test APIs using:

- Postman


---

##  Limitations

- No payment gateway integration
- No image upload system
- No distributed caching
- Monolithic architecture
- No email notification system

---

##  Key Highlights

- Production-style RBAC implementation
- Transactional order management
- Secure JWT-based authentication
- Real-world business rule enforcement
- Clean layered architecture
- DTO-based API design

---

## Author

Developed as a backend system to demonstrate advanced Spring Boot architecture, security implementation, and real-world e-commerce domain modeling.

---
