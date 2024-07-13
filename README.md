"This Spring Boot project implements secure user authentication and authorization with bcrypt and JWT, offers RESTful APIs, file upload/download, database integration, global exception handling, input validation, logging, monitoring, unit testing, Swagger documentation, SSL configuration, and PayPal payment gateway integration."

## Table of Contents

- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- Java 17+
- Maven 3.6+
- MySQL 5.7+

- ### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/your-repo-name.git
   
2. Navigate to the project directory:
   ```sh
   cd your-repo-name

3. Configure application properties:
   ```sh
   # database properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_table
   soring.datasource.username=username
   spring.datasource.password=password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   # logging properties
   logging.level.root=INFO
   logging.level.org.springframework.web=DEBUG
   logging.level.com.myproject=TRACE
   logging.file.name=logs/spring-boot-application.log
   logging.level.com.myproject.Collection=INFO

   # ssl properties
   server.port=8443
   server.ssl.key-store=classpath:keystore/baeldung.p12
   server.ssl.key-store-password=123456
   server.ssl.key-store-type=PKCS12
   server.ssl.key-alias=baeldung

   # swagger properties
   springdoc.api-docs.path=/api-docs
   springdoc.swagger-ui.path=/swagger-ui.html
   springdoc.swagger-ui.display-request-duration=true

4. Dependencies
   ```sh
   <dependencies>
       <dependency>
           <groupId>org.springdoc</groupId>
           <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
           <version>2.4.0</version>
       </dependency>
       <dependency>
           <groupId>com.auth0</groupId>
           <artifactId>java-jwt</artifactId>
           <version>4.0.0</version>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-logging</artifactId>
       </dependency>
       <dependency>
           <groupId>org.slf4j</groupId>
           <artifactId>slf4j-api</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-aop</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-validation</artifactId>
       </dependency>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>8.0.32</version>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-security</artifactId>
       </dependency>
       <dependency>
           <groupId>org.thymeleaf.extras</groupId>
           <artifactId>thymeleaf-extras-springsecurity6</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-devtools</artifactId>
           <scope>runtime</scope>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
           <scope>test</scope>
       </dependency>
       <dependency>
           <groupId>org.springframework.security</groupId>
           <artifactId>spring-security-test</artifactId>
           <scope>test</scope>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-thymeleaf</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-data-jpa</artifactId>
       </dependency>
   </dependencies>

## Running the Application
- Once the application is running, you can access it at 'http://localhost:8080'.
- The API documentation is available via Swagger at 'http://localhost:8080/swagger-ui.html'.

## Project Structure

Here's an overview of the project structure:


### Directories and Files

- **aspect**: Contains classes for handling exceptions and defining aspect-oriented programming (AOP) aspects.
- **controller**: Contains REST controllers for handling HTTP requests.
- **dto**: Contains Data Transfer Objects (DTO) for encapsulating request data.
- **entity**: Contains JPA entities representing database tables.
- **repository**: Contains repository interfaces for database access.
- **security**: Contains security configuration and filters.
- **service**: Contains service interfaces and their implementations.
- **swagger**: Contains configuration for API documentation.

## Features
- Logging and Monitoring: Integrated SLF4J for effective logging and application monitoring.
- JWT Authentication: Secure authentication mechanism using JSON Web Tokens.
- Password Hashing with bcrypt: Ensures secure storage of user passwords.
- Database Integration: Efficient data management using JPA/Hibernate with MySQL.
- Global Exception Handling: Consistent and informative error responses across the application.
![image](https://github.com/user-attachments/assets/85b0b391-977b-424d-9f60-b475a9dc58be)
- Role-based Access Control: Authorization based on user roles, with higher-level roles having more functionalities
![image](https://github.com/user-attachments/assets/185e78dd-15d7-41f0-9bb4-669fd95cf129)
- File Storage Service: Supports file upload and download operations.
![image](https://github.com/user-attachments/assets/329f29a1-f191-4e7a-9831-85006617ba8c)
- PayPal Payment Integration: Enables secure online transactions through PayPal.
![image](https://github.com/user-attachments/assets/046ea7b2-943c-48a3-b124-e07acb53e4bc)
- API Documentation with Swagger: Interactive interface for exploring and testing API endpoints.
![image](https://github.com/user-attachments/assets/f5cc4d9a-0d8c-43d3-80a1-7d62851e3b75)
- Input Validation: Ensures data integrity and security by validating user inputs.
![image](https://github.com/user-attachments/assets/86ea2b4a-f939-49bb-970f-8a400d09de53)
- SSL Configuration: Ensures secure data transmission by configuring SSL for the application.
![image](https://github.com/user-attachments/assets/26dff4ef-c01d-4d20-bb1e-a8abc0063907)
- Unit Testing: Validates functionality and reliability of application components through unit tests.
![image](https://github.com/user-attachments/assets/b1afd3de-e0d6-484d-ac73-e617a75f732b)


## Technologies Used

- Spring Boot
- Spring Security
- JWT
- JPA/Hibernate
- MySQL
- Swagger
- bcrypt
- PayPal SDK
- Maven
- SLF4J
- JUnit
- Mockito
- SSL/TLS

## API Documentation
You can access the API documentation using Swagger UI at the following URL:
[Swagger UI](https://app.swaggerhub.com/apis/DAVID36924_1/SpringBoot-collection-functions-API/v0)

## Contributing
Follow the existing coding style.
- https://github.com/paypal
- https://github.com/auth0/java-jwt

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more info.


