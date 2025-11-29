package com.hospital.dao;

import com.hospital.model.Appointment;
import com.hospital.exception.DatabaseException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    
    private final String BASE_SELECT_SQL = 
        "SELECT a.*, p.name as patient_name, d.name as doctor_name " +
        "FROM appointments a " +
        "JOIN patients p ON a.patient_id = p.id " + 
        "JOIN doctors d ON a.doctor_id = d.id ";  

    
    public List<Appointment> getAllAppointments() throws DatabaseException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving appointments", e);
        }
        return appointments;
    }
    
    public List<Appointment> getRecentAppointments(int limit) throws DatabaseException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "ORDER BY a.created_at DESC LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving recent appointments", e);
        }
        return appointments;
    }

    public boolean createAppointment(Appointment appointment) throws DatabaseException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, reason) " +
                     "VALUES (?, ?, ?, ?, 'Booked', ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointment.getPatientId()); // This is patients.id
            stmt.setInt(2, appointment.getDoctorId()); // This is doctors.id
            stmt.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            stmt.setTime(4, Time.valueOf(appointment.getAppointmentTime()));
            stmt.setString(5, appointment.getReason());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error creating appointment", e);
        }
    }

    // ✅ CORRECTED METHOD - Use a.patient_id instead of p.user_id
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws DatabaseException {
        List<Appointment> appointments = new ArrayList<>();
        // FIXED: Use a.patient_id which matches what we stored in the appointments table
        String sql = BASE_SELECT_SQL + 
                     "WHERE a.patient_id = ? " +  // ✅ CORRECT: Use the patient_id from appointments table
                     "ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);  // This should be the patients.id value (1 in your case)
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving appointments for patient", e);
        }
        return appointments;
    }
    
    public List<Appointment> getTodaysAppointments() throws DatabaseException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = BASE_SELECT_SQL + 
                     "WHERE a.appointment_date = CURDATE() AND a.status = 'Booked' " +
                     "ORDER BY a.appointment_time";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving today's appointments", e);
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws DatabaseException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "WHERE a.doctor_id = ? ORDER BY a.appointment_date, a.appointment_time";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving appointments for doctor", e);
        }
        return appointments;
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) throws DatabaseException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "WHERE a.appointment_date = ? AND a.status = 'Booked'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving appointments by date", e);
        }
        return appointments;
    }
    
    private Appointment extractAppointmentFromResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        appointment.setStatus(rs.getString("status"));
        appointment.setReason(rs.getString("reason"));
        appointment.setPatientName(rs.getString("patient_name"));
        appointment.setDoctorName(rs.getString("doctor_name"));
        appointment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return appointment;
    }
}