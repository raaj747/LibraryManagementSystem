# SpringApiTesting - Library Management System

## Roles

ADMIN:
- Manage users
- Manage books
- View reports

LIBRARIAN:
- Manage books
- Issue books to members
- Return books
- View/waive fines

MEMBER:
- Register/login
- Search books
- Issue books
- Return own books
- View own fines
- Pay own fines

## Demo accounts

Password for all demo accounts: `password`

- admin@example.com
- librarian@example.com
- member@example.com

These accounts are created automatically by DataInitializer.

## Start

1. Create database:
   - Run `mysql_setup.sql`
2. Open `src/main/resources/application.properties`
3. Replace:
   spring.datasource.password=YOUR_MYSQL_PASSWORD
4. Run:
   mvn spring-boot:run

The application runs on:
http://localhost:8080

## Authentication

POST /api/auth/admin/login
POST /api/auth/librarian/login
POST /api/auth/member/login
POST /api/auth/member/register

For protected endpoints send:

Authorization: Bearer YOUR_JWT_TOKEN

## Important

This is a learning/demo project. Change the demo passwords and JWT secret before using it for a real deployment.

## Swagger

After starting the application, open:
`http://localhost:8080/swagger-ui.html`

For protected endpoints, use the Swagger **Authorize** button and enter:
`Bearer YOUR_JWT_TOKEN`
