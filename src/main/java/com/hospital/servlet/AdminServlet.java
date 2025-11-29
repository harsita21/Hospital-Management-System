package com.hospital.servlet;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.AppointmentDAO;
//import com.hospital.model.Doctor;
//import com.hospital.model.Patient;
import com.hospital.model.Appointment;
import com.hospital.exception.DatabaseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminServlet extends HttpServlet {
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;
    private AppointmentDAO appointmentDAO;
    
    @Override
    public void init() throws ServletException {
        doctorDAO = new DoctorDAO();
        patientDAO = new PatientDAO();
        appointmentDAO = new AppointmentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Get statistics
            int doctorsCount = doctorDAO.getAllDoctors().size();
            int patientsCount = patientDAO.getAllPatients().size();
            int appointmentsCount = appointmentDAO.getAllAppointments().size();
            int todayAppointments = appointmentDAO.getTodaysAppointments().size();
            
            // Get recent appointments
            List<Appointment> recentAppointments = appointmentDAO.getRecentAppointments(5);
            
            // Set attributes for JSP
            request.setAttribute("doctorsCount", doctorsCount);
            request.setAttribute("patientsCount", patientsCount);
            request.setAttribute("appointmentsCount", appointmentsCount);
            request.setAttribute("todayAppointments", todayAppointments);
            request.setAttribute("recentAppointments", recentAppointments);
            
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } catch (DatabaseException e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
}