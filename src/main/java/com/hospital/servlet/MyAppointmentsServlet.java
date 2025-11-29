package com.hospital.servlet;
import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Patient;
import com.hospital.exception.DatabaseException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/patient/my-appointments")
public class MyAppointmentsServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    public void init() { appointmentDAO = new AppointmentDAO(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || !"patient".equals(session.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    try {
        // FIX: Get the Patient object and use getPatientId() instead of userId
        Patient patient = (Patient) session.getAttribute("patient");
        int patientId = patient.getPatientId(); // This matches what we inserted
        
        List<Appointment> appointmentList = appointmentDAO.getAppointmentsByPatientId(patientId);
        request.setAttribute("appointmentList", appointmentList);
        request.getRequestDispatcher("/patient/my-appointments.jsp").forward(request, response);
    } catch (DatabaseException e) {
        request.setAttribute("error", "Could not load appointments: " + e.getMessage());
        request.getRequestDispatcher("/patient/dashboard.jsp").forward(request, response);
    }
}
}