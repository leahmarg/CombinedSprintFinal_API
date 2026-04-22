# ✈️ Airport API

A Spring Boot REST API for managing an airport system including airports, airlines, aircraft, gates, flights, and passengers.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Jakarta Validation
- Maven
- Postman
- MySQL (Docker)

---

## ▶️ How to Run

### Clone the project:
git clone https://github.com/leahmarg/CombinedSprintFinal_API

### Build project
mvn clean package -DskipTests

### Run with Docker
docker compose up --build

### Access API
http://localhost:8080

---

## 🗄️ Database

- MySQL running in Docker  
- Spring Data JPA (Hibernate) handles schema creation  
- Configured via application.properties  
- Database name: airport_db

---

## 📮 Postman Collection

Import:
postman/Airport_API.postman_collection.json

---

## ⚠️ IMPORTANT ORDER (VERY IMPORTANT)

Create entities in this order:

1. Airports
2. Airlines
3. Aircraft
4. Gates
5. Flights
6. Passengers

---

## 🔗 Entity Relationships

- Airport → has many Gates
- Airline → has many Flights
- Aircraft → used in many Flights
- Gate → belongs to Airport
- Flight → connects Airport, Airline, Aircraft, Gate
- Passenger → belongs to Flight

---

## 🧪 CRUD SUPPORT

All entities support:

- Create
- Read (all + by ID)
- Update
- Delete

---

## 👤 USER STORIES

Airports:
- Create, view, update, delete airports

Airlines:
- Create, view, update, delete airlines

Aircraft:
- Register aircraft with model and capacity
- Manage aircraft data

Gates:
- Assign gates to airports
- Manage gate numbers

Flights:
- Create flights between airports
- Assign airline, aircraft, and gate
- Manage schedules

Passengers:
- Assign passengers to flights
- Manage passenger details

---

## 🔐 Authentication (Basic Security)

This API is protected using Spring Security with Basic Authentication.

All endpoints require authentication before access is granted.

### Default Login Credentials

Username: admin  
Password: admin123

### How to Use in Postman

1. Open Postman request
2. Go to the **Authorization** tab
3. Select **Basic Auth**
4. Enter credentials:
    - Username: admin
    - Password: admin123

### Security Notes

- All API routes are secured by default
- Authentication is handled in-memory (no database required)
- CSRF protection is disabled for testing purposes
- Suitable for development and demonstration purposes

---

## ⚠️ NOTES

- All required fields must be provided
- IDs are auto-generated
- Foreign keys must exist before creating dependent entities
- Always use Postman in the correct order