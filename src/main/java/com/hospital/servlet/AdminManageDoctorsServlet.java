package com.hospital.servlet;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;
import com.hospital.exception.DatabaseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

// This annotation @WebServlet("/admin/doctors") is what fixes the 404 error
@WebServlet("/admin/doctors")
public class AdminManageDoctorsServlet extends HttpServlet {
    
    private DoctorDAO doctorDAO;
    
    @Override
    public void init() throws ServletException {
        doctorDAO = new DoctorDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            List<Doctor> doctorList = doctorDAO.getAllDoctors();
            request.setAttribute("doctorList", doctorList);
            request.getRequestDispatcher("/admin/doctors.jsp").forward(request, response);
            
        } catch (DatabaseException e) {
            request.setAttribute("error", "Error loading doctors: " + e.getMessage());
            // Send back to dashboard with an error
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
}