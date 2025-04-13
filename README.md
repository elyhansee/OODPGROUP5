## SC2302 Object Oriented Design and Programming Group 5
# E-Commerce Management System

This is a Java-based console application simulating an e-commerce management system. It supports multiple user roles — **Customer**, **Seller**, and **Administrator** — each with their own capabilities and menu interfaces.

The application follows the **Model-View-Controller (MVC)** design pattern and adheres to key **SOLID object-oriented design principles** to ensure extensibility and maintainability.

---

## 👥 Contributors

| Name                 | 
|----------------------|
| Esther Lee           |
| Ethan Leo            | 
| Tan Dehan            | 
| Chen Kangxiang       |
| Cynthia Ng Geok Hwee |


## 🚀 Features

### ✅ Customer
- Browse and search products
- Add items to cart
- Checkout and place orders
- View order history and status

### ✅ Seller
- List new products
- Update existing products
- View sales orders
- Generate sales reports

### ✅ Administrator
- Register new users (Customer/Seller)
- View user data
- Generate platform-wide insights (orders & inventory)

---

## 🛠️ Technologies Used

- Java 17+
- Standard Java libraries (no external dependencies)
- File-based persistence using CSV (manual read/write)
- Console-based UI

---

## 🧭 Project Structure

The system follows an **MVC architecture**:
- `model/` → All data classes (e.g., Product, Order, User)
- `controller/` → Controllers managing logic per role
- `view/` → CLI views for interacting with users
- `Main.java` → Entry point and login handler
- `data/` → CSV files used for data storage

---

## 📂 How to Run the Program

> **Pre-requisites:** Java installed and `javac`/`java` available in your system's PATH

### ✨ Step-by-step:
1. Open terminal in the root project directory.
2. Compile all `.java` files:
   ```bash
   javac */*.java *.java
### Alternatively:
1. Open the entire folder in IntelliJ IDE
2. Ensure to declare a `.env` file and specify the 
root path for which the program should import saved CSV data from.
