# Hospital Management System

A robust, web-based application for managing hospital operations, including patient registration, appointment scheduling, doctor management, and administrative reporting. Built using **Java Servlets, JSP, and MySQL**.

## 🎥 Demo

**View the application demo here:** [Watch Demo Video](https://drive.google.com/file/d/1KDvRx_c6BxyCGBFLliY-MyF3d6JMlBs6/view?usp=sharing)

---

## 📋 Features

### 🔐 User Roles & Authentication
* **Role-Based Access Control (RBAC):** Distinct dashboards and functionalities for **Admins**, **Doctors**, and **Patients**.
* **Secure Authentication:** User login system with password hashing.

### 🏥 Patient Module
* **Registration:** New patients can sign up for an account.
* **Appointment Booking:** Schedule appointments with available doctors.
* **Dashboard:** View upcoming appointments and personal history.

### 👨‍⚕️ Doctor Module
* **Dashboard:** View assigned appointments and patient details.
* **Schedule Management:** Manage availability.

### 🛠 Administrative Module
* **Doctor Management:** Add, update, or remove doctor profiles.
* **Patient Management:** View and manage registered patients.
* **Reports:** Generate system-wide reports (e.g., appointment stats).
* **Appointment Oversight:** View and manage all scheduled appointments.

## 💻 Technology Stack

* **Backend:** Java 17, Java Servlets, JSP (JavaServer Pages)
* **Database:** MySQL 8.0+
* **Build Tool:** Maven
* **Frontend:** HTML, CSS, JSTL (JavaServer Pages Standard Tag Library)
* **Server:** Apache Tomcat (or any Servlet 4.0+ compatible container)

## ⚙️ Prerequisites

Ensure you have the following installed:
* **Java Development Kit (JDK) 17**
* **Apache Maven**
* **MySQL Server**

## 🚀 Installation & Setup

### 1. Clone the Repository

### 2. Database Configuration
1.  **Create the Database:**
    Open your MySQL client (Workbench or CLI) and run:
    ```sql
    CREATE DATABASE hospital_management;
    USE hospital_management;
    ```

2.  **Create the Users Table:**
    (Based on the system's authentication logic)
    ```sql
    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        email VARCHAR(100) NOT NULL,
        password VARCHAR(255) NOT NULL,
        role VARCHAR(20) NOT NULL, -- Values: 'admin', 'doctor', 'patient'
        is_active BOOLEAN DEFAULT TRUE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    ```
    *Note: You may need to create additional tables (e.g., `doctors`, `patients`, `appointments`) depending on the full functionality.*

3.  **Insert an Admin User (Optional):**
    You will need a way to log in initially. Insert a user directly into the DB (ensure the password matches your hashing logic, or register via the UI if allowed):
    ```sql
    INSERT INTO users (username, email, password, role) 
    VALUES ('admin', 'admin@hospital.com', 'hashed_secret_password', 'admin');
    ```

### 3. Application Configuration
Locate the configuration file at `src/main/resources/application.properties`. Update the database credentials to match your local MySQL setup:

```properties
# MySQL Configuration
db.url=jdbc:mysql://localhost:3306/hospital_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.username=root  <-- Change to your MySQL username
db.password=your_password  <-- Change to your MySQL password

# App Settings
app.session.timeout=30
```

### 4. Build the Project
Use Maven to compile the code and package it into a WAR file:
```bash
mvn clean install
```
This will generate `HospitalManagementSystem.war` in the `target/` folder.

### 5. Deploy to Server
1.  Copy the generated **WAR file** (`target/HospitalManagementSystem.war`).
2.  Paste it into the `webapps/` directory of your Apache Tomcat installation.
3.  Start (or restart) the Tomcat server:
    * **Windows:** `bin\startup.bat`
    * **Mac/Linux:** `bin/startup.sh`

### 6. Run the Application
Once the server has started, open your web browser and navigate to:
```
http://localhost:8080/HospitalManagementSystem/
```
*(Port 8080 is default; adjust if your server uses a different port)*

---

## 📂 Project Structure

```
src/main/
├── java/com/hospital/
│   ├── dao/            # Database Access Logic (UserDAO, DatabaseConnection)
│   ├── model/          # Data Models (User, etc.)
│   ├── servlet/        # Controllers handling HTTP requests
│   └── util/           # Utility classes (PasswordUtil, Config)
├── resources/
│   └── application.properties  # Database & App Config
└── webapp/
    ├── WEB-INF/        # Configuration (web.xml)
    ├── admin/          # Administrator views
    ├── doctor/         # Doctor views
    ├── patient/        # Patient views
    └── shared/         # Common views (Login, Error)
```

## 🛡️ Security Features
* **SQL Injection Protection:** All database interactions use `PreparedStatement`.
* **Password Hashing:** Passwords are securely hashed before storage using `PasswordUtil`.
* **Session Management:** Sessions automatically timeout after 30 minutes of inactivity.
