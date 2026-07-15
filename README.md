# MatriQ - Backend

Spring Boot backend for MatriQ, a material sourcing platform for architects, interior designers, and construction engineers. MatriQ streamlines material discovery, technical specification verification, and the Request for Quote (RFQ) process into a single digital workspace.

## Project Links
- Frontend Repository: https://github.com/shebnem-m/MatriQ-frontend
- Live Application: https://matriq-backend-p4u3.onrender.com

## Tech Stack
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Docker & Docker Compose

## Core Architecture and Entities
The application is structured into four core modules following the layered architecture pattern (Controller -> Service -> Repository -> Entity):

1. User Module: Manages core authentication, registration, and user profiles.
   - Fields: id, fullName, email, password, role, birthDate, phoneNumber.
   - Roles supported: ADMIN, SUPPLIER, CUSTOMER.

2. Supplier Module: Handles supplier-specific business registrations and profiles.
   - Fields: id, ownerId, name, description, email, phoneNumber, address, createdAt.

3. Listing and Review Modules: Controls material listings and user-generated feedback.
   - Listing Fields: id, supplierId, title, description, category, materialType, unit, price, stockQuantity, deliveryDays, createdAt, updatedAt.
   - Review Fields: id, listingId, ownerId, rating, comment, createdAt, updatedAt.

4. Order Module: Implements purchasing and RFQ business logic with automated discounts.
   - Order Fields: id, buyerId, listingId, quantity, unitPrice, totalPrice, status, createdAt, updatedAt.
   - Business Rules: Automatically validates stock, copies current listing price to unitPrice, applies volume discounts (5% for quantity >= 100, 10% for quantity >= 500), and reduces stock upon order creation.

## System Exception Handling
The API implements a unified global exception handling mechanism. All errors are returned in the following JSON format:

{
  "timestamp": "2026-07-15T10:54:19Z",
  "status": 400,
  "message": "Validation Failed",
  "errors": [
    "Field 'email' must be a valid email address"
  ]
}

## Key API Endpoints

### Authentication
- POST  /auth/register - Register a new user
- POST  /auth/login - Authenticate user and return JWT

### Users
- GET  /users/{id} - Get user by ID
- GET  /users - Get paginated users list
- POST /users - Create user
- PUT  /users/{id} - Update user
- DELETE /users/{id} - Delete user

### Suppliers
- GET  /suppliers - Get paginated supplier list
- GET  /suppliers/{id} - Get supplier by ID
- GET  /suppliers/{id}/listings - Get listings by supplier
- POST /suppliers - Create supplier profile
- PUT  /suppliers/{id} - Update supplier profile
- DELETE  /suppliers/{id} - Delete supplier profile

### Listings and Reviews
- GET  /listings - Get paginated listings
- GET  /listings/{id} - Get listing by ID
- GET  /listings/search - Search listings with filters
- POST  /listings - Create listing
- PUT  /listings/{id} - Update listing
- DELETE  /listings/{id} - Delete listing
- GET  /listings/{id}/reviews - Get reviews for a listing
- POST  /listings/{id}/reviews - Add review
- PUT  /reviews/{id} - Update review
- DELETE  /reviews/{id} - Delete review

### Orders
- POST  /orders - Create a new order with stock validation and auto-discounts
- GET  /orders/{id} - Get order details
- GET  /users/{id}/orders - Get orders by user
- PUT  /orders/{id}/status - Update order status
- DELETE  /orders/{id} - Delete/cancel order

### Admin Management
- GET  /admin/users - Administrative user list
- GET  /admin/orders - Administrative order oversight
- DELETE  /admin/reviews/{id} - Administrative moderation of reviews

## Local Setup and Deployment Instructions

### Prerequisites
- Java 17
- Maven 3.8+
- Docker and Docker Compose

### Environment Variables
Configure the following properties in your system or environment configuration:
- SPRING_DATASOURCE_URL - JDBC URL for PostgreSQL
- SPRING_DATASOURCE_USERNAME - Database username
- SPRING_DATASOURCE_PASSWORD - Database password
- JWT_SECRET - Secure key for signing JWT tokens

### Run via Docker Compose
To run the Spring Boot application and the PostgreSQL database container together, use:

docker compose up --build

Docker volumes ensure database state persistence between container restarts.

### Render Deployment Steps
1. Provision a PostgreSQL instance on Render.
2. Link the GitHub repository and configure the service as a Web Service.
3. Bind the environment variables listed in the Environment Variables section.
4. Set the Build Command: ./mvnw clean package
5. Set the Start Command: java -jar target/*.jar

## Project Team
- Shabnam Muradova - User Module & Deployment: [GitHub](https://github.com/shebnem-m)
- Mohsin - Supplier Module: [GitHub](https://github.com/mohsin-github)
- Yaqut Rasulbayli- Listing, Review Modules & Authorization Security: [GitHub](https://github.com/yagutrslbyl)
- Nihad Bagirzade- Order Module & JWT Core Security: [GitHub](https://github.com/TheGlitch26)
