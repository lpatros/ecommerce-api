[PT-BR](README-PTBR.md)

<div align="center">
  <h1 style="font-size: 32px; border: none; line-height: 0; font-weight: bold">Ecommerce API</h1>
  <p>Development of a RESTful API, structured according to the MVC (Model-View-Controller) architectural pattern, with the goal of managing products, orders, users, and other resources in an e-commerce system.</p>
    <div style="margin-bottom: 10px">
    <img src="https://img.shields.io/badge/Language-Java-orange.svg" alt="Java: 21"/>
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?logo=Spring&logoColor=white" alt="Spring Boot: 3.5.6"/>
    <img src="https://img.shields.io/badge/PostgreSQL-blue?logo=Postgresql&logoColor=white" alt="PostgreSQL"/>
    <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT"/>
    </div>
    <br>
</div>

# Quick Links

- [Description](#description)
- [Technologies](#technologies)
- [Features](#features)
- [Swagger](#swagger)
- [Project Structure](#project-structure)
- [License](#license)

## Description

This project is a **RESTful API** built with **Spring Boot** for complete ecommerce management. The application implements CRUD (Create, Read, Update, Delete) operations for categories, products, users, and orders, following best practices for development and layered architecture.

The API uses **PostgreSQL** as the database, **Flyway** for migration versioning, and **Spring Data JPA** for the persistence layer.

## Technologies

- **Java 21**
- **Spring Boot 3.5.6**
  - Spring Web
  - Spring Data JPA
  - Spring Boot DevTools
- **PostgreSQL** - Relational database
- **Flyway** - Database version control
- **Lombok** - Reduces boilerplate code
- **SpringDoc OpenAPI** - Automatic API documentation (Swagger)
- **Maven** - Dependency management

## Features

### Category Management
- Create, list, update, and remove product categories
- Filters and pagination

### Product Management
- Full CRUD for products
- Association with categories
- Inventory control
- Pagination and sorting

### User Management
- Full CRUD for users
- Data validations

### Order Management
- Create and query orders
- Associated order items
- Total calculations

## Swagger

This project uses **SpringDoc OpenAPI** to automatically generate interactive API documentation through **Swagger UI**.

### Accessing Swagger

When the application is running, you can access the interactive API documentation at the following route:

> [!NOTE]
> The base URL (`{{URL}}`) is usually `http://localhost:8080`, unless you have configured a different port.

```
{{URL}}/swagger-ui/index.html
```

You can also access the OpenAPI specification in JSON format:

```
{{URL}}/v3/api-docs
```

## Project Structure

```
ecommerce-api/
├── src/
│   └── main/
│      ├── java/com/lpatros/ecommerce_api/
│      │   ├── configuration/      # Application configuration
│      │   ├── controller/         # REST controllers
│      │   ├── dto/                # Data Transfer Objects
│      │   ├── entity/             # JPA entities
│      │   ├── exception/          # Exception handling
│      │   ├── mapper/             # DTO <-> Entity mappers
│      │   ├── repository/         # JPA repositories
│      │   ├── service/            # Business rules
│      │   ├── util/               # Utilities
│      │   └── validator/          # Custom validators
│      └── resources/
│          ├── application.properties
│          └── db/migration/       # Flyway scripts
│
├── pom.xml                        # Maven dependencies
├── README-PTBR.md                 # Documentation in Portuguese
└── README.md                      # Documentation in English
```

## License

This project is licensed under the [MIT License](https://github.com/lpatros/ecommerce-api/blob/main/LICENSE.txt).
