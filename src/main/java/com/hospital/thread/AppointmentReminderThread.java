package com.hospital.thread;

import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import com.hospital.util.FileUtil;
import java.util.List;
import java.time.LocalDate;

public class AppointmentReminderThread extends Thread {
    private AppointmentDAO appointmentDAO;
    private boolean running = true;
    
    public AppointmentReminderThread() {
        this.appointmentDAO = new AppointmentDAO();
        this.setName("AppointmentReminderThread");
        this.setDaemon(true);
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                // Check for tomorrow's appointments every hour
                List<Appointment> tomorrowAppointments = appointmentDAO.getAppointmentsByDate(
                    LocalDate.now().plusDays(1)
                );
                
                if (!tomorrowAppointments.isEmpty()) {
                    String reminderMessage = generateReminderMessage(tomorrowAppointments);
                    FileUtil.writeToFile("appointment_reminders.txt", reminderMessage);
                    System.out.println("Appointment reminders generated for " + tomorrowAppointments.size() + " patients");
                }
                
                // Sleep for 1 hour
                Thread.sleep(60 * 60 * 1000);
                
            } catch (InterruptedException e) {
                System.out.println("Appointment reminder thread interrupted");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private String generateReminderMessage(List<Appointment> appointments) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Appointment Reminders for ").append(LocalDate.now().plusDays(1)).append(" ===\n");
        
        for (Appointment appointment : appointments) {
            sb.append("Patient: ").append(appointment.getPatientName())
              .append(" | Doctor: ").append(appointment.getDoctorName())
              .append(" | Time: ").append(appointment.getAppointmentTime())
              .append("\n");
        }
        sb.append("Total appointments: ").append(appointments.size()).append("\n\n");
        
        return sb.toString();
    }
    
    public void stopThread() {
        this.running = false;
        this.interrupt();
    }
}