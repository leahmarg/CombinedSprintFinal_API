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

## 🔐 AUTH
If security is enabled:
Username: admin  
Password: admin123  
Basic Auth (Postman)

---

## ⚠️ NOTES

- All required fields must be provided
- IDs are auto-generated
- Foreign keys must exist before creating dependent entities
- Always use Postman in the correct order