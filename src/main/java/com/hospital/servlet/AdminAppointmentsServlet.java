package com.hospital.servlet;
import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import com.hospital.exception.DatabaseException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/appointments")
public class AdminAppointmentsServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    public void init() { appointmentDAO = new AppointmentDAO(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            List<Appointment> appointmentList = appointmentDAO.getAllAppointments();
            request.setAttribute("appointmentList", appointmentList);
            request.getRequestDispatcher("/admin/appointments.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("error", "Error loading appointments: " + e.getMessage());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
}