# Parking System

A backend system for managing a parking lot, built with Java 17+, Spring Boot, Spring Data JPA, and PostgreSQL.

## Requirements
- Java 17 or higher
- PostgreSQL
- Maven

## How to connect PostgreSQL

Create a new PostgreSQL database and user (or use existing ones). The default configuration expects:

- **Database name:** `parking_db`

You can change these settings in the `src/main/resources/application.properties` file:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/parking_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## How to build and run the project

1. Open a terminal in the root directory of the project.
2. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```
   *(On Windows, use `mvnw.cmd clean install`)*
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(On Windows, use `mvnw.cmd spring-boot:run`)*

Alternatively, you can run the `ParkingSystemApplication.java` class directly from your IDE.

## API Documentation (Swagger)

Once the application is running, you can access the Swagger UI to interact with the API endpoints at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Simple Interface

A simple HTML/JS frontend is provided to test the basic functionality. You can access it at:
[http://localhost:8080/index.html](http://localhost:8080/index.html)

## Features Included
1. **Vehicle management**: CRUD operations for cars, motorcycles, and trucks.
2. **Parking spot management**: CRUD operations, checking free spots.
3. **Parking sessions**: Entry (with type and availability validation) and exit (with cost calculation).
4. **Tariffs applied automatically**:
   - CAR — 100 som/hour
   - MOTORCYCLE — 50 som/hour
   - TRUCK — 150 som/hour
