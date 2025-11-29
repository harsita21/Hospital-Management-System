import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.hospital.util.PasswordUtil;

public class TestSetup {

    // --- CONFIGURATION ---
    // Make sure your MySQL server is running on localhost:3306
    private static final String DB_HOST = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "hospital_management";
    private static final String DB_URL = DB_HOST + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    
    // Set your MySQL root username and password here
    private static final String USERNAME = "root";
    
    // !!! IMPORTANT: CHANGE THIS TO YOUR ACTUAL MYSQL PASSWORD !!!
    private static final String PASSWORD = "Hm@2025"; 
    // (Your application.properties file says "01;new", so it might be that)


    public static void main(String[] args) {
        System.out.println("=== Hospital Management System Database Setup ===");

        try {
            // 1. Test MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ CRITICAL ERROR: MySQL Driver not found.");
            System.out.println("   Please make sure 'mysql-connector-j-9.4.0.jar' is in your project's build path.");
            return;
        }

        Connection conn = null;
        Statement stmt = null;
        
        try {
            // 2. Connect to the MySQL server (without a specific database)
            System.out.println("Connecting to MySQL server...");
            conn = DriverManager.getConnection(DB_HOST + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", USERNAME, PASSWORD);
            stmt = conn.createStatement();
            System.out.println("✅ Connection successful.");

            // 3. Create the database
            System.out.println("Creating database '" + DB_NAME + "'...");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("✅ Database created or already exists.");

            // 4. Close this connection and reconnect to the new database
            stmt.close();
            conn.close();
            
            System.out.println("Connecting to database '" + DB_NAME + "'...");
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            System.out.println("✅ Connected to '" + DB_NAME + "'.");

            // 5. Create tables
            System.out.println("Creating tables...");

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL," +
                "role VARCHAR(20) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "is_active BOOLEAN DEFAULT true" +
            ")";
            stmt.executeUpdate(createUsersTable);
            System.out.println("   - Table 'users' created.");

            String createPatientsTable = "CREATE TABLE IF NOT EXISTS patients (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT NOT NULL UNIQUE," +
                "name VARCHAR(100) NOT NULL," +
                "date_of_birth DATE," +
                "gender VARCHAR(10)," +
                "phone VARCHAR(20)," +
                "address TEXT," +
                "emergency_contact VARCHAR(100)," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
            ")";
            stmt.executeUpdate(createPatientsTable);
            System.out.println("   - Table 'patients' created.");

            String createDoctorsTable = "CREATE TABLE IF NOT EXISTS doctors (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT NOT NULL UNIQUE," +
                "name VARCHAR(100) NOT NULL," +
                "specialization VARCHAR(100)," +
                "department_id INT DEFAULT 1," +
                "phone VARCHAR(20)," +
                "experience INT," +
                "bio TEXT," +
                "is_active BOOLEAN DEFAULT true," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
            ")";
            stmt.executeUpdate(createDoctorsTable);
            System.out.println("   - Table 'doctors' created.");
            
            String createDepartmentsTable = "CREATE TABLE IF NOT EXISTS departments (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL" +
            ")";
            stmt.executeUpdate(createDepartmentsTable);
            System.out.println("   - Table 'departments' created.");

            // Insert a demo department for Dr. Sharma
            String demoDept = "INSERT IGNORE INTO departments (id, name) VALUES (1, 'Cardiology')";
            stmt.executeUpdate(demoDept);
            System.out.println("   - Demo department 'Cardiology' added.");
            
            String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "patient_id INT NOT NULL," +
                "doctor_id INT NOT NULL," +
                "appointment_date DATE NOT NULL," +
                "appointment_time TIME NOT NULL," +
                "status VARCHAR(20) NOT NULL," +
                "reason TEXT," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (patient_id) REFERENCES patients(id)," +
                "FOREIGN KEY (doctor_id) REFERENCES doctors(id)" +
            ")";
            stmt.executeUpdate(createAppointmentsTable);
            System.out.println("   - Table 'appointments' created.");
            
            System.out.println("✅ All tables created successfully.");

            // 6. Insert demo data
            System.out.println("Inserting demo users...");
            
            // Admin user (admin / admin123)
            // Admin user (admin / admin123)
            String adminPasswordHash = PasswordUtil.hashPassword("admin123");
            String adminUser = "INSERT IGNORE INTO users (username, email, password, role) VALUES (" +
                "'admin', " +
                "'admin@hospital.com', " +
                "'" + adminPasswordHash + "', " + // <-- THE FIX
                "'admin'" +
            ")";
            stmt.executeUpdate(adminUser);
            System.out.println("   - Demo admin user created.");

            // Doctor user (drsharma / admin123)
            // Doctor user (drsharma / admin123)
            String doctorPasswordHash = PasswordUtil.hashPassword("admin123"); // Can re-use admin hash
            String doctorUser = "INSERT IGNORE INTO users (username, email, password, role) VALUES (" +
                "'drsharma', " +
                "'drsharma@hospital.com', " +
                "'" + doctorPasswordHash + "', " + // <-- THE FIX
                "'doctor'" +
            ")";
            stmt.executeUpdate(doctorUser, Statement.RETURN_GENERATED_KEYS);
            System.out.println("   - Demo doctor user created.");
            
            // Doctor profile
            String doctorProfile = "INSERT IGNORE INTO doctors (user_id, name, specialization, phone) " +
                "SELECT id, 'Dr. Sharma', 'Cardiologist', '555-1234' " +
                "FROM users WHERE username = 'drsharma'";
            stmt.executeUpdate(doctorProfile);
            System.out.println("   - Demo doctor profile created.");

            System.out.println("\n✅ Database setup complete! You can now run the web application.");

        } catch (SQLException e) {
            System.out.println("❌ ERROR during database setup: " + e.getMessage());
            e.printStackTrace();
            System.out.println("\n--- Troubleshooting ---");
            System.out.println("1. Did you change the 'PASSWORD' variable in this file to your real MySQL password?");
            System.out.println("2. Is your MySQL server running on 'localhost:3306'?");
            System.out.println("3. Do you have permission to create databases and tables with the '" + USERNAME + "' user?");
        } finally {
            // 7. Clean up
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}