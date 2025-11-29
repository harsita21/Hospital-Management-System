import java.sql.Connection;
import java.sql.Statement;
import com.hospital.dao.DatabaseConnection;

public class ClearUsers {
    public static void main(String[] args) {
        System.out.println("=== Clearing Demo Users from Database ===");
        
        // IMPORTANT: Must delete from "child" tables first to avoid foreign key errors
        String clearDoctors = "DELETE FROM doctors WHERE user_id IN (SELECT id FROM users WHERE username='admin' OR username='drsharma')";
        String clearPatients = "DELETE FROM patients WHERE user_id IN (SELECT id FROM users WHERE username='admin' OR username='drsharma')";
        String clearAppointments = "DELETE FROM appointments WHERE patient_id IN (SELECT id FROM users WHERE username='admin' OR username='drsharma') OR doctor_id IN (SELECT id FROM users WHERE username='admin' OR username='drsharma')";

        // Now we can delete from the "parent" table
        String clearUsers = "DELETE FROM users WHERE username='admin' OR username='drsharma'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Attempting to clear appointments...");
            int apptRows = stmt.executeUpdate(clearAppointments);
            System.out.println("   - " + apptRows + " appointments cleared.");

            System.out.println("Attempting to clear doctor profiles...");
            int docRows = stmt.executeUpdate(clearDoctors);
            System.out.println("   - " + docRows + " doctor profiles cleared.");
            
            System.out.println("Attempting to clear patient profiles...");
            int patRows = stmt.executeUpdate(clearPatients);
            System.out.println("   - " + patRows + " patient profiles cleared.");

            System.out.println("Attempting to clear users...");
            int userRows = stmt.executeUpdate(clearUsers);
            System.out.println("   - " + userRows + " users ('admin', 'drsharma') cleared.");

            System.out.println("\n✅ User cleanup complete. You can now run TestSetup.");

        } catch (Exception e) {
            System.out.println("❌ ERROR during user cleanup:");
            e.printStackTrace();
        }
    }
}