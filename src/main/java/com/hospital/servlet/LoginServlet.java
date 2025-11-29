package com.hospital.servlet;

import com.hospital.dao.UserDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.model.User;
import com.hospital.model.Patient;
import com.hospital.model.Doctor;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.UserNotFoundException;
import com.hospital.thread.AppointmentReminderThread;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
        
        // Start background threads
        startBackgroundThreads();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/shared/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    try {
        User user = userDAO.authenticate(username, password);
        
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        session.setAttribute("userId", user.getId());
        
        // Set user-specific data
        if ("patient".equals(user.getRole())) {
            Patient patient = patientDAO.getPatientByUserId(user.getId());
            
            // --- FIX: CHECK IF PATIENT PROFILE EXISTS ---
            if (patient == null) {
                request.setAttribute("error", "Login successful, but your patient profile is missing. Please contact support.");
                request.getRequestDispatcher("/shared/login.jsp").forward(request, response);
                return;
            }
            // --- END FIX ---

            session.setAttribute("patient", patient);
            session.setAttribute("patientName", patient.getFullName());
            response.sendRedirect("patient/dashboard");

        } else if ("doctor".equals(user.getRole())) {
            Doctor doctor = doctorDAO.getDoctorByUserId(user.getId());
            
            // --- FIX: CHECK IF DOCTOR PROFILE EXISTS ---
            if (doctor == null) {
                request.setAttribute("error", "Login successful, but your doctor profile is missing. Please contact support.");
                request.getRequestDispatcher("/shared/login.jsp").forward(request, response);
                return;
            }
            // --- END FIX ---

            session.setAttribute("doctor", doctor);
            session.setAttribute("doctorName", doctor.getName());
            response.sendRedirect("doctor/dashboard");
            
        } else if ("admin".equals(user.getRole())) {
            response.sendRedirect("admin/dashboard");
        }
        
    } catch (UserNotFoundException e) {
        request.setAttribute("error", e.getMessage());
        request.getRequestDispatcher("/shared/login.jsp").forward(request, response);
    } catch (DatabaseException e) {
        request.setAttribute("error", "System error: " + e.getMessage());
        request.getRequestDispatcher("/shared/login.jsp").forward(request, response);
    // --- FIX: ADDED GENERIC CATCH BLOCK ---
    } catch (Exception e) {
        request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        request.getRequestDispatcher("/shared/login.jsp").forward(request, response);
    }
}
    
    private void startBackgroundThreads() {
        // Start appointment reminder thread
        AppointmentReminderThread reminderThread = new AppointmentReminderThread();
        reminderThread.start();
        
        // Add shutdown hook to stop threads gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(reminderThread::stopThread));
    }
}