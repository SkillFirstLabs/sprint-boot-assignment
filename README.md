<<<<<<< HEAD
# sprint-boot-assignment
=======
# 🎓 Student Management System — Spring Boot + PostgreSQL (Cloud)

This project is a **RESTful Student Management System** built with **Spring Boot 3.x**, connected to a **cloud-hosted PostgreSQL database** via Render. It supports complete CRUD operations, filtering, sorting, pagination, and soft delete functionality.

> ✅ Submitted for: **Spring Boot Intern Assessment - Round 1**  
> 💡 Author: Naresh V

---

## 🚀 Features

- ✅ Cloud DB integration (PostgreSQL on Render)
- ✅ Create, Read, Update, Delete (CRUD)
- ✅ Soft delete via `status = INACTIVE`
- ✅ Advanced filtering:
  - `status=ACTIVE/INACTIVE`
  - GPA range: `minGpa`, `maxGpa`
  - Name search (first or last name)
- ✅ Sorting by any field (multi-field supported)
- ✅ Pagination support
- ✅ Validation & proper error handling

---

## 🧠 Technologies Used

| Tech               | Version       |
|--------------------|---------------|
| Java               | 17            |
| Spring Boot        | 3.x           |
| Spring Data JPA    | ✅            |
| PostgreSQL (Render)| Cloud DB      |
| Gradle             | ✅            |
| Postman            | For Testing   |

---

## 📁 Project Structure

```
student-management-system/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/student/studentmanagement/
│       │       ├── controller/         # API endpoints
│       │       │   └── StudentController.java
│       │       ├── exception/          # Custom exceptions
│       │       │   └── StudentNotFoundException.java
│       │       ├── model/              # Entity + Enum
│       │       │   ├── Student.java
│       │       │   └── StudentStatus.java
│       │       ├── repository/         # JPA repository
│       │       │   └── StudentRepository.java
│       │       ├── service/            # Business logic
│       │       │   ├── StudentService.java
│       │       │   └── StudentServiceImpl.java
│       │       └── specification/      # Dynamic filters
│       │           └── StudentSpecification.java
│       └── resources/
│           ├── application.properties  # Cloud DB config
│
├── build.gradle
├── settings.gradle
├── .gitignore
├── README.md
```

---

## 🔐 Cloud PostgreSQL Configuration

> In `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://<your-render-db-url>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 📮 API Endpoints

| Method | Endpoint                   | Description                           |
|--------|----------------------------|---------------------------------------|
| POST   | `/api/students`            | Create new student                    |
| GET    | `/api/students/{id}`       | Get student by ID                     |
| PUT    | `/api/students/{id}`       | Update student                        |
| DELETE | `/api/students/{id}`       | Soft delete student                   |
| GET    | `/api/students`            | List all students with filters        |

---

### 📌 Query Parameters for `/api/students`

| Parameter   | Type     | Description                            |
|-------------|----------|----------------------------------------|
| `page`      | int      | Page number (default: 0)               |
| `size`      | int      | Items per page (default: 10)           |
| `sort`      | string   | e.g. `firstName,asc` or `gpa,desc`     |
| `status`    | string   | `ACTIVE` or `INACTIVE`                 |
| `minGpa`    | double   | Minimum GPA                            |
| `maxGpa`    | double   | Maximum GPA                            |
| `name`      | string   | Filter by first or last name (partial) |

---

## ❌ Error Handling Examples

| Scenario                  | Status Code | Message Example                          |
|---------------------------|-------------|-------------------------------------------|
| Invalid email format      | 400         | `Invalid email format`                   |
| Missing required fields   | 400         | `First name is required`                 |
| Student not found (GET)   | 404         | `Student not found with ID: {id}`        |
| No results after filtering| 204         | No Content                                |

---

## 🧪 How to Test

Use **Postman** or any REST client:

### ✅ Create Student (POST)

```
POST http://localhost:8080/api/students
```

```json
{
  "firstName": "Meera",
  "lastName": "Iyer",
  "email": "meera.iyer@example.com",
  "dateOfBirth": "2004-01-15",
  "enrollmentDate": "2025-07-01",
  "gpa": 9.1
}
```

---

### ✅ List All with Filters

```
GET http://localhost:8080/api/students?status=ACTIVE&minGpa=7.5&sort=gpa,desc
```

---

### ✅ Get Student by ID

```
GET http://localhost:8080/api/students/1
```

---

### ✅ Update Student

```
PUT http://localhost:8080/api/students/1
```

```json
{
  "firstName": "Meera",
  "lastName": "Krishnan",
  "email": "meera.krishnan@example.com",
  "dateOfBirth": "2004-01-15",
  "enrollmentDate": "2025-07-01",
  "gpa": 9.3,
  "status": "ACTIVE"
}
```

---

### ✅ Soft Delete Student

```
DELETE http://localhost:8080/api/students/1
```

---

## 🎥 Demo Video Requirements

Include in your screen recording:

- ✅ Terminal logs showing PostgreSQL cloud connection
- ✅ All API endpoints tested via Postman
- ✅ Validations and error responses
- ✅ Filtering, pagination, sorting in action

---

## 📦 Build & Run

```bash
./gradlew build
./gradlew bootRun
```

> If port 8080 is busy:
> - Kill the process using:  
>   `npx kill-port 8080` or `netstat -ano | findstr :8080` + `taskkill /PID <PID> /F`

---

>>>>>>> de29667f4a9b10a5684fce7f64af69c013b68db8
