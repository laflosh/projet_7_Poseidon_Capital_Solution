# 💼 Poseidon Capital Solution

**Poseidon Capital Solution** is the 7ᵗʰ project in the Java Developer path at **OpenClassrooms**.  
It is a financial aggregator application designed to simulate financial transactions by integrating various market tools into a functional prototype.

---

## 🚀 Project Overview

The front-end was already implemented using **Thymeleaf**, with all entity models defined and SQL scripts provided to set up the database.

This project involved:

- Implementing the entire **back-end functionality**
- Setting up **authentication and security**
- Creating **CRUD operations** for all entities
- Implementing **session-based login** via username/password
- Defining **security rules** (public routes, login-protected routes, and role-based access)
- Adding new front-end views for **user management**
- Creating a **full unit test campaign** (controllers, authentication, HTML templates)

---

## 🔐 Features

- **MySQL integration** for persistent data
- **Session-based authentication**
- **User management** interface
- **Role-based access control**
- **Unit testing** with JUnit 5
- **Thymeleaf**-powered HTML templates

---

## 🛠️ Tech Stack

| Technology      | Description         |
|-----------------|---------------------|
| Java            | Language            |
| Spring Boot     | Framework           |
| Spring Data JPA | ORM                 |
| Spring Security | Authentication/Security |
| Spring Web      | REST Controllers    |
| Thymeleaf       | HTML Templating     |
| JUnit 5         | Unit Testing        |
| MySQL           | Database            |

---

## 📥 Setup Instructions

### ▶️ Prerequisites

- Java 17+
- Maven
- MySQL
- Git

### 📦 Installation

```bash
git clone https://github.com/your-username/poseidon-capital-solution.git
cd poseidon-capital-solution
```

Update `src/main/resources/application.properties` with your MySQL credentials and database info.

Then run:

```bash
mvn spring-boot:run
```

---

## 🗃️ Database Setup

SQL scripts are available in the `/sql/` directory.  
Import them to your MySQL database before starting the application.

---

## ✅ Testing

To run unit tests:

```bash
mvn test
```

---
