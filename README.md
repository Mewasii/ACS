# Spring Boot Web Backend For Animal Convenient Store
A Spring Boot backend application with RESTful APIs, authentication, and modular architecture.
Designed for scalability, maintainability, and ease of integration with frontend or mobile clients

 My goal is to serve a backend application in Java, powered by Spring Boot, that is easy to use, maintain, and extend.
The project focuses on providing a clean, production-ready structure while meeting the following key criteria:

 - Authentication & Authorization (secure user management with JWT, OAuth2)

 - Database integration with Spring Data JPA and relational databases (MySQL/SQLServer)

 - Modular architecture (Controller → Service → Repository pattern)

 - Testing support with JUnit and Mockito

 - Docker-ready deployment for easy setup in different environments
 
 # Features
 
 🧭 Object Mapping with MapStruct for cleaner DTO/entity conversions
 
 🏷️ Lombok for reducing boilerplate code (getters, setters, builders)
 
 🔑 Password encryption using BCrypt
 
 🛡️ Spring Security configuration with role-based access control
 
 ⚖️ Fine-grained authorization (endpoint-level, role & permission-based)
 
 ✅ Validation with Hibernate Validator + custom annotations

 🔄 Logout & Refresh Token mechanism for secure session management

 🚨 Global Exception Handling - Each business/domain error is mapped to a unique error code
# Project Structure
```pgsql
├───config          # Application & security configurations
├───controller      # REST controllers (expose API endpoints)
├───dto             # Data Transfer Objects (for API input/output)
│   ├───request     # Incoming request payloads
│   └───response    # Outgoing response payloads
├───entity          # JPA entities (database models)
├───enums           # Enum definitions (roles, statuses, etc.)
├───exception       # Custom exceptions & global error handling
├───mapper          # MapStruct mappers (Entity <-> DTO conversion)
├───repository      # Spring Data JPA repositories
├───service         # Business logic layer
│   └───impl        # Service implementations
└───validator       # Custom annotations & validators
```
# Overal Design
## 1/ Database
<img width="1044" height="671" alt="image" src="https://github.com/user-attachments/assets/8284a45e-b5d3-4e52-9878-79c9d7e0b350" />

## 2/ Mapper layer
The Mapper layer (using MapStruct) plays a key role in converting data between the API layer (DTOs) and the domain layer (Entities).

When handling an incoming request, the Controller receives data as a RequestDTO, service layer can process by taking data from DTO and JWT
.The Mapper converts this into an Entity to interact with Reposistory Layer.

When preparing an outgoing response, the Service returns an Entity. The Mapper transforms it into a ResponseDTO, which is sent back to the client.

This approach ensures:

- A clean separation between internal models and external API contracts

- Sensitive fields (e.g., password hashes) remain hidden from clients

- Reduced boilerplate through automatic DTO ↔ Entity mapping

- Consistent and maintainable code structure

In short:

<img width="1278" height="675" alt="image" src="https://github.com/user-attachments/assets/b735c240-e405-4768-85f8-5a26298c46f8" />

3/ JWT and Authentication
This project provides a secure authentication system using Spring Security with JWT (JSON Web Token).
It demonstrates how to implement token-based authentication, validate user credentials, and protect API endpoints.
### JWT (JSON Web Token)
JWT is used as a stateless way to authenticate and authorize users 
<img width="1100" height="640" alt="image" src="https://github.com/user-attachments/assets/4f158b6a-2736-479b-9ed7-117d862e1323" />


Flow:

- User logs in with username and password.

- If valid, the server generates a JWT token and returns it to the client.

- The client includes this token in the Authorization header for subsequent requests.

- The server validates the token before allowing access to protected resources.

Benefits:

- Stateless (no server-side sessions required).

- Lightweight and efficient for distributed systems.

- Built-in support for expiration and claims.

### Authentication

Login Process:

- The system verifies user credentials against stored data.

- On success, it issues a JWT token that grants access to protected APIs.

Spring Security Configuration (SecurityConfig.java):

- Defines which endpoints require authentication.

- Configures JWT filters to intercept and validate requests.

- Supports role-based access control for fine-grained permissions.

Authentication Service (AuthenticationServiImpl.java):

- Handles user login and token generation.

- Validates user credentials using the authentication manager.

- Encapsulates JWT logic to keep security concerns centralized.
