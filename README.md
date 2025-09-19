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

<img width="1275" height="665" alt="image" src="https://github.com/user-attachments/assets/f446e939-e016-4a86-851b-0ba194edba89" />


