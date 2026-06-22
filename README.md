# Task Manager – Full Stack Web Application

A full-stack task management web application built with **Spring Boot**, **PostgreSQL**, and **vanilla JavaScript**, deployed on **Render** using Docker.

Live Application: https://taskmanager-1-rt1r.onrender.com
*(Note: initial load may take 2–3 minutes due to Render free-tier cold start)*

---

## Overview

This project is a cloud-deployed task management system that allows users to register, log in, and manage personal tasks. It demonstrates full-stack development skills including REST API design, database integration, authentication, and frontend-backend communication.

The application is split into:

* Backend: Spring Boot REST API
* Database: PostgreSQL (hosted on Render)
* Frontend: HTML, CSS, JavaScript (Fetch API)
* Deployment: Docker + Render Web Service

---

## Features

* User registration and login system
* Session handling using browser `localStorage`
* Create, read, update, and delete (CRUD) tasks
* Mark tasks as complete/incomplete
* Task filtering:

  * Completed / Incomplete
  * Due date filtering (today, upcoming, overdue)
* Persistent cloud database storage (PostgreSQL)
* Responsive dynamic UI updates without page reloads

---

## Tech Stack

**Backend**

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA (Hibernate)
* PostgreSQL

**Frontend**

* HTML5
* CSS3
* JavaScript (Fetch API)

**Deployment**

* Docker
* Render (Web Service + PostgreSQL)

---

## Project Structure

```
TaskManager/
├── src/main/java
│   ├── controller
│   ├── service
│   ├── model
│   ├── repository
├── src/main/resources
│   ├── application.properties
├── frontend
│   ├── login.html
│   ├── app.html
│   ├── index.html
│   ├── register.html
│   ├── app.js
│   ├── index.js
│   ├── login.js
│   ├── register.js
│   ├── styles.css
├── Dockerfile
├── build.gradle
```

---

## API Endpoints

### User Controller

* `POST /users` → Create user
* `POST /users/login` → Login user
* `GET /users/{username}` → Find user

### Task Controller

* `GET /tasks/user/{userId}` → Get tasks for user
* `POST /tasks/user/{userId}` → Create task
* `PUT /tasks/{id}` → Update task
* `DELETE /tasks/{id}` → Delete task
* `PUT /tasks/{id}/complete` → Mark complete
* `PUT /tasks/{id}/incomplete` → Mark incomplete

---

## Environment Variables (Production)

The application uses environment variables for database configuration:

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Example JDBC format:

```
jdbc:postgresql://<host>/<database>
```

---

## Deployment Notes

* Built using Gradle (`./gradlew bootJar`)
* Packaged into a Docker container
* Deployed on Render Web Service
* Uses Render-managed PostgreSQL instance
* Cold start delay (~2–3 minutes) on free tier

---

## Local Setup

### Prerequisites

* Java 17+
* PostgreSQL installed locally
* Gradle

### Steps

```bash
git clone <repo-url>
cd TaskManager
./gradlew bootRun
```

Update `application.properties` with local DB credentials:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

## Future Improvements

* Password encryption (BCrypt)
* JWT authentication instead of localStorage
* Pagination for tasks
* UI framework (React or Vue)
* Role-based access control
* Better error handling and validation

---

## Author

Built by Jayden D. as a full-stack development project to demonstrate backend API design, database integration, and cloud deployment.

---
