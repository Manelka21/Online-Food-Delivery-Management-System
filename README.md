# 💳 Payment Management System

### FoodFleet – SE1020 Object Oriented Programming Project

---

## 📋 Overview

This is the **Payment subsystem** of the FoodFleet Online Food Delivery Management System.
Built with **Java Spring Boot**, **Thymeleaf HTML**, and **file-based text storage** (`payments.txt`).

**Student:** R. Rosairo
**Student ID:** IT25100202
**Subsystem:** Payment Management

---

## 🚀 How to Run in IntelliJ IDEA

### Prerequisites
- IntelliJ IDEA (Community or Ultimate)
- Java JDK 17+
- Maven (bundled with IntelliJ)

### Steps

1. **Open the project**
   - Open IntelliJ IDEA → `File` → `Open` → Select the `payment` folder inside `Backend`

2. **Wait for Maven to sync**
   - IntelliJ will automatically download all dependencies from `pom.xml`
   - Wait for the bottom progress bar to complete

3. **Run the application**
   - Open `src/main/java/com/fooddelivery/payment/PaymentApplication.java`
   - Click the green ▶ Run button next to the `main` method

4. **Open in browser**
   - Customer page: `http://localhost:8080/customer`
   - Admin panel: `http://localhost:8080/payments`

---

## 🌐 Pages & URLs

| Page | URL | CRUD Operation |
|---|---|---|
| Customer Home | `/customer` | – |
| Pay for Order | `/customer` → Pay Now | **CREATE** |
| View My Payments | `/customer` → My Payments | **READ** |
| Admin Dashboard | `/payments` | **READ All** |
| View Payment | `/payments/view/{id}` | **READ** |
| Edit Payment Status | `/payments/edit/{id}` | **UPDATE** |
| Delete Payment | `/payments/delete/{id}` | **DELETE** |
| Filter by Status | `/payments/filter?status=` | **READ** |

---

## ⚙️ CRUD Operations Summary

- **Create** → `POST /customer/pay` – Customer submits payment, saved to `payments.txt`
- **Read** → `GET /payments` – Admin views all payments from `payments.txt`
- **Read** → `POST /customer/my-payments` – Customer views their own payments
- **Update** → `POST /payments/edit/{id}` – Admin updates payment status
- **Delete** → `POST /payments/delete/{id}` – Admin removes payment from `payments.txt`

---

## 🧠 OOP Concepts Used

| Concept | Implementation |
|---|---|
| **Encapsulation** | All fields in `Payment.java` are `private` — accessed only via getters/setters |
| **Inheritance** | `CardPayment`, `CashPayment`, `OnlinePayment` all `extend Payment` |
| **Polymorphism** | `getPaymentType()` and `processPayment()` overridden differently in each subclass |
| **Abstraction** | `Payment.java` is `abstract` — cannot be instantiated directly |

---

## 📁 Project Structure

```
payment/
├── src/
│   └── main/
│       ├── java/com/fooddelivery/payment/
│       │   ├── controller/
│       │   │   ├── CustomerController.java   ← Customer side URLs
│       │   │   └── PaymentController.java    ← Admin side URLs
│       │   ├── model/
│       │   │   ├── Payment.java              ← Abstract parent class
│       │   │   ├── CardPayment.java          ← Extends Payment
│       │   │   ├── CashPayment.java          ← Extends Payment
│       │   │   └── OnlinePayment.java        ← Extends Payment
│       │   ├── service/
│       │   │   └── PaymentService.java       ← All CRUD logic
│       │   ├── util/
│       │   │   └── FileHandler.java          ← File read/write operations
│       │   └── PaymentApplication.java       ← Spring Boot entry point
│       └── resources/
│           ├── templates/
│           │   ├── customer/
│           │   │   ├── home.html             ← Customer payment page
│           │   │   └── my-payments.html      ← Customer payment history
│           │   └── payments/
│           │       ├── index.html            ← Admin dashboard
│           │       ├── view.html             ← View single payment
│           │       └── edit.html             ← Edit payment status
│           ├── data/
│           │   └── payments.txt              ← Text file database
│           └── application.properties
└── pom.xml
```

---

## 🗄️ File Storage Format (`payments.txt`)

Each line represents one payment record in CSV format:

```
paymentId,orderId,customerId,amount,status,createdAt,type,field1,field2
```

**Example:**
```
PAY-A1B2C3D4,ORD-00101,CUST-001,1500.00,COMPLETED,2026-03-31 10:15,CARD,4242,Kasun Perera
PAY-E5F6G7H8,ORD-00102,CUST-002,850.50,PENDING,2026-03-31 11:30,CASH,DRV-003,N/A
PAY-I9J0K1L2,ORD-00103,CUST-001,2200.00,COMPLETED,2026-03-31 12:00,ONLINE,PayHere,N/A
```

---

## 💳 Payment Types

| Type | Extra Fields |
|---|---|
| **CARD** | Last 4 digits of card, Card holder name |
| **CASH** | Delivery Driver ID |
| **ONLINE** | Wallet provider (PayHere, FriMi, koko, Genie) |

---

## 🔗 How it Connects to Other Subsystems

| Subsystem | Connection |
|---|---|
| **Cart (Saranga)** | Customer pays for orders created by Cart system |
| **Delivery Driver (Buddhila)** | Cash payments reference Driver ID |
| **Customer** | Payment records linked by Customer ID |

---

## 👤 Sample Data

| Customer ID | Order ID | Amount | Type | Status |
|---|---|---|---|---|
| CUST-001 | ORD-00101 | LKR 1500.00 | CARD | COMPLETED |
| CUST-002 | ORD-00102 | LKR 850.50 | CASH | PENDING |
| CUST-001 | ORD-00103 | LKR 2200.00 | ONLINE | COMPLETED |

---

## 🛠️ Tech Stack

- **Language:** Java
- **Framework:** Spring Boot 3.2.0 (embedded Tomcat)
- **Frontend:** HTML, CSS, Bootstrap 5, Thymeleaf
- **Storage:** Text files (.txt) — File Handling (no database)
- **Version Control:** GitHub
- **IDE:** IntelliJ IDEA
