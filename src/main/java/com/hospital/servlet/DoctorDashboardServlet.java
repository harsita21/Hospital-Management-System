package com.hospital.servlet;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.exception.DatabaseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctor/dashboard")
public class DoctorDashboardServlet extends HttpServlet {
    
    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;
    
    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
        doctorDAO = new DoctorDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"doctor".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Get the logged-in doctor's user ID
            int doctorUserId = (Integer) session.getAttribute("userId");
            
            // ✅ FIX: Get the doctor's profile to get the correct doctor_id
            Doctor doctor = doctorDAO.getDoctorByUserId(doctorUserId);
            if (doctor == null) {
                request.setAttribute("error", "Doctor profile not found");
                request.getRequestDispatcher("/doctor/dashboard.jsp").forward(request, response);
                return;
            }
            
            // ✅ Use the correct doctor_id (20) instead of user_id (41)
            int doctorId = doctor.getDoctorId();
            
            // Use the DAO method with the correct doctor_id
            List<Appointment> myAppointments = appointmentDAO.getAppointmentsByDoctorId(doctorId);
            
            request.setAttribute("myAppointments", myAppointments);
            request.getRequestDispatcher("/doctor/dashboard.jsp").forward(request, response);
            
        } catch (DatabaseException e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/doctor/dashboard.jsp").forward(request, response);
        }
    }
}