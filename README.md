# 🚴 Delivery Driver Management System
### FoodFleet – SE1020 Object Oriented Programming Project

---

## 📋 Overview
This is the **Delivery Driver subsystem** of the FoodFleet Online Food Delivery Management System.  
Built with **Java Spring Boot**, **JSP**, and **file-based text storage** (`drivers.txt`).

---

## 🚀 How to Run in IntelliJ IDEA

### Prerequisites
- IntelliJ IDEA (Community or Ultimate)
- Java JDK 17+
- Maven (bundled with IntelliJ)

### Steps

1. **Open the project**
   - Open IntelliJ IDEA → `File` → `Open` → Select the `DeliveryDriverSystem` folder

2. **Wait for Maven to sync**
   - IntelliJ will automatically download all dependencies from `pom.xml`
   - Wait for the bottom progress bar to complete

3. **Run the application**
   - Open `src/main/java/com/fooddelivery/DeliveryDriverApplication.java`
   - Click the **green ▶ Run** button next to the `main` method
   - OR right-click the file → `Run 'DeliveryDriverApplication'`

4. **Open in browser**
   - Navigate to: `http://localhost:8080`
   - You will be redirected to the Driver Login page

---

## 🌐 Pages & URLs

| Page             | URL                              | CRUD Operation |
|------------------|----------------------------------|----------------|
| Login            | `/driver/login`                  | –              |
| Register         | `/driver/register`               | **CREATE**     |
| Dashboard        | `/driver/dashboard`              | –              |
| View Profile     | `/driver/profile`                | **READ**       |
| Edit Details     | `/driver/edit`                   | **UPDATE**     |
| Delete Account   | `/driver/delete`                 | **DELETE**     |
| All Drivers List | `/driver/all`                    | **READ All**   |
| Logout           | `/driver/logout`                 | –              |

---

## 🔐 Sample Login Credentials

| Email                   | Password | Name              |
|-------------------------|----------|-------------------|
| buddhila@foodfleet.lk   | pass123  | Buddhila Perera   |
| kasun@foodfleet.lk      | pass456  | Kasun Silva       |
| nuwan@foodfleet.lk      | pass789  | Nuwan Fernando    |

---

## 📁 Project Structure

```
DeliveryDriverSystem/
├── data/
│   └── drivers.txt                  ← Text file database
├── src/
│   └── main/
│       ├── java/com/fooddelivery/
│       │   ├── DeliveryDriverApplication.java   ← Spring Boot entry point
│       │   ├── model/
│       │   │   └── DeliveryDriver.java           ← Model (Encapsulation)
│       │   ├── servlet/
│       │   │   └── DriverController.java         ← MVC Controller (all routes)
│       │   └── util/
│       │       └── FileHandler.java              ← File I/O (CRUD on .txt)
│       ├── resources/
│       │   └── application.properties
│       └── webapp/
│           ├── css/
│           │   └── style.css                     ← Styles
│           ├── index.jsp                         ← Redirect entry
│           └── WEB-INF/views/driver/
│               ├── login.jsp
│               ├── register.jsp
│               ├── dashboard.jsp
│               ├── profile.jsp
│               ├── edit.jsp
│               ├── delete.jsp
│               ├── all.jsp
│               └── sidebar.jsp
└── pom.xml
```

---

## 💡 OOP Concepts Demonstrated

| Concept           | Where                                              |
|-------------------|----------------------------------------------------|
| **Encapsulation** | `DeliveryDriver.java` – private fields + getters/setters |
| **Abstraction**   | `FileHandler.java` – abstracts all file I/O operations |
| **Inheritance**   | `DeliveryDriverApplication` extends `SpringBootServletInitializer` |
| **Polymorphism**  | Spring MVC controller method overloading (GET/POST same path) |

---

## 📄 File Storage Format (`drivers.txt`)

Each line represents one driver, pipe-delimited:

```
driverId|firstName|lastName|email|password|phone|vehicleType|licenseNumber|status|registeredDate
```

Example:
```
DD001|Buddhila|Perera|buddhila@foodfleet.lk|pass123|+94771234567|MOTORBIKE|B1234567|ACTIVE|2024-01-15
```

---

## ✅ CRUD Operations Summary

- **Create** → `POST /driver/register` – Registers a new driver, appends to `drivers.txt`
- **Read**   → `GET /driver/profile` – Reads and displays driver profile from file
- **Read**   → `GET /driver/all` – Reads and displays all drivers
- **Update** → `POST /driver/edit` – Updates driver details, rewrites `drivers.txt`
- **Delete** → `POST /driver/delete` – Removes driver from `drivers.txt`
