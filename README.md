# Student Management System

This is a Spring Boot 3.x RESTful API project that manages student records. It provides full CRUD operations with pagination, sorting, filtering, validation, and soft delete functionality. It connects to a PostgreSQL database and is suitable for deployment with either a local or cloud-hosted PostgreSQL instance.

---

## 🚀 Features

- ✅ Create a new student
- ✅ View a student by ID
- ✅ Update student information
- ✅ Soft delete a student (mark as INACTIVE)
- ✅ List all students with:
  - Pagination
  - Sorting (e.g., by GPA or ID)
  - Filtering (by GPA range, name, status)
- ✅ Validation (email, GPA range, required fields)
- ✅ Error handling (400 for invalid input, 404 for not found)

---

## 💻 Tech Stack

| Layer       | Technology      |
|-------------|-----------------|
| Backend     | Spring Boot 3.x |
| Language    | Java 17         |
| Database    | PostgreSQL      |
| Build Tool  | Gradle          |
| Testing     | Postman         |
| DB Client   | pgAdmin         |

---

## 🎯 Student Entity Fields

| Field          | Type     | Validation                     |
|----------------|----------|--------------------------------|
| id             | Long     | Auto-generated                 |
| firstName      | String   | Required                       |
| lastName       | String   | Required                       |
| email          | String   | Required, must be valid email  |
| dateOfBirth    | Date     | Required                       |
| enrollmentDate | Date     | Required                       |
| gpa            | Double   | Between 0.0 and 10.0           |
| status         | Enum     | ACTIVE / INACTIVE (default: ACTIVE) |

---

## 🧪 API Endpoints

| Method | Endpoint               | Description         |
|--------|------------------------|---------------------|
| POST   | /api/students          | Create student      |
| GET    | /api/students          | List students       |
| GET    | /api/students/{id}     | Get student by ID   |
| PUT    | /api/students/{id}     | Update student      |
| DELETE | /api/students/{id}     | Soft delete student |

🧠 Query Params for GET /api/students:

- Pagination: page, size  
- Sorting: sort=gpa,desc  
- Filters: status, minGpa, maxGpa, name

Example:  
/api/students?page=0&size=5&sort=gpa,desc&status=ACTIVE&minGpa=6.0&name=john

---

## ⚠️ Error Handling

- 400 Bad Request → for invalid input (e.g., email or GPA)
- 404 Not Found → for non-existing student ID
- 204 No Content → when a student is deleted

---

## 🔧 How to Run the Project

1. Clone the repository
2. Create a PostgreSQL database (local or cloud)
3. Configure application.properties:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Run the app:
   ```
   ./gradlew bootRun
   ```

5. Test with Postman or curl.

---

## ✅ Submission Checklist

- [x] Spring Boot 3.x with Java 17
- [x] All CRUD endpoints
- [x] Validation & error handling
- [x] Soft delete implemented
- [x] Filtering, pagination, sorting
- [x] PostgreSQL integration
- [ ] Cloud DB configured (to be added)
- [x] Postman-tested