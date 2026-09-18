# 📚 Library Management System — Backend

A **Spring Boot REST API** for managing a complete library system, including **books, users, members, librarians, book issues, and fines**.

The system provides **JWT-based authentication** and **role-based authorization** for Admin, Librarian, and Member users. It uses **MySQL** for persistent data storage and **Spring Data JPA / Hibernate** for database management.

Swagger / OpenAPI is also integrated for easy **API documentation and testing**.

---

## 🚀 Features

* 🔐 JWT-based authentication
* 👥 Role-based authorization
* 👑 Admin management
* 👨‍💼 Librarian management
* 👤 Member management
* 📚 Book management
* 📖 Book issue and return management
* 💰 Fine management
* 🗄️ MySQL database integration
* 🔄 Spring Data JPA & Hibernate
* ✅ Request validation using Bean Validation
* 📑 Global exception handling
* 📖 Swagger / OpenAPI API documentation
* 🛡️ Spring Security integration
* 🌐 RESTful API architecture

---

## 👥 User Roles

The application supports three main roles:

| Role                | Description                                                      |
| ------------------- | ---------------------------------------------------------------- |
| 👑 **ADMIN**        | Manage users, librarians, members, books, and library operations |
| 👨‍💼 **LIBRARIAN** | Manage books, issues, returns, and fines                         |
| 👤 **MEMBER**       | View available books and manage personal borrowing activities    |

Access to API endpoints is controlled using **Spring Security + JWT + role-based authorization**.

---

## 🛠️ Technologies Used

| Technology               | Purpose                         |
| ------------------------ | ------------------------------- |
| ☕ **Java**               | Backend programming language    |
| 🍃 **Spring Boot**       | REST API development            |
| 🌐 **Spring MVC**        | Controllers and API endpoints   |
| 🗃️ **Spring Data JPA**  | Database operations             |
| 🔄 **Hibernate**         | Object-Relational Mapping       |
| 🔐 **Spring Security**   | Authentication & authorization  |
| 🎫 **JWT**               | Secure user authentication      |
| 🐬 **MySQL**             | Relational database             |
| 📦 **Maven**             | Dependency & project management |
| 📖 **Swagger / OpenAPI** | API documentation & testing     |
| ✅ **Bean Validation**    | Request validation              |

---

## 🏗️ Project Architecture

The project follows a layered Spring Boot architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Main Layers

* **Controller** — Handles HTTP requests and API endpoints
* **Service** — Contains business logic
* **Repository** — Handles database operations using Spring Data JPA
* **Entity** — Represents database tables
* **DTO** — Handles request and response data
* **Security** — JWT authentication and authorization
* **Exception** — Custom exceptions and global exception handling
* **Config** — Application and Swagger/security configuration

---

## 📁 Project Structure

```text
Library-Management-System/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── .../
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       └── SpringApiTestingApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── mysql_setup.sql
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🔐 Authentication

Authentication is implemented using **JSON Web Tokens (JWT)**.

The general authentication flow is:

```text
Client
  │
  │ Login
  ▼
Authentication API
  │
  │ Validate Credentials
  ▼
Spring Security
  │
  │ Generate JWT
  ▼
Client
  │
  │ Authorization: Bearer <JWT>
  ▼
Protected API
```

After successful login, the client receives a JWT token.

The token can then be included in requests:

```http
Authorization: Bearer <your-jwt-token>
```

Spring Security validates the token and checks the user's role before allowing access to protected endpoints.

---

## 🗄️ Database

The application uses **MySQL** as the relational database.

Database configuration is stored in:

```text
src/main/resources/application.properties
```

A database setup script is also included:

```text
src/main/resources/mysql_setup.sql
```

The application uses **Hibernate/JPA** to map Java entities to database tables.

---

## 📚 Main Modules

### 📖 Book Management

Provides functionality for managing library books.

Typical operations include:

```text
GET     /api/books
GET     /api/books/{id}
POST    /api/books
PUT     /api/books/{id}
DELETE  /api/books/{id}
```

---

### 👤 Member Management

Handles library members and their information.

```text
GET     /api/members
GET     /api/members/{id}
POST    /api/members
PUT     /api/members/{id}
DELETE  /api/members/{id}
```

---

### 👨‍💼 Librarian Management

Allows authorized users to manage librarians.

```text
GET     /api/librarians
POST    /api/librarians
PUT     /api/librarians/{id}
DELETE  /api/librarians/{id}
```

---

### 📚 Book Issue & Return

The system manages the complete borrowing process:

```text
Book Available
      ↓
   Book Issued
      ↓
Member Borrows Book
      ↓
   Book Returned
      ↓
Book Available
```

The system can also track overdue books and associated fines.

---

### 💰 Fine Management

The fine module manages fines related to overdue book returns.

Example workflow:

```text
Due Date Passed
      ↓
Overdue Book
      ↓
Fine Generated
      ↓
Fine Paid
```

---

## 📖 Swagger

Swagger is included to make API documentation and testing easier.

After starting the application, Swagger UI can be accessed through the configured Swagger endpoint.

For example:

```text
http://localhost:8080/swagger-ui/index.html
```

From Swagger UI you can:

* 📋 View available endpoints
* 📝 View request/response models
* 🔐 Authorize using JWT
* 🧪 Test API endpoints
* 📊 Inspect API responses

> The exact Swagger URL may depend on the OpenAPI configuration used in the project.

---

## ⚙️ Requirements

Before running the project, make sure you have installed:

* ☕ Java 8+
* 📦 Maven
* 🐬 MySQL
* 💻 IntelliJ IDEA / Eclipse / VS Code
* 🌐 Git

You can verify Java and Maven:

```bash
java -version
mvn -version
```

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone <your-repository-url>
```

Move into the project directory:

```bash
cd Library-Management-System
```

---

### 2️⃣ Create MySQL Database

Open MySQL and create the database required by the application.

For example:

```sql
CREATE DATABASE library_management_system;
```

If the project contains a database setup script, execute:

```text
src/main/resources/mysql_setup.sql
```

---

### 3️⃣ Configure Database

Open:

```text
src/main/resources/application.properties
```

Configure your MySQL connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
spring.datasource.username=root
spring.datasource.password=your_password
```

Update the username and password according to your local MySQL configuration.

---

### 4️⃣ Build the Project

Using Maven:

```bash
mvn clean install
```

---

### 5️⃣ Run the Application

Run:

```bash
mvn spring-boot:run
```

Or run:

```text
SpringApiTestingApplication.java
```

from your IDE.

The application will normally start on:

```text
http://localhost:8080
```

---

## 🧪 API Testing

You can test the APIs using:

* Swagger UI
* Postman
* IntelliJ HTTP Client
* cURL

Example:

```http
GET http://localhost:8080/api/books
```

For protected endpoints:

```http
Authorization: Bearer <your-jwt-token>
```

---

## 🔄 API Request Flow

A typical request follows this architecture:

```text
HTTP Request
     │
     ▼
Controller
     │
     ▼
Security / JWT Validation
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
MySQL Database
     │
     ▼
Response
```

This separation keeps the application easier to maintain and extend.

---

## 🛡️ Security

Security is handled using:

* Spring Security
* JWT authentication
* Password encryption
* Role-based authorization
* Protected API endpoints
* Request validation

Sensitive configuration such as database passwords and JWT secrets should **not be committed to GitHub**.

Use environment variables or local configuration for production credentials.

---

## 📌 Future Improvements

Possible future improvements include:

* 📧 Email notifications for overdue books
* 🔎 Advanced book search and filtering
* 📊 Admin dashboard
* 📈 Library statistics and reports
* 📄 Pagination and sorting
* 🔄 Refresh token support
* 🐳 Docker support
* ☁️ Cloud deployment
* 🧪 Automated unit and integration tests
* 📋 Audit logging

---

## 🤝 Contributing

Contributions are welcome.

To contribute:

```bash
git clone <your-repository-url>
cd Library-Management-System
```

Create a new branch:

```bash
git checkout -b feature/your-feature
```

Commit your changes:

```bash
git add .
git commit -m "Add your feature"
```

Push the branch:

```bash
git push origin feature/your-feature
```

Then open a Pull Request.

---

## 📄 License

This project is created for **educational and learning purposes**.

---

## 👨‍💻 Author

**S. M. Rafiuujjaman Raj**

Backend project built with:

```text
Java + Spring Boot + Spring Security + JWT + MySQL
```

⭐ If you find this project useful, consider giving the repository a **star**!
