# ⚡ Electricity Billing System (Java CLI)

A simple command-line based Electricity Billing System in Java.
No database required — all data is stored in plain text files.

---

## 📁 Project Structure

```
ElectricityBilling/
├── Main.java           → Entry point, main menu
├── Customer.java       → Customer model
├── Bill.java           → Bill model
├── BillingSystem.java  → All business logic
├── BillCalculator.java → Rate slab calculations
├── FileHandler.java    → Read/write from .txt files
└── README.md           → This file
```

---

## 🚀 How to Compile and Run

### Step 1 – Open terminal in the project folder

```bash
cd ElectricityBilling
```

### Step 2 – Compile all Java files

```bash
javac *.java
```

### Step 3 – Run the program

```bash
java Main
```

---

## 🗄️ Data Files (auto-created on first use)

| File           | Description                    |
|----------------|-------------------------------|
| customers.txt  | Stores customer records        |
| bills.txt      | Stores all bills/history       |

These files are created automatically when you add a customer or generate a bill.

---

## 💡 Features

| #  | Feature             | Description                              |
|----|---------------------|------------------------------------------|
| 1  | Add Customer        | Register with name, address, phone, type |
| 2  | View All Customers  | List all registered customers            |
| 3  | Generate Bill       | Enter meter reading → auto-calculate     |
| 4  | Pay Bill            | Mark bill as paid                        |
| 5  | View Bill History   | See all bills for a customer             |
| 6  | Search Customer     | Find by ID or name                       |
| 7  | Delete Customer     | Remove customer (only if bills cleared)  |

---

## ⚡ Rate Slabs

### Domestic
| Units       | Rate/Unit |
|-------------|-----------|
| 0 – 100     | Rs. 3.50  |
| 101 – 200   | Rs. 5.00  |
| 201 – 300   | Rs. 6.50  |
| 300+        | Rs. 8.00  |
- Fixed Charge: Rs. 50
- Tax: 5%

### Commercial
| Units       | Rate/Unit |
|-------------|-----------|
| 0 – 100     | Rs. 6.00  |
| 101 – 300   | Rs. 8.50  |
| 300+        | Rs. 10.00 |
- Fixed Charge: Rs. 150
- Tax: 10%

---

## 📋 Requirements
- Java JDK 8 or higher
- No external libraries needed
