# Electronic Products Inventory Management System

## 📋 Project Overview

This is a JavaFX-based desktop application for managing an electronic products inventory. The application features a login system, a dynamic GUI interface, and a backend MySQL database to store and retrieve product data. It includes:

- **Login Screen** for user authentication
- **Main Product Management Interface** to view, add, and update product details
- **MySQL Database Integration** for persistent storage

---

## 🚀 Features

- **User Authentication** via a secure login form (SHA-256 hashed passwords)
- **Product Listing** using JavaFX ListView
- **Detailed Product Info** displayed upon selection
- **MySQL Backend** for storing product and user data
- **Modular Code Design** using classes like `DatabaseHelper`, `Product`, `ProductManagerScreen`, etc.

---

## 🛠️ Technologies Used

- Java (JavaFX)
- MySQL
- JDBC (Java Database Connectivity)
- SHA-256 hashing for passwords

---

## 🗃️ Database Setup

Import the following SQL files in order:

1. `electronicproducts.sql` — Sets up the `electronicproducts` database and populates the `electronicproducts` table.
2. `electronicproducts_login.sql` — Adds the `users` table and inserts demo login credentials.

```sql
-- Sample credentials:
Username: admin
Password: password123

Username: sbonner3
Password: Passsword01
```

> 💡 Passwords are stored securely using SHA2-256 hashing.

---

## 🧱 Project Structure

```
📁 src/
├── DatabaseHelper.java         # Handles DB connection and queries
├── HelloApplication.java       # JavaFX launcher
├── HelloController.java        # Controller for main view
├── LoginScreen.java            # Login screen and auth logic
├── Main.java                   # Launches the application
├── Product.java                # Product model class
└── ProductManagerScreen.java   # GUI for product viewing/updating
```

---

## ▶️ How to Run

1. Ensure MySQL is installed and running.
2. Import the SQL files to set up the database.
3. Update DB connection credentials in `DatabaseHelper.java`.
4. Run `Main.java` or `HelloApplication.java` from your IDE.

---

## 👨‍💻 Author

Sa'Cairo Bonner
