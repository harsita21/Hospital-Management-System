package com.hospital.servlet;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import com.hospital.exception.DatabaseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/patients")
public class AdminManagePatientsServlet extends HttpServlet {
    
    private PatientDAO patientDAO;
    
    @Override
    public void init() throws ServletException {
        patientDAO = new PatientDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --- Admin Session Check ---
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // --- End Session Check ---
        
        try {
            // 1. Get all patients from the DAO
            List<Patient> patientList = patientDAO.getAllPatients();
            
            // 2. Set the list as an attribute for the JSP
            request.setAttribute("patientList", patientList);
            
            // 3. Forward to the JSP page
            request.getRequestDispatcher("/admin/patients.jsp").forward(request, response);
            
        } catch (DatabaseException e) {
            request.setAttribute("error", "Error loading patients: " + e.getMessage());
            request.getRequestDispatcher("/admin/patients.jsp").forward(request, response);
        }
    }
}
