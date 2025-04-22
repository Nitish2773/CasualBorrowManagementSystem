
# 📘 CasualBorrowManagementSystem

**CasualBorrowManagementSystem** is a minimalist yet mighty console-based Java application that lets you track borrowed items like books, gadgets, tools, and more. Built with raw **JDBC + MySQL**, it's perfect for those who love working close to the metal — no bloated frameworks, just clean, functional code.

---

## 🚀 Features (aka What You Can Do)

Here’s the full menu of options my app supports:

1. **Register** – New user? Sign up with a username and password.
2. **Login** – Existing user? Authenticate yourself.
3. **Add Borrower** – Record someone to whom you lend things.
4. **Lend Item** – Add an item you've lent to a borrower, with due date.
5. **Mark Item as Returned** – Item came back? Mark it so.
6. **View Overdue Items** – List items past their due date.
7. **View Trust Scores** – See how trustworthy each borrower is based on return history.
8. **Search Borrowers by Name** – Look up borrowers by first/last name.
9. **Search Items by Name** – Search for lent items by name.
10. **View All Borrowed Items** – List everything currently borrowed.
11. **Delete Borrower** – Remove a borrower (and cascade? careful).
12. **Delete Item** – Remove an item record.
13. **View All Borrowers** – See your full borrower list.
14. **Exit** – Peace out!

---

## 🛠️ Tech Stack

- **Java** (Core + JDBC)
- **MySQL**
- **SQL DDL/DML**
- Plain ol’ Java Console I/O

---

## 🧠 How the App Works

1. User connects to a MySQL database by name (dynamic `getConnection(dbName)`).
2. They register/login.
3. Then they manage borrowers and items using a menu-based CLI.
4. Trust scores are calculated based on total items returned vs borrowed.
5. Everything is neatly separated into modular `services/` and `db/` packages.

---

## 🗃️ Database Schema

```sql
-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Borrowers Table
CREATE TABLE IF NOT EXISTS borrowers (
    borrower_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    contact_number VARCHAR(15),
    total_borrowed INT DEFAULT 0,
    total_returned INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Items Table
CREATE TABLE IF NOT EXISTS items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    borrower_id INT,
    description TEXT,
    available BOOLEAN DEFAULT TRUE,
    due_date DATE,
    date_borrowed DATE,
    date_returned DATE DEFAULT NULL,
    FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id)
);
```

---

## ▶️ How to Run It

### 1. Clone This Repo

```bash
git clone https://github.com/yourusername/CasualBorrowManagementSystem.git
cd CasualBorrowManagementSystem
```

### 2. Setup MySQL

- Create a schema (e.g., `cbms`)
- Run the SQL schema above to set up tables

### 3. Configure DB Credentials

In your `DBConnection.java` file, update:

```java
String url = "jdbc:mysql://localhost:3306/cbms";
String user = "root";
String password = "yourpassword";
```

### 4. Add JDBC Driver

Download and add `mysql-connector-java.jar` to your classpath.

### 5. Compile & Run

```bash
javac Main.java
java Main
```

---

## 🗂️ Project Structure 

```
CasualBorrowManagementSystem/
│
├── main/
│   └── Main.java
├── services/
│   ├── UserService.java
│   ├── BorrowerService.java
│   └── ItemService.java
├── db/
│   └── DBConnection.java
├── models/
│   ├── User.java
│   ├── Borrower.java
│   └── Item.java
└── README.md
```

## 📸 Screenshots 

<p align="center">
  <img src="assets/Screenshot (233).png" width="400"/>
  <img src="assets/Screenshot (234).png" width="400"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (235).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (236).png" width="300"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (237).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (238).png" width="300"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (239).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (240).png" width="300"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (241).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (242).png" width="300"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (243).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (244).png" width="300"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (245).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (246).png" width="300"/>
</p>

<p align="center">
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (247).png" width="300"/>
  <img src="D:/Spring-Tool-Suite-Workspace/CasualBorrowManagementSystem/assets/Screenshot (248).png" width="300"/>
</p>


---

## 🔮 Future Enhancements

- Web interface (Spring MVC or JSP/Servlets)
- Email/SMS reminders for due items
- PDF/CSV reports
- QR code tagging of physical items
- Login session tracking

---

## 📌 Why This Project?

Unlike cliché Java projects (student DBs or ticket bookings), **CasualBorrowManagementSystem** solves a *relatable* life problem: “Where the heck is my charger/book/headphones?” It’s your personal memory for borrowed stuff — with trust ratings baked in. Forget spreadsheets. Go pro.

---

## 📄 License

MIT License — Free to use, remix, or extend. Just give credit where it’s due. ✌️

---
