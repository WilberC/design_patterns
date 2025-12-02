# Minimarket Design Patterns Project

This project is a web-based Minimarket administration platform built with Java and Spring Boot. It demonstrates the application of **SOLID principles**, **GoF patterns**, and **GRASP** to solve real-world business problems like inventory management, transactions, and reporting.

## Features

- **Inventory Management**: Create, list, and delete products.
- **Transactions**: Record **Purchases** (stock entry) and **Sales** (stock exit) with automatic stock updates.
- **Reporting**: Generate transaction reports in **CSV** or **HTML** formats.
- **Authentication**: Secure login for admin access.

## Architecture & Design Patterns

This project strictly follows software engineering best practices:

### SOLID Principles

- **SRP**: Classes like `StockUpdater` and `ReportGenerator` have single responsibilities.
- **OCP**: New pricing strategies or report formats can be added without modifying core logic.
- **LSP**: `Purchase` and `Sale` are interchangeable subclasses of `Transaction`.
- **ISP**: Interfaces like `PricingStrategy` are specific and focused.
- **DIP**: High-level services depend on abstractions (`StockObserver`, `ReportFormatter`), not concrete implementations.

### GoF Patterns Implemented

| Category       | Pattern             | Usage                                                                                |
| :------------- | :------------------ | :----------------------------------------------------------------------------------- |
| **Creational** | **Builder**         | Construction of complex `Product` objects.                                           |
|                | **Factory Method**  | Creating `Purchase` or `Sale` transactions based on type.                            |
| **Structural** | **Facade**          | `MinimarketFacade` simplifies the interface between Controllers and Services.        |
|                | **Bridge**          | Decouples `ReportGenerator` (Abstraction) from `ReportFormatter` (Implementation).   |
| **Behavioral** | **Strategy**        | `PricingStrategy` handles different pricing logic (Regular vs Discount).             |
|                | **Observer**        | `StockUpdater` observes transactions to automatically update inventory.              |
|                | **Template Method** | `ReportGenerator` defines the report structure, delegating formatting to the Bridge. |

### GRASP

- **Controller**: Dedicated controllers for Products, Transactions, and Reports.
- **Information Expert**: Domain objects and strategies contain their own logic.
- **Low Coupling & High Cohesion**: Achieved through the use of Facades and Interfaces.

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

## Database

This project uses **SQLite** for persistence.

- **Database File:** `minimarket.db` (created in the project root)
- **JDBC URL:** `jdbc:sqlite:minimarket.db`
- **Dialect:** `org.hibernate.community.dialect.SQLiteDialect`

## Project Structure

- `src/main/java/com/minimarket/model`: Domain entities (Product, Transaction).
- `src/main/java/com/minimarket/service`: Business logic and Pattern implementations (Strategy, Observer, Factory).
- `src/main/java/com/minimarket/controller`: Web controllers.
- `src/main/java/com/minimarket/service/facade`: Facade pattern entry point.
- `src/main/resources/templates`: Thymeleaf views.

## Testing

To run the automated tests:

```bash
./mvnw test
```
