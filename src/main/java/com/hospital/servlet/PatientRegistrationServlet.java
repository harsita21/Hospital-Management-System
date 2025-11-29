package com.hospital.servlet;

import com.hospital.dao.UserDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.model.User;
import com.hospital.model.Patient;
import com.hospital.util.PasswordUtil;
import com.hospital.exception.DatabaseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/patient/register")
public class PatientRegistrationServlet extends HttpServlet {
    private UserDAO userDAO;
    private PatientDAO patientDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        patientDAO = new PatientDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get form parameters with multiple catch blocks for different exceptions
            String username = getParameterSafe(request, "username");
            String email = getParameterSafe(request, "email");
            String password = getParameterSafe(request, "password");
            String confirmPassword = getParameterSafe(request, "confirm_password");
            String fullName = getParameterSafe(request, "full_name");
            String phone = getParameterSafe(request, "phone");
            String gender = request.getParameter("gender");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            
            // Validate required fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty() || phone.isEmpty()) {
                request.setAttribute("error", "All required fields must be filled");
                request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
                return;
            }
            
            // Validate passwords match
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
                return;
            }
            
            // Check if username exists
            if (userDAO.isUsernameExists(username)) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
                return;
            }
            
            // Create user
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(PasswordUtil.hashPassword(password));
            user.setRole("patient");
            
            if (userDAO.createUser(user)) {
                // Create patient
                Patient patient = new Patient();
                patient.setId(user.getId()); // Set user ID as patient ID
                patient.setFullName(fullName);
                patient.setPhone(phone);
                patient.setGender(gender);
                patient.setAddress(address);
                
                if (dob != null && !dob.isEmpty()) {
                    try {
                        patient.setDateOfBirth(LocalDate.parse(dob));
                    } catch (Exception e) {
                        // Handle date parsing error
                        request.setAttribute("error", "Invalid date format");
                        request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
                        return;
                    }
                }
                
                if (patientDAO.createPatient(patient)) {
                    request.setAttribute("success", "Registration successful! Please login.");
                    response.sendRedirect(request.getContextPath() + "/login");
                } else {
                    request.setAttribute("error", "Error creating patient profile");
                    request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Error creating user account");
                request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
            }
            
        } catch (DatabaseException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/patient/register.jsp").forward(request, response);
        }
    }
    
    private String getParameterSafe(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return value != null ? value.trim() : "";
    }
}