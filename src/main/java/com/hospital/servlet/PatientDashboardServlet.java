package com.hospital.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/patient/dashboard")
public class PatientDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --- Patient Session Check ---
        HttpSession session = request.getSession(false);
        if (session == null || !"patient".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // --- End Session Check ---
        
        // You can add logic here to load appointments, etc.
        // For now, just forward to the JSP.
        
        request.getRequestDispatcher("/patient/dashboard.jsp").forward(request, response);
    }
}