# Minimarket Design Patterns Project

This project is a web-based Minimarket administration platform built with Java and Spring Boot. It demonstrates the application of SOLID principles, GoF patterns, and GRASP.

## Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher (or use the included `mvnw` wrapper)

## Installation

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd design_patterns
   ```

2. **Install dependencies:**
   ```bash
   ./mvnw clean install
   ```

## Running the Application

To start the application, run the following command in the project root:

```bash
./mvnw spring-boot:run
```

The application will start on port `8080`.

- **URL:** [http://localhost:8080](http://localhost:8080)

## Login Credentials

The system is pre-configured with a single admin user:

- **Username:** `admin`
- **Password:** `admin123`

## Database & Migrations

This project uses **H2 Database** (In-Memory) for development simplicity.

- **Database Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:minimarket`
- **User:** `sa`
- **Password:** `password`

### Migrations

Currently, the database schema is automatically managed by Hibernate (`spring.jpa.hibernate.ddl-auto=update`). No manual migration scripts are required for this phase. The schema is recreated/updated on every application startup.

## Testing

To run the automated tests:

```bash
./mvnw test
```

## Project Structure

- `src/main/java`: Source code
- `src/main/resources`: Configuration and templates
- `src/test/java`: Unit and integration tests
