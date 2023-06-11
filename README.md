# Medical Code Manager

Medical Code Manager is a Spring Boot application that provides a REST API to manage medical codes and related information. It enables users to upload (from a CSV file), retrieve, and delete data through rest APIs using an in-memory H2 database.

## Features

- Upload CSV files containing medical codes
- Retrieve medical codes with pagination and filtering options
- Get a specific medical code by its code
- Delete all medical codes

## Prerequisites

- Java 17
- Maven (the maven wrapper is provided can be used to build and run the project)

## Getting Started
It is possible to run the project in an IDE or use maven wrapper like this:
1. Build the project:

`./mvnw clean install`

2. Build the project:

`./mvn spring-boot:run`

The application will be available at http://localhost:8080.

## API Documentation

The API documentation is generated using Springdoc OpenAPI with the Springdoc OpenAPI UI dependency, and can be accessed at http://localhost:8080/swagger-ui.html. The following REST endpoints are available:

- `POST /v1/medical-codes/upload` Upload medical codes by a csv file
- `GET /v1/medical-codes` - Get all medical codes (all parameters are optional)`
- `DELETE /v1/medical-codes` - Delete all medical codes`
- `GET /v1/medical-codes/{code}` - Get medical code by code`

## Logging

The application's logs are configured using the `logback.xml` file located in the `src/main/resources` folder. Logs will be recorded in the `/logs` directory under the project root.

## Tests

The project includes both unit and integration tests available in the `test` package. The following technologies have been used for testing:

- JUnit
- Mockito
- TestRestTemplate

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency Management
- [H2 Database](https://www.h2database.com/) - In-memory database
- [MapStruct](https://mapstruct.org/) - Java Bean mappings
- [Lombok](https://projectlombok.org/) - Boilerplate code reduction
- [Springdoc OpenAPI](https://springdoc.org/) - API documentation generation
- [Springdoc OpenAPI UI](https://springdoc.org/) - API documentation user interface
- [OpenCSV](http://opencsv.sourceforge.net/) - CSV file parsing

## Author

- **Payam Soudachi** - [psouda.com](http://psouda.com/)
    - Email: [psoudachi@gmail.com](mailto:psoudachi@gmail.com)