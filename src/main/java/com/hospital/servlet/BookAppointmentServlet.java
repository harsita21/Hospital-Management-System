package com.hospital.servlet;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.exception.DatabaseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/patient/book-appointment")
public class BookAppointmentServlet extends HttpServlet {
    
    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    
    @Override
    public void init() throws ServletException {
        doctorDAO = new DoctorDAO();
        appointmentDAO = new AppointmentDAO();
    }

    // This method shows the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"patient".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get all doctors to show in the dropdown
            List<Doctor> doctorList = doctorDAO.getAllDoctors();

            System.out.println("=== DEBUG doGet() ===");
            System.out.println("Number of doctors retrieved: " + doctorList.size());
            for (Doctor doc : doctorList) {
                System.out.println("Doctor - ID: " + doc.getId() + 
                                  ", DoctorID: " + doc.getDoctorId() + 
                                  ", Name: " + doc.getName() + 
                                  ", Specialization: " + doc.getSpecialization());
            }

            request.setAttribute("doctorList", doctorList);
            request.getRequestDispatcher("/patient/book-appointment.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("error", "Could not load doctors: " + e.getMessage());
            request.getRequestDispatcher("/patient/dashboard.jsp").forward(request, response);
        }
    }

    // This method handles the form submission
    // ... inside BookAppointmentServlet.java

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"patient".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Patient patient = (Patient) session.getAttribute("patient");
            
            // --- THIS IS THE FIX ---
            // We use patient.getPatientId() (e.g., 1, 2, 3)
            // NOT patient.getId() (which is the user_id, e.g., 21)
            int patientId = patient.getPatientId();

            System.out.println("=== DEBUG doPost() ===");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patient.getFullName());
            
            // --- END FIX ---
            
            int doctorId = Integer.parseInt(request.getParameter("doctorId")); 
            System.out.println("Doctor ID from form: " + doctorId);
            LocalDate apptDate = LocalDate.parse(request.getParameter("appointmentDate"));
            LocalTime apptTime = LocalTime.parse(request.getParameter("appointmentTime"));
            String reason = request.getParameter("reason");

            System.out.println("Appointment Date: " + apptDate);
            System.out.println("Appointment Time: " + apptTime);
            System.out.println("Reason: " + reason);
            
            Appointment appointment = new Appointment();
            appointment.setPatientId(patientId); // <-- Use the correct patientId
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDate(apptDate);
            appointment.setAppointmentTime(apptTime);
            appointment.setReason(reason);
            
            if (appointmentDAO.createAppointment(appointment)) {
                System.out.println("=== SUCCESS: Appointment created in database ===");
                session.setAttribute("success", "Appointment booked successfully!");
            } else {
                System.out.println("=== FAILED: Appointment not created ===");
                session.setAttribute("error", "Failed to book appointment.");
            }
            response.sendRedirect(request.getContextPath() + "/patient/my-appointments");

        } catch (DatabaseException e) {
            System.out.println("=== DATABASE EXCEPTION: " + e.getMessage() + " ===");
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            doGet(request, response); 
        } catch (Exception e) {
            System.out.println("=== GENERAL EXCEPTION: " + e.getMessage() + " ===");
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            doGet(request, response); 
        }
    }
}