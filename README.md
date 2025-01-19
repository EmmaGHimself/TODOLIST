# Todo List API

This repository contains the backend code for a simple Todo List API built with Spring Boot.

## Features

* **Create Todo Items:** Users can create new todo items with a title, description, due date, and priority.
* **Read Todo Items:** Users can retrieve a list of all todo items.
* **Update Todo Items:** Users can update existing todo items by modifying their title, description, due date, priority, and completion status.
* **Delete Todo Items:** Users can delete existing todo items.

## Technologies

* **Spring Boot:**  Framework for building RESTful APIs.
* **Spring Data JPA:**  Simplified data access layer for interacting with the database.
* **H2 Database:** Embedded database for development and testing.
* **Swagger:**  Open-source framework for API documentation and testing.

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/EmmaGHimself/TODOLIST
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API documentation:**
   Open your web browser and navigate to `http://localhost:8080/docs`.

5. **Access the Production API documentation:**
   Open your web browser and navigate to `https://todolist-app-wotma.ondigitalocean.app/docs`.

## Usage

You can use a REST client like Postman or curl to interact with the API.

**Example: Create a new todo item**

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Buy groceries",
    "description": "Milk, eggs, bread",
    "dueDate": "2024-02-15",
    "priority": 5
  }' \
  http://localhost:8080/api/todos
```

**Example: Get all todo items**

```bash
curl -X GET \
  http://localhost:8080/api/todos
```