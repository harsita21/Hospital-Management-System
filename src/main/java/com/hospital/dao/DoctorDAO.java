package com.hospital.dao;

import com.hospital.model.Doctor;
import com.hospital.exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    
    public List<Doctor> getAllDoctors() throws DatabaseException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.*, u.username, u.email, dep.name as department_name " +
                    "FROM doctors d " +
                    "JOIN users u ON d.user_id = u.id " +
                    "LEFT JOIN departments dep ON d.department_id = dep.id " +
                    "WHERE d.is_active = true";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Doctor doctor = extractDoctorFromResultSet(rs);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving doctors", e);
        }
        return doctors;
    }
    
    public Doctor getDoctorByUserId(int userId) throws DatabaseException {
        String sql = "SELECT d.*, u.username, u.email, dep.name as department_name " +
                    "FROM doctors d " +
                    "JOIN users u ON d.user_id = u.id " +
                    "LEFT JOIN departments dep ON d.department_id = dep.id " +
                    "WHERE d.user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractDoctorFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving doctor by user ID", e);
        }
    }
    
    public Doctor getDoctorById(int doctorId) throws DatabaseException {
        String sql = "SELECT d.*, u.username, u.email, dep.name as department_name " +
                    "FROM doctors d " +
                    "JOIN users u ON d.user_id = u.id " +
                    "LEFT JOIN departments dep ON d.department_id = dep.id " +
                    "WHERE d.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractDoctorFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving doctor by ID", e);
        }
    }
    
    public boolean createDoctor(Doctor doctor) throws DatabaseException {
        String sql = "INSERT INTO doctors (user_id, name, specialization, department_id, phone, experience, bio) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctor.getId());  // This is the user_id from User class
            stmt.setString(2, doctor.getName());
            stmt.setString(3, doctor.getSpecialization());
            stmt.setInt(4, doctor.getDepartmentId());
            stmt.setString(5, doctor.getPhone());
            stmt.setInt(6, doctor.getExperience());
            stmt.setString(7, doctor.getBio());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error creating doctor", e);
        }
    }
    
    private Doctor extractDoctorFromResultSet(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        
        // Set User class fields (inherited)
        doctor.setId(rs.getInt("user_id"));          // users.id
        doctor.setUsername(rs.getString("username"));
        doctor.setEmail(rs.getString("email"));
        
        // Set Doctor class fields
        doctor.setDoctorId(rs.getInt("id"));         // doctors.id (primary key) - THIS IS CRITICAL!
        doctor.setName(rs.getString("name"));
        doctor.setSpecialization(rs.getString("specialization"));
        doctor.setDepartmentId(rs.getInt("department_id"));
        doctor.setPhone(rs.getString("phone"));
        doctor.setExperience(rs.getInt("experience"));
        doctor.setBio(rs.getString("bio"));
        doctor.setDepartmentName(rs.getString("department_name"));
        
        return doctor;
    }
}