# GitHub Repository Searcher – Spring Boot Backend

## 📌 Overview

**GitHub Repository Searcher** is a Spring Boot backend application that integrates with the **GitHub REST API** to search public repositories, persist the results in a **PostgreSQL** database, and expose RESTful endpoints to retrieve stored repositories using flexible filters and sorting options.

The application follows a **clean layered architecture**, supports **UPSERT behavior**, includes **robust error handling**, and is fully **testable via Postman** (no UI).

---

## 🎯 Features

* Search public GitHub repositories using GitHub REST API
* Persist repository metadata in PostgreSQL
* Update existing repositories instead of duplicating records (UPSERT logic)
* Retrieve stored repositories with:

    * Language filter
    * Minimum stars filter
    * Sorting by stars, forks, or last updated date
* Centralized exception handling
* JUnit-based test coverage
* Clean separation of concerns (Controller, Service, Repository)

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Web**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL**
* **JUnit 5 & Mockito**
* **Maven**

---

## 🏗️ Architecture

The application follows a standard layered architecture:

```
Controller → Service → Repository → Database
                     ↓
               GitHub REST API
```

### Key Design Decisions

* **DTOs** are used to decouple GitHub API responses from database entities
* **Service layer** contains all business logic
* **Repository layer** contains only persistence logic
* **GitHub API client** is isolated for easy replacement (e.g., WebClient)
* **Centralized exception handling** via `@RestControllerAdvice`

---

## ⚙️ Prerequisites

* Java 17+
* Maven 3.9+
* PostgreSQL 14+
* Git (optional)

---

## 🗄️ Database Setup

```sql
CREATE DATABASE githubdb;

CREATE USER githubuser WITH PASSWORD 'your_password_here';

ALTER DATABASE githubdb OWNER TO githubuser;

GRANT ALL PRIVILEGES ON DATABASE githubdb TO githubuser;
```

---

## 🔧 Configuration

Update `application.yml`:

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/githubdb
    username: githubuser
    password: <your_db_pasword>

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Optional (Recommended): GitHub Token

To increase GitHub API rate limits

```yml
github:
  token: ${GITHUB_TOKEN}
```

Set environment variable:

---

## ▶️ Running the Application

```bash
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## 📡 API Endpoints

### 1️⃣ Search GitHub Repositories

**Endpoint**

```
POST /api/github/search
```

**Request Body**

```json
{
  "query": "spring boot",
  "language": "Java",
  "sort": "stars"
}
```

**Response**

```json
{
  "message": "Repositories fetched and saved successfully",
  "repositories": [
    {
      "id": 123456,
      "name": "spring-boot-example",
      "description": "Example project",
      "owner": "user123",
      "language": "Java",
      "stars": 450,
      "forks": 120,
      "lastUpdated": "2024-01-01T12:00:00Z"
    }
  ]
}
```

---

### 2️⃣ Retrieve Stored Repositories

**Endpoint**

```
GET /api/github/repositories
```

**Query Parameters**

* `language` (optional)
* `minStars` (optional)
* `sort` → `stars` | `forks` | `updated` (default: `stars`)

**Example**

```
GET /api/github/repositories?language=Java&minStars=100&sort=stars
```

---

## 🔁 UPSERT Behavior

* GitHub repository ID is used as the primary key
* If a repository already exists, its details are updated
* Prevents duplication and ensures latest repository statistics

---

## 🚨 Error Handling

* GitHub API errors (rate limits, invalid responses) are handled gracefully
* Centralized error responses via `@RestControllerAdvice`
* Meaningful HTTP status codes and descriptive error messages

---

## 🧪 Testing

The project includes:

* Repository tests using `@DataJpaTest`
* Service tests with mocked GitHub API client
* Coverage of core business logic and database filtering

Run tests:

```bash
mvn test
```

---

## 📈 Future Improvements

* Replace `RestTemplate` with `WebClient` for reactive support
* Add pagination support
* Add caching for GitHub API responses
* Introduce API documentation using Swagger / OpenAPI

---

## 👨‍💻 Author Notes

This project was built with a strong focus on:

* Code quality
* Maintainability
* Real-world backend best practices

The application is fully REST-compliant, testable, and production-ready in structure.
