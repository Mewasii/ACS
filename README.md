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
