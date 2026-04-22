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

---

## ▶️ How to Run

Clone the project:
git clone <repo-url>

Run the application:
./mvnw spring-boot:run

Open in browser:
http://localhost:8080

---

## 🗄️ Database

- Spring Data JPA (Hibernate)
- Configurable via application.properties
- Supports H2 or MySQL

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

(Required due to relationships)

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

## ⚠️ NOTES

- All required fields must be provided
- IDs are auto-generated
- Foreign keys must exist before creating dependent entities
- Always use Postman in the correct order